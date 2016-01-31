package cat.udl.eps.butterp.data;


import cat.udl.eps.butterp.environment.Environment;

public final class Real implements SExpression {

    public double value;

    public Real(double value) {
        this.value = value;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

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
