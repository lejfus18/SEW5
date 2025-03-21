# SEW5

**Author:** Lejdi Fusha  
**Projekt:** Sew  
**Lehrer:** Herr Paul Opitz

## Projekt Description

This project is part of the SEW5 course, an integral part of the Software Engineering curriculum. The objective was to develop a comprehensive Java application that utilizes multiple concepts in software engineering, such as RESTful API consumption, data processing, multi-threading, logging, and integration with databases and file systems.

The project is centered around analyzing book data, which is fetched from an external REST API. This data consists of various books with attributes such as title, text, and ID. The goal was to create a system that processes this information and calculates several key metrics:

1. **Word Count**: For each book, we calculate the total word count and the word count excluding common "stop words" like "the", "and", "or", etc.
2. **Pattern Matching**: We also implemented a system to identify the frequency of the term "Mensch" (and its variations like "mensch" and "MENSCH"), highlighting how often certain keywords appear in the text.
3. **Long Words**: Another requirement was to create a list of all words exceeding 18 characters in length. This was achieved through regular expressions (Regex).

In addition to these core functionalities, the project also emphasizes good software practices and methodologies:

- **Version Control with Git**: I used Git throughout the development process to track changes, manage different versions of the code, and ensure proper collaboration. The project is hosted on GitHub, and each of the individual tasks was tracked using GitHub Issues and a Kanban board.

- **REST API Integration**: The project fetches book data from a public REST API (https://htl-assistant.vercel.app/api/projects/sew5) using a GET request. The fetched data is then processed and analyzed, and the results are sent back to the server via a POST request. This allows for seamless integration with an external data source and efficient data processing.

- **Multi-threading and Concurrent Processing**: To optimize performance, particularly for larger datasets, I implemented a concurrent processing approach using Java's `Callable` and `Future` interfaces. This allowed for parallel processing of multiple books at the same time, improving the efficiency of the word analysis.

- **Data Output**: The processed results are written to both a CSV file and a MySQL database, demonstrating an understanding of file I/O and database interaction. The results are saved in a structured format, with columns such as `id`, `title`, `word_count`, `main_word_count`, `mensch_count`, and `long_words`.

- **Testing and Code Coverage**: Unit tests were written for each method in the `BookAnalysis` class to ensure the correctness of the program. Positive and negative test cases were considered to validate the results. Code coverage was tracked and reported to ensure thorough testing of all components.

- **Logging**: Throughout the development, I incorporated detailed logging using Java’s `java.util.logging` package. This provides clear logs at various levels (INFO, WARNING, SEVERE, and FINE) to help track the flow of execution, identify potential issues, and aid in debugging.

- **API Authentication**: To ensure secure data submission, the project uses Basic Authentication for the POST request. The credentials used are `"student"` and `"supersecret"`, which are required to authenticate the request when sending the processed data to the server.

- **Documentation**: I also took care to document the entire codebase using JavaDoc comments. Each class and method is described in detail to ensure clarity and make the code easily understandable to other developers or anyone reviewing the project.

This project allowed me to deepen my understanding of Java, software design patterns, and best practices in software development. It was an excellent opportunity to apply theoretical knowledge in a practical, real-world scenario, while also learning how to work with external APIs, manage concurrent processes, and ensure the efficiency and reliability of a software system.

### Key Technologies Used:
- Java 11
- Maven (for dependency management)
- REST APIs (for data fetching and posting)
- MySQL (for data storage)
- Java NIO (for writing CSV files)
- Java Util Logger (for logging)
- Git and GitHub (for version control)
- JUnit (for testing)
- Regex (for pattern matching)

This project not only demonstrated my technical ability to handle complex programming tasks but also emphasized the importance of good software engineering practices such as clean code, thorough testing, version control, and continuous integration.

I look forward to any feedback on my work and am eager to discuss the challenges and solutions in more detail!


## License

This project is licensed under the MIT License - see the [License](license) file for details.
