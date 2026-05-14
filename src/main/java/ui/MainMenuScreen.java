package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The application's home screen. Displays three navigation buttons (Quiz, Flashcards, Dictionary)
 * on the left and a language selector panel on the right.
 *
 * <p>Selecting a language highlights the chosen button and updates the selected language in
 * {@link MainApp}. The navigation buttons delegate to the corresponding show*Screen methods on
 * {@link MainApp}; those methods are no-ops until a language is selected.
 */
public class MainMenuScreen extends BorderPane {

    /** The currently highlighted language button; null when no language is selected. */
    private Button selectedLangButton = null;

    // ---- Button style constants ----

    private static final String LANG_BTN =
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;" +
                     "-fx-background-radius: 15; -fx-border-radius: 15;";
    private static final String QUIZ_BTN =
            "-fx-background-color: #2ecc71; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;" +
                    "-fx-background-radius: 15; -fx-border-radius: 15;";

    private static final String FLASH_BTN =
            "-fx-background-color: #3498db; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;" +
                    "-fx-background-radius: 15; -fx-border-radius: 15;";

    private static final String DICT_BTN =
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;" +
                    "-fx-background-radius: 15; -fx-border-radius: 15;";

    /** Default (unselected) style for language buttons. */
    private static final String BTN_DEFAULT =
            "-fx-background-color: white; -fx-border-color: black; " +
                    "-fx-border-width: 2; -fx-background-radius: 15; -fx-border-radius: 15;";

    /** Style applied to the currently selected language button. */
    private static final String BTN_SELECTED =
            "-fx-background-color: black; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2; " +
                    "-fx-background-radius: 0; -fx-border-radius: 0;";

    public MainMenuScreen(MainApp app) {

        // Header bar with colour-coded section labels
        HBox headerBar = new HBox();
        headerBar.setPrefHeight(50);

        Label quizHeader = new Label("IMPARA LA DIZIONE");
        quizHeader.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 25px;");
        quizHeader.setMaxWidth(Double.MAX_VALUE);
        quizHeader.setAlignment(Pos.CENTER);

        Label flashHeader = new Label("LEARN DICTION");
        flashHeader.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 25px;");
        flashHeader.setMaxWidth(Double.MAX_VALUE);
        flashHeader.setAlignment(Pos.CENTER);

        Label dictHeader = new Label("APRENDE DICCION");
        dictHeader.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-size: 25px;");
        dictHeader.setMaxWidth(Double.MAX_VALUE);
        dictHeader.setAlignment(Pos.CENTER);

        HBox.setHgrow(quizHeader, Priority.ALWAYS);
        HBox.setHgrow(flashHeader, Priority.ALWAYS);
        HBox.setHgrow(dictHeader, Priority.ALWAYS);

        headerBar.getChildren().addAll(quizHeader, flashHeader, dictHeader);
        setTop(headerBar);

        // Left panel: navigation buttons
        VBox navBox = new VBox(16);
        navBox.setPadding(new Insets(60, 40, 40, 60));
        navBox.setAlignment(Pos.CENTER_LEFT);

        Button quizBtn = makeButton("Dictionary Quiz", 200, 50);
        Button flashBtn = makeButton("Flashcard Study", 200, 50);
        Button dictBtn = makeButton("Dictionary", 200, 50);

        quizBtn.setOnAction(e -> app.showQuizScreen());
        flashBtn.setOnAction(e -> app.showFlashcardScreen());
        dictBtn.setOnAction(e -> app.showDictionaryScreen());

        navBox.getChildren().addAll(quizBtn, flashBtn, dictBtn);

        // Right panel: language selection
        VBox langBox = new VBox(12);
        langBox.setPadding(new Insets(40, 60, 40, 40));
        langBox.setAlignment(Pos.CENTER);

        Label langLabel = new Label("Language Selection");
        langLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox langButtons = new VBox(8);
        langButtons.setAlignment(Pos.CENTER);

        String[] languages = {"Spanish", "French", "Italian", "Portuguese", "Romanian", "German"};
        for (String lang : languages) {
            Button btn = makeButton(lang, 260, 40);
            btn.setStyle(LANG_BTN);
            btn.setOnAction(e -> {
                // Deselect the previously chosen language button
                if (selectedLangButton != null) {
                    selectedLangButton.setStyle(BTN_DEFAULT);
                }
                selectedLangButton = btn;
                btn.setStyle(BTN_SELECTED);
                app.setSelectedLanguage(lang);
            });

            // Restore visual selection state when returning from another screen
            if (lang.equals(app.getSelectedLanguage())) {
                btn.setStyle(BTN_SELECTED);
                selectedLangButton = btn;
            }

            langButtons.getChildren().add(btn);
        }

        langBox.getChildren().addAll(langLabel, langButtons);

        HBox center = new HBox();
        HBox.setHgrow(navBox, Priority.ALWAYS);
        HBox.setHgrow(langBox, Priority.ALWAYS);
        center.getChildren().addAll(navBox, langBox);
        setCenter(center);
    }

    /**
     * Creates a sized button and applies the colour style matching its label.
     * Language buttons receive their style from the caller instead.
     */
    private Button makeButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefWidth(width);
        btn.setPrefHeight(height);

        switch (text) {
            case "Dictionary Quiz":
                btn.setStyle(QUIZ_BTN);
                break;
            case "Flashcard Study":
                btn.setStyle(FLASH_BTN);
                break;
            case "Dictionary":
                btn.setStyle(DICT_BTN);
                break;
        }

        return btn;
    }
}
