package ui;

import controller.DataController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Word;

import java.util.List;

/**
 * JavaFX application entry point. Owns the primary {@link Stage} and is responsible
 * for constructing and swapping scenes when the user navigates between screens.
 *
 * <p>Each screen (e.g. {@link QuizScreen}) receives a reference to this class so it
 * can trigger navigation back to the menu or to another screen.
 */
public class MainApp extends Application {

    private Stage stage;
    /** The language currently selected on the main menu (empty string means none chosen). */
    private String selectedLanguage = "";
    private final DataController dataController = new DataController();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("SakuraSpeak");
        showMainMenu();
        stage.show();
    }

    public void showMainMenu() {
        stage.setScene(new Scene(new MainMenuScreen(this), 900, 600));
    }

    /** Navigates to the quiz screen. No-op if no language has been selected. */
    public void showQuizScreen() {
        if (selectedLanguage.isEmpty()) return;
        stage.setScene(new Scene(new QuizScreen(this), 900, 600));
    }

    /** Navigates to the flashcard screen. No-op if no language has been selected. */
    public void showFlashcardScreen() {
        if (selectedLanguage.isEmpty()) return;
        stage.setScene(new Scene(new FlashcardScreen(this), 900, 600));
    }

    /** Navigates to the dictionary screen. No-op if no language has been selected. */
    public void showDictionaryScreen() {
        if (selectedLanguage.isEmpty()) return;
        stage.setScene(new Scene(new DictionaryScreen(this), 900, 600));
    }

    public void setSelectedLanguage(String language) {
        this.selectedLanguage = language;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    /** Loads the word list for the currently selected language from the CSV. */
    public List<Word> getWordsForSelectedLanguage() {
        return dataController.loadWords(selectedLanguage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
