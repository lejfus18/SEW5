package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BooksApi {
    public void ShowBook() {

            String apiUrl = "https://htl-assistant.vercel.app/api/projects/sew5";

            // Create an HttpClient instance (Java 11's HttpClient)
            HttpClient client = HttpClient.newHttpClient();

            // Create an HttpRequest to the API URL
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))  // Set the URI
                    .GET()  // Specify the GET method
                    .build();

            try {
                // Send the GET request and get the response as a string
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Parse the JSON response using Jackson's ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.body());

                // Pretty-print the entire JSON response to the console
                System.out.println("Response JSON: ");
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonResponse));

                // Example: Accessing a field in the JSON (assuming the response has a 'books' field)
                JsonNode booksNode = jsonResponse.get("books");
                if (booksNode != null) {
                    System.out.println("Books: ");
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booksNode));
                } else {
                    System.out.println("No books found in the response.");
                }

            } catch (IOException | InterruptedException e) {
                // Handle any errors (e.g., network issues, parsing errors)
                System.err.println("Error during the request: " + e.getMessage());
            }
        }

    }

