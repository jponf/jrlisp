package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public abstract class Special implements CallableSExpression {
    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public SExpression call(SExpression args, Environment env) {
        return applySpecial(args, env);
    }

    public abstract SExpression applySpecial(SExpression args, Environment env);

    @Override
    public String toString() {
        return String.format("<special-%x>", hashCode());
    }
}
