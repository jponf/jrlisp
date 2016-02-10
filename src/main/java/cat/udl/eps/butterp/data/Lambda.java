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
    }

    @Override
    public SExpression apply(SExpression evargs, Environment callingEnv) {
        Environment localEnvironment = definitionEnv.extend();

        /*Iterator<SExpression> argsIt = ListOps.createIterator(evargs);
        Iterator<Symbol> paramsIt = ListOps.createIterator(params);
        while (argsIt.hasNext() && paramsIt.hasNext()) {
            localEnvironment.bind(paramsIt.next(), argsIt.next());
        }

        if (argsIt.hasNext() || paramsIt.hasNext())
            throw new EvaluationError(String.format("%s: not enought arguments provided", toString()));*/
        processParams(localEnvironment, params, evargs);
        return body.eval(localEnvironment);
    }

    @Override
    public String toString() {
        return String.format("<lambda-function-%x | parameters: %s>", hashCode(), params.toString());
    }

    private void processParams(Environment localEnv, SExpression params, SExpression args) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
