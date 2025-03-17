package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class BookAnalysis {
    private static final List<String> STOP_WORDS = Arrays.asList("und", "oder", "der", "die", "das", "ein", "eine");

    public static int wordCount(String text) {
        return text.split("\\s+").length;
    }

    public static int mainWordCount(String text) {
        return (int) Arrays.stream(text.split("\\s+")).filter(word -> !STOP_WORDS.contains(word.toLowerCase())).count();
    }

    public static int menschCount(String text) {
        return (int) Pattern.compile("(?i)mensch").matcher(text).results().count();
    }

    public static List<String> longWords(String text) {
        return Arrays.stream(text.split("\\s+")).filter(word -> word.length() > 18).toList();
    }

    public static void writeResultsToFile(List<Map<String, Object>> results, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("id\ttitle\tword_count\tmain_word_count\tmensch_count\tlong_words\n");

        int id = 1;
        for (Map<String, Object> result : results) {
            sb.append(id++).append("\t");
            sb.append("Buchtitel").append("\t");
            sb.append(result.get("wordCount")).append("\t");
            sb.append(result.get("mainWordCount")).append("\t");
            sb.append(result.get("menschCount")).append("\t");
            sb.append(String.join(", ", (List<String>) result.get("longWords"))).append("\n");
        }

        Files.write(Path.of(filePath), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}