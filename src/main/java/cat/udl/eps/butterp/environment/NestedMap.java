package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.EvaluationError;
import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

import java.util.HashMap;
import java.util.Map;

public class NestedMap implements Environment {

    private Map<Symbol, SExpression> mappedSymbols;

    public NestedMap() {
        mappedSymbols = new HashMap<>();
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        mappedSymbols.put(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        if (mappedSymbols.containsKey(symbol))
            return mappedSymbols.get(symbol);

        throw new EvaluationError(String.format("Symbol %s is not defined", symbol.toString()));
    }

    @Override
    public Environment extend() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
