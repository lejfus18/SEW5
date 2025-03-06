package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

public class BookAnalysisTest {
    BookAnalysis book = new BookAnalysis();

    @Test
    void testWordCount() {
        assertEquals(8, book.wordCount("this sentence has eight words in its structure"));
    }

    @Test
    void testMainWordCount() {
        assertEquals(8, book.mainWordCount("this sentence has eight words in its structure"));
    }

    @Test
    void testMenschCount() {
        assertEquals(2, book.menschCount("Mensch oder menschen"));
    }

    @Test
    void testLongWords() {
        assertEquals(1, book.longWords("immunohistochemical"));
    }
}
