package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public final class Integer implements SExpression {

    public final int value;  // Si el definiu privat caldrà un getter

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

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
