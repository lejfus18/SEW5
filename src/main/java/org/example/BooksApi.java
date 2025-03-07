package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class BooksApi {

    public void fetchAndDisplayBooks() {
        String apiUrl = "https://htl-assistant.vercel.app/api/projects/sew5";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            JsonNode booksNode = jsonResponse.get("books");
            if (booksNode != null && booksNode.isArray()) {
                List<String[]> tableData = new ArrayList<>();
                tableData.add(new String[]{"ID", "Title", "Word Count", "Main Words", "Mensch Count", "Long Words"});

                StringBuilder csvData = new StringBuilder();
                csvData.append("id,title,word_count,main_word_count,mensch_count,long_words\n");

                for (JsonNode book : booksNode) {
                    int id = book.has("id") ? book.get("id").asInt() : 0;
                    String title = book.has("title") ? book.get("title").asText() : "Unknown";
                    String text = book.has("text") ? book.get("text").asText() : "";  // Using full book text now

                    // Perform book analysis
                    int wordCount = BookAnalysis.wordCount(text);
                    int mainWordCount = BookAnalysis.mainWordCount(text);
                    int menschCount = BookAnalysis.menschCount(text);
                    int longWordCount = BookAnalysis.longWords(text);

                    tableData.add(new String[]{
                            String.valueOf(id), title,
                            String.valueOf(wordCount),
                            String.valueOf(mainWordCount),
                            String.valueOf(menschCount),
                            String.valueOf(longWordCount)
                    });

                    csvData.append(String.format("%d,%s,%d,%d,%d,%d\n",
                            id, title, wordCount, mainWordCount, menschCount, longWordCount));
                }

                printTable(tableData);
                Files.write(Paths.get("results.csv"), csvData.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("\n✅ Data successfully written to results.csv!");

            } else {
                System.out.println("❌ No books found in the API response.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Error during the request: " + e.getMessage());
        }
    }

    private void printTable(List<String[]> table) {
        int[] columnWidths = new int[table.get(0).length];

        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        StringBuilder separator = new StringBuilder("+");
        for (int width : columnWidths) {
            separator.append("-".repeat(width + 2)).append("+");
        }

        System.out.println(separator);
        for (int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
            String[] row = table.get(rowIndex);
            System.out.print("|");
            for (int i = 0; i < row.length; i++) {
                System.out.printf(" %-" + columnWidths[i] + "s |", row[i]);
            }
            System.out.println();
            if (rowIndex == 0) System.out.println(separator);
        }
        System.out.println(separator);
    }

    public static void main(String[] args) {
        new BooksApi().fetchAndDisplayBooks();
    }
}
