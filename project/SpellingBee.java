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
import java.util.List;
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
    //private TextArea correctWordsList = new TextArea();
    private Label correctWordsTitle;
    private Label incorrectWordsTitle;
    private ListView<String> correctWordsList;
    private ListView<String> incorrectWordsList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeGame();


        // title styling
        title = new Label();
        title.setText("New York Times Spelling Bee");
        title.setStyle("-fx-font-size:35; -fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        title.setUnderline(true);
    
        // input box
        inputField = new TextField();
        inputField.setPromptText("Enter a word");
        inputField.setMaxWidth(800);

        // rule label to display rules
        ruleLabel1 = new Label();
        ruleLabel1.setText("RULES\nConstruct as many words as you can using at least 4 letters, including the center (bolded) letter of the puzzle.\nWords should be at least 4 letters (no maximum limit).\nEach Spelling Bee puzzle is curated to focus on relatively common words (with a few tougher ones periodically to keep things challenging).");
        //ruleLabel1.setStyle("-fx-font-size: 17;");
        //lettersLabel = new Label();
        resultLabel = new Label();
        correctLabel = new Label("Correct: 0");
        //correctLabel.setStyle("-fx-font-size:20;");
        incorrectLabel = new Label("Incorrect: 0");
        
        // display list of correct words entered
        correctWordsList = new ListView<>();
        correctWordsList.setMaxWidth(300);
        correctWordsTitle = new Label("Correct Words Entered");
        correctWordsTitle.setStyle("-fx-font-weight: bold;");

        // display list of incorrect words entered
        incorrectWordsList = new ListView<>();
        incorrectWordsList.setMaxWidth(300);
        incorrectWordsTitle = new Label("Incorrect Words Entered");
        incorrectWordsTitle.setStyle("-fx-font-weight: bold;");

    

        // Set up the vbox layout
        VBox root = new VBox(10);
        root.setStyle("-fx-font-size:20; -fx-background-color: #FFFF99;");
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        
        
        HBox listsBox = new HBox(10);
        listsBox.setAlignment(Pos.CENTER);
        
        listsBox.getChildren().addAll(
            createCorrectWordsBox(),
            createIncorrectWordsBox() 
        );

        VBox centeringBox = new VBox(listsBox);
        centeringBox.setAlignment(Pos.CENTER);



        root.getChildren().addAll(title, ruleLabel1, createCenteredLetterBox(), inputField, resultLabel, correctLabel, incorrectLabel, centeringBox);

        // Set up the scene
        Scene scene = new Scene(root, 1600, 900);

        // Set up error
        inputField.setOnAction(e -> handleInput(inputField.getText(), primaryStage));
        inputField.setText("");

        // Set up stage
        primaryStage.setTitle("New York Spelling Bee");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createCenteredLetterBox() {
        HBox letters = new HBox(10); // Horizontal box to display game letters
        letters.setAlignment(Pos.CENTER);
        letters.setStyle("-fx-font-size: 20;"); 

        // Add labels for each letter
        letters.getChildren().addAll(
                new Label("A"),
                new Label("B"),
                new Label("C"),
                createBoldLabel(centerLetter), // Center letter in bold
                new Label("D"),
                new Label("K"),
                new Label("Y")
        );

        return letters;
    }


    // make the center letter (required letter) in bold for better user understanding
    private Label createBoldLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private void initializeGame() {

        // path to valid word set list file
        // -------------------------------------------------------
        loadValidWords("C:\\Users\\kaden\\OneDrive\\Documents\\java\\ajdwad\\project\\test.txt");
        //--------------------------------------------------------

        // set up the letters for the game
        centerLetter = "R";
        otherLetters.add("A");
        otherLetters.add("B");
        otherLetters.add("C");
        otherLetters.add("D");
        otherLetters.add("K");
        otherLetters.add("Y");
    }
   
    // load the word list file into valid words set
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

        // user can exit by typing exit
        if (word.equalsIgnoreCase("exit")) {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            exitAlert.setTitle("Exit Confirmation");
            exitAlert.setHeaderText("Exit? No more spelling for you?");
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                primaryStage.close();
            }

        } else {
            
            // converts all words to uppercase that way it is case insensitive
            word = word.toUpperCase();
            
            
            if (isValidWord(word) &&!correctWords.contains(word) && word.length() < 7)  {
                resultLabel.setText(word + " was a valid word!");
                updateCountAndLabel(true);
                correctWords.add(word);
                //updateResultLabel(word + " was a valid word!", Color.GREEN);


                flashGreen(resultLabel);
                updateCorrectWordsList(word);
                //int correctCounter = correctCounter + 1;
            }

            // special else if for if word entered is a pangram, using all 7 letters
            else if(word.length() >= 7 && isValidWord(word)){
                resultLabel.setText(word + " is a Pangram! WOW");
                flashGreen(resultLabel);
                updateCountAndLabel(true);
                correctWords.add(word);
                updateCorrectWordsList(word);
            }

            // catch if the user tries to enter the same word twice, make sure it doesnt count to add on to the score
            else if (correctWords.contains(word)){
                resultLabel.setText(word + " has been entered already");
                flashOrange(resultLabel);
            } 

            // if word is not valid, then notify user and add 1 to the incorrectCount counter
            else {
                resultLabel.setText("Invalid word: " + word);
                updateCountAndLabel(false);
                flashRed(resultLabel);
                updateIncorrectWordsList(word);
            }
            
            
            //clears the text field after word is entered
            inputField.setText("");
        }

    }


    // flash green if correct valid guess is made
      private void flashGreen(Label label) {
        // Change the text fill color to green and revert after a short pause
        label.setStyle("-fx-text-fill: green;");
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> label.setStyle(""));
        pause.play();
    }


    // flash red if incorrect guess is made
    private void flashRed(Label label) {
        label.setStyle("-fx-text-fill: red;");
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> label.setStyle(""));
        pause.play();
    }


    // flash orange if word has already been used
    private void flashOrange(Label label) {
        label.setStyle("-fx-text-fill: #cc5500;");
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
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
        // Check if the word is in the list of valid words
        return validWords.contains(word);
    }


    // update both the correct anmd incorrect counter
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

    private VBox createCorrectWordsBox() {
        VBox box = new VBox(10);
        box.getChildren().addAll(correctWordsTitle, correctWordsList);
        return box;
    }

    // update list of correct words 
    private void updateCorrectWordsList(String word) {
        correctWordsList.getItems().add(word);
    }


    private VBox createIncorrectWordsBox() {
        VBox box = new VBox(10);
        box.getChildren().addAll(incorrectWordsTitle, incorrectWordsList);
        return box;
    }

    
    private void updateIncorrectWordsList(String word) {
        incorrectWordsList.getItems().add(word);
    }




}
