using System.Collections.Generic;
using UnityEngine;

public abstract class Carousel<DataType, ContainerType> where ContainerType : MonoBehaviour {
    protected readonly CarouselContentAdapter<DataType, ContainerType> contentAdapter;

    protected readonly List<ContainerType> children = new();

    public Carousel(CarouselContentAdapter<DataType, ContainerType> contentAdapter) {
        this.contentAdapter = contentAdapter;
    }
}
