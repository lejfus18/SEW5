package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BookAnalysisTest {
    private final String sampleText = "Mensch Menschlichkeit unbeschreiblich der Himmel ist blau und wunderbar.";

    @Test
    void testWordCount() {
        assertEquals(8, BookAnalysis.wordCount(sampleText));
    }

    @Test
    void testMainWordCount() {
        assertEquals(5, BookAnalysis.mainWordCount(sampleText));
    }

    @Test
    void testMenschCount() {
        assertEquals(2, BookAnalysis.menschCount(sampleText));
    }

    @Test
    void testLongWords() {
        List<String> result = BookAnalysis.longWords(sampleText);
        assertTrue(result.contains("unbeschreiblich"));
    }
}
