package cat.udl.eps.butterp.environment;


import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

/**
 * Null pattern applied to evaluation environments.
 *
 * If no parent environment is defined, it must automatically be NullEnvironment, which
 * always fails on find and its globalBind refers to its child bind.
 */
public final class NullEnvironment implements Environment {

    private Environment nestedEnvironment;

    public NullEnvironment(Environment nested) {
        assert !(nested instanceof  NullEnvironment);
        nestedEnvironment = nested;
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("NullEnvironment cannot bind symbols");
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        nestedEnvironment.bind(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        throw new EvaluationError(String.format("Symbol %s is not defined", symbol.toString()));
    }

    @Override
    public Environment extend() {
        throw new UnsupportedOperationException("NullEnvironment is not extensible");
    }
}
