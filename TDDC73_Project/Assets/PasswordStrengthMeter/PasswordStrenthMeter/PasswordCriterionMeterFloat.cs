public class PasswordCriterionMeterFloat : PasswordCriterionMeterBase<float> {

    protected override float TransformCriteriaPasses(string password) {
        return CalculateStrengthFromCriteriaPasses(password);
    }
}
