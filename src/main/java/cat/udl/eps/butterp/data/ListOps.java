package cat.udl.eps.butterp.data;

import java.util.*;

public class ListOps {

    public static SExpression cons(SExpression car, SExpression cdr) {
        return new ConsCell(car, cdr);
    }

    public static <T extends SExpression> T car(SExpression sexpr) {
        return (T) ((ConsCell)sexpr).car;  // Intentionally unchecked cast
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
        if (Symbol.NIL.equals(sexpr))
            return 0;

        return 1 + length(cdr(sexpr));
    }

    public static SExpression nth(SExpression list, int n) {
        return n <= 0 ? ListOps.car(list) : nth(ListOps.cdr(list), n - 1);
    }

    public static boolean isListOf(SExpression params, Class<?> klass) {
        return Symbol.NIL.equals(params) ||
              (klass.isInstance(ListOps.car(params)) && isListOf(ListOps.cdr(params), klass));
    }

    public static boolean allDiferent(SExpression list) {
        Set<SExpression> seen = new HashSet<>();
        Iterator<SExpression> it = createIterator(list);
        while (it.hasNext()) {
            if (!seen.add(it.next()))
                return false;
        }
        return true;
    }

    public static <T> Iterator<T> createIterator(final SExpression sexpr) {
        return new Iterator<T>() {
            SExpression index = sexpr;

            @Override
            public boolean hasNext() {
                return Symbol.NIL != index;
            }

            @Override
            public T next() {
                T retVal = ListOps.car(index);  // Intentionally unchecked cast
                index = ListOps.cdr(index);
                return retVal;
            }
        };
    }
}
