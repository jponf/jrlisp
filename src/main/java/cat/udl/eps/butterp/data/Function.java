package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Function implements CallableSExpression {

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public SExpression call(SExpression args, Environment env) {
        List<SExpression> evargs = new ArrayList<>();
        Iterator<SExpression> it = ListOps.createIterator(args);
        while (it.hasNext()) {
            evargs.add(it.next().eval(env));
        }

        return apply(ListOps.list(evargs), env);
    }

    public abstract SExpression apply(SExpression evargs, Environment env);

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
