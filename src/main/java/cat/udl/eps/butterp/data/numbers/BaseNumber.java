package cat.udl.eps.butterp.data.numbers;


import cat.udl.eps.butterp.data.SExpression;

public interface BaseNumber extends SExpression {

    /**
     * Performs the addition of this number and the given one by dispatching
     * the appropriate addTo(...) method.
     * @param operand The number to add.
     * @return A new BaseNumber with the result of the operation.
     */
    BaseNumber add(BaseNumber operand);

    BaseNumber addTo(Integer operand);
    BaseNumber addTo(Real operand);

    /**
     * Performs the subraction of this number and the given one by dispatching
     * the appropriate subtractTo(...) method.
     * @param operand The number to subtract.
     * @return A new BaseNumber with the result of the operation.
     */
    BaseNumber subtract(BaseNumber operand);

    BaseNumber subtractTo(Integer operand);
    BaseNumber subtractTo(Real operand);

    /**
     * Performs the multiplication of this number and the given one by dispatching
     * the appropriate multiplyTo(...) method.
     * @param operand The number to multiply.
     * @return A new BaseNumber with the result of the operation.
     */
    BaseNumber multiply(BaseNumber operand);

    BaseNumber multiplyTo(Integer operand);
    BaseNumber multiplyTo(Real operand);

    /**
     * Performs the division of this number and the given one by dispatching
     * the appropriate divideTo(...) method.
     * @param operand The divisor.
     * @return A new BaseNumber with the result of the operation.
     * @throws ArithmeticException If the divisor is zero.
     */
    BaseNumber divide(BaseNumber operand);

    BaseNumber divideTo(Integer operand);
    BaseNumber divideTo(Real operand);
}
