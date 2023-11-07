# minesweeperstorage: Module documentation

Welcome to the `minesweeperstorage` module documentation. This module is an integral part of the Minesweeper game application, providing robust data management functionalities. It is designed to permanently store game achievements so that high scores are maintained across sessions, providing an exquisite gaming experience.

Within this documentation, you will find detailed information about the classes and methods used to manage highscore data. The storage package includes all the necessary operations for reading, writing, and manipulating highscore information stored in JSON format, ensuring easy retrieval.

The SpringBoot package, on the other hand, details the application's configuration and the web server's initialization using Spring Boot. It also outlines how the RESTful web service handles various HTTP requests pertaining to the highscore data.

Please use this documentation as a guide for understanding the internal workings of the `minesweeperstorage` module.

## Contents

- [minesweeperstorage: Module documentation](#minesweeperstorage-module-documentation)
  - [Contents](#contents)
  - [Storage Package](#storage-package)
    - [Classes](#classes)
      - [`HighscoreFileManager`](#highscorefilemanager)
      - [`UserScore`](#userscore)
  - [SpringBoot package](#springboot-package)
    - [Classes](#classes-1)
      - [`SpringApp`](#springapp)
      - [`HighscoreRestController`](#highscorerestcontroller)
      - [`HighscoreService`](#highscoreservice)
  - [Dependencies and other information](#dependencies-and-other-information)

## Storage Package

The storage package handles everything which has to do with data which should be stored also after the application has been closed. In our app, the only things which need to be stored permanently are the achieved `UserScore` objects.
The storage package defines `UserScore` objects, and provides functionality for reading, writing and deleting scores from a JSON-based highscore file.

### Classes

#### `HighscoreFileManager`

Responsible for manipulation of the JSON-file containing highscore entries.

- Methods
  - `getFile()`: Returns the file used for storing highscores.
  - `writeToHighscore(UserScore userScore, File file)`: Adds a `UserScore` to the highscore file and sorts the scores.
  - `readFromHighscore(File file)`: Reads highscores from a file and returns a list of `UserScore` objects.
  - `deleteFromHighscore(String name, int time, String date)`: Deletes a specific `UserScore` from the file.
  - `clearHighscore(File file)`: Clears all highscores from the file.

#### `UserScore`

Object representing a highscore-entry. The object contains the name, score, date, and the difficulty level of the game when the score was achieved.

- Methods
  - `toJson()`: Converts the `UserScore` instance to a JSON string.
  - `getName()`, `getScore()`, `getDate()`, `getDifficulty`: Getters for the different fields.

## SpringBoot package

The `springboot` package contains classes for running the spring boot application, and handling the different HTTP-requests which the RESTapi supports.

### Classes

#### `SpringApp`

This is the entry point for the Spring Boot application. It is responsible for firing up the jetty web server.

#### `HighscoreRestController`

This is a REST controller which handles all of the HTTP-requests which are supported. In our cases this will be HTTP-requests relating to the highscore list, see [rest documentation](./src/main/java/springboot/REST_DOCUMENTATION.md)

- Endpoints
  - `GET /highscores`: Retrieves all highscores
  - `POST /highscores`: Adds a new highscore entry
  - `DELETE /highscores`: Remove all saved highscores (not yet implemented).

#### `HighscoreService`

A service class which is responsible for the core logic needed to handle the HTTP-requests. The service class defines which requests the RESTapi supports, and the service class defines how these requests should be processed. The `HighscoreService` class is tightly connected to the `HighscoreFilemanager` class.

- Methods:
  - `getAllHighscores()`: Retrieves all highscores from the save file.
  - `addHighscore(UserScore userScore)`: Adds a new highscore to the save file.
  - `clearAllHighscores()`: Deletes all highscores from the save file.

## Dependencies and other information

- **Jackson Library**: Utilized for JSON processing, enabling easy conversion of `UserScore` objects to/from JSON.
- **Spring Boot**: Automates configuration and startup processes for the web server, simplifies server management and reduces boilerplate code.
