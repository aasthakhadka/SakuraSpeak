package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides substring search over a list of {@link Word}s by either
 * the foreign-language word or its English translation.
 */
public class Dictionary {
    private List<Word> wordList;

    public Dictionary(List<Word> wordList) {
        this.wordList = wordList;
    }

    /**
     * Returns all words whose foreign-language text contains {@code query} (case-insensitive).
     */
    public List<Word> searchByWord(String query) {
        List<Word> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Word w : wordList) {
            if (w.getWord().toLowerCase().contains(q)) {
                results.add(w);
            }
        }
        return results;
    }

    /**
     * Returns all words whose English translation contains {@code query} (case-insensitive).
     */
    public List<Word> searchByTranslation(String query) {
        List<Word> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Word w : wordList) {
            if (w.getTranslation().toLowerCase().contains(q)) {
                results.add(w);
            }
        }
        return results;
    }
}
