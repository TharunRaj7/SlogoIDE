# SLOGO_QUESTIONS.md

Thomas Owens (to57), Vineet Alaparthi (va68), Andrew Krier (ak513), Tharun Mani Raj (tm272) 

## High Level Overview

#### Front-end:
Displays the movement path of the turtle after a command was made.

Handles UI interactions by calling appropriate back-end methods to process user commands.


#### Back-end:

Handles data, (eg turtle location, speed, penDown, color) and processes user commands accordingly. It also parses user commands into something understandable by the software. 

## Related Questions

1. #### When does parsing need to take place and what does it need to start properly?

    Parsing needs to take place every time the user enters a command into the prompt. It needs the text that the user enters, along with the corresponding language resource file in order to interpret that text.
    
2. #### What is the result of parsing and who receives it?

    The result of parsing should be a discernable action that is interpretable by the backend, that in turns sends an instruction to the visualization to display. 

3. #### When are errors detected and how are they reported?

    Errors could either be detected in the parser itself, or when a parsed command is taken in by the backend. Parsing and language errors are handled by the parser, which protects the backend by simply sending an error message back to the visualization method to display. Logic and control errors, such as moving the turtle to out of bounds coordinates or entering logically erroneous commands, are handled by the backend, which could catch the output of the erroneous commands and see if anything can be done from there.

4. #### What do commands know, when do they know it, and how do they get it?

    Commands know different things depending on how intensive of a command it is. This difference can be seen through a move(x, y) command and a moveTo(x, y) command. One would need to know the initial position of the turtle while it's not necessary for the other.

5. #### How is the GUI updated after a command has completed execution?
    
    It will move the turtle to a new location, as well as show a line along the path dependent on if the pen for the turtle is down or not. It will also add the previously executed command to the console history.
    
[//]: # (https://hackmd.io/@b7D19V2FSWulY5D11map5w/rkpRTBXQU)