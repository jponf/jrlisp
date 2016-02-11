package cat.udl.eps.butterp.data.callable;


import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

public class DoNotEvaluatedArgumentsStrategy implements ArgumentsEvaluationStrategy {

    @Override
    public SExpression evaluateArguments(SExpression args, Environment env) {
        return args;
    }
}
