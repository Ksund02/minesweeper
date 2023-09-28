# Minesweeper Project
![Bomb](/pictures/bomb.png)

## Table of Contents 📚
- [Project Description](#project-description-🕹️)
- [Project Members](#members-🧔)
- [Repository Structure](#repository-structure-👀)
- [User story](#user-story🧑‍🌾)
- [How to run the game](#how-to-run-the-game-🚂)
- [Priority labels](#issue-priority-labels-🚩)

## Project Description 🕹️
The goal of the project is to make a working minesweeper game.
More information about the implementation and the environment setup can be found in [Minesweeper README](minesweeper/README.md)

## Members 🧔
* Christian Fredrik Johnsen
* David Tuan Kiet Tran
* Kristian Underdal
* Oskar Emil Wavold

## Repository Structure 👀
- `pictures/`: Contains pictures used in readme-files.
- `docs/`: Contains all sort of documentation about the project, including the reports after each Release.
- `minesweeper/`: This is the folder where all of the code for the Minesweeper-game is.

## User story🧑‍🌾
We have made a story about Truls who is playing minesweeper.
Truls' adventure can be read here: [user story](docs/release-1/user-story.md)

## How to Run the game 🚂

1. **Navigate to the minesweeper directory**
```cmd
cd minesweeper
```

2. **Compile the code**
```cmd
mvn clean compile
```

3. **Navigate to the ui directory**
```cmd
cd ui
```

4. **Run the application**
```cmd
mvn javafx:run
```

## Issue Priority Labels 🚩
To streamline our development process and provide clear guidance on the importance of each task, we use a color-coded labeling system for our GitLab issues. Each color signifies a specific level of priority, detailed as follows:

Green: Optional Features 🟢
- What it Means: Issues marked green are considered optional. They are "nice-to-haves" but not essential for the project's core functionality.
- Action: Address these issues only after resolving all higher-priority tasks.

Orange: Medium Priority 🟠
- What it Means: Issues marked orange need to be implemented but are not immediate concerns.
- Action: These issues should be addressed in the next release cycle. They take precedence unless there are critical issues that require immediate attention.

Red: High Priority 🔴
- What it Means: Issues marked red are critical and must be resolved immediately as they either block further development or introduce serious bugs.
- Action: Prioritize these issues, they must be resolved before attending to tasks of lower priority.