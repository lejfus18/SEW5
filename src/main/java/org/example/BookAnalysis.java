package org.example;
import java.util.*;
import java.util.regex.Pattern;

public class BookAnalysis{
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

    public static int longWords(String text) {
        return (int) Pattern.compile("\\b\\w{19,}\\b").matcher(text).results().count();

    }
}