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
        if (!Symbol.NIL.equals(sexpr))
            return 0;

        return 1 + length(cdr(sexpr));
    }

    public static SExpression nth(SExpression sexpr, int n) {
        return n <= 0 ? ListOps.car(sexpr) : nth(ListOps.cdr(sexpr), n - 1);
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        return Symbol.NIL.equals(params) ||
              (klass.isInstance(ListOps.car(params)) && isListOf(ListOps.cdr(params), klass));
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
