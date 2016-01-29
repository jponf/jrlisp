package cat.udl.eps.butterp.main;

import cat.udl.eps.butterp.data.*;
import cat.udl.eps.butterp.data.Integer;
import cat.udl.eps.butterp.environment.Environment;

public class Primitives {

    public static void loadPrimitives(Environment env) {
        env.bindGlobal(Symbol.NIL, Symbol.NIL);
        env.bindGlobal(Symbol.TRUE, Symbol.TRUE);

        loadPrimitiveFunctions(env);
        loadPrimitiveSpecials(env);

        /*

        An example of a predefined Function:

        env.bindGlobal(new Symbol("function"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */

        /*

        An example of a predefined Special:

        env.bindGlobal(new Symbol("special"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                throw new UnsupportedOperationException("not implemented yet");
            }
        });

        */
    }

    /**
     * Loads all the primitive functions into the given environment
     * @param env The environment that will hold the newly defined functions.
     */
    private static void loadPrimitiveFunctions(Environment env) {
        loadPrimitiveArithmeticFunctions(env);
        loadPrimitiveListFunctions(env);
        loadPrimitiveComparisonFunctions(env);
    }

    /**
     * Adds the functions: add and mult
     */
    private static void loadPrimitiveArithmeticFunctions(Environment env) {
        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.isListOf(evargs, Integer.class)) {
                    int sum = 0;
                    for (int i = 0; i < ListOps.length(evargs); ++i) {
                        sum += ((Integer)ListOps.nth(evargs, i)).value;
                    }
                    return new Integer(sum);
                }

                throw new EvaluationError("Invalid add arguments");
            }
        });

        env.bindGlobal(new Symbol("mult"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                if (ListOps.isListOf(evargs, Integer.class)) {
                    int mult = 1;
                    for (int i = 0; i < ListOps.length(evargs); ++i) {
                        mult *= ((Integer)ListOps.nth(evargs, i)).value;
                    }
                    return new Integer(mult);
                }

                throw new EvaluationError("Invalid mult arguments");
            }
        });
    }

    /**
     * Adds the functions: car, cdr, cons and list
     */
    private static void loadPrimitiveListFunctions(Environment env) {
        env.bindGlobal(new Symbol("car"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int nargs = ListOps.length(evargs);
                if (nargs != 1)
                    throw new EvaluationError(String.format("car: expected 1 arguments, %d given", nargs));
                else if (ConsCell.class != ListOps.car(evargs).getClass())
                    throw new EvaluationError("car: the given argument is not a list");

                return ListOps.car(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int nargs = ListOps.length(evargs);
                if (nargs != 1)
                    throw new EvaluationError(String.format("car: expected 1 arguments, %d given", nargs));
                else if (ConsCell.class != ListOps.car(evargs).getClass())
                    throw new EvaluationError("car: the given argument is not a list");

                return ListOps.cdr(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {  // TODO: Simplify
                int nargs = ListOps.length(evargs);
                if (nargs != 2)
                    throw new EvaluationError(String.format("cons: expected 2 arguments, %d given", nargs));

                SExpression firstArg = ListOps.nth(evargs, 0);
                SExpression secondArg = ListOps.nth(evargs, 1);

                if (Symbol.NIL.equals(secondArg)) {
                    return ListOps.list(firstArg);
                } else if (secondArg instanceof ConsCell) {
                    return new ConsCell(ListOps.nth(evargs, 0), secondArg);
                }

                throw new EvaluationError("cons: the second argument must be either nil or a list");
            }
        });

        env.bindGlobal(new Symbol("list"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                return evargs;
            }
        });
    }

    /**
     * Adds the functions: eq
     */
    private static void loadPrimitiveComparisonFunctions(Environment env) {
        env.bindGlobal(new Symbol("eq"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int nargs = ListOps.length(evargs);
                if (nargs != 2)
                    throw new EvaluationError(String.format("cons: expected 2 arguments, %d given", nargs));

                return ListOps.nth(evargs, 0).equals(ListOps.nth(evargs, 1)) ? Symbol.TRUE : Symbol.NIL;
            }
        });
    }


    /**
     * Loads all the primitive special forms into the given environment
     * @param env The environment that will hold the newly defined specials.
     */
    private static void loadPrimitiveSpecials(Environment env) {
        // The declarations is made in alphabetical order

        env.bindGlobal(new Symbol("quote"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                int nargs = ListOps.length(args);
                if (nargs != 1)
                    throw new EvaluationError(String.format("quote: expected 1 argument, received %d", nargs));

                return ListOps.car(args);
            }
        });
    }
}
