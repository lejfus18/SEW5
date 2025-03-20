package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://htl-projekt.com:3306/2024_4by_lejdifusha_video";  // Database URL
    private static final String USER = "lejdifusha";  // MySQL username
    private static final String PASSWORD = "!Insy_2023$";  // MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }

    // Method to insert results into the 'results' table
    public static void insertResult(String title, int wordCount, int mainWordCount, int menschCount, String longWords) {
        String query = "INSERT INTO results (title, word_count, main_word_count, mensch_count, long_words) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters for the query
            statement.setString(1, title);
            statement.setInt(2, wordCount);
            statement.setInt(3, mainWordCount);
            statement.setInt(4, menschCount);
            statement.setString(5, longWords);

            // Execute the insert
            statement.executeUpdate();

            System.out.println("Data inserted successfully into the results table.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

}
