using System.Collections.Generic;

public interface IPasswordStrengthVisualizer<PasswordStrengthMetricType> {
    public void VisualizePasswordStrength(PasswordStrengthMetricType passwordStrength, List<string> failedCriteria);
}