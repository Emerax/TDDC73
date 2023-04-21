using UnityEngine;

public abstract class PasswordStrengthProviderBase<T> {
    public abstract T CalculatePasswordStrength(string password);
}
