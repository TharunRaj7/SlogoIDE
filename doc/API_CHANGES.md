# API_CHANGES.md
Thomas Owens, to57 
Andrew Krier, ak513
Vineet Aparthi, va68
Tharun Raj, tm272

## Frontend API Changes

#### External:
- The frontend external API has changed to support multiple turtles.
    - Methods previously held in Turtle and called by the backend have now been moved to TurtleController, which delegates to individual Turtles internally.
    - New methods have been added to support multiple turtles, such as tellTurtles, getActiveTurtleImages, getAllTurtleImages, and resetTurtles.
- A small fix was made in assigning Turtle headings to fix inconsistencies and clarify that headings are measured from the positive vertical axis, clockwise.

#### Internal:
- The frontend internal API has changed to include support for multiple Workspaces.
    - The SlogoFrame no longer has a method to add components to its layout, instead, it holds Workspaces.
    - Workspaces are created by a WorkspaceFactory, now, which added several internal methods.
- New utility classes in the utility package handle creation of Buttons and Menus, adding functionality to the API for flexible GUI designs.
- GUI elements like the TurtleCanvas and ScriptEditor now share a common parent, which provides API functionality to update languages and store/load elements in XML nodes.

## Backend API CHanges

#### External:
- Changed giveTurtle's argument in IParse to a TurtleController instance from a Turtle instance
    - This is to support the mirrored change in the frontend for supporting multiple turtles
    - TurtleController is then held by the parser and gets passed to each instance of an ICommand class
- Changed makeCommand's argument in IParse to a TurtleController instance from a Turtle instance
    - This is to support the mirrored change in the frontend for supporting multiple turtles
    - TurtleController is given to each method to be able to execute on it given what type of command it is
- Changed makeCommand to a privately held method
    - Isn't being used outside of the parser
    - Any fewer public methods we have, the generally better the code is


#### Internal:
- BlockCommand is now extended for any commands where an input is supposed to be a block command (eg For, Repeat, To)