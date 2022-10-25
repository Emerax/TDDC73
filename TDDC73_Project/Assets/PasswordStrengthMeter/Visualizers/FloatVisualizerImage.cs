using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class FloatVisualizerImage : PasswordStrengthVisualizerBase<float> {
    [SerializeField]
    private Color badColor;
    [SerializeField]
    private Color goodColor;
    private Image bar;
    void Awake() {
        bar = GetComponentInChildren<Image>();
        Visualize(0f);
    }

    public override void Visualize(float passwordStrength) {
        bar.color = Color.Lerp(badColor, goodColor, passwordStrength);
        bar.fillAmount = passwordStrength;
    }
}
