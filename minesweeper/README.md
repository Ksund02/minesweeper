# Minesweeper code base
![Minesweeper Game](../pictures/minesweeper_game.png)

## Table of contents 📚
- [Game Description](#game-description-🕹️)
- [Features](#features-🎈)
- [Optional Features](#optional-features-🔨)
- [Intended Use](#intended-use-🎮)
- [How to run the game](#how-to-run-the-game-🚂)
- [How to check test coverage](#check-test-coverage🧪)


## Game Description 🕹️
[Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game)) is a puzzle video game created by Microsoft in the 1990's.
The goal of the game is to click on all the cells in the game grid which do not contain bombs.
When you click on a tile, the number of bombs on neighboring tiles is shown.
If you click on a tile which contains a bomb, then you have lost.
Points are given to the player based on how quickly the player was able to clear the grid, by clicking on all non-bomb tiles.

The goal of our project is to make a working version of minesweeper. 
A [screenshot](#minesweeper-code-base) of the game is shown at the top of this file.
A picture of how the high-score list might look like is presented below.

![High-Score List](../pictures/highscore_list.png)

## Features 🎈
- **Basic Minesweeper Gameplay**: Clear a grid of tiles without triggering mines.
- **High-Score List**: Players can submit their names and scores after successfully completing a game.

## Optional Features 🔨
- **Difficulty Levels**: Choose between Easy, Medium, and Hard levels.
- **Game Saving**: Save the current game state and resume later. Note: Saved games will not be eligible for the high-score list to prevent cheating.

## Intended Use 🎮
1. Open the application
2. The main screen is presented
3. Click a button to restart the timer
4. Play the game, and don't lose 😄
5. Submit your name to the high-score list
6. Click the high-score button to view the high-score list


## How to Run the game 🚂

1. **Navigate to the minesweeper directory**
```cmd
cd minesweeper
```

2. **Run the application**
```cmd
mvn javafx:run
```
## Check test coverage🧪

1. **Navigate to the minesweeper directory**
```cmd
cd minesweeper
```

2. **Run the tests**
```cmd
mvn test jacoco:report
```

3. **Check the results**

Navigate to minesweeper\target\site\jacoco, and view the results in jacoco.csv

