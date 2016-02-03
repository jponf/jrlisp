package cat.udl.eps.butterp.reader;


public class StringLexer extends Lexer {

    protected final String input; // input string
    protected int  p = 0;         // index into input of current character

    @Override
    public void consume() {
        p++;
        setCurrentCharacter(p < input.length() ? input.charAt(p) : EOF);
    }


    public StringLexer(String input) {
        super();
        this.input = input.isEmpty() ? " " : input;
        setCurrentCharacter(this.input.charAt(p)); // prime lookahead
    }
}
