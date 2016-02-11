package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import java.util.Iterator;

public class Lambda extends Function {

    private SExpression params;
    private SExpression body;
    private Environment definitionEnv;

    /**
     * Creates a new lambda function.
     * @param params The list of symbols that represent the parameters of the function.
     * @param body The body of the function that this lambda will evaluate.
     * @param definitionEnv The environment where this lambda was created.
     */
    public Lambda(SExpression params, SExpression body, Environment definitionEnv) {
        assert ListOps.isListOf(params, Symbol.class);  // disabled by default (debug only)
        this.params = params;
        this.body = body;
        this.definitionEnv = definitionEnv;

        checkParameters();
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        Environment localEnvironment = definitionEnv.extend();

        processParams(localEnvironment, params, evargs);
        return body.eval(localEnvironment);
    }

    @Override
    public String toString() {
        return String.format("<lambda-function-%x | parameters: %s>", hashCode(), params.toString());
    }

    private void checkParameters() {
        boolean variadic = false;

        Iterator<Symbol> it = ListOps.createIterator(params);
        while (it.hasNext() && !variadic) {
            if (Symbol.AND.equals(it.next())) {
                variadic = true;
                if (!it.hasNext()) {
                    throw new EvaluationError(String.format("%s: expected parameter name after &", toString()));
                }
            }
        }

        if (variadic) {
            if (Symbol.AND.equals(it.next())) {
                throw new EvaluationError(String.format("%s: %s cannot be used as the variadic parameter name",
                        toString(), Symbol.AND.toString()));
            }
            if (it.hasNext()) {
                throw new EvaluationError(String.format("%s: variadic parameter must be the last one", toString()));
            }
        }
    }

    private void processParams(Environment localEnv, SExpression params, SExpression args) {
        if (!Symbol.NIL.equals(params)) {
            Symbol p = ListOps.car(params);
            if (Symbol.AND.equals(p)) {
                processParams(localEnv, ListOps.cdr(params), ListOps.cons(args, Symbol.NIL));
            } else if (Symbol.NIL.equals(args)) {
                throw new EvaluationError(String.format("%s: incorrect number of arguments", toString()));
            } else {
                localEnv.bind(p, ListOps.car(args));
                processParams(localEnv, ListOps.cdr(params), ListOps.cdr(args));
            }
        } else if (!Symbol.NIL.equals(args)) {
            throw new EvaluationError(String.format("%s: incorrect number of arguments", toString()));
        }
    }
}
