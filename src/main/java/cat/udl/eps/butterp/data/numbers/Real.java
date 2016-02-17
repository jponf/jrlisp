package cat.udl.eps.butterp.data.numbers;


import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

import java.lang.*;

public final class Real implements BaseNumber {

    public double value;

    public Real(double value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override public BaseNumber add(BaseNumber operand) { return operand.addTo(this); }
    @Override public BaseNumber addTo(Integer operand)  { return new Real(value + operand.value); }
    @Override public BaseNumber addTo(Real operand)     { return new Real(value + operand.value); }

    @Override public BaseNumber subtract(BaseNumber operand) { return operand.subtractTo(this); }
    @Override public BaseNumber subtractTo(Integer operand)  { return new Real(operand.value - value); }
    @Override public BaseNumber subtractTo(Real operand)     { return new Real(operand.value - value); }

    @Override public BaseNumber multiply(BaseNumber operand) { return operand.multiplyTo(this); }
    @Override public BaseNumber multiplyTo(Integer operand)    { return new Real(value * operand.value); }
    @Override public BaseNumber multiplyTo(Real operand)       { return new Real(value * operand.value); }

    @Override public BaseNumber divide(BaseNumber operand) { return operand.divideTo(this); }
    @Override public BaseNumber divideTo(Integer operand)  { return new Real(operand.value / value); }
    @Override public BaseNumber divideTo(Real operand)     { return new Real(operand.value / value); }

    @Override
    public boolean equals(Object o) {
        return o instanceof Real && ((Real)o).value == value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
