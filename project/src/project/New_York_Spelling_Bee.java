package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class New_York_Spelling_Bee {
    private static Set<String> validWords = new HashSet<>();
    private static String centerLetter;
    private static Set<String> otherLetters = new HashSet<>();

    public static void main(String[] args) {
        initializeGame();
        playGame();
    }

    private static void initializeGame() {
        // Load a list of valid English words into the 'validWords' set (not provided here).
        // Set the 'centerLetter' and 'otherLetters' according to the puzzle.
        loadValidWords("\"C:\\Users\\Collin Follett\\eclipse-workspace\\project\\src\\project\\valid_answers.txt\"");
        centerLetter = "R";
        otherLetters.add("A");
        otherLetters.add("B");
        otherLetters.add("C");
        otherLetters.add("D");
        otherLetters.add("K");
        otherLetters.add("Y");
    }

    private static void loadValidWords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                validWords.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading the word list file: " + e.getMessage());
        }
    }

    private static void playGame() {
        // Game loop (you can implement your own loop structure or use a GUI).
        while (true) {
            String word = getPlayerInput(); // Get player input (e.g., from the GUI).
            if (word.equalsIgnoreCase("exit")) {
                break; // Exit the game loop.
            }
            if (isValidWord(word)) {
                System.out.println("Valid word: " + word);
            } else {
                System.out.println("Invalid word: " + word);
            }
        }
    }

    private static boolean isValidWord(String word) {
        // Check if the word is valid according to the game constraints.
        if (word.length() < 4 || !word.contains(centerLetter)) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1).toUpperCase();
            if (!letter.equals(centerLetter) && !otherLetters.contains(letter)) {
                return false;
            }
        }
        // Check if the word is in the list of valid English words (you need to load the word list).
        return validWords.contains(word);
    }

    private static String getPlayerInput() {
        // Simulate player input (you can replace this with GUI input).
        // In this example, we use the console for input.
        System.out.print("Enter a word (or 'exit' to quit): ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        return scanner.nextLine().trim();
    }
}
