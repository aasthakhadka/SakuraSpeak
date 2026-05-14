package ui;

import controller.DictionaryController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Dictionary;
import model.Word;

import java.util.List;

/**
 * Dictionary screen: a searchable, scrollable list of all words for the selected language.
 *
 * <p>Search matches both the foreign word and its English translation simultaneously.
 * Results update live as the user types. Clicking a result shows the word's full definition.
 */
public class DictionaryScreen extends BorderPane {

    // ---- Button style constants ----
    private static final String MENU_BTN =
            "-fx-background-color: #f39c12; -fx-text-fill: white; " +
                    "-fx-border-color: black; -fx-border-width: 2;";

    private static final String BTN_STYLE =
            "-fx-background-color: white; -fx-border-color: black; " +
                    "-fx-border-width: 2; -fx-background-radius: 0; -fx-border-radius: 0;";

    private final DictionaryController dictController;
    private final List<Word> allWords;
    private final ListView<String> resultsView = new ListView<>();
    /** The Word objects backing the current list view rows (parallel to resultsView items). */
    private List<Word> currentResults;

    public DictionaryScreen(MainApp app) {
        allWords = app.getWordsForSelectedLanguage();
        dictController = new DictionaryController(new Dictionary(allWords));

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

        // Center: search bar, results list, detail panel
        VBox center = new VBox(16);
        center.setPadding(new Insets(32));
        center.setAlignment(Pos.TOP_CENTER);

        // Search field with placeholder text
        TextField searchField = new TextField();
        searchField.setPromptText("Search by English or " + app.getSelectedLanguage() + " word...");
        searchField.setPrefWidth(500);
        searchField.setStyle(
                "-fx-background-color: white; -fx-border-color: black; " +
                        "-fx-border-width: 2; -fx-background-radius: 0;"
        );

        Button searchBtn = makeButton("Search", 100, 32);
        searchBtn.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 6 14 6 14;" +
                        "-fx-background-color: #3498db;" +
                        "-fx-background-radius: 20;"
        );

        HBox searchRow = new HBox(12, searchField, searchBtn);
        searchRow.setAlignment(Pos.CENTER);

        // Scrollable results list
        resultsView.setPrefHeight(340);
        resultsView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-background-radius: 0;");

        // Detail panel shown when a result row is selected
        Label detailLabel = new Label();
        detailLabel.setFont(Font.font("Arial", 14));
        detailLabel.setWrapText(true);
        detailLabel.setPadding(new Insets(8));
        detailLabel.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        detailLabel.setPrefWidth(500);
        detailLabel.setMinHeight(60);
        detailLabel.setVisible(false);

        // Show word + translation + definition when the user selects a list row
        resultsView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int idx = newVal.intValue();
            if (currentResults != null && idx >= 0 && idx < currentResults.size()) {
                Word w = currentResults.get(idx);
                detailLabel.setText(
                        w.getWord() + "  →  " + w.getTranslation() + "\n" + w.getDefinition()
                );
                detailLabel.setVisible(true);
            }
        });

        // Search logic: merge results from both the foreign-word and translation searches,
        // then de-duplicate while preserving foreign-word results first.
        Runnable doSearch = () -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                currentResults = allWords;
            } else {
                List<Word> byForeign = dictController.searchWord(query);
                List<Word> byEnglish = dictController.searchByTranslation(query);
                byForeign.addAll(byEnglish.stream()
                        .filter(w -> !byForeign.contains(w))
                        .toList());
                currentResults = byForeign;
            }
            populateList();
            detailLabel.setVisible(false);
        };

        searchBtn.setOnAction(e -> doSearch.run());
        searchField.setOnAction(e -> doSearch.run());
        // Live filtering: results update on every keystroke
        searchField.textProperty().addListener((obs, oldVal, newVal) -> doSearch.run());

        center.getChildren().addAll(searchRow, resultsView, detailLabel);
        setCenter(center);

        // Populate with the full word list on first load
        currentResults = allWords;
        populateList();
    }

    /** Rebuilds the list view from the current {@code currentResults} list. */
    private void populateList() {
        resultsView.getItems().clear();
        for (Word w : currentResults) {
            resultsView.getItems().add(w.getWord() + "  →  " + w.getTranslation());
        }
    }

    private Button makeButton(String text, double width, double height) {
        Button btn = new Button(text);
        btn.setPrefWidth(width);
        btn.setPrefHeight(height);
        btn.setStyle(BTN_STYLE);
        return btn;
    }
}
