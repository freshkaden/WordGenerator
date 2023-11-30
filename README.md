<h1 align="center">New York Times Spelling Bee</h1>
<img src="https://github.com/freshkaden/WordGenerator/assets/66493708/d9026236-241d-466b-bb20-02766f09caef" />


<h2 align="left">Authors</h2>
Kaden C,
Collin F

<h2 align="left">Description</h2>
This Java Project is an implementation of the New York Times Spelling Bee game using JavaFX. The game challenges players to construct words using a set of letters, with a specific letter at the center. The goal is to create valid words, adhering to certain rules.

<h2 align="left">Features</h2>
- Word validation and recognition
- Counting correct and incorrect words
- Displaying a list for correct words entered, and a list for incorret words entered
- Special recognition if user enters a pangram (word using all the letters)
- Simple and easy to use graphical interface


<h2 align="left">Download</h2>
Clone the Repository:
bash
Copy code
git clone https://github.com/freshkaden/WordGenerator.git
cd project

<h2 align="left">How To Play</h2>

Open the project in your favorite Java IDE.
Locate the SpellingBee class and run the main method.
Enter words in the provided input field.
Press Enter to submit the word.
Make sure your word follows the rules!

Game Rules:
Construct words using at least 4 letters.
Include the bolded center letter in each word.
Recognizes pangrams (words using all available letters).
Exiting the Game:
Type "exit" in the input field to exit the application.

<h2 align="left">Dependencies</h2>
Java 8 or higher
JavaFX library

<h2 align="left">Further Improvements</h2>
- error handling for user entering space instead of letters so it will not count against their score,
- expanding beyond preset letters of ABCDRKY
- make the javafx vbox and hbox more adaptable to smaller screen sizes using adjustable padding
- repeated correct words dont count against score, but repeated incorrect words currently do





