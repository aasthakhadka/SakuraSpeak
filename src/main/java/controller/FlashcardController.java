package controller;

import model.FlashcardSet;
import model.Word;

import java.util.List;

/**
 * Mediates between {@link ui.FlashcardScreen} and {@link FlashcardSet}.
 * Exposes navigation, flip, and random-jump actions and converts the
 * zero-based internal index to a 1-based display value.
 */
public class FlashcardController {
    private FlashcardSet flashcards;
    private int total;

    public FlashcardController(List<Word> words) {
        flashcards = new FlashcardSet(words);
        total = words.size();
    }

    /** Advances to the next card and returns it. */
    public Word nextCard() {
        return flashcards.nextCard();
    }

    /** Steps back to the previous card. */
    public void previousCard() {
        flashcards.previousCard();
    }

    public Word getCurrentWord() {
        return flashcards.getCurrentWord();
    }

    /**
     * Flips the current card between its foreign-word and English-translation sides.
     *
     * @return the text that should now be displayed on the card
     */
    public String flipCard() {
        return flashcards.flipCard();
    }

    /** Returns the 1-based position of the current card (for display as "3 / 50"). */
    public int getIndex() {
        return flashcards.getCurrentIndex() + 1;
    }

    public int getTotal() {
        return total;
    }

    /** Jumps to a uniformly random card in the deck. */
    public void goToRandom() {
        int randomIndex = (int) (Math.random() * total);
        flashcards.goToIndex(randomIndex);
    }
}
