package co.lex.domain.model.lexicon.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 10/11/16.
 */
@RunWith(value = Parameterized.class)
public class TokenTest {

    private TokenType tokenType;

    private Word word;

    public TokenTest(TokenType aTokenType, Word aString){
        this.tokenType = aTokenType;
        this.word = aString;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewToken_NotAllowedWord_ExceptionThrown(){
        Token token = new Token(this.tokenType, this.word);
    }

    @Parameters
    public static Iterable<Object[]> parameters(){
        List<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, new Word("aString_1234_;", 0, 10)});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, new Word("      ;", 10, 10)});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, new Word("aString_1234_", 10, 40)});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, new Word("    ", 2, 10)});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, new Word(" ;", 0, 9)});
        return parameters;
    }

}