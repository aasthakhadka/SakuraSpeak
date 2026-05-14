package model;

/**
 * Represents a single vocabulary entry: a foreign word paired with its English translation,
 * the language it belongs to, and an English definition.
 */
public class Word {
    private String word;
    private String translation;
    private String language;
    private String definition;

    public Word(String word, String translation, String language, String definition) {
        this.word = word;
        this.translation = translation;
        this.language = language;
        this.definition = definition;
    }

    /** The foreign-language word. */
    public String getWord() {
        return word;
    }

    /** The English translation of this word. */
    public String getTranslation() {
        return translation;
    }

    /** The language this word belongs to (e.g. "Spanish", "French"). */
    public String getLanguage() {
        return language;
    }

    /** An English definition or context sentence for this word. */
    public String getDefinition() {
        return definition;
    }

    /** Returns true if {@code userAnswer} matches the English translation (case-insensitive). */
    public boolean checkAnswer(String userAnswer) {
        return translation.equalsIgnoreCase(userAnswer.trim());
    }
}
