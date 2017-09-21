package TextAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("1)TextAnalyzer\n" + "2)Check bracket sequence\n" + "Your choice?");
        String choice = reader.readLine();

        if (choice.equals("1"))
            Analyzer.countWords();
        else {
            System.out.println("Input sequence of brackets");
            String sequence = reader.readLine();
            Analyzer.checkBracketSequence(sequence);
        }
    }
}
