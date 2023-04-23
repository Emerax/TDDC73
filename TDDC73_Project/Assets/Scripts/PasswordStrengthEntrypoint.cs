using System.Collections.Generic;
using System.Linq;
using System.Text;
using TMPro;
using UnityEngine;

public class PasswordStrengthEntrypoint : MonoBehaviour {
    [SerializeField]
    private TMP_InputField passwordInput;
    [SerializeField]
    private PasswordStrengthBarVisualizer barLength;
    [SerializeField]
    private PasswordStrengthBarVisualizer barCriteria;
    [SerializeField]
    private PasswordErrorVisualizerTextBox errorBox;

    private readonly List<string> unsafePasswords = new() {
        "password",
        "Password123456",
        "Tr0u64D0uR",
        "CorrectHorseBatteryStaple0"
    };

    private PasswordStrengthProviderLength lengthProvider;
    private PasswordStrengthProviderCriteria<string> criteriaProvider;

    private PasswordStrengthVisualizerHandler<PasswordStrengthBarVisualizer> passwordStrengthVisualizerHandlerLength;
    private PasswordStrengthVisualizerHandler<PasswordStrengthBarVisualizer> passwordStrengthVisualizerHandlerCriteria;
    private PasswordStrengthVisualizerErrorDisplayHandler<PasswordErrorVisualizerTextBox, string> passwordErrorVisualizerHandlerCriteria;

    private void Awake() {
        lengthProvider = new(14, 26);
        criteriaProvider = new PasswordStrengthProviderCriteria<string>()
            .AddCriteria((string password) => password.Length >= 18, "Password must be atleast 18 characters long")
            .AddCriteria((string password) => password.Any(char.IsUpper), "Password must contain atleast one upper case charater")
            .AddCriteria((string password) => password.Any(char.IsLower), "Password must contain atleast one lower case character")
            .AddCriteria((string password) => password.Any(char.IsNumber), "Password must contain atleast one number")
            .AddCriteria((string password) => !unsafePasswords.Contains(password), "That password is too similar to one in a list of known unsafe passwords!")
            .SetRequiredCriteria(3);

        passwordStrengthVisualizerHandlerLength = new(lengthProvider, barLength, (PasswordStrengthBarVisualizer v, float s) => {
            v.BarImage.fillAmount = Mathf.Clamp(s, 0.05f, 1f);
            v.BarImage.color = Color.Lerp(Color.red, Color.green, s);
        });
        passwordStrengthVisualizerHandlerCriteria = new(lengthProvider, barCriteria, (PasswordStrengthBarVisualizer v, float s) => {
            v.BarImage.fillAmount = Mathf.Clamp(s, 0.05f, 1f);
            v.BarImage.color = Color.Lerp(Color.red, Color.green, s);
        });
        passwordErrorVisualizerHandlerCriteria = new(passwordStrengthVisualizerHandlerCriteria, errorBox, criteriaProvider, (PasswordErrorVisualizerTextBox v, List<string> errors) => {
            StringBuilder sb = new();
            foreach(string error in errors) {
                sb.AppendLine(error);
            }

            v.TextField.text = sb.ToString();
        });
        
        passwordInput.onValueChanged.AddListener(passwordStrengthVisualizerHandlerLength.VisualizePasswordStrength);
        passwordInput.onValueChanged.AddListener(passwordErrorVisualizerHandlerCriteria.VisualizePasswordStrength);
    }
}
