package cat.udl.eps.butterp.data;


import cat.udl.eps.butterp.environment.Environment;

public interface CallableSExpression extends SExpression {

    SExpression call(SExpression evargs, Environment env);
}
