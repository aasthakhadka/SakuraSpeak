package controller;

import model.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mediates between {@link ui.QuizScreen} and the quiz model classes.
 * Delegates word selection to {@link QuizSession} and records per-word
 * attempt history in a {@link Progress} map for future adaptive difficulty use.
 */
public class QuizController {
    private QuizSession session;
    /** Tracks correct/incorrect attempts for every word in the loaded pool. */
    private Map<Word, Progress> progressMap;

    public QuizController(List<Word> words) {
        session = new QuizSession(words);
        progressMap = new HashMap<>();
        for (Word w : words) {
            progressMap.put(w, new Progress(w));
        }
    }

    /** Picks the next random word and returns it to be displayed as the question. */
    public Word generateQuestion() {
        return session.getNextWord();
    }

    /**
     * Submits {@code answer} for the current word, records the result, and returns whether it was correct.
     */
    public boolean submitAnswer(String answer) {
        Word current = session.getCurrentWord();
        boolean correct = session.submitAnswer(answer);
        Progress p = progressMap.get(current);
        if (correct) p.recordCorrect();
        else p.recordIncorrect();
        return correct;
    }

    public Word getCurrentWord() {
        return session.getCurrentWord();
    }
}
