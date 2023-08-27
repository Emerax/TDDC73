using System;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class DragEventCarouselRefs : MonoBehaviour, IBeginDragHandler, IDragHandler, IEndDragHandler {
    [SerializeField]
    private RectTransform contentMask;
    [SerializeField]
    private RectTransform contentRoot;
    [SerializeField]
    private HorizontalLayoutGroup contentLayoutGroup;
    [SerializeField]
    private AnimationCurve bounceBackCurve;
    [SerializeField]
    private float bounceDuration;

    public Action<PointerEventData> beginDragEvent;
    public Action<PointerEventData> onDragEvent;
    public Action<PointerEventData> endDragEvent;

    public RectTransform ContentRoot { get => contentRoot; }
    public HorizontalLayoutGroup ContentLayoutGroup { get => contentLayoutGroup; }
    public RectTransform ContentMask { get => contentMask; }
    public AnimationCurve BounceBackCurve { get => bounceBackCurve; }
    public float BounceDuration { get => bounceDuration; }

    private void Awake() {
        Debug.Log($"Content root null? {contentRoot == null}");
    }

    public void OnBeginDrag(PointerEventData eventData) {
        beginDragEvent?.Invoke(eventData);
    }

    public void OnDrag(PointerEventData eventData) {
        onDragEvent?.Invoke(eventData);
    }

    public void OnEndDrag(PointerEventData eventData) {
        endDragEvent?.Invoke(eventData);
    }
}
