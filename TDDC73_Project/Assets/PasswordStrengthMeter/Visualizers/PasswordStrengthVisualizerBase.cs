using System;
using UnityEngine;

public abstract class PasswordStrengthVisualizerBase<PasswordStrengthMetricType> : MonoBehaviour {
    public abstract void Visualize(PasswordStrengthMetricType passwordStrength);
}