# Demo
![Application in action](https://github.com/MikeNyugen/Nerdle-clone/blob/main/demo.png)

# Running the application

This package contains the resources needed to run theNumble Application.

To run the server, use the following command:
./gradlew -PmainClass=com.numble.NumbleApplication run

To run the client with the GUI, type:
./gradlew -PmainClass=com.numble.UI.StartGUI run


# API Description:

POST method /new_game
arguments: mode, which is one of EASY, MEDIUM, HARD, SUPERHARD
this method created a new game and returns its id as an integer

GET method /get_target/{id}
arguments: id, which is game id
this method returns the string the user is supposed to guess

GET method /get_target_result/{id}
arguments: id, which is game id
this method returns the result of the equation (target)

GET method /has_won/{id}
arguments: id, which is game id
this method tells whether the game has been won

GET method /target_length/{id}
arguments: id, which is game id
returns the length of the target

POST method /check_guess/{id}
arguments: id (game id), guess (string with the guess of the user. 'p' should be used instead of '+')
checks the guess and returns an array of colours to be displayed
GREEN means that the character is in the right place
ORANGE means that the character is present in the target, but in the wrong place
GREY means that the charecter is not present
PURPLE means that the user is supposed to enter expressions that evaluate to the correct result (is using HARD or SUPERHARD mode) and entered a wrong eqaution (e. g. 1+1=3)

GET method /has_lost/{id}
