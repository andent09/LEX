package co.lex.application.view;

import co.lex.domain.model.lexicon.analysis.Token;
import co.lex.domain.model.syntax.analysis.AnalysisTree;
import co.lex.domain.model.syntax.analysis.SyntaxAnalysisReport;
import co.lex.domain.model.syntax.analysis.SyntaxAnalyzer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JDialog {
    private JPanel contentPane;
    private JButton evaluateBtn;
    private JTextArea runTextArea;
    private JTextArea codeTextArea;
    private JButton LimpiarBtn;
    private JButton buttonOK;
    private JButton buttonCancel;
    private SyntaxAnalyzer syntaxAnalyzer;

    public Main() {
        setContentPane(contentPane);
        setModal(true);

        syntaxAnalyzer = new SyntaxAnalyzer();

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        evaluateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTextArea.setText(null);
                String codeString = codeTextArea.getText();
                SyntaxAnalysisReport syntaxAnalysisReport = syntaxAnalyzer.evaluate(codeString);
                if(syntaxAnalysisReport.isEverythingOK()){
                    runTextArea.setText("");
                    runTextArea.append("BUILD SUCCESSFULL");
                }
                else {
                    List<Token> lexicalErrors = syntaxAnalysisReport.lexicalErrorList();
                    runTextArea.setText(null);
                    for (Token t :
                            lexicalErrors) {
                        String subString = codeString.substring(0, t.endLocation());
                        //System.out.print("MAIN:   "+t.startLocation()+"   "+t.endLocation());
                        runTextArea.append("\nLexical Error in line "+countLines(subString));
                    }
                    AnalysisTree analysisTree = syntaxAnalysisReport.syntaxAnalysisTree();
                    Token t = analysisTree.rightmostValidToken();
                    System.out.print(t);
                    String subString = codeString.substring(0, t.endLocation());
                    runTextArea.append("\nSyntax Error in line "+countLines(subString));
                }

            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Main dialog = new Main();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private int countLines(String aString){
        Pattern p = Pattern.compile("\n");
        Matcher m = p.matcher(aString);
        int count = 1;
        while (m.find()) {
            count++;
        }
        return count;
    }

}