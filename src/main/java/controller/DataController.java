package controller;

import model.Word;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Loads vocabulary words from the bundled CSV file ({@code /data/direct_translations.csv}).
 *
 * <p>The CSV uses {@code |} as the delimiter and has the following column layout:
 * <pre>
 *   index | definition | English | Spanish | French | Italian | Portuguese | Romanian | German
 *     0        1           2         3        4         5          6           7         8
 * </pre>
 */
public class DataController {

    /** Maps a language display name to its zero-based column index in the CSV. */
    private static final Map<String, Integer> LANG_COLUMN = Map.of(
            "English",    2,
            "Spanish",    3,
            "French",     4,
            "Italian",    5,
            "Portuguese", 6,
            "Romanian",   7,
            "German",     8
    );

    /**
     * Reads all words for the given language from the CSV.
     *
     * @param language one of the keys in {@link #LANG_COLUMN}
     * @return list of {@link Word}s; empty if the language is unknown or the file cannot be read
     */
    public List<Word> loadWords(String language) {
        List<Word> words = new ArrayList<>();
        int langCol = LANG_COLUMN.getOrDefault(language, -1);
        if (langCol == -1) return words;

        try (InputStream is = getClass().getResourceAsStream("/data/direct_translations.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header row
                String[] parts = line.split("\\|", -1);
                if (parts.length <= langCol) continue; // skip incomplete rows

                String definition = parts[1].trim();
                String englishWord = parts[2].trim();
                String foreignWord = parts[langCol].trim();

                words.add(new Word(foreignWord, englishWord, language, definition));
            }

        } catch (Exception e) {
            System.err.println("Failed to load CSV: " + e.getMessage());
        }

        return words;
    }
}
