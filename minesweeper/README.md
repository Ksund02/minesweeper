# Minesweeper code base
![Minesweeper Game](../pictures/minesweeper_game.png)

## Table of contents 📚
- [Game Description](#game-description-🕹️)
- [Features](#features-🎈)
- [Optional Features](#optional-features-🔨)
- [Intended Use](#intended-use-🎮)
- [How to check test coverage](#check-test-coverage🧪)
- [Environment setup](#environment-setup🕶️)
- [Priority Labels](#issue-priority-labels-🚩)


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


## Check test coverage🧪

1. **Navigate to the minesweeper directory**
```cmd
cd minesweeper
```

2. **Run the tests**
```cmd
mvn test
```

3. **Check the results**
- A jacoco report is made automatically, once a test is run.
- To check the results, navigate to minesweeper\core\site\jacoco, minesweeper\storage\site\jacoco or minesweeper\ui\site\jacoco and open index.html

## Environment setup🕶️
- Java: JDK 17
- Maven: Version 3.8.1 

## Issue Priority Labels 🚩
To streamline our development process and provide clear guidance on the importance of each task, we use a color-coded labeling system for our GitLab issues. Each color signifies a specific level of priority, detailed as follows:

Green: Optional Features 🟢
- What it means: Issues marked green are considered optional. They are "nice-to-haves" but not essential for the project's core functionality.
- Action: Address these issues only after resolving all higher-priority tasks.

Orange: Medium Priority 🟠
- What it means: Issues marked orange need to be implemented but are not immediate concerns.
- Action: These issues should be addressed in the next release cycle. They take precedence unless there are critical issues that require immediate attention.

Red: High Priority 🔴
- What it means: Issues marked red are critical and must be resolved immediately as they either block further development or introduce serious bugs.
- Action: Prioritize these issues, they must be resolved before attending to tasks of lower priority.