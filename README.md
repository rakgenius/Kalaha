This is a spring boot project to implement the kalaha game which is played by two players


Requirements:

Java 1.8
Optional : Mongodb installed and running for more api access and viewing the game in different view
Intellij/Eclipse

Steps to run the game



1.	Import the project into Intellij or eclipse using the ?Import as existing maven project? option.

2.	Once the project is opened wait for all the dependencies to downloaded and the project to set it up.

3.	After everything is setup, open the file BolGameApplication.java in src/main/java/com/bol/bolgame/ directory.

4.	Right click on the file and select ?Run <file?

5.	Once the application runs successfully, navigate to http://localhost:8080 in your browser

6.	This page loads a new game where two players can play the game in the same page

7.	The ?Game status? tells which player has to play next and the corresponding player has to click on the box to make a move.

8.	Multiple games can be played at the same time by opening the above link in multiple tabs.

9.	Once the tab is closed or the page is refreshed, the game is lost.



API?s



The following API?s are available to get the status of the game



1.	http://localhost:8080/api/games  : To get the list of active games currently being played

2.	http://localhost:8080/api/games/{id} : To get the status of a particular game with id as ?{id}?

3.	http://localhost:8080/api/newgame : Using postman we can create a new game using this api

4.	http://localhost:8080/api/games/delete/all : To delete all the games from the system

5.	http://localhost:8080/api/games/delete/{id} : To delete a particular game using its id.



Mongodb support



The pom.xml already includes the dependencies needed to install and setup mongodb java driver in intellij/eclipse. If mongodb is installed and running in the system, then the application stores the data in the database which can be viewed either in mongodb shell or in the UI



Currently the mongodb related configurations are commented out in ?application.properties? file. If mongodb is successfully running then uncomment the mongodb related configurations and run the program.



This program assumes that it can connect to mongodb without any authentication needed and has the permissions to create the database.

If you have setup any authentication in the database then you need to add the extra configuration properties which includes username, password and authenticationDatabase so that program can connect to the DB.



After setting up the mongodb successfully, uncomment the mongodb related configurations in ?application.properties? file and restart the application and navigate to http://localhost:8080 to create a new game



API?s



The following api?s are available



1.	http://localhost:8080/mongo/games : To get list of all active games

2.	http://localhost:8080/mongo/games/{id} : To get a particular game with id as {id}

3.	http://localhost:8080/mongo/games/load : This opens up a web page which asks user to enter the game id. If the game id is found in the database then it loads that game

4.	http://localhost:8080/mongo/games/delete/all : To delete all the games from the database

5.	http://localhost:8080/mongo/games/delete/{id} : to delete a game with particular id





Running unit tests:



Navigate to the folder ?src/test/java/com/bol/bolgame in intellij or eclipse. Right click on this folder and select ?Run Tests in com.bol.bolgame?. this will run all the unit tests in the directory.



