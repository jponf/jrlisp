package cat.udl.eps.butterp.data.callable;


import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.environment.Environment;

public interface CallableSExpression extends SExpression {

    ArgumentsEvaluationStrategy getEvaluationStrategy();

    SExpression apply(SExpression args, Environment env);
}

