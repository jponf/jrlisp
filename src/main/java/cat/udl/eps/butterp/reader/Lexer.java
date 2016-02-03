package cat.udl.eps.butterp.reader;

// Based on an example from Language Implementation Patterns by Terrence Parr

public abstract class Lexer {

    public static final char EOF = (char) -1; // represent end of file char

    private char c;                         // current character

    public abstract void consume();

    public void match(char x) {
        if (c == x) { consume(); }
        else throw new LexerError(String.format("expecting '%s' but got '%s'%s", x, c, Character.getName(c)));
    }

    private boolean isALPHA() {
        return Character.isLetterOrDigit(c);
    }

    private boolean isLETTER() {
        return Character.isLetter(c);
    }

    private boolean isSIGN() {
        return c == '+' || c == '-';
    }

    private boolean isNUMBER() {
        return isSIGN() || isDIGIT();
    }

    private boolean isDIGIT() {
        return Character.isDigit(c);
    }

    private boolean isWS() {
        return Character.isWhitespace(c);
    }

    public Token nextToken() {
        while (c != EOF) {
            if (isWS()) {
                WS();
            } else if (c == '(') {
                consume();
                return Token.LPAREN;
            } else if (c == ')') {
                consume();
                return Token.RPAREN;
            } else if (c =='\'') {
                consume();
                if (!isWS()) {
                    return Token.QUOTE;
                } else {
                    throw new LexerError("No spaces allowed after quote character.");
                }
            } else if (isLETTER()) {
                return ATOM();
            } else if (isNUMBER()) {
                return INTEGER();
            } else {
                throw new LexerError(invalidCharacter(c));
            }
        }
        return Token.EOF;
    }

    protected void setCurrentCharacter(char ch) {
        this.c = ch;
    }

    private Token ATOM() {
        StringBuilder buf = new StringBuilder();

        do {
            buf.append(c);
            consume();
        } while (isALPHA());

        if (c == EOF || c == ')' || isWS()) {
            return Token.ATOM(buf.toString());
        } else {
            throw new LexerError(invalidCharacter(c));
        }
    }

    private Token INTEGER() {
        StringBuilder buf = new StringBuilder();

        if (isSIGN()) {
            buf.append(c);
            consume();
        }
        if (!isDIGIT()) {
            throw new LexerError(invalidCharacter(c));
        }

        do {
            buf.append(c);
            consume();
        } while (isDIGIT());

        if (c == EOF || c == ')' || isWS()) {
            return Token.INTEGER(buf.toString());
        } else {
            throw new LexerError(invalidCharacter(c));
        }
    }

    private void WS() {
        while (isWS()) {
            consume();
        }
    }

    private static String invalidCharacter(char c) {
        return String.format("invalid character: '%s' (%s)", c, Character.getName(c));
    }
}