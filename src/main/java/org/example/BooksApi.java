package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksApi {

    public void fetchAndInsertBooksIntoDatabase() {
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
                    tableData.add(new String[]{
                            String.valueOf(tableData.size()), title,
                            String.valueOf(wordCount),
                            String.valueOf(mainWordCount),
                            String.valueOf(menschCount),
                            longWordsStr
                    });

                    // Insert the analyzed data into the database
                    DatabaseConnection.insertResult(title, wordCount, mainWordCount, menschCount, longWordsStr);
                }

                // After inserting into the database, print the table
                printTable(tableData);
                System.out.println("✅ Data successfully inserted into the database.");
            } else {
                System.out.println("❌ No books found in the API response.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Error during the request: " + e.getMessage());
        }
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
