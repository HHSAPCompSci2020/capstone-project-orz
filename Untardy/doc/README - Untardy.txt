AP Computer Science Final Project - README Template


Instructions:
The first step in creating an excellent APCS final project is to write up a README. At this stage, this README file acts as your project proposal. Once you’ve filled in all components, Shelby will read through it and suggest edits. Ultimately, you need a document that adequately describes your project idea and we must agree on this plan.


Have one member of your group make a copy of this Google Doc. Then, they should share it with all other members and with Mr. Shelby so that every group member has edit permissions, and Shelby can add comments on your ideas.


There’s a lot of parts of this document that you might not have full answers for yet. Because you haven’t written the program yet, it’s difficult to think about the instructions or which group members will do which parts. Even though this is hard to think about, you must have something in these sections that acts as your current plan. However, during the course of the project, you’ll continuously update this document. This means that you will not be held to exactly what you put here - components of this document can change (and it’s pretty common!).


There is one exception: the Features List section. Once Shelby OKs your README, the Features List section cannot be modified. For this reason, it is most important that you get a solid idea of what you want to make and the primary features it will have now.


Talk with your group. Consider drawing some pictures of what you think your project might look like. Be precise. When you’re ready, fill this out together. Each component in brackets below ( [these things] ) should be replaced with your ideas. Note that there are several sample READMEs posted on this assignment for you to use as guidance.


-------------------When README is finalized, remove everything above this line--------------------


Untardy
Authors: Jerry Wu, Justin Chiang
Revision: 5/5/2021


Introduction: 


The first day of freshman year is always stressful. Students are constantly referring to the back of their school-issued planner while scrambling from building to building struggling to get to class on time. We faced this very issue back in freshman year and plans to alleviate the stress of finding the pathway to get to class by producing an interactive game where the player tries to draw the shortest path from a starting location to a designated building. Our game will consist of a 2d grid of cells. Each cell may represent a pathway, obstacle, or building. The user will be able to click and drag on the grid in order to produce a possible pathway from the start to the designated location. A timer countdown will be added in order to emulate the passing period time constraint. After the user has completed drawing a pathway, the program will run an internal shortest path algorithm and compare that against the user drawn path. The program will give a score based on how short the user path is from the optimal path.


Instructions:
At the start of the program, the user can use their mouse to navigate a menu to start the game, read the instructions, or quit the program. During the game, the user can use the arrow keys on their keyboard to move the player model around in the four basic directions (up, down, left, right) and interact with the game.


Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
* Start menu for the player to start the game, read the instructions, or quit the program
* Graphical representation of the Homestead school campus with a grid-like appearance
* Input function for the user to input their class schedule- classes, periods
* Functional player interaction with the grid- movement and interaction, and a way to store the player’s data- scores, lives, etc
* Algorithm that finds the shortest path for the given level
* Algorithm that scores the player depending on how accurate they were to the shortest path and how long they took
* Timer that counts down how long the player has to get to their next class


Want-to-have Features:
* Allow players to choose between different campuses, such as Fremont, Lynbrook, etc.
* Allow player stats to persist between games by saving game stats to a local .txt file
* Implement more precise types of Cell objects such as bush, bike rack, fence, etc
* Assign realistic schedules and have player walk through a typical week
* Add other students on campus that represent friends- bumping into them causes a time delay in getting to your next class


Stretch Features:
* Allow players to design campuses through a campus editor tool- user clicks on cells to design their own campus, and are able to play the game on them
* Allow player stats to persist between games by connecting program to Firebase database
* Add randomized “congestion zones” where there is heavy student traffic on campus - player is unable to cross those areas until traffic clears up








Class List:
* Main: instantiates DrawingPanel and runs the program
* DrawingSurface: PApplet that displays the game
* Grid: contains Cell objects and handles interactions in the game
* Cell: one spacial unit that describes the school (pathways, buildings, friends, etc)
* PlayerData: represents the player’s data throughout the game (score, lives, etc)
* StartMenu: start menu that contains buttons (start, stats, instructions)
* ShortestPathFinder: finds shortest path from Cell A to Cell B




Credits:
* Jerry: responsible for Grid, Cell, ShortestPathFinder
* Justin: responsible for Main, DrawingSurface, PlayerData, StartMenu
* External resources:
   * Processing Java library - graphics of the program
   * RecursionIn2DArray - inspiration
   * Google Maps - inspiration