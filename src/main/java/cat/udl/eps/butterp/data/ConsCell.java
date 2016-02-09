package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        SExpression carExpr = this.car.eval(env);

        if (carExpr instanceof CallableSExpression) {
            return ((CallableSExpression)carExpr).call(this.cdr, env);
        }

        throw new EvaluationError(
                String.format("Error evaluating %s, which is not a callable s-expression", carExpr.toString()));
    }

    @Override
    public boolean equals(Object o) {  // Auto-generated
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        ConsCell obj = (ConsCell) o;
        return car.equals(obj.car) && cdr.equals(obj.cdr);
    }

    @Override
    public int hashCode() {  // Auto-generated
        return 31 * car.hashCode() + cdr.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(this.car.toString());

        Iterator<SExpression> it = ListOps.createIterator(cdr);
        while (it.hasNext()) {
            buffer.append(" ").append(it.next());
        }

        return String.format("(%s)", buffer.toString());
    }
}
