# DESIGN.md

### Introduction

The goal of this project is to create an IDE for the SLogo programming language, a simplified version of Logo, which 
formed the foundation for 'turtle' graphics. Our main aim in this project is to create an IDE that is easily extensible 
with new command types and GUI add-ons.

At a high level, the project is made up of several distinct components. The back end is composed of a parser module, 
which needs to parse user input passed to it by the front end, and a controller module, which carries out parsed 
commands. The front end contains a GUI with flexible add-ons or modules, such as a console for user input, a text 
editor for writing scripts, and a panel that displays the turtle's output. 

In general, the parser and GUI frame should be closed. Parsing should work the same way no matter the command being 
executed, and no matter what additions are made  to the program. However, the addition of new commands and 
functionality should be easy and open. Additionally, the GUI should always have the capability to hold UI modules. 
However, the specific modules visible in the frame should be highly flexible.

### Overview
The program is broadly split into three parts, the model, controller, and view, each with subdivisions laid out below:

#### Model

##### Parser:
* Handles parsing of all input, passed to it by the View. 
* Translates an input string into a series of Command instances that can be carried out by the controller.
* Has a corresponding error class for parsing errors.
* Possible Implementations:
    * A tree of conditionals that checks input against every possible command, and checks syntax against all of the 
    possible syntax cases manually.
    * The same as above, but using regex for checking syntax.
    * A Map of alias to Command type, using regex for checking syntax.

##### Command:
* Holds information about a specific command, including aliases and a breakdown of function.

#### Controller

##### Turtle:
* Holds the location and heading of the 'turtle', and uses this information to translate commands from the back end 
into paths that can be drawn on the front end.
* Acts as the bridge between the Parser and the Canvas, enabling isolation between the two parts.
* Possible Implementations:
    * Information about the turtle could be stored as a unit vector which starts at the turtle's location and points in 
    the direction of its heading.
    * The turtle's information could be stored as three integers: x position, y position, and heading from 0 to 360 
    degrees from vertical.


#### View

##### Frame:
* Serves as the main window of the program.
* Holds other UI components, such as the Console or Canvas.
* Organizes components in a flexible and modular frame, which can be extended with new UI widgets as wanted.
* Possible Implementations:
    * The Frame could be a custom LayoutPane that intelligently adds new Panes to itself, shifting around existing 
    panes to efficiently lay out components.
    * The Frame could be a GridPane that just puts components in a new row every time a new one is added.

##### Console:
* Is a UI component used for single-line instruction input, like the Windows or Unix terminal.
* Passes input to the Parser directly to be executed.
* Maintains a history of previously executed commands.
* Possible Implementations:
    * The Console could use a stack to store previously executed commands.
    * The Console could store previously executed commands in an array of Strings that is saved and reinitialized to be 
    one item longer each time a new command is put in.

##### Script Editor:
* Is a UI component used for multiple-line instruction input, like a typical IDE.
* Passes input to the Parser directly to be executed.
* Possible Implementations:
    * The Script Editor could store the typed text internally as a .txt file in data, autosaved whenever the user inputs 
    something.
    * It could also store an ArrayList of commands that are entered, dividing entries with whitespace.

##### Canvas:
* Maintains previously drawn paths and the turtle icon.
* Adjusts pen color and thickness, along with background color and other visual features.
* Possible Implementations:
    * The Canvas could be stored internally as an image file, and could directly manipulate pixels to add new paths.
    * It could store a 2D array of pixels instead, skipping the step of saving and loading the image each time.

The picture below diagrams the points of interaction between classes in this program structure.

