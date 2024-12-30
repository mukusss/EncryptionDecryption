
# Secure Password Encryption and Decryption System

**Overview**  
This project demonstrates the development of a secure password storage system using Java, MySQL, and JDBC. The system encrypts passwords before storing them in a MySQL database and decrypts them upon retrieval, ensuring a high level of security for sensitive information. This project highlights secure coding practices and demonstrates a practical implementation of encryption techniques to protect user data.


## Features
- **Encryption & Decryption:** Securely encrypts passwords using Java's cryptography libraries and decrypts them when needed.
- **Secure Storage:** Ensures passwords are never stored in plain text in the database.
- **Database Integration:** Uses JDBC to interact with a MySQL database, storing encrypted passwords and retrieving them for decryption.
## Technologies Used
- **Java:** Core programming language used for implementing encryption and decryption algorithms.
- **MySQL:** Relational database used for storing encrypted passwords.
- **JDBC (Java Database Connectivity):** To establish the connection between Java and MySQL for performing database operations.
## Prerequisites
To run this project, ensure you have the following installed:
- **Java Development Kit (JDK)** - Version 8 or higher
- **MySQL Server** - Version 5.x or higher
- **JDBC Driver for MySQL** - Download the appropriate driver for Java-MySQL integration.
## Future Enhancements
- **Integration with Web Applications:**   Extend the system to work as a backend service for web applications.
- **Support for Multiple Algorithms:** Add support for various encryption algorithms (AES, RSA) based on user requirements.
- **Enhanced User Management:** Build a more comprehensive user authentication and management system around the encryption functionality.