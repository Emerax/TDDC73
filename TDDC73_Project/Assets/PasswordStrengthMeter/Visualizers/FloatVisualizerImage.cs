using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class FloatVisualizerImage : MonoBehaviour, IPasswordStrengthVisualizer<float> {
    [SerializeField]
    private Color badColor;
    [SerializeField]
    private Color goodColor;
    private Image bar;
    void Awake() {
        bar = GetComponentInChildren<Image>();
        VisualizePasswordStrength(0f);
    }

    public void VisualizePasswordStrength(float passwordStrength) {
        bar.color = Color.Lerp(badColor, goodColor, passwordStrength);
        bar.fillAmount = passwordStrength;
    }
}
