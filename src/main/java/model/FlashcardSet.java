package model;

import java.util.List;

/**
 * An ordered deck of {@link Word}s with sequential and random navigation.
 * Tracks which side of the card is currently shown (front = foreign word, back = English translation).
 */
public class FlashcardSet {
    private List<Word> wordList;
    private int currentIndex = 0;
    private boolean showingFront = true;

    public FlashcardSet(List<Word> wordList) {
        this.wordList = wordList;
    }

    /** Advances to the next card (wraps around) and resets to the front side. */
    public Word nextCard() {
        currentIndex = (currentIndex + 1) % wordList.size();
        showingFront = true;
        return wordList.get(currentIndex);
    }

    /** Steps back to the previous card (wraps around) and resets to the front side. */
    public Word previousCard() {
        currentIndex = (currentIndex - 1 + wordList.size()) % wordList.size();
        showingFront = true;
        return wordList.get(currentIndex);
    }

    /**
     * Toggles between front (definition) and back (foreign word) of the current card.
     *
     * @return the text that should now be displayed
     */
    public String flipCard() {
        showingFront = !showingFront;
        Word current = wordList.get(currentIndex);
        return showingFront ? current.getDefinition() : current.getWord();
    }

    public Word getCurrentWord() {
        return wordList.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    /** Jumps directly to the card at {@code index} without resetting the flip state. */
    public void goToIndex(int index) {
        currentIndex = index;
    }
}
