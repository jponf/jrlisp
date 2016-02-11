package cat.udl.eps.butterp.data.callable;

import cat.udl.eps.butterp.data.ConsCell;
import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;
import cat.udl.eps.butterp.environment.Environment;

public class EvaluateAllArgumentsStrategy implements ArgumentsEvaluationStrategy {

    @Override
    public SExpression evaluateArguments(SExpression args, Environment env) {
        if (Symbol.NIL.equals(args)) {
            return args;
        }

        return new ConsCell(ListOps.car(args).eval(env), evaluateArguments(ListOps.cdr(args), env));
    }
}
