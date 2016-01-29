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
        SExpression symValue = this.car.eval(env);

        if (symValue instanceof Function) {
            return evaluateFunction((Function)symValue, env);
        } else if (symValue instanceof Special) {
            return ((Special)symValue).applySpecial(this.cdr, env);
        }

        throw new EvaluationError(
                String.format("Error evaluating %s, which is nor a function nor a special", symValue.toString()));
    }

    private SExpression evaluateFunction(Function function, Environment env) {
        List<SExpression> evargs = new ArrayList<>();
        Iterator<SExpression> it = ListOps.createIterator(this.cdr);
        while (it.hasNext()) {
            evargs.add(it.next().eval(env));
        }

        return function.apply(ListOps.list(evargs), env);
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

        SExpression currentCDR = cdr;
        while (!Symbol.NIL.equals(currentCDR)) {
            buffer.append(" ").append(ListOps.car(currentCDR));
            currentCDR = ListOps.cdr(currentCDR);
        }

        return String.format("(%s)", buffer.toString());
    }
}
