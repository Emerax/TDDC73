using TMPro;
using UnityEngine;

public class TextVisualizer : MonoBehaviour, IPasswordStrengthVisualizer<object> {
    private TMP_Text text;

    private void Awake() {
        text = GetComponent<TMP_Text>();
    }

    public void VisualizePasswordStrength(object passwordStrength) {
        text.text = $"{passwordStrength}";
    }
}
