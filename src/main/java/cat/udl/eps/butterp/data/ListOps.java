package cat.udl.eps.butterp.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static SExpression car(SExpression sexpr) {
        return ((ConsCell)sexpr).car;
    }

    public static SExpression cdr(SExpression sexpr) {
        return ((ConsCell)sexpr).cdr;
    }

    public static SExpression list(SExpression... elems) {
        return ListOps.list(Arrays.asList(elems));
    }

    public static SExpression list(List<SExpression> elems) {
        SExpression head = Symbol.NIL;

        ListIterator<SExpression> it = elems.listIterator(elems.size());
        while (it.hasPrevious()) {
            head = ListOps.cons(it.previous(), head);
        }

        return head;
    }

    public static int length(SExpression sexpr) {
        int len = 0;

        SExpression currentExpr = sexpr;
        while (!Symbol.NIL.equals(currentExpr)) {
            len += 1;
            currentExpr = cdr(currentExpr);
        }

        return len;
    }

    public static SExpression nth(SExpression sexpr, int n) {
        if (n <= 0) {  // Avoid infinite recursion warning (when  == 0)
            return ListOps.car(sexpr);
        }
        return nth(ListOps.cdr(sexpr), n - 1);
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        if (!(params instanceof ConsCell))
            return false;

        boolean ok = true;
        SExpression it = params;
        while (!Symbol.NIL.equals(it) && ok) {
            ok = klass.isInstance(ListOps.car(it));
            it = ListOps.cdr(it);
        }

        return ok;
    }

    public static Iterator<SExpression> createIterator(final SExpression sexpr) {
        return new Iterator<SExpression>() {
            SExpression index = sexpr;

            @Override
            public boolean hasNext() {
                return !Symbol.NIL.equals(index);
            }

            @Override
            public SExpression next() {
                SExpression retVal = ListOps.car(index);
                index = ListOps.cdr(index);
                return retVal;
            }
        };
    }
}
