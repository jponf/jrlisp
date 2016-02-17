package cat.udl.eps.butterp.data.numbers;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

public final class Integer implements BaseNumber {

    public final int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override public BaseNumber add(BaseNumber operand) { return operand.addTo(this); }
    @Override public BaseNumber addTo(Integer operand)  { return new Integer(operand.value + value); }
    @Override public BaseNumber addTo(Real operand)     { return new Real(operand.value + value); }

    @Override public BaseNumber subtract(BaseNumber operand) { return operand.subtractTo(this); }
    @Override public BaseNumber subtractTo(Integer operand)  { return new Integer(operand.value - value); }
    @Override public BaseNumber subtractTo(Real operand)     { return new Real(operand.value - value); }

    @Override public BaseNumber multiply(BaseNumber operand) { return operand.multiplyTo(this); }
    @Override public BaseNumber multiplyTo(Integer operand)  { return new Integer(operand.value * value); }
    @Override public BaseNumber multiplyTo(Real operand)     { return new Real(operand.value * value); }

    @Override public BaseNumber divide(BaseNumber operand) { return operand.divideTo(this); }
    @Override public BaseNumber divideTo(Integer operand)  { return new Integer(operand.value / value); }
    @Override public BaseNumber divideTo(Real operand)     { return new Real(operand.value / value); }

    @Override
    public boolean equals(Object o) {
        return  o instanceof Integer && ((Integer)o).value == value;
    }

    @Override
    public int hashCode() {
        return value;  // Since the return is an int, we can use its actual value.
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
