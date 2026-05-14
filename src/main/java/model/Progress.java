package model;

/**
 * Records how many times a word has been answered correctly or incorrectly
 * and computes a priority score used to surface harder words more often.
 */
public class Progress {
    private Word word;
    private int correctAttempts;
    private int incorrectAttempts;

    public Progress(Word word) {
        this.word = word;
    }

    public void recordCorrect() {
        correctAttempts++;
    }

    public void recordIncorrect() {
        incorrectAttempts++;
    }

    /**
     * Returns a score in [0, 1) that increases as the ratio of incorrect answers grows.
     * The +1 in the denominator prevents division by zero on the first attempt.
     */
    public double getPriorityScore() {
        return (double) incorrectAttempts / (correctAttempts + incorrectAttempts + 1);
    }

    public Word getWord() {
        return word;
    }
}
