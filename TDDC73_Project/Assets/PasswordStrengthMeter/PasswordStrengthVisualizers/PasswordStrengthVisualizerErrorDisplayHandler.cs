using System;
using System.Collections.Generic;

public class PasswordStrengthVisualizerErrorDisplayHandler<VISUALIZER_TYPE, ERROR_TYPE> : IPasswordStrengthVisualizerHandler {
    private readonly IPasswordStrengthVisualizerHandler strengthVisualizerHandler;
    private readonly VISUALIZER_TYPE errorVisualizer;
    private readonly PasswordStrengthProviderCriteria<ERROR_TYPE> passwordStrengtProvider;
    private readonly Action<VISUALIZER_TYPE, List<ERROR_TYPE>> visualizePasswordErrors;

    public PasswordStrengthVisualizerErrorDisplayHandler(IPasswordStrengthVisualizerHandler strengthVisualizerHandler, VISUALIZER_TYPE errorVisualizer, PasswordStrengthProviderCriteria<ERROR_TYPE> passwordStrengtProvider, Action<VISUALIZER_TYPE, List<ERROR_TYPE>> visualizePasswordStrength) {
        this.strengthVisualizerHandler = strengthVisualizerHandler;
        this.errorVisualizer = errorVisualizer;
        this.passwordStrengtProvider = passwordStrengtProvider;
        this.visualizePasswordErrors = visualizePasswordStrength;

        VisualizePasswordStrength("");
    }

    public void VisualizePasswordStrength(string password) {
        (float passwordStrength, List<ERROR_TYPE> errorMessages) = passwordStrengtProvider.CalculatePasswordStrength(password);
        strengthVisualizerHandler.VisualizePasswordStrength(passwordStrength);
        visualizePasswordErrors(errorVisualizer, errorMessages);
    }

    public void VisualizePasswordStrength(float strength) {
        strengthVisualizerHandler.VisualizePasswordStrength(strength);
    }
}
