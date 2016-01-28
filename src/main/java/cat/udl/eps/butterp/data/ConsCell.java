package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public final class ConsCell implements SExpression {

    public final SExpression car;
    public final SExpression cdr;

    public ConsCell(SExpression car, SExpression cdr) {
        assert car != null;
        assert cdr != null;
        this.car = car;
        this.cdr = cdr;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    @Override
    public boolean equals(Object o) {  // Auto-generated
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ConsCell obj = (ConsCell) o;  // car cannot be null, cdr can
        return car.equals(obj.car) && cdr.equals(obj.cdr);
    }

    @Override
    public int hashCode() {  // Auto-generated
        return 31 * car.hashCode() + cdr.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(this.car.toString());

        SExpression currentCDR = ListOps.cdr(this);
        while (!Symbol.NIL.equals(currentCDR)) {
            buffer.append(" ").append(ListOps.car(currentCDR));
            currentCDR = ListOps.cdr(currentCDR);
        }

        return String.format("(%s)", buffer.toString());
    }
}
