package cat.udl.eps.butterp.data.callable;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

public abstract class Special implements CallableSExpression {

    private final ArgumentsEvaluationStrategy argsEvalStrategy = new DoNotEvaluatedArgumentsStrategy();

    @Override
    public ArgumentsEvaluationStrategy getEvaluationStrategy() {
        return argsEvalStrategy;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression apply(SExpression args, Environment env);

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
