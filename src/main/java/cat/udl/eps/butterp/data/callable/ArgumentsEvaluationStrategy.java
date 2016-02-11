package cat.udl.eps.butterp.data.callable;


import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

public interface ArgumentsEvaluationStrategy {

    SExpression evaluateArguments(SExpression args, Environment env);
}
