package cat.udl.eps.butterp.data;


public interface BaseNumber extends SExpression {

    BaseNumber add(BaseNumber operand);
    BaseNumber add(Integer operand);
    BaseNumber add(Real operand);

    BaseNumber multiply(BaseNumber operand);
    BaseNumber multiply(Integer operand);
    BaseNumber multiply(Real operand);
}
