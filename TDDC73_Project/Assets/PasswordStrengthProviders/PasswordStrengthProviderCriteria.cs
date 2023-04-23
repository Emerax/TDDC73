using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public class PasswordStrengthProviderCriteria<ERROR_TYPE> : PasswordStrengthProviderBase<(float passwordStrength, List<ERROR_TYPE> errorMessages)> {
    private readonly List<PasswordCriteria<ERROR_TYPE>> criteriaList = new();

    private int requiredCriteria = -1;
    private string lastPassword = "";

    public PasswordStrengthProviderCriteria() {
        CalculatePasswordStrength("");
    }

    public override (float passwordStrength, List<ERROR_TYPE> errorMessages) CalculatePasswordStrength(string password) {
        lastPassword = password;

        if (criteriaList.Count == 0) {
            return (0, new List<ERROR_TYPE>());
        }
        List<ERROR_TYPE> failedCriteria = criteriaList
            .Where(c => !c.Passes(password))
            .Select(c => c.ErrorMessage)
            .Where(e => e != null)
            .ToList();

        return (CalculatePasswordStrengthInternal(failedCriteria.Count), failedCriteria);
    }

    public PasswordStrengthProviderCriteria<ERROR_TYPE> AddCriteria(Func<string, bool> passFunction) {
        PasswordCriteria<ERROR_TYPE> criteria = new(passFunction);
        AddPasswordCriteriaInternal(criteria);
        return this;
    }

    public PasswordStrengthProviderCriteria<ERROR_TYPE> AddCriteria(Func<string, bool> passFunction, ERROR_TYPE errorMessage) {
        PasswordCriteria<ERROR_TYPE> criteria = new(passFunction, errorMessage);
        AddPasswordCriteriaInternal(criteria);
        return this;
    }

    public PasswordStrengthProviderCriteria<ERROR_TYPE> SetRequiredCriteria(int requiredCriteria) {
        if (requiredCriteria > criteriaList.Count) {
            throw new ArgumentException($"Tried to set {requiredCriteria} required criteria but only {criteriaList.Count} have been added!");
        }

        this.requiredCriteria = requiredCriteria;
        return this;
    }

    public PasswordStrengthProviderCriteria<ERROR_TYPE> SetRequiredCriteria(float passRatio) {
        int requiredCriteria = Mathf.FloorToInt((float)criteriaList.Count * passRatio);
        return SetRequiredCriteria(requiredCriteria);
    }

    private float CalculatePasswordStrengthInternal(int numberOfFailedCriteria) {
        if (requiredCriteria == -1 || requiredCriteria == criteriaList.Count) {
            //Require all
            return 1 - numberOfFailedCriteria;
        }

        int successes = criteriaList.Count - numberOfFailedCriteria;
        float strength = (float)(successes - requiredCriteria) / (criteriaList.Count - requiredCriteria);
        return strength;
    }

    private void AddPasswordCriteriaInternal(PasswordCriteria<ERROR_TYPE> criteria) {
        criteriaList.Add(criteria);
        CalculatePasswordStrength(lastPassword);
    }

    private class PasswordCriteria<T> {
        private readonly Func<string, bool> passFunction;
        private readonly T errorMessage;
        public T ErrorMessage => errorMessage;

        public bool Passes(string password) => passFunction(password);

        public PasswordCriteria(Func<string, bool> passFunction) {
            this.passFunction = passFunction;
        }

        public PasswordCriteria(Func<string, bool> passFunction, T errorMessage) {
            this.passFunction = passFunction;
            this.errorMessage = errorMessage;
        }
    }
}
