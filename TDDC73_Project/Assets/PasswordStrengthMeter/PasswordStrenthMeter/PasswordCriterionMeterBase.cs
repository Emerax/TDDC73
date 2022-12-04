using System;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;

public abstract class PasswordCriterionMeterBase<T> : MonoBehaviour {
    [SerializeField]
    protected IPasswordStrengthVisualizer<T> visualizer;

    protected class Criteria{
        public string Description { get; private set; }
        public bool Passes(string password) => check(password);
        private readonly Func<string, bool> check;

        public Criteria(Func<string, bool> checkFunction, string description) {
            check = checkFunction;
            Description = description;
        }
    }

    /// <summary>
    /// On passworkd failure, either report all password criteria, or only the ones that are currently failed.
    /// </summary>
    [SerializeField]
    protected bool onlyReportFailedCriteria;

    /// <summary>
    /// This is -1 if it hasn't been overwritten, and means that all criteria must pass.
    /// </summary>
    protected int requiredPasses = -1;

    protected readonly List<Criteria> criterion = new();

    public void HandlePasswordChange(string password) {
        float criteriaPassRatio = CalculateStrengthFromCriteriaPasses(password, out List<string> failedCriteria);
        T passwordStrength = TransformCriteriaPasses(criteriaPassRatio);
        visualizer.VisualizePasswordStrength(passwordStrength, failedCriteria);
    }

    public PasswordCriterionMeterBase<T> AddCriteria(Func<string, bool> criteria, string description) {
        criterion.Add(new Criteria(criteria, description));
        return this;
    }

    /// <summary>
    /// Returns a value between 0 and 1 corresponding to the number of passed criteria.
    /// </summary>
    private float CalculateStrengthFromCriteriaPasses(string password, out List<string> failedCriteria) {
        failedCriteria = criterion.Where(c => c.Passes(password)).Select(c => c.Description).ToList();
        int passes = criterion.Count - failedCriteria.Count;
        if (requiredPasses == -1) {
            return passes == requiredPasses ? 1.0f : 0f;
        }

        return (passes - requiredPasses) / (criterion.Count - requiredPasses);
    }

    /// <summary>
    /// This method is used to transform the ratio of passed criteria (given as a [0, 1] float) into
    /// a subclass' chosen return type.
    /// </summary>
    protected abstract T TransformCriteriaPasses(float passWordStrength);
}