using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PasswordLengthFloatMeter : PasswordStrengthMeterBase<float> {
    [SerializeField]
    private int minLength;
    [SerializeField]
    private int okLength;
    [SerializeField]
    private int greatLength;

    private void Awake() {
        if (minLength > okLength || okLength > greatLength) {
            Debug.LogError("Password length preferences must be strictly increasing!");
        }
    }

    protected override float CalculateStrength(string password) {
        int length = password.Length;
        if (length < minLength) {
            return 0;
        }
        int extra = length - minLength;
        int range = greatLength - okLength;
        return Mathf.Clamp((float)extra / range, 0f, 1f);
    }
}
