package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

public final class Symbol implements SExpression {

    public static final Symbol TRUE = new Symbol("t");
    public static final Symbol NIL = new Symbol("nil");

    public final String name; // Si el definiu privat caldrà un getter

    public Symbol(String name) {
        this.name = name;
    }

    @Override
    public SExpression eval(Environment env) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Symbol && ((Symbol)o).name.equals(name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
