package controller;

import model.Dictionary;
import model.Word;

import java.util.List;

/**
 * Mediates between {@link ui.DictionaryScreen} and {@link Dictionary}.
 * Exposes search by foreign word and search by English translation.
 */
public class DictionaryController {
    private Dictionary dictionary;

    public DictionaryController(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Returns all words whose foreign-language text contains {@code input} (case-insensitive).
     */
    public List<Word> searchWord(String input) {
        return dictionary.searchByWord(input);
    }

    /**
     * Returns all words whose English translation contains {@code input} (case-insensitive).
     */
    public List<Word> searchByTranslation(String input) {
        return dictionary.searchByTranslation(input);
    }

    /** Prints a word's translation and definition to stdout (used for CLI/debug purposes). */
    public void displayDefinition(Word word) {
        System.out.println(word.getWord() + " - " + word.getTranslation());
        System.out.println(word.getDefinition());
    }
}
