package TextAnalyzer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Analyzer {
    private static String readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        StringBuilder str = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            str.append(line);
            str.append(" ");
        }
        return str.toString();
    }

    static void countWords() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input path to the txt:");
        String path = reader.readLine();
        Map<String, Integer> count = new HashMap<>();
        String[] words = Analyzer.readFile(path).toLowerCase().split("[\\s,.!?:;-]+");
        String[] check = Analyzer.readFile("src/main/resources/ExceptionWords").split("\\s");

        for (String word : words)
            count.put(word, count.containsKey(word) ? (count.get(word) + 1) : 1);

        for(Iterator<Map.Entry<String, Integer>> it = count.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            for (String aCheck : check)
                if (entry.getKey().equals(aCheck)) {
                    it.remove();
                }
        }

        count = sortByValue(count);

        int i = 0;
        if (count.size() > 10) {
            for (Map.Entry<String, Integer> item : count.entrySet()) {
                if (i != 10) {
                    System.out.printf("word: %s, count: %d %n", item.getKey(), item.getValue());
                    i++;
                }
            }
        } else {
            for (Map.Entry<String, Integer> item : count.entrySet()) {
                System.out.printf("word: %s, count: %d %n", item.getKey(), item.getValue());
            }
        }

    }

    static void checkBracketSequence(String sequence) {

        boolean flagGood = true;
        int i = 0;
        Stack<Character> check = new Stack<>(); //создаём стэк
        while(i != sequence.length() && flagGood) { //проходим циклом пока не дойдём до конца строки
            if (sequence.charAt(i) == '(' || sequence.charAt(i) == ')' || sequence.charAt(i) == '[' || sequence.charAt(i) == ']' ||
                    sequence.charAt(i) == '{' || sequence.charAt(i) == '}') { //является ли последующий символ строки скобкой
                if (sequence.charAt(i) == '(' || sequence.charAt(i) == '[' || sequence.charAt(i) == '{') //если скобка открывающая,то добавляем её в в стэк
                    check.push(sequence.charAt(i));
                else if (sequence.charAt(i) == ')' || sequence.charAt(i) == ']' || sequence.charAt(i) == '}' && !check.isEmpty()) {
                    char g = check.pop(); //если скобка закрывающая, то извлекаем её
                    if ((g != '(' || sequence.charAt(i) != ')') && (g != '[' || sequence.charAt(i) != ']') && (g != '{' || sequence.charAt(i) != '}'))
                        flagGood = false;
                }
                else flagGood = false;
            }
            i += 1;
        }

        if (flagGood && check.isEmpty())
            System.out.println("correct");
        else
            System.out.println("incorrect");
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
