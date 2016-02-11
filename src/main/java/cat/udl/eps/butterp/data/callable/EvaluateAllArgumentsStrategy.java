package cat.udl.eps.butterp.data.callable;

import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EvaluateAllArgumentsStrategy implements ArgumentsEvaluationStrategy {

    @Override
    public SExpression evaluateArguments(SExpression args, Environment env) {
        List<SExpression> evargs = new ArrayList<>();
        Iterator<SExpression> it = ListOps.createIterator(args);
        while (it.hasNext()) {
            evargs.add(it.next().eval(env));
        }

        return ListOps.list(evargs);
    }
}
