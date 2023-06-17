using System;

public class PasswordStrengthVisualizerHandler<VISUALIZER_TYPE> : IPasswordStrengthVisualizerHandler {
    private readonly PasswordStrengthProviderBase<float> provider;
    private readonly VISUALIZER_TYPE visualizer;
    private readonly Action<VISUALIZER_TYPE, float> visualizePasswordStrength;

    public PasswordStrengthVisualizerHandler(PasswordStrengthProviderBase<float> provider, VISUALIZER_TYPE visualizer, Action<VISUALIZER_TYPE, float> visualizePasswordStrength) {
        this.provider = provider;
        this.visualizer = visualizer;
        this.visualizePasswordStrength = visualizePasswordStrength;

        VisualizePasswordStrength("");
    }

    public void VisualizePasswordStrength(string password) {
        float passwordStrength = provider.CalculatePasswordStrength(password);
        visualizePasswordStrength(visualizer, passwordStrength);
    }

    public void VisualizePasswordStrength(float strength) {
        visualizePasswordStrength(visualizer, strength);
    }
}
