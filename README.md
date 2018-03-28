Tic Tac Toe

March 28th, 2018
Chapman University 
CPSC 353 Dr. Fahy

Game Developers:

Riccardo Angiolini
angio102@mail.chapman.edu
contributions:
-general overview/supervision of all files
-tested/found all possible bugs and errors in fuctionality
-added program functionality to exit code after a winner is found
-implemented checkstyle compliance for ClientListener.java & most of ClientHandler.java
-in charge of READ.ME instructions

Ben Wasserman,
wasse114@mail.chapman.edu,
2280906,
contributions:
-created board for game
-created initial user prompts for game
-created functionality of playing on board
-created functionality of checking for duplicate plays in a single spot
-wrote functions and implemented them to check for wins and ties
-wrote prompts for users upon connection
-wrote communication between ClientListener.java and ClientHandler.java
-commented code
-general overview/supervision of all files

Rita Sachechelashvili
sache100@mail.chapman.edu
contributions:
-worked on making console outputs user friendly
-worked on a method for ClientHandler.java that checks if a cell has been already occupied by a player's move to prevent double input
-completed checkstyle for MtClient.java and for ClientHandler.java

Instructions on how to run program:
- open 3 terminal windows
-first run MtServer.java class
-then run the MtClient.java class twice on each of the other 2 terminals respectively
-game will launch, you will have ability to enter colum index and row index
- by entering 11 you are telling the game that player 1 is marking colum 1 row 1
- player 2 (in the second Client terminal window) will not be able to mark 11 on the board as it has already been taken, but can mark any other spot on the 3 x 3 tic tac board
- in case of a tie the program will tell the users that a tie was found and there was no winner
-in case of a win by either player the program will thank the users for playing and exit.
