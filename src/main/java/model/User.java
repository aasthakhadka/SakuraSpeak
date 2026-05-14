package model;

/**
 * Represents an app user with a chosen language and cumulative score.
 * Intended as the foundation for future user persistence / login features.
 */
public class User {
    private int userID;
    private String username;
    private String selectedLanguage;
    private int totalScore;

    public User(int userID, String username) {
        this.userID = userID;
        this.username = username;
        this.selectedLanguage = "";
        this.totalScore = 0;
    }

    public void setLanguage(String language) {
        this.selectedLanguage = language;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    /** Adds {@code points} to the running total score. */
    public void updateScore(int points) {
        totalScore += points;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
