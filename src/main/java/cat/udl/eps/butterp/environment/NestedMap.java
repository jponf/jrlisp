package cat.udl.eps.butterp.environment;

import cat.udl.eps.butterp.data.SExpression;
import cat.udl.eps.butterp.data.Symbol;

import java.util.HashMap;
import java.util.Map;

public class NestedMap implements Environment {

    private Environment parentEnvironment;
    private Map<Symbol, SExpression> mappedSymbols;


    public NestedMap() {
        parentEnvironment = new NullEnvironment(this);
        mappedSymbols = new HashMap<>();
    }

    private NestedMap(Environment parent) {
        parentEnvironment = parent;
        mappedSymbols = new HashMap<>();
    }

    @Override
    public void bindGlobal(Symbol symbol, SExpression value) {
        parentEnvironment.bindGlobal(symbol, value);
    }

    @Override
    public SExpression find(Symbol symbol) {
        if (mappedSymbols.containsKey(symbol))
            return mappedSymbols.get(symbol);

        return parentEnvironment.find(symbol);
    }

    @Override
    public Environment extend() {
        return new NestedMap(this);
    }

    @Override
    public void bind(Symbol symbol, SExpression value) {
        mappedSymbols.put(symbol, value);
    }

}
