package cat.udl.eps.butterp.data;

import cat.udl.eps.butterp.environment.Environment;

import java.util.Iterator;

public class Lambda extends Function {

    private SExpression params;
    private int numParams;
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

        Iterator<SExpression> argsIt = ListOps.createIterator(evargs);
        Iterator<SExpression> paramsIt = ListOps.createIterator(params);
        while (argsIt.hasNext() && paramsIt.hasNext()) {
            localEnvironment.bind((Symbol)paramsIt.next(), argsIt.next());
        }

        return body.eval(localEnvironment);
    }

    @Override
    public String toString() {
        return String.format("<lambda-function-%x>", hashCode());
    }
}
