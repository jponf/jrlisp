package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.data.callable.CallableSExpression;
import cat.udl.eps.butterp.environment.Environment;

import java.util.Iterator;

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
        try {
            CallableSExpression carExpr = (CallableSExpression)this.car.eval(env);
            return carExpr.apply(carExpr.getEvaluationStrategy().evaluateArguments(cdr, env), env);
        } catch (ClassCastException e) {
            throw new EvaluationError(
                    String.format("Error evaluating %s, which is not a callable s-expression",
                            car.toString()));
        }
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
        for (SExpression sexpr : ListOps.iterate(cdr))
            buffer.append(' ').append(sexpr);

        return String.format("(%s)", buffer.toString());
    }
}
