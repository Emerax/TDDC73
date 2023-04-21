using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class PasswordStrengthEntrypoint : MonoBehaviour {
    [SerializeField]
    private TMP_InputField passwordInput;
    [SerializeField]
    private PasswordStrengthBarVisualizer barShort;
    [SerializeField]
    private PasswordStrengthBarVisualizer barLong;
    [SerializeField]
    private PasswordStrengthBarVisualizer barImpossible;

    private PasswordStrengthProviderLength lengthProviderShort;
    private PasswordStrengthProviderLength lengthProviderLong;
    private PasswordStrengthProviderLength lengthProviderImpossible;

    private PasswordStrengthVisualizerBarHandler passwordStrengthVisualizerHandlerShort;
    private PasswordStrengthVisualizerBarHandler passwordStrengthVisualizerHandlerLong;
    private PasswordStrengthVisualizerBarHandler passwordStrengthVisualizerHandlerImpossible;

    private void Awake() {
        lengthProviderShort = new(8, 14);
        lengthProviderLong = new(14, 26);
        lengthProviderImpossible = new(9999);

        passwordStrengthVisualizerHandlerShort = new(lengthProviderShort, barShort, Color.red, Color.green);
        passwordStrengthVisualizerHandlerLong = new(lengthProviderLong, barLong, Color.red, Color.green);
        passwordStrengthVisualizerHandlerImpossible = new(lengthProviderImpossible, barImpossible, Color.red, Color.green);

        passwordInput.onValueChanged.AddListener(passwordStrengthVisualizerHandlerShort.VisualizePasswordStrength);
        passwordInput.onValueChanged.AddListener(passwordStrengthVisualizerHandlerLong.VisualizePasswordStrength);
        passwordInput.onValueChanged.AddListener(passwordStrengthVisualizerHandlerImpossible.VisualizePasswordStrength);
    }
}
