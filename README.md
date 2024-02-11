<img src="https://github.com/AlxisHenry/epitech/assets/91117127/6a6dc000-01e2-431d-b0bd-fc17033944ef" alt="CATQUEST_LOGO" width="300" height=300 align="right"/>

# Welcome to Cat Quest :rocket:

This is a game in java made with [JavaFX](https://openjfx.io/) project made by 3 students of Epitech Strasbourg.

You have access to the documentation of the source code [here](https://alxishenry.github.io/catquest/).

> We choose [JavaFX](https://openjfx.io/) because it's a framework that we have never used before and we wanted to learn something new.

## Table of contents
1. [Synopsis](#synopsis)
2. [Prerequisites](#prerequisites)
3. [Technical explanation](#technical-explanation)
4. [Setup and configuration](#setup-and-configuration)
5. [Run the project](#running-the-project)
6. [Bonus](#bonus)
7. [Technologies](#technologies)
8. [Authors](#authors)

## Synopsis

In a mysterious underground world, a daring little cat finds itself trapped in a labyrinthine cave. Guided by its courage and curiosity, this furry little hero must navigate through dark and mysterious rooms, each containing its own challenges and secrets.

The ultimate goal? To find the magical portal hidden in each room, a glittering passage that transports him to increasingly complex and enigmatic levels. With each teleportation, our protagonist gets closer to freedom, but the path is far from easy.

Daunting obstacles block his way. Moreover, enemies lurk in the shadows, ready to thwart his escape. These adversaries range from fierce underground creatures to malevolent portal guardians, each presenting a unique challenge.

Our little cat must demonstrate agility, intelligence, and bravery to overcome these trials. By collecting magical artifacts, he acquires new skills and powers that aid in his journey.

The journey is also a path of self-discovery, where our hero learns the importance of perseverance and courage in the face of adversity.

In "Cat Quest", each player is invited to experience a thrilling adventure, full of discovery and action. It's a quest for freedom, a battle against the darkness, and a story of courage in the face of the unknown.

## Prerequisites

- IntelliJ IDEA 2023.2.4
- Java SDK 21
- Maven 4.0.0
- JavaFX

## Technical explanation

When you fire up the game, you land on the main menu where you get to choose between:

- **Continue** (if you already have an existing game)
- **New Game**
- **Settings**
- **Quit**

### Continue

Based on the existence of the `save.json` file, the game determines if you already have a saved game. If so, this button pops up.

When you click, the game will load your save. Otherwise, you'll be redirected to the "New Game" page with a message about a glitch in handling the save file.

The save holds several things:
- a seed (it's like your world's DNA)
- general game info (stopwatch, score, ...)
- player info (stats, XP, level, ...)

### New Game

If you try starting a new game while already having a save, a confirmation prompt appears. If you proceed or click "Continue" in the confirmation, you'll be asked to name your player.

After the form's filled out, the game creation begins. A loading screen appears, reminding you to chill out. Once the game's loaded, you're off to the gaming screen.

### Settings

The audio settings page lets you crank up/down the music and/or sound effects volume.

Note: Volume ranges from 0 to 100.

The controls page allows you to set up your keys for a better gaming experience.

Remember: No stealing keys already taken by other controls!

These settings stick around thanks to nifty json save files. They load when the game starts to update your chosen options.

### Quit

The "Quit" button gracefully exits the game.

### Game Creation

When starting a fresh game, 3 worlds, each with 5 levels, are set up. For each level, a bunch of random rooms are cooked up. The map for each level is like a blueprint used to create classes and link rooms together.

For a game in progress, the save ensures the same map is loaded using that magical `seed`, along with all your game info.

Hope that makes the game menu sound more like an adventure in itself!


## Setup and configuration

### Clone the repository

```bash
$ git clone git@github.com:EpitechMscProPromo2026/T-JAV-501-STG_11.git
$ cd T-JAV-501-STG_11/
```
### Run the tests

You can run the tests with the IDE using the custom `tests` configuration.

### Run the project

You can run the application with the IDE using the custom `run` configuration.

## Bonus

- Controls configuration
- Audio configuration
- Musics and sound effects
- Save and load game
- Easter egg (when you name your cat mimi)
- Unit and feature tests
- Minimap (with the position of the player)
- Score and level system

## Technologies

![](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&color=20232a)

## Authors

- [@AlxisHenry](https://github.com/AlxisHenry)
- [@Flaironne](https://github.com/Flaironne)
- [@Leily67](https://github.com/Leily67)