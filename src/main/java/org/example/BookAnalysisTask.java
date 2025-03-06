package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

class BookAnalysisTask implements Callable<Map<String, Object>> {
    private final String text;

    public BookAnalysisTask(String text) {
        this.text = text;
    }

    @Override
    public Map<String, Object> call() {
        Map<String, Object> analysisResult = new HashMap<>();
        analysisResult.put("wordCount", BookAnalysis.wordCount(text));
        analysisResult.put("mainWordCount", BookAnalysis.mainWordCount(text));
        analysisResult.put("menschCount", BookAnalysis.menschCount(text));
        analysisResult.put("longWords", BookAnalysis.longWords(text));
        return analysisResult;
    }
}


