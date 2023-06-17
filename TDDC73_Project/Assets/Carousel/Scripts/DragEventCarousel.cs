using UnityEngine;
using UnityEngine.EventSystems;

public class DragEventCarousel : Carousel, IBeginDragHandler, IDragHandler {
    private Vector2 velocity = Vector2.zero;
    private Vector2 previousPointerPos = default;

    public void OnBeginDrag(PointerEventData eventData) {
        previousPointerPos = eventData.position;
    }

    public void OnDrag(PointerEventData eventData) {
        Vector2 dragDelta = eventData.position - previousPointerPos;
        Vector2 targetPos = new(ContentRoot.anchoredPosition.x + dragDelta.x, ContentRoot.anchoredPosition.y);
        //TODO: Clamp!
        ContentRoot.anchoredPosition = Vector2.SmoothDamp(ContentRoot.anchoredPosition, targetPos, ref velocity, 0.003f);
        previousPointerPos = eventData.position;
    }
}
