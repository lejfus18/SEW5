package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelBookAnalyser {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<String> books = List.of(
                "Mensch Menschlichkeit ist wichtig und notwendig.",
                "Die Welt ist voller Möglichkeiten und Abenteuer.",
                "Unbeschreiblich schöne Landschaften inspirieren die Menschen."
        );

        List<Future<Map<String, Object>>> futures = new ArrayList<>();
        for (String book : books) {
            futures.add(executor.submit(new BookAnalysisTask(book)));
        }

        for (Future<Map<String, Object>> future : futures) {
            System.out.println(future.get());
        }

        executor.shutdown();
    }
}
