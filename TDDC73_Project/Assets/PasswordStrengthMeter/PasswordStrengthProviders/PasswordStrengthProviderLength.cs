using UnityEngine;

public class PasswordStrengthProviderLength : PasswordStrengthProviderBase<float> {
    private readonly int passLength;
    private readonly int goodLength = -1;

    public PasswordStrengthProviderLength(int passLength) {
        this.passLength = passLength;
    }

    public PasswordStrengthProviderLength(int passLength, int goodLength) {
        this.passLength = passLength;
        this.goodLength = goodLength;
    }

    public override float CalculatePasswordStrength(string password) {
        if (goodLength > passLength) {
            return (float)(password.Length - passLength) / (goodLength - passLength);
        }

        return password.Length >= passLength ? 1 : 0;
    }
}
