package cat.udl.eps.butterp.reader;

// Based on an example from Language Implementation Patterns by Terrence Parr

public abstract class Lexer {

    public static final char EOF = (char) -1; // represent end of file char

    private char ch;                         // current character

    public abstract void consume();

    public void match(char x) {
        if (ch == x) {
            consume();
        } else {
            throw new LexerError(String.format("expecting '%s' but got '%s'%s", x, ch, Character.getName(ch)));
        }
    }

    private boolean isAlphaNum() {
        return Character.isLetterOrDigit(ch);
    }

    private boolean isLetter() {
        return Character.isLetter(ch);
    }

    private boolean isOperand() {
        return ch == '*' || ch == '/' || ch == '&' || isNumberSign();
    }

    private boolean isNumberSign() {
        return ch == '+' || ch == '-';
    }

    private boolean isDigit() {
        return Character.isDigit(ch);
    }

    private boolean isFullStop() {
        return ch == '.';
    }

    private boolean isWhitespace() {
        return Character.isWhitespace(ch);
    }

    public Token nextToken() {
        while (ch != EOF) {
            if (isWhitespace()) {
                skipWhitespace();
            } else if (ch == '(') {
                consume();
                return Token.LPAREN;
            } else if (ch == ')') {
                consume();
                return Token.RPAREN;
            } else if (ch =='\'') {
                consume();
                if (!isWhitespace()) {
                    return Token.QUOTE;
                } else {
                    throw new LexerError("No spaces allowed after quote character.");
                }
            } else if (isNumberSign()) {   // An operand is a number sign so the order is important
                return parseAtomOrNumber(new StringBuilder());
            } else if (isLetter() || isOperand()) {
                return parseAtom(new StringBuilder());
            } else if (isDigit()) {
                return parseNumber(new StringBuilder());
            } else {
                throw new LexerError(invalidCharacter(ch));
            }
        }
        return Token.EOF;
    }

    protected void setCurrentCharacter(char ch) {
        this.ch = ch;
    }

    private Token parseAtom(StringBuilder buf) {
        if (isOperand()) {    // Operands are only allowed at the beginning
            buf.append(ch);
            consume();
        }

        while (isAlphaNum()) {
            buf.append(ch);
            consume();
        }

        if (ch == EOF || ch == ')' || isWhitespace()) {
            return Token.newAtom(buf.toString());
        } else {
            throw new LexerError(invalidCharacter(ch));
        }
    }

    private Token parseAtomOrNumber(StringBuilder buf) {
        if (isNumberSign()) {
            buf.append(ch);
            consume();
        }

        if (isDigit()) {
            return parseNumber(buf);
        } else {
            return parseAtom(buf);
        }
    }

    private Token parseNumber(StringBuilder buf) {  // Sign is handled in parseAtomOrNumber
        boolean decimalPoint = false;
        while (isDigit() || (isFullStop() && !decimalPoint)) {
            if (isFullStop())
                decimalPoint = true;
            buf.append(ch);
            consume();
        }

        if (ch == EOF || ch == ')' || isWhitespace()) {
            return decimalPoint ? Token.newReal(buf.toString()) : Token.newInteger(buf.toString());
        } else {
            throw new LexerError(invalidCharacter(ch));
        }
    }

    private void skipWhitespace() {
        while (isWhitespace()) {
            consume();
        }
    }

    private static String invalidCharacter(char c) {
        return String.format("invalid character: '%s' (%s)", c, Character.getName(c));
    }
}