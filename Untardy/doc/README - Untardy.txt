AP Computer Science Final Project - README Template


Untardy
Authors: Jerry Wu, Justin Chiang
Revision: 5/5/2021


Introduction: 
The first day of freshman year is always stressful. Students are constantly referring to the back of their school-issued planner while scrambling from building to building struggling to get to class on time. We faced this very issue back in freshman year and plans to alleviate the stress of finding the pathway to get to class by producing an interactive game where the player attempts to travel from their starting location to their next designated building in the shortest amount of time.


In our game, each ‘level’ will be a day’s class schedule, for a total of one full week (5 levels). Each day’s class schedule will be randomized to prevent the player from playing the same schedule at the same level. The schedules will be a realistic Homestead student’s schedule, such as a balanced course load (math, English, science, history, etc) with actual class names.


Our game will consist of a two-dimensional grid of cells, which represent spacial units on the school campus. Each cell may represent a pathway, building, or other special object that interacts with the player. The player will use arrow keys to move their character cell in the four cardinal directions on the campus grid. An in-game clock will be displayed at the bottom of the screen to tell the player how much more time they have left in the passing period to reach their next classroom’s building. If the player fails to make it to their destination in time, they receive a tardy and must restart that day/level. A player may have up to three tardies to complete the entire week.


After the user has reached their next building, the program will then score the user on the travel completion time, as well as how optimized their path was. The program will use a function of time to award the player points. Additionally, the program will run an internal shortest path algorithm and compare that path’s length against the player’s traveled path. Depending on the proximity of the length of the player’s traveled path to that of the shortest path, the player will be awarded extra points for that day.


Instructions:
At the start of the program, the user will be prompted with a brief introduction of the game as well as the schedule for their first day of class. The game will guide the player through the game primarily with text-based instructions as the player progresses. During the game, the user can use the arrow keys to move their player around the grid and interact with other objects on campus, and use their mouse to navigate buttons and pop-ups.




Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
* Basic instruction prompt at startup
* Graphical representation of the Homestead school campus with a grid-like appearance
* Algorithm to randomize class schedule for each day
* Functional player interaction with the grid- movement and interaction, and a way to store the player’s data (scores, lives, etc)
* Algorithm that finds the shortest path for the given level, and compares its length to the player’s traveled distance
* Method that scores the player depending on how accurate they were to the shortest path and how long they took
* Timer that counts down how long the player has to get to their next class. Initial amount of time TBD
* Message that congratulates player after beating the game


Want-to-have Features:
* Allow players to choose between different campuses, such as Fremont, Lynbrook, etc.
* Allow player stats to persist between games by saving game stats to a local .txt file
* Implement more precise types of Cell objects such as bush, bike rack, fence, etc
* Assign realistic schedules and have player walk through a typical week
* Add other students on campus that represent friends - bumping into them causes a time delay in getting to your next class


Stretch Features:
* Allow players to design campuses through a campus editor tool- user clicks on cells to design their own campus, and are able to play the game on them
* Allow player stats to persist between games by connecting program to Firebase database
* Add randomized “congestion zones” where there is heavy student traffic on campus - player is unable to cross those areas until traffic clears up
* Starting tutorial with pauses and text to familiarize the user with the mechanics of the game




Class List:
* Main: instantiates DrawingPanel and runs the program
* DrawingSurface: PApplet that displays the game
* Grid: contains Cell objects and handles interactions in the game
* Clock: contains stopwatch and timer feature
* Cell: one spacial unit that describes the school (pathways, buildings, friends, etc)
   * PlayerCell: represents the player model, is controlled by the user and moves around on the campus grid
   * BuildingCell: represents a campus building that the player cannot walk through
   * EntranceCell: represents the entrance to a campus building that the player must reach
   * PathCell: represents a cell that the player can walk on to navigate the campus
      * FriendCell: represents a cell that mingles with the player at the cost of a time penalty
      * CongestionCell: represents a cell that is part of a congested zone that the player can pass through at the cost of a time penalty
   * VegetationCell: represents a cell that is vegetation (does nothing)
* CellFactory: helps to fill a Grid object with Cell objects depending on characters present in the read-in text file
* Player: represents the player’s data throughout the game (schedule, score, lives, etc)


Credits:
* Jerry: responsible for Grid, Cell, CellFactory, PlayerCell, BuildingCell, EntranceCell, PathCell, VegetationCell (stretch features: FriendCell, CongestionCell)
   * Primarily responsible for grid/cell mechanics, such as player movement, different kinds of cells, etc
* Justin: responsible for Main, DrawingSurface, Clock, Player
   * Primarily responsible for game logic and display, such as checking cases for player tardy, win condition, and displaying the grid with clock, schedule, etc
* External resources:
   * Inspiration:
      * RecursionIn2DArray Lab - inspiration
      * Google Maps - inspiration for the program
   * Resources for the program
      * Processing Java library - graphics of the program
      * Google Fonts (fonts.google.com) - resource for fonts used
      * DaFont (dafont.com) - resource for fonts used
      * W3Schools (w3schools.com) - resource for finding RGB values for colors used
      * StackOverflow (stackoverflow.com) - general help for troubleshooting