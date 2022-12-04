using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NewBehaviourScript<MetricType> : MonoBehaviour, IPasswordStrengthVisualizer<MetricType> {
    public List<IPasswordStrengthVisualizer<MetricType>> visualizers = new();

    public void VisualizePasswordStrength(MetricType passwordStrength) {
        foreach(IPasswordStrengthVisualizer<MetricType> visualizer in visualizers) {
            visualizer.VisualizePasswordStrength(passwordStrength);
        }
    }
}
