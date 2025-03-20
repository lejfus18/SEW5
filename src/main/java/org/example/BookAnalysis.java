package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        // Compiles a regex to find whole words at least 19 characters long
        return Pattern.compile("\\b\\w{19,}\\b")
                .matcher(text)
                .results()  // Finds all matches for the pattern
                .map(matchResult -> matchResult.group())  // Extracts the matched word from each match result
                .collect(Collectors.toList());  // Collects all matched words into a List
    }

    public static void writeResultsToFile(List<Map<String, Object>> results, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("id\ttitle\tword_count\tmain_word_count\tmensch_count\tlong_words\n");

        int id = 1;
        for (Map<String, Object> result : results) {
            sb.append(id++).append("\t");
            sb.append("title").append("\t");
            sb.append(result.get("word_count")).append("\t");
            sb.append(result.get("main_word_count")).append("\t");
            sb.append(result.get("mensch_count")).append("\t");
            sb.append(String.join(", ", (List<String>) result.get("long_words"))).append("\n");
        }

        Files.write(Path.of(filePath), sb.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}