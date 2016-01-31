package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public final class Integer implements BaseNumber {

    public final int value;  // Si el definiu privat caldrà un getter

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override public BaseNumber add(BaseNumber operand) { return operand.add(this); }
    @Override public BaseNumber add(Integer operand)    { return new Integer(value + operand.value); }
    @Override public BaseNumber add(Real operand)       { return new Real(value + operand.value); }

    @Override public BaseNumber multiply(BaseNumber operand) { return operand.multiply(this); }
    @Override public BaseNumber multiply(Integer operand)    { return new Integer(value * operand.value); }
    @Override public BaseNumber multiply(Real operand)       { return new Real(value * operand.value); }

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
