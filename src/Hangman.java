import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    private static final String[] WORDS = {"apple", "banana", "orange", "pear", "grape"};
    private static final int MAX_ERRORS = 6;

    public static void main(String[] args) {
        String word = getRandomWord();
        char[] hiddenWord = initializeHiddenWord(word);
        char[] wrongAttempts = new char[MAX_ERRORS];
        int wrongCount = 0;
        System.out.println("Игра ПОЛЕ ЧУДЕС на жизнь!!!");
        
        while (wrongCount < MAX_ERRORS) {
            drawHangman(wrongCount);
            System.out.println("Загаданное слово: "+ new String(hiddenWord));
            System.out.println("Количество ошибок: "+ wrongCount);
            System.out.println("Неверные буквы: "+ Arrays.toString(getNotEmptyCharacters(wrongAttempts)));

            char letter = getUserInput(hiddenWord, wrongAttempts);
            
            if(word.indexOf(letter) >=0) {
                updateHiddenWord(word, letter, hiddenWord);
                System.out.println("Верно!");
            }else  {
                wrongAttempts[wrongCount] = letter;
                wrongCount++;
                System.out.println("Неверно!");
            }
            if (isWordGuessed(hiddenWord)){
                System.out.println("Ура !!!, Вы выиграли ");
                return;
            }
        }
        drawHangman(wrongCount);
        System.out.println("Вы проиграли!!!");
    }
    private  static  boolean isWordGuessed(char[] hiddenWord) {
        return !new String(hiddenWord).contains("_");
    }

    private static void updateHiddenWord(String word, char letter, char[] hiddenWord) {
        for (int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == letter) {
                hiddenWord[i] = letter;
            }
        }
    }

    private static char getUserInput(char[] hiddenWord, char[] wrongAttempts) {
        Scanner scanner = new Scanner(System.in);
        char letter;

        while (true){
            System.out.print("Введите букву: ");
            String input = scanner.nextLine().toLowerCase();
            if(input.length() != 1){
                System.out.println("Пожалуйста, введите одну букву.");
                continue;
            }
            letter = input.charAt(0);
            if(containsCharacter(hiddenWord, letter) || containsCharacter(wrongAttempts, letter)){
                System.out.println("Вы уже вводили такую букву. Попробуйте другую букву");
            }else {
                break;
            }
        }
        return letter;
    }

    private static boolean containsCharacter(char[] letters, char letter) {
        for (char c : letters) {
            if (c == letter) {
                return true;
            }
        }
        return false;
    }


    private static char[] getNotEmptyCharacters(char[] wrongAttempts) {
        StringBuilder result = new StringBuilder();
        for (char wrongAttempt : wrongAttempts) {
            if(wrongAttempt != '\0'){
                result.append(wrongAttempt);
            }

        }
        return result.toString().toCharArray();
    }

    private static void  drawHangman(int wrongCount) {
        String[][] hangman =
                {
                        {"_", "_", "_", "_", "_", " "," ", " "},
                        {"|", " ", " ", " ", "|", " "," ", " "},
                        {"|", " ", " ", " ", " ", " "," ", " "},
                        {"|", " ", " ", " ", " ", " "," ", " "},
                        {"|", " ", " ", " ", " ", " "," ", " "},
                        {"|", " ", " ", " ", " ", " "," ", " "},
                        {"|", " ", " ", " ", " ", " "," ", " "},
                        {"_", " ", " ", " ", " ", " "," ", " "},
                };
        if(wrongCount >=1) hangman[2][4] = "O";
        if(wrongCount >=2) hangman[3][4] = "|";
        if(wrongCount >=3) hangman[3][3] = "/";
        if(wrongCount >=4) hangman[3][5] = "\\";
        if(wrongCount >=5) hangman[4][3] = "/";
        if(wrongCount >=6) hangman[4][5] = "\\";

        System.out.println("Виселица:");
        for (String[] row : hangman) {
            for (String column : row) {
                System.out.print(column);
            }
            System.out.println();
        }
    }

    private static char[] initializeHiddenWord(String word) {
        char[] hiddenWord = new char[word.length()];
        Arrays.fill(hiddenWord, '_');
        return hiddenWord;
    }

    private static String getRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }

}