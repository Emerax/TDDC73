public interface IPasswordStrengthVisualizerHandler {
    /// <summary>
    /// Calculate and visualize the strength of the given <paramref name="password"/>-
    /// </summary>
    public void VisualizePasswordStrength(string password);
    /// <summary>
    /// Visualize the given strength immediately.
    /// Used to allow compund visualizers to "pass their work" to child-visualizers.
    /// </summary>
    public void VisualizePasswordStrength(float passwordStrength);
}
