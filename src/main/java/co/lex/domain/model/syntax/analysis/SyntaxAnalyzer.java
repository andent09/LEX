package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.LexicalAnalysisReport;
import co.lex.domain.model.lexicon.analysis.LexicalAnalyzer;
import co.lex.domain.model.lexicon.analysis.Token;

import java.util.List;

/**
 * Created by jotauribe on 11/11/16.
 */

/**
 * ANOTACIONES:
 *      El analizador sintactico encuentra un error cuando la cadena de tokens evaluada
 *      no encuentra un grupo de reglas que coincida con su estructura.
 *
 * POSIBLES CAUSAS DE ERRROR
 *      --Caracteres intrusos
 *      --Caaracteres faltantes
 *
 * APROXIMACIONES PARA EL MANEJO DE ERRORES
 *
 *      --CARACTERES INTRUSOS
 *
 *              +OMISION DE SIMBOLOS EXTRAÑOS:
 *                      Al encontrar un token no esperado, el analizador podria omitirlo y empezar
 *                      de nuevo una evaluacion desde el token que le sigue a este.
 *
 *              +UN PASO ADELANTE EN LAS REGLAS:
 *                      Al encontrar un token no esperado, se omite y se empieza a verificar la cadena
 *                      de Tokens desde el token siguiente, empezando a verificar las reglas desde su
 *                      segundo simbolo
 *
 */
public class SyntaxAnalyzer {

    private Grammar grammar;

    private LexicalAnalyzer lexicalAnalyzer;

    public SyntaxAnalyzer(){
        grammar = new Grammar();
        lexicalAnalyzer = new LexicalAnalyzer();
    }

    public AnalysisTree parse(List<Token> aTokenList){

        Token currentToken = aTokenList.get(0);

        RuleGroup start = this.grammar.rules().get(18);


        if(start != null){

            return start.evaluate(currentToken);
        }
        else return new AnalysisTree();
    }

    public SyntaxAnalysisReport evaluate(String aString){
        LexicalAnalysisReport lexicalAnalysisReport = lexicalAnalyzer.evaluate(aString);
        AnalysisTree analysisTree = parse(lexicalAnalysisReport.getTokenList());
        SyntaxAnalysisReport syntaxAnalysisReport = new SyntaxAnalysisReport(lexicalAnalysisReport, analysisTree);

        return syntaxAnalysisReport;
    }

}