![](https://i.imgur.com/67Ay2kU.jpg)

There are four main APIs in this project: the front end internal API, which includes interactions between View 
components; the front end external API (considering Turtle a front end component), which describes the interface 
between Model components and the Turtle, or Controller; the back end internal API, which has interactions between 
Model components; and the back end external API, which has the methods used to send data from the front end to the 
back end. See the other sections of this document and the Java code APIs for further details on the method signatures 
included in each API.

### User Interface
The initial plan for the UI is to have the following:
- A script editor that enables the user to write a string of commands to move the turtle and change the GUI settings 
(color, line width etc.)
    - There will be buttons (run, clear console, load script file etc) on the script editor for the user to run scripts,
     load script files if needed and other functions that we will implement as seen fit.
    - There will be some interaction form to change the color, pen size, whether the pen is down or not, and whether the
     turtle is hidden or not.
    - Two buttons to reveal the available variables or the available programmed functions on the screen
    - The script editor will also be able to access a history of past run commands.
    - Invalid commands would be followed with error messages in the console, but would not affect what is shown in the 
    visualizer.
- A visualizer or canvas that displays the movement of the turtle after the user runs their commands.
    - Canvas also shows location and direction of the turtle


### Design Details

#### Front End

##### External
The Front end external API would essentially contain the methods in the ITurtle interface which is used by the back end 
to get attribute values of the turtle, for the back end to call specific movement functions such as move, moveForward 
and rotate after the back end has parsed the user inputs.

##### Internal
The internal API of the front end handles the interaction between the different front end components.
The methods in the IVisualize interface are all part of this API group. The turtle class would call respective functions
 in the Visualizer class (internal APIs) to update the display accordingly.

#### Back End

##### External
Back end external API is essentially passing a command from the termainal to the parser, and providing a turtle object 
to be able to interact with the front end. Access to the available variables and functions already made can be handled 
through binding.

##### Internal
The internal API manages interactions between the parser, command manager, and command objects. The parser makes a 
command using the information from the given string and passes the command to the command manager, who then puts 
together a tree of commands with leaves of either arguments or other commands. Once a command is able to execute it is 
called by the manager and allowed to set the appropriate values of the turtle.


### API as Code

#### Interface files to look at:
##### Front End 
###### External:
 - ITurtle

###### Internal:
 - IFrame
 - IVisualize

##### Back End:
###### External:
 - IParse

###### Internal:
 - ICommand
 - IManager

#### Use Cases

 - The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the
  command is added to the environment's history.
     - Console passes input to Parser using Parser.parse(input). Parser makes one command with the turtle and sends it 
     to the manager using addCommand. The parser then finds the argument, and sends that to the manager using 
     addArg(double arg). The manager tests if it has enough arguments to run the most recent command (in this case 'fd')
      and calls the execute(Turtle turtle) command through the runCommand method. The execute method unique to a forward
       command class would call moveForward() on the turtle passing the 50 in as an argument. The turtle then passes 
       that input to the Canvas using the drawPath method with the appropriate internally calculated path.
 - The user sets the pen's color using the UI so subsequent lines drawn when the turtle moves use that color.
     - The user enters a color using the Canvas pane's UI. No external calls are made. The Canvas calls 
     setPenColor(color).

#### Individual Use Cases
 - Thomas
     - The user types 'penup fd 50 pendown fd 50' in the script editor, then hits run. They see the turtle move in the 
     display window leaving a trail.
         - ScriptEditor passes the input to Parser using Parser.parse(input). Parser creates four Command instances 
         using Command.command(turtle, args...), then calls Command.execute(turtle) for each command. Each command 
         calls appropriate methods in Turtle, such as Turtle.move(50). The Turtle passes this input to the Canvas using
          Canvas.drawPath(path) with an appropriate path.
     - The user clears the canvas by typing 'clearscreen' in the console, deleting all previous drawings.
         - Console passes the input to Parser using Parser.parse(input). Parser creates a new Command instance using 
         Command.command(turtle, args...), then calls Command.execute(turtle). The command calls the method 
         Turtle.clear(). The Turtle calls the method Canvas.clear(), and the Canvas deletes all previous paths.
 - Vineet
     - The user types right 30 fd 40 in the script editor and clicks run.
         - The ScriptEditor passes the input to Parser using Parser.parse(input). Based on the command entered (in this 
         case forward and right) the parser would create instances of these two commands, and call 
         command.execute(turtle). This would call methods moveForward and Rotate in the turtle class. Finally, the path 
         is drawn once the turtle passes the input the the Canvas and the drawPath method is called. 
     - The user wants to hide the turtle so that lines are drawn without the turtle's image being present on the canvas.
        - The user interacts with a button in the Canvas UI, which toggles whether or not the turtle is visible by 
        directly calling the turtle's "show()" and "hide()" methods.
 - Andrew
     - The user wants to change the pen thickness using the UI so subsequent lines drawn when the turtle moves use that 
     thickness.
         - Thickness change is handled in the Canvas' UI, this is all handled by an internal call of 
         Visualize.setPenThickness
     - The user types 'fd fd 50' in the command window and sees it move across the screen leaving a line 
         - Since handling 'fd 50' was discussed in a previous use case, this one will only concern handling the returned 
         value of 'fd 50'. The parser will parse the first command, make a Command, and send it to the manager with the 
         addCommand method. The manager will add the command to the command tree and see that there is insufficient 
         arguments to execute it. The parser will then parse the second command, making another Command object that is 
         passed to the manager again with the addCommand method. The second command is placed in the argument leaf for 
         the first command within the addCommand method. The manager then sees that again there is insufficient 
         arguments to execute the most recent command so it does not run. The parser then reads in 50 as an argument and 
         sends it to the manager via the addArg method. The parser adds this value as a leaf to the most recent command 
         and checks if it can be run through the enoughArgs boolean. Since it does it uses the runCommand method that 
         changes the turtle exactly like 'fd 50' would. The output of runCommand replaces the command that ran it in the 
         command tree and checks if the command above it can now run. Now the first 'fd' is satisfied (checking through 
         the enoughArgs method once more) and executed via the turtle again.
 - Raj
     - The user enters an invalid command such as : "afwsadfg 575"
         - The ScriptEditor passes the input to Parser using Parser.parse(input). The method would check the command 
         with the list of commands it has in the parser class and when it does not find the appropriate command, an 
         error is thrown on the display, and this error handling logic will be located in dedicated error class.
     - The user wants to rotate the turtle. They enter "right 45."
         - The ScriptEditor would pass the user input to the parser using the Parser.parser(input) method. The parser an 
         instance of the rotate command class using Command.command(turtle, args...), then calls 
         Command.execute(turtle). The turtle would pass this input into the setHeading method in the visualizer class 
         which would update the heading of the turtle visual accordingly.

### Design Considerations
One important discussion our group had was regarding the interaction between the front-end and the back-end components 
of our program. 

We decided that the front-end would handle reading user input from the script editor and the visualization, and the 
back-end would handle parsing the user input, and conveying the specific commands to the visualizer.

One concern we quickly noticed was sending information between the controller module in the back-end, to the visualizer 
in the front-end, and how we could do so. After the discussion, we decided to have a turtle class(the object that moves 
on the display, which we are calling turtle for now) which stores essential information regarding the position, 
orientation, and pen color among other things of the turtle object on the display. The controller module would then use 
the external APIs of the front end to change these values based on the parsed user commands (which we would feed into 
the front-end command by command). The controller would also then use the external APIs to invoke elementary methods to 
process those changes on the front-end, which would later be reflected on the display. An advantage of using this design 
implementation is simplicity and organized code because all the necessary movement and state attributes would be 
contained in one class and the APIs would essentially be getter and setter methods, and that is conceptually simple and 
elegant.

An alternative would be to have multiple methods in the front-end to mirror the specific commands the user could input. 
A downside to this design is that there would be too many functions in both the front-end and back-end since we would 
have needed to make 2 functions for all the different commands, one on each end of the program.

### Team 
Front End: Thomas, Tharun Raj
Back End: Andrew, Vineet

