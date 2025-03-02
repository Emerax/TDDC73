using UnityEngine;
using UnityEngine.UI;

public class SimpleCarouselContainerRefs : MonoBehaviour, IRectTransformAccessor {
    [SerializeField]
    private RectTransform rectTransform;
    [SerializeField]
    private Image image;

    public RectTransform RectTransform => rectTransform;
    public Image Image { get => image; }
}
