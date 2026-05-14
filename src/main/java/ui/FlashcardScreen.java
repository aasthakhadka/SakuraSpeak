package ui;

import controller.FlashcardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Word;

/**
 * Flashcard study screen. Displays one word at a time; the user can:
 * <ul>
 *   <li>Flip — toggle between the foreign word and its English translation.</li>
 *   <li>Prev / Next — step through the deck sequentially.</li>
 *   <li>Random — jump to a random card.</li>
 * </ul>
 * Navigating to the next or previous card always resets the view to the foreign-word side.
 */
public class FlashcardScreen extends BorderPane {

    // ---- Button style constants ----
    private static final String MENU_BTN =
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;";

    private static final String BTN_STYLE =
            "-fx-background-color: white; -fx-border-color: black; " +
                    "-fx-border-width: 2; -fx-background-radius: 0; -fx-border-radius: 0;";

    private final FlashcardController flashcards;
    private final Label cardLabel = new Label();       // main text displayed on the card
    private final Label cardIndexLabel = new Label();  // "3 / 50" counter
    /** True when the card is showing the foreign word; false when showing the English translation. */
    private boolean showingForeign = true;

    public FlashcardScreen(MainApp app) {
        flashcards = new FlashcardController(app.getWordsForSelectedLanguage());

        // Top bar: language badge (centre) + menu button (right)
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(12, 16, 12, 16));
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Label langLabel = new Label(app.getSelectedLanguage());
        langLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
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
        HBox langBox = new HBox(langLabel);
        langBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(langBox, Priority.ALWAYS);

        Button menuBtn = makeButton("Menu", 80, 32);
        menuBtn.setStyle(MENU_BTN);
        menuBtn.setOnAction(e -> app.showMainMenu());

        topBar.getChildren().addAll(langBox, menuBtn);
        setTop(topBar);

        // Card display
        cardLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        cardLabel.setWrapText(true);
        cardLabel.setAlignment(Pos.CENTER);
        cardLabel.setMaxWidth(420);
        cardLabel.setMaxHeight(240);

        StackPane card = new StackPane(cardLabel);
        card.setPrefSize(420, 240);
        card.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        card.setPadding(new Insets(20));

        cardIndexLabel.setFont(Font.font("Arial", 13));

        // Navigation and flip buttons
        Button flipBtn   = makeButton("Flip",    100, 36);
        Button prevBtn   = makeButton("< Prev",  100, 36);
        Button nextBtn   = makeButton("Next >",  100, 36);
        Button randomBtn = makeButton("Random",  100, 36);

        randomBtn.setOnAction(e -> {
            showingForeign = true;
            flashcards.goToRandom();
            updateCard();
        });

        flipBtn.setOnAction(e -> handleFlip());
        prevBtn.setOnAction(e -> handlePrev());
        nextBtn.setOnAction(e -> handleNext());

        HBox btnRow = new HBox(16, prevBtn, flipBtn, nextBtn, randomBtn);
        btnRow.setAlignment(Pos.CENTER);

        VBox center = new VBox(20, card, cardIndexLabel, btnRow);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(40));
        setCenter(center);

        updateCard();
    }

    /** Toggles the card between its foreign-word and English-translation sides. */
    private void handleFlip() {
        showingForeign = !showingForeign;
        Word current = flashcards.getCurrentWord();
        cardLabel.setText(showingForeign ? current.getWord() : current.getTranslation());
    }

    private void handleNext() {
        showingForeign = true; // always show foreign word on navigation
        flashcards.nextCard();
        updateCard();
    }

    private void handlePrev() {
        showingForeign = true; // always show foreign word on navigation
        flashcards.previousCard();
        updateCard();
    }

    /** Refreshes the card text and position counter from the current controller state. */
    private void updateCard() {
        Word current = flashcards.getCurrentWord();
        cardLabel.setText(current.getWord());
        cardIndexLabel.setText(flashcards.getIndex() + " / " + flashcards.getTotal());
    }

    private Button makeButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefWidth(width);
        btn.setPrefHeight(height);
        btn.setStyle(BTN_STYLE);
        return btn;
    }
}
