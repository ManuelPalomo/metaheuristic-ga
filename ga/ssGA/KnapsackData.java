package ga.ssGA;

public class KnapsackData {
    private int variableNumber;
    private int constraintNumber;
    private int optimalValue;
    private int[] values;
    private int[][] constraints;
    private int[] constraintLimit;

    public int getVariableNumber() {
        return variableNumber;
    }

    public void setVariableNumber(int variableNumber) {
        this.variableNumber = variableNumber;
    }

    public int getConstraintNumber() {
        return constraintNumber;
    }

    public void setConstraintNumber(int constraintNumber) {
        this.constraintNumber = constraintNumber;
    }

    public int getOptimalValue() {
        return optimalValue;
    }

    public void setOptimalValue(int optimalValue) {
        this.optimalValue = optimalValue;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int[][] getConstraints() {
        return constraints;
    }

    public void setConstraints(int[][] constraints) {
        this.constraints = constraints;
    }

    public int[] getConstraintLimit() {
        return constraintLimit;
    }

    public void setConstraintLimit(int[] constraintLimit) {
        this.constraintLimit = constraintLimit;
    }
}
