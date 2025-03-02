using System;
using System.Collections.Generic;
using UnityEngine;

public class CarouselContentActionAdapter<DataType, ContainerType> : CarouselContentAdapter<DataType, ContainerType> where ContainerType : MonoBehaviour {
    private readonly Action<DataType, ContainerType> populateAction;
    private readonly Func<ContainerType, DataType> inversePopulateAction;

    public CarouselContentActionAdapter(Action<DataType, ContainerType> populateAction, Func<ContainerType, DataType> inversePopulateAction) {
        this.populateAction = populateAction;
        this.inversePopulateAction = inversePopulateAction;
    }

    public override IEnumerable<ContainerType> Containers => throw new NotImplementedException();

    public override DataType InversePopulate(ContainerType container) {
        return inversePopulateAction(container);
    }

    public override void Populate(DataType data, ContainerType container) {
        populateAction(data, container);
    }
}
