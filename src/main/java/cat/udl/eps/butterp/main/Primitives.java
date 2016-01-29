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
    }

    /**
     * Loads all the primitive functions into the given environment
     * @param env The environment that will hold the newly defined functions.
     */
    private static void loadPrimitiveFunctions(Environment env) {
        loadPrimitiveArithmeticFunctions(env);
        loadPrimitiveListFunctions(env);
        loadPrimitiveComparisonFunctions(env);
        loadPrimitiveEvalAndApplyFunctions(env);
    }

    /**
     * Adds the functions: add and mult
     */
    private static void loadPrimitiveArithmeticFunctions(Environment env) {
        env.bindGlobal(new Symbol("add"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int nargs = ListOps.length(evargs);
                if (nargs > 0 && !ListOps.isListOf(evargs, Integer.class))
                    throw new EvaluationError("add: Invalid argument(s) type");

                int sum = 0;
                for (int i = 0; i < nargs; ++i) {
                    sum += ((Integer)ListOps.nth(evargs, i)).value;
                }
                return new Integer(sum);
            }
        });

        env.bindGlobal(new Symbol("mult"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                int nargs = ListOps.length(evargs);
                if (nargs > 0 && !ListOps.isListOf(evargs, Integer.class))
                    throw new EvaluationError("mult: Invalid argument(s) type");

                int mult = 1;
                for (int i = 0; i < nargs; ++i) {
                    mult *= ((Integer)ListOps.nth(evargs, i)).value;
                }
                return new Integer(mult);
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
                checkExactNumberOfArguments("car", 1, evargs);
                if (ConsCell.class != ListOps.car(evargs).getClass())
                    throw new EvaluationError("cdr: the given argument is not a list");

                return ListOps.car(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("cdr"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                checkExactNumberOfArguments("cdr", 1, evargs);
                if (ConsCell.class != ListOps.car(evargs).getClass())
                    throw new EvaluationError("cdr: the given argument is not a list");

                return ListOps.cdr(ListOps.car(evargs));
            }
        });

        env.bindGlobal(new Symbol("cons"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                checkExactNumberOfArguments("cons", 2, evargs);

                SExpression firstArg = ListOps.nth(evargs, 0);
                SExpression secondArg = ListOps.nth(evargs, 1);
                if (Symbol.NIL.equals(secondArg) || secondArg instanceof ConsCell)
                    return new ConsCell(firstArg, secondArg);

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
                checkExactNumberOfArguments("eq", 2, evargs);
                return ListOps.nth(evargs, 0).equals(ListOps.nth(evargs, 1)) ? Symbol.TRUE : Symbol.NIL;
            }
        });
    }

    /**
     * Adds the functions: eval and apply
     */
    private static void loadPrimitiveEvalAndApplyFunctions(Environment env) {
        env.bindGlobal(new Symbol("eval"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                checkExactNumberOfArguments("eval", 1, evargs);
                return ListOps.car(evargs).eval(env);
            }
        });

        env.bindGlobal(new Symbol("apply"), new Function() {
            @Override
            public SExpression apply(SExpression evargs, Environment env) {
                checkExactNumberOfArguments("apply", 2, evargs);

                SExpression fun = ListOps.nth(evargs, 0);
                SExpression args = ListOps.nth(evargs, 1);
                if (fun instanceof Function)
                    return ((Function)fun).apply(args, env);

                throw new EvaluationError("apply: first argument is not a function");
            }
        });
    }


    /**
     * Loads all the primitive special forms into the given environment
     * @param env The environment that will hold the newly defined specials.
     */
    private static void loadPrimitiveSpecials(Environment env) {
        env.bindGlobal(new Symbol("define"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                checkExactNumberOfArguments("define", 2, args);

                SExpression sym = ListOps.nth(args, 0);
                SExpression expr = ListOps.nth(args, 1);
                if (!(sym instanceof Symbol))
                    throw new EvaluationError("define: first argument must be a symbol");

                env.bindGlobal((Symbol)sym, expr.eval(env));
                return Symbol.NIL;
            }
        });

        env.bindGlobal(new Symbol("if"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                checkExactNumberOfArguments("if", 3, args);

                if (Symbol.NIL.equals(ListOps.nth(args, 0).eval(env)))  // evaluate condition
                    return ListOps.nth(args, 2).eval(env);              // evaluate else expression
                return ListOps.nth(args, 1).eval(env);                  // evaluate then expression
            }
        });

        env.bindGlobal(new Symbol("lambda"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                checkExactNumberOfArguments("lambda", 2, args);

                SExpression params = ListOps.nth(args, 0);
                SExpression body = ListOps.nth(args, 1);

                if (!ListOps.isListOf(params, Symbol.class))
                    throw new EvaluationError("lambda: the first argument must be a list of symbols");

                return new Lambda(params, body, env);
            }
        });

        env.bindGlobal(new Symbol("quote"), new Special() {
            @Override
            public SExpression applySpecial(SExpression args, Environment env) {
                checkExactNumberOfArguments("quote", 1, args);
                return ListOps.car(args);
            }
        });


    }

    /**
     * Utility method that checks if the number of arguments is the expected one.
     * @param symbolName Name of the function/special that is being evaluated.
     * @param expectedArgs Number of expected arguments.
     * @param args List of function/special arguments.
     * @throws EvaluationError If the number of arguments does not match the expected amount.
     */
    private static void checkExactNumberOfArguments(String symbolName, int expectedArgs, SExpression args) {
        int nargs = ListOps.length(args);
        if (nargs != expectedArgs)
            throw new EvaluationError(
                    String.format("%s: expected %d argument(s), received %d", symbolName, expectedArgs, nargs)
            );
    }
}
