using System.Collections.Generic;
using UnityEngine;

public abstract class CarouselContentAdapter<DataType, ContentContainerType> where ContentContainerType : MonoBehaviour {
    public abstract void Populate(DataType data, ContentContainerType container);
    public abstract DataType InversePopulate(ContentContainerType container);
    public abstract IEnumerable<ContentContainerType> Containers { get; }
}
