using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public abstract class PasswordStrengthMeterBase<PasswordStrengthMetricType> : MonoBehaviour {
    [SerializeField]
    protected PasswordStrengthVisualizerBase<PasswordStrengthMetricType> visualizer;

    public void HandlePasswordChange(string password) {
        PasswordStrengthMetricType passwordStrength = CalculateStrength(password);
        visualizer.Visualize(passwordStrength);
    }

    protected abstract PasswordStrengthMetricType CalculateStrength(string password);
} 
