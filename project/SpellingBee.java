package project;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class SpellingBee extends Application {
    private TextField inputField;
    private Label title;
    private Set<String> validWords = new HashSet<>();
    private String centerLetter;
    private Set<String> otherLetters = new HashSet<>();
    private Label resultLabel;
    private Label ruleLabel1;
    private Label correctLabel;
    private Label incorrectLabel;
    private Set<String> correctWords = new HashSet<>();
    private TextArea correctWordsList = new TextArea();
    private Label correctWordsTitle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeGame();

        title = new Label();
        title.setText("New York Times Spelling Bee");
        title.setStyle("-fx-font-size:35; -fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        TextFlow titleTextFlow = new TextFlow();
        Text titleText = new Text("New York Times Spelling Bee");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        title.setUnderline(true);
        titleText.setFill(Color.BLACK); // Set the desired text color
        titleTextFlow.getChildren().add(titleText);
    

        // Create GUI components
        inputField = new TextField();
        inputField.setPromptText("Enter a word");
        inputField.setMaxWidth(800);
        ruleLabel1 = new Label();
        ruleLabel1.setText("RULES\nConstruct as many words as you can using at least 4 letters, including the center (bolded) letter of the puzzle.\nWords should be at least 4 letters (no maximum limit).\nEach Spelling Bee puzzle is curated to focus on relatively common words (with a few tougher ones periodically to keep things challenging).");
        //ruleLabel1.setStyle("-fx-font-size: 17;");
        //lettersLabel = new Label();
        resultLabel = new Label();
        correctLabel = new Label("Correct: 0");
        //correctLabel.setStyle("-fx-font-size:20;");
        incorrectLabel = new Label("Incorrect: 0");
        correctWordsList.setEditable(false);
        correctWordsList.setMaxWidth(300);
        correctWordsTitle = new Label("Correct Words Entered");
        correctWordsTitle.setStyle("-fx-font-weight: bold;");
        //correctWordsTitle.setVisible(false);
    

        // Set up the layout
        VBox root = new VBox(10);
        //root.setTop(title);
        root.setStyle("-fx-font-size:20; -fx-background-color: #FFFF99;");
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER); // Center its children
        root.getChildren().addAll(title, ruleLabel1, createCenteredLetterBox(), inputField, resultLabel, correctLabel, incorrectLabel, correctWordsList, correctWordsTitle);

        // Set up the scene
        Scene scene = new Scene(root, 1920, 1080);

        // Set up event handling
        inputField.setOnAction(e -> handleInput(inputField.getText(), primaryStage));
        inputField.setText("");

        // Set up the stage
        primaryStage.setTitle("New York Spelling Bee");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createCenteredLetterBox() {
        HBox letters = new HBox(10); // Horizontal box for arranging letters
        letters.setAlignment(Pos.CENTER);
        letters.setStyle("-fx-font-size: 20;"); 

        // Add labels for each letter
        letters.getChildren().addAll(
                new Label("A"),
                new Label("B"),
                new Label("C"),
                new Label("D"),
                createBoldLabel(centerLetter), // Center letter in bold
                new Label("K"),
                new Label("Y")
        );

        return letters;
    }

    private Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private void initializeGame() {
        // Load a list of valid English words into the 'validWords' set (not provided here).
        // Set the 'centerLetter' and 'otherLetters' according to the puzzle.
        loadValidWords("C:\\Users\\kaden\\OneDrive\\Documents\\java\\ajdwad\\project\\test.txt");
        centerLetter = "R";
        otherLetters.add("A");
        otherLetters.add("B");
        otherLetters.add("C");
        otherLetters.add("D");
        otherLetters.add("K");
        otherLetters.add("Y");
    }

    private void loadValidWords(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                validWords.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading the word list file: " + e.getMessage());
        }
    }

    private void handleInput(String word, Stage primaryStage) {
        //lettersLabel.setText("Other Letters: " + otherLetters);

        if (word.equalsIgnoreCase("exit")) {
            // Confirm exit using an Alert
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            exitAlert.setTitle("Exit Confirmation");
            exitAlert.setHeaderText("Are you sure you want to exit?");
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                primaryStage.close();
            }
        } else {
            word = word.toUpperCase(); // Convert the word to uppercase
            if (isValidWord(word) &&!correctWords.contains(word) && word.length() < 7)  {
                resultLabel.setText(word + " was a valid word!");
                updateCountAndLabel(true);
                correctWords.add(word);
                //updateResultLabel(word + " was a valid word!", Color.GREEN);


                flashGreen(resultLabel);
                updateCorrectWordsList();
                //int correctCounter = correctCounter + 1;
            }
            else if(word.length() >= 7 && isValidWord(word)){
                resultLabel.setText(word + " is a Pangram! WOW");
                flashGreen(resultLabel);
                updateCountAndLabel(true);
                correctWords.add(word);
                updateCorrectWordsList();
            }
            else if (correctWords.contains(word)){
                resultLabel.setText(word + " has been entered already");
                flashOrange(resultLabel);
            } 
            else {
                resultLabel.setText("Invalid word: " + word);
                updateCountAndLabel(false);
                flashRed(resultLabel);
                // Set the result label with red font for invalid words
                //updateResultLabel("Invalid word: " + word, Color.RED);
            }
            //clears the text field after word is entered
            inputField.setText("");
        }

    }

      private void flashGreen(Label label) {
        // Change the text fill color to green and revert after a short pause
        label.setStyle("-fx-text-fill: green;");
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> label.setStyle(""));
        pause.play();
    }

    private void flashRed(Label label) {
        // Change the text fill color to green and revert after a short pause
        label.setStyle("-fx-text-fill: red;");
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> label.setStyle(""));
        pause.play();
    }

    private void flashOrange(Label label) {
        // Change the text fill color to green and revert after a short pause
        label.setStyle("-fx-text-fill: #cc5500;");
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> label.setStyle(""));
        pause.play();
    }

    private boolean isValidWord(String word) {
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

    private void updateCountAndLabel(boolean isCorrect) {
        int correctCount = Integer.parseInt(correctLabel.getText().split(":")[1].trim());
        int incorrectCount = Integer.parseInt(incorrectLabel.getText().split(":")[1].trim());
    
        if (isCorrect) {
            correctCount++;
            correctLabel.setText("Correct: " + correctCount);
        } else {
            incorrectCount++;
            incorrectLabel.setText("Incorrect: " + incorrectCount);
        }
    }

    
    private void updateCorrectWordsList() {
        correctWordsList.setText(String.join("\n", correctWords));
    }


}
