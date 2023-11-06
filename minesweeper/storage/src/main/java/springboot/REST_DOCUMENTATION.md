# Documentation of the supported HTTP requests

## Contents
- Server type
- GET-requests
- POST-requests
- DELETE-requests


## Server type
We are using jetty as our server.

## GET - Returns a list of registered highscores
    - URI: http://localhost:8080/highscores
    - parameters: none
    - returns: JSON-string containing all stored UserScores

### Example of returned object:
```json
[ {
    "name" : "Player1",
    "score" : 10,
    "date" : "2023-01-24",
    "difficulty" : "EASY"
}, {
    "name" : "BobTheMiner",
    "score" : 16,
    "date" : "2001-09-11",
    "difficulty" : "HARD"
}, {
    "name" : "George Mallory",
    "score" : 35,
    "date" : "1924-06-08",
    "difficulty" : "MEDIUM"
} ]
```

## POST - Adds a new highscore
    - URI: http://localhost:8080/highscores
    - Content type: application/json
    - Request body: UserScore object in JSON-format.
    - 