# User interface documentation

The user interface of this minesweeper application is made with JavaFX and FXML. This 
document outlines the different elements in the user interface of this minesweeper application.

## Contents

- [User interface documentation](#user-interface-documentation)
  - [Contents](#contents)
  - [MineApp](#mineApp)
  - [GamePageController](#gamePageController)
  - [HighscoreListController](#highscoreListController)
  - [SettingsController](#settingsController)
  - [GamePage.fxml](#gamePage.fxml)
  - [HighscoreList.fxml](#highscoreList.fxml)
  - [Settings.fxml](#settings.fxml)

# MineApp

This file launches the minesweeper application

# GamePageController

GamePageController controls all the JavaFX logic for the minesweeper game. It uses GamePage.fxml as its core. When launching the application the user will be sent to GamePage.fxml where GamePageController controls the different user interactions. In the fxml there is a gridpane with tiles which work as a minesweeper board. When winning the user will be able to send its userscore to the leaderboard with a button. In addition, there are buttons for moving to the Settings.fxml and HighscoreList.fxml. 

# HighScoreListController

This controller controls the JavaFX logic for the highscore list. When the user presses the Leaderboard button in GamePage.fxml the user is moved to HighscoreList.fxml which HighScoreListController controls. The HighScoreListController ensures that the leaderboard in HighscoreList.fxml is up to date with the userscores in the REST server by using the RestRequest class. There is also a button for the user to go back to the GamePage.fxml.

# SettingsController

SettingsController controls the JavaFX logic for settings. When the user presses the "Settings" button in GamePage.fxml the user is moved to Settings.fxml which SettingsController controls. There are buttons for switching between difficulties and themes in the fxml. The controller ensures that when clicking on those buttons the difficulty and theme get set to the buttons pressed. There is, in addition, a back button for the user to go back to the GamePage.fxml just like in the HighscoreList.fxml.

# GamePage.fxml

GamePage.fxml is a fxml file of the minesweeper application. It contains the user interface of the minesweeper application.

# HighscoreList.fxml

HighscoreList.fxml contains the user interface for the highscorelist in the minesweeper application.

# Settings.fxml

Settings.fxml contains the user interface for the settings in the minesweeper application.