using System.Collections;
using System.Linq;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class DragEventCarousel<DataType, ContainerType> : Carousel<DataType, ContainerType> where ContainerType : MonoBehaviour, IRectTransformAccessor {
    private readonly RectTransform contentRoot;
    private readonly HorizontalLayoutGroup contentLayoutGroup;
    private readonly RectTransform contentMask;
    private readonly AnimationCurve bounceCurve;
    private readonly float bounceDuration;
    private readonly DragEventCarouselRefs carouselRefs;

    private float previousPointerX;

    public DragEventCarousel(CarouselContentAdapter<DataType, ContainerType> contentAdapter, DragEventCarouselRefs carouselRefs) : base(contentAdapter) {
        contentRoot = carouselRefs.ContentRoot;
        contentLayoutGroup = carouselRefs.ContentLayoutGroup;
        contentMask = carouselRefs.ContentMask;
        bounceCurve = carouselRefs.BounceBackCurve;
        bounceDuration = carouselRefs.BounceDuration;

        carouselRefs.beginDragEvent += HandleBeginDrag;
        carouselRefs.onDragEvent += HandleOnDrag;
        carouselRefs.endDragEvent += HandleEndDrag;

        this.carouselRefs = carouselRefs;
    }

    private void HandleBeginDrag(PointerEventData eventData) {
        if(children.Count <= 0) {
            return;
        }

        carouselRefs.StopAllCoroutines();
        previousPointerX = eventData.position.x;
    }

    private void HandleOnDrag(PointerEventData eventData) {
        if(children.Count <= 0) {
            return;
        }

        float deltaX = eventData.position.x - previousPointerX;
        float x = contentRoot.anchoredPosition.x + deltaX;
        float y = contentRoot.anchoredPosition.y;

        contentRoot.anchoredPosition = new Vector2(x, y);

        previousPointerX = eventData.position.x;
    }

    private void HandleEndDrag(PointerEventData eventData) {
        if(children.Count <= 0) {
            return;
        }

        if(TryShouldClampContentPosition(out float targetX)) {
            carouselRefs.StopAllCoroutines();
            carouselRefs.StartCoroutine(AnimateClamp(targetX, bounceDuration));
        }
    }

    private bool TryShouldClampContentPosition(out float targetX) {
        targetX = 0f;
        if(contentRoot.anchoredPosition.x >= 0) {
            targetX = 0;
            return true;
        }

        RectTransform lastChild = children.LastOrDefault().RectTransform;
        float maskWidth = contentMask.rect.xMax * 2 - contentLayoutGroup.spacing;
        float contentWidth = lastChild.anchoredPosition.x + lastChild.rect.xMax;

        //No need to clamp if everything fits with anchoredPos.x == 0
        if(contentWidth <= maskWidth) {
            return false;
        }

        float lastChildMaxX = contentRoot.anchoredPosition.x + contentWidth;
        if(lastChildMaxX <= maskWidth) {
            float deltaX = maskWidth - lastChildMaxX;
            targetX = contentRoot.anchoredPosition.x + deltaX;
            return true;
        }

        return false;
    }

    private IEnumerator AnimateClamp(float targetX, float animationDuration) {
        float elapsed = 0f;
        yield return null;

        do {
            elapsed += Time.deltaTime;
            float normalizedDeltaTime = elapsed / animationDuration;
            float t = bounceCurve.Evaluate(normalizedDeltaTime);
            float x = Mathf.LerpUnclamped(contentRoot.anchoredPosition.x, targetX, t);

            contentRoot.anchoredPosition = new Vector2(x, contentRoot.anchoredPosition.y);
            yield return null;
        }
        while(elapsed <= animationDuration);
    }

    protected override void GetChildContainers() {
        foreach(RectTransform child in carouselRefs.ContentRoot) {
            if(child.TryGetComponent(out ContainerType container)) {
                children.Add(container);
            }
        }
    }
}
