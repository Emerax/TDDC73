using System.Collections.Generic;
using UnityEngine;

public class CarouselEntrypoint : MonoBehaviour {
    [SerializeField]
    private DragEventCarouselRefs editorPopulatedCarouselRefs;
    [SerializeField]
    private DragEventCarouselRefs inversePopulatedCarouselRefs;

    private CarouselContentActionAdapter<Color, SimpleCarouselContainerRefs> simpleCarouselAdapter;
    private DragEventCarousel<Color, SimpleCarouselContainerRefs> editorPopulatedCarousel;
    private DragEventCarousel<Color, SimpleCarouselContainerRefs> inversePopulatedCarousel;

    private readonly List<Color> colors = new();

    private void Awake() {
        simpleCarouselAdapter = new((Color c, SimpleCarouselContainerRefs container) => {
            container.Image.color = c;
        },
        (SimpleCarouselContainerRefs container) => {
            return container.Image.color;
        });
        editorPopulatedCarousel = new(simpleCarouselAdapter, editorPopulatedCarouselRefs);
    }
}
