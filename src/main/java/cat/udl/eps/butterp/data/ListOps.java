package cat.udl.eps.butterp.data;

import java.util.Arrays;
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
        throw new UnsupportedOperationException("not implemented yet");
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        throw new UnsupportedOperationException("not implemented yet");
    }

}
