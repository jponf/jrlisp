package cat.udl.eps.butterp.data.callable;

import cat.udl.eps.butterp.data.ListOps;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.callable.CallableSExpression;
import cat.udl.eps.butterp.environment.Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Function implements CallableSExpression {

    public final static ArgumentsEvaluationStrategy argsEvalStrategy = new EvaluateAllArgumentsStrategy();

    @Override
    public ArgumentsEvaluationStrategy getEvaluationStrategy() {
        return argsEvalStrategy;
    }

    @Override
    public SExpression eval(Environment env) {
        return this;
    }

    public abstract SExpression apply(SExpression evargs, Environment env);

    @Override
    public String toString() {
        return String.format("<function-%x>", hashCode());
    }
}
