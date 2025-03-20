package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.BookAnalysis.writeResultsToFile;

public class BooksApi {

    public void fetchAndInsertBooksIntoDatabase() {
        // Start time measurement (GET request)
        long startTime = System.currentTimeMillis();

        String apiUrl = "https://htl-assistant.vercel.app/api/projects/sew5";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            // Send the request and get the response from the API
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.body());

            JsonNode booksNode = jsonResponse.get("books");
            List<String[]> tableData = new ArrayList<>();
            tableData.add(new String[]{"ID", "Title", "Word Count", "Main Word Count", "Mensch Count", "Long Words"});

            List<Map<String, Object>> analysisResults = new ArrayList<>();

            if (booksNode != null && booksNode.isArray()) {
                // Iterate over each book in the response
                for (JsonNode book : booksNode) {
                    String title = book.has("title") ? book.get("title").asText() : "Unknown";
                    String text = book.has("text") ? book.get("text").asText() : "";  // Book text

                    // Perform book analysis using the BookAnalysis methods
                    int wordCount = BookAnalysis.wordCount(text);
                    int mainWordCount = BookAnalysis.mainWordCount(text);
                    int menschCount = BookAnalysis.menschCount(text);
                    List<String> longWords = BookAnalysis.longWords(text);

                    // Convert the long words list to a comma-separated string
                    String longWordsStr = String.join(", ", longWords);

                    // Add the data to the table
                    tableData.add(new String[] {
                            String.valueOf(tableData.size()), title,
                            String.valueOf(wordCount),
                            String.valueOf(mainWordCount),
                            String.valueOf(menschCount),
                            longWordsStr
                    });

                    // Insert the analyzed data into the database
                    DatabaseConnection.insertResult(title, wordCount, mainWordCount, menschCount, longWordsStr);

                    // Store analysis results for file writing
                    Map<String, Object> result = new HashMap<>();
                    result.put("word_count", wordCount);
                    result.put("main_word_count", mainWordCount);
                    result.put("mensch_count", menschCount);
                    result.put("long_words", longWords);
                    analysisResults.add(result);
                }

                // After inserting into the database, print the table
                printTable(tableData);
                System.out.println("Data successfully inserted into the database.");

                // Write the results to a file
                writeResultsToFile(analysisResults, "results.csv");
                System.out.println("Analysis results written to results.csv");

            } else {
                System.out.println("No books found in the API response.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error during the request: " + e.getMessage());
        }

        // End time measurement (POST request)
        long endTime = System.currentTimeMillis();

        // Calculate the total time in milliseconds
        long totalTime = endTime - startTime;

        // Output the total time
        System.out.println("Total time: " + totalTime + " milliseconds");
    }

    // Print the table in a readable format
    private void printTable(List<String[]> table) {
        int[] columnWidths = new int[table.get(0).length];

        // Calculate the maximum width of each column
        for (String[] row : table) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        // Build the separator row
        StringBuilder separator = new StringBuilder("+");
        for (int width : columnWidths) {
            separator.append("-".repeat(width + 2)).append("+");
        }

        // Print the table
        System.out.println(separator);
        for (int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
            String[] row = table.get(rowIndex);
            System.out.print("|");
            for (int i = 0; i < row.length; i++) {
                System.out.printf(" %-" + columnWidths[i] + "s |", row[i]);
            }
            System.out.println();
            if (rowIndex == 0) System.out.println(separator); // print the separator after the header
        }
        System.out.println(separator);
    }

    public static void main(String[] args) {
        new BooksApi().fetchAndInsertBooksIntoDatabase();
    }
}
