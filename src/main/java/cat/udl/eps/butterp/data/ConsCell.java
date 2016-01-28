package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public final class ConsCell implements SExpression {

    public final SExpression car;
    public final SExpression cdr;

    public ConsCell(SExpression car, SExpression cdr) {
        assert car != null;
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object o) {  // Auto-generated
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ConsCell obj = (ConsCell) o;  // car cannot be null, cdr can
        return car.equals(obj.car) && (cdr != null ? cdr.equals(obj.cdr) : obj.cdr == null);
    }

    @Override
    public int hashCode() {  // Auto-generated
        return 31 * car.hashCode() + (cdr != null ? cdr.hashCode() : 0);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("not implemented yed.");
    }
}
