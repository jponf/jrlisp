package cat.udl.eps.butterp.reader;

import java.io.IOException;
import java.io.Reader;

public class ReaderLexer extends Lexer {

    private final Reader reader;

    public ReaderLexer(Reader reader) {
        this.reader = reader;
        consume();
    }

    @Override
    public void consume() {
        try {
            setCurrentCharacter((char) reader.read());
        } catch (IOException e) {
            throw new LexerError(e.getMessage());
        }
    }
}
