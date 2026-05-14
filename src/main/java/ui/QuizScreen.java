package ui;

import controller.QuizController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Word;

/**
 * Quiz screen: shows a foreign-language word and asks the user to type its English translation.
 *
 * <p>Flow per question:
 * <ol>
 *   <li>Foreign word displayed; answer field is editable.</li>
 *   <li>User submits (button or Enter key).</li>
 *   <li>Feedback and correct answer revealed; "Next" button appears.</li>
 *   <li>Clicking "Next" loads a new random word and resets the view.</li>
 * </ol>
 */
public class QuizScreen extends BorderPane {

    // ---- Button style constants ----
    private static final String SUBMIT_BTN =
            "-fx-background-color: #3498db; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;";

    private static final String MENU_BTN =
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;";

    private static final String BTN_STYLE =
            "-fx-background-color: white; -fx-border-color: black; " +
                    "-fx-border-width: 2; -fx-background-radius: 0; -fx-border-radius: 0;";

    private final QuizController quiz;
    private final Label foreignWordLabel = new Label();
    private final TextField answerField = new TextField();
    private final Label feedbackLabel = new Label();       // "Correct!" or "Incorrect."
    private final Label correctAnswerLabel = new Label();  // shows the right answer after submission
    private final Button nextBtn = new Button("Next");

    public QuizScreen(MainApp app) {
        quiz = new QuizController(app.getWordsForSelectedLanguage());

        // Top bar: language badge (centre) + menu button (right)
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(12, 16, 12, 16));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Label langLabel = new Label(app.getSelectedLanguage());
        langLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2c3e50;" +
                        "-fx-padding: 6 12 6 12;" +
                        "-fx-background-color: #3498db;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1;"
        );
        langLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        HBox langBox = new HBox(langLabel);
        langBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(langBox, Priority.ALWAYS);

        Button menuBtn = makeButton("Menu", 80, 32);
        menuBtn.setStyle(MENU_BTN);
        menuBtn.setOnAction(e -> app.showMainMenu());

        topBar.getChildren().addAll(langBox, menuBtn);
        setTop(topBar);

        // Center: feedback row, word + answer input row, action buttons
        VBox center = new VBox(24);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(40));

        // Feedback labels (hidden until the user submits an answer)
        feedbackLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        feedbackLabel.setVisible(false);

        correctAnswerLabel.setFont(Font.font("Arial", 14));
        correctAnswerLabel.setVisible(false);

        VBox feedbackBox = new VBox(6, feedbackLabel, correctAnswerLabel);
        feedbackBox.setAlignment(Pos.CENTER);

        // Foreign word display box
        foreignWordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        foreignWordLabel.setPrefWidth(360);
        foreignWordLabel.setPrefHeight(120);
        foreignWordLabel.setAlignment(Pos.CENTER);
        foreignWordLabel.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 12;");

        // Answer input field
        answerField.setPromptText("Type translation here...");
        answerField.setPrefWidth(360);
        answerField.setPrefHeight(120);
        answerField.setStyle(
                "-fx-background-color: white; -fx-border-color: black; " +
                        "-fx-border-width: 2; -fx-background-radius: 0; -fx-font-size: 16px; -fx-padding: 12;"
        );

        HBox inputRow = new HBox(24, foreignWordLabel, answerField);
        inputRow.setAlignment(Pos.CENTER);

        // Submit / Next buttons
        Button submitBtn = makeButton("Submit", 120, 36);
        nextBtn.setPrefWidth(120);
        nextBtn.setPrefHeight(36);
        submitBtn.setStyle(SUBMIT_BTN);
        nextBtn.setStyle(BTN_STYLE);
        nextBtn.setVisible(false); // revealed after each submission

        HBox btnRow = new HBox(12, submitBtn, nextBtn);
        btnRow.setAlignment(Pos.CENTER);

        // Pressing Enter in the answer field is equivalent to clicking Submit
        submitBtn.setOnAction(e -> handleSubmit());
        answerField.setOnAction(e -> handleSubmit());
        nextBtn.setOnAction(e -> loadNextWord());

        center.getChildren().addAll(feedbackBox, inputRow, btnRow);
        setCenter(center);

        loadNextWord();
    }

    /** Evaluates the typed answer, shows feedback, and reveals the Next button. */
    private void handleSubmit() {
        String userAnswer = answerField.getText().trim();
        if (userAnswer.isEmpty()) return;

        boolean correct = quiz.submitAnswer(userAnswer);
        Word current = quiz.getCurrentWord();

        feedbackLabel.setText(correct ? "Correct!" : "Incorrect.");
        feedbackLabel.setStyle(correct
                ? "-fx-text-fill: green;"
                : "-fx-text-fill: red;");
        feedbackLabel.setVisible(true);

        correctAnswerLabel.setText("Answer: " + current.getTranslation());
        correctAnswerLabel.setVisible(true);

        nextBtn.setVisible(true);
        answerField.setEditable(false); // prevent editing after submission
    }

    /** Loads the next random word and resets all per-question UI state. */
    private void loadNextWord() {
        Word w = quiz.generateQuestion();
        foreignWordLabel.setText(w.getWord());
        answerField.clear();
        answerField.setEditable(true);
        feedbackLabel.setVisible(false);
        correctAnswerLabel.setVisible(false);
        nextBtn.setVisible(false);
        answerField.requestFocus();
    }

    private Button makeButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefWidth(width);
        btn.setPrefHeight(height);
        btn.setStyle(BTN_STYLE);
        return btn;
    }
}
