package model;

import java.util.List;
import java.util.Random;

/**
 * Manages the state of a single quiz session: randomly selects words from the pool
 * and tracks the word currently being answered.
 */
public class QuizSession {
    private Word currentWord;
    private List<Word> wordPool;
    private Random random = new Random();

    public QuizSession(List<Word> wordPool) {
        this.wordPool = wordPool;
    }

    /** Picks and returns a random word from the pool, making it the current word. */
    public Word getNextWord() {
        currentWord = wordPool.get(random.nextInt(wordPool.size()));
        return currentWord;
    }

    /**
     * Checks {@code answer} against the current word's translation.
     *
     * @return true if the answer is correct; false if no word has been selected yet or answer is wrong
     */
    public boolean submitAnswer(String answer) {
        return currentWord != null && currentWord.checkAnswer(answer);
    }

    public Word getCurrentWord() {
        return currentWord;
    }
}
