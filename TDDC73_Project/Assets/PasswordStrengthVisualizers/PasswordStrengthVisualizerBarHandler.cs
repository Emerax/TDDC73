using UnityEngine;

public class PasswordStrengthVisualizerBarHandler {
    private readonly PasswordStrengthProviderBase<float> provider;
    private readonly PasswordStrengthBarVisualizer visualizer;

    private readonly Color badColor;
    private readonly Color goodColor;

    public PasswordStrengthVisualizerBarHandler(PasswordStrengthProviderBase<float> provider, PasswordStrengthBarVisualizer visualizer, Color badColor, Color goodColor) {
        this.provider = provider;
        this.visualizer = visualizer;
        this.badColor = badColor;
        this.goodColor = goodColor;

        VisualizePasswordStrength("");
    }

    public void VisualizePasswordStrength(string password) {
        float passwordStrength = provider.CalculatePasswordStrength(password);
        visualizer.BarImage.fillAmount = Mathf.Clamp(passwordStrength, 0.05f, 1);
        visualizer.BarImage.color = Color.Lerp(badColor, goodColor, passwordStrength);
    }
}
