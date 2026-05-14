package controller;

import model.User;

/**
 * Top-level controller that manages the current {@link User} and their selected language.
 * Intended as a bridge between the UI and the User model for future persistence or login flows.
 */
public class AppController {
    private User user;

    public AppController(User user) {
        this.user = user;
    }

    /** Persists the chosen language on the user model. */
    public void selectLanguage(String language) {
        user.setLanguage(language);
    }

    /** Prints the main menu options to stdout (used for CLI/debug purposes). */
    public void showMenu() {
        System.out.println("1. Quiz");
        System.out.println("2. Flashcards");
        System.out.println("3. Dictionary");
    }
}
