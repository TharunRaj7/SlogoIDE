# SIMULATION_API.md

Thomas Owens (to57), Vineet Alaparthi (va68), Andrew Krier (ak513), Tharun Mani Raj (tm272) 

### API Exploration

public class VisualizationView { 
    public VisualizationView(VisualizationModel model): EXTERNAL
    public Scene makeScene(int width, int height): INTERNAL
    public void updateGraph(): EXTERNAL
    public void graphData(Cell cell): EXTERNAL
    public void setSimData(SimData sim): EXTERNAL
    public Grid getSetGrid(): EXTERNAL
    public Grid getMyGridTwo(): EXTERNAL
    public void setAnimation(Timeline Animation): INTERNAL
}

public class VisualizationModel { 
    public VisualizationModel(): EXTERNAL
    public void setSimData(SimData sim): EXTERNAL
    public Grid getGrid(): EXTERNAL
    public Grid start(Timeline Animation): INTERNAL
    public void end(): INTERNAL
    public void speed(double speed): EXTERNAL
    public void stepThrough(): EXTERNAL
}

public abstract class Cell extends Polygon{ 
    public Cell(double... varargs): EXTERNAL
    public int getCurrentState(): INTERNAL
    public int getPreviousState(): INTERNAL
    public void updateCellColor(): INTERNAL
    public boolean checkGameOn(ArrayList<ArrayList<Cell\>> gridOfCells): INTERNAL
    public int getxVal(): INTERNAL
    public int getyVal(): INTERNAL
    public Color getColor(): EXTERNAL
    protected void resetState(): INTERNAL
    protected  abstract void updateCellValue(Neighborhood neighborhood): INTERNAL
    protected ArrayList<Cell\> getNeighborArray(): INTERNAL
}

public class Grid { 
    public Grid(int vCellNum, int hCellNum, String gameVariation,      List<List<Integer\>> cellVals, double spreadProb, String wrapType, String neighborType, int sides): EXTERNAL 
    public void updateGrid(): EXTERNAL
    public void updateColors(): INTERNAL
    public ArrayList<ArrayList<Cell\>> getListOfCells(): EXTERNAL
}

public class DataException extends ArithmeticException { 
    public DataException (String message, Object ... values): INTERNAL
    public DataException (String message): INTERNAL
}

public class SimData { 
    public SimData (String author, String simType, int cellSides, int cellRows, int cellColumns, String vals, double spreadProb, String wrapType, String neighborType): EXTERNAL
    public SimData (Map<String, String> dataValues): EXTERNAL
    public String getSimType (): EXTERNAL
    public String getAuthor (): EXTERNAL
    public int getRows (): EXTERNAL
    public int getColumns (): EXTERNAL
    public int getShape (): EXTERNAL
    public double getSpreadProb (): EXTERNAL
    public List<List<Integer\>> getValList (): EXTERNAL
    public String getWrapType (): EXTERNAL
    public String getNeighborType (): EXTERNAL
}

public class XMLDocumentBuilder { 
    public XMLDocumentBuilder (SimData simData, Grid grid): INTERNAL
}

public class XMLException extends RuntimeException { 
    public XMLException (String message, Object ... values): INTERNAL
    public XMLException (Throwable cause, String message, Object ... values): INTERNAL
    public XMLException (Throwable cause): INTERNAL
}

public class XMLParser { 
    public XMLParser (String type): EXTERNAL
    public SimData getSimData (File dataFile): EXTERNAL 
}

public class Neighborhood { 
    public Neighborhood(Cell cell, ArrayList<ArrayList<Cell\>> listOfCells, String type, String edgeCaseType): EXTERNAL
    public ArrayList<Cell\> getNeighbors(): EXTERNAL
    protected void findPartialNeighbors(ArrayList<ArrayList<Cell\>> gridOfCells, String type): EXTERNAL
    protected void findFullNeighbors(ArrayList<ArrayList<Cell\>> gridOfCells, String type): EXTERNAL
    protected void populateNeighbors(ArrayList<ArrayList<Cell\>> gridOfCells, int[][] arrayOfVals, String type): EXTERNAL
    protected void regularPopulate(ArrayList<ArrayList<Cell\>> gridOfCells, int[] cellCoordinate): EXTERNAL
    protected void toroidalPopulate(ArrayList<ArrayList<Cell\>> gridOfCells, int[] cellCoordinate): EXTERNAL
}

### Use Cases:

External: as a client of the backend, the frontend selects an existing simulation to display and then starts running that kind of simulation, updating its own grid visualization

English:
Visualization is able to call getSimData on a file given to it, then will use that simData as well as the getters available to initialize the initial condition of the simulation. From there, it should be able to call updates to the grid, as well as request the color from each cell to be able to display the proper values, without handling any of the decision making of the backend. 

Java:
call getSimData
implement grid()
call updateGrid()
implement cell()
call getColor()


Internal: a new developer joins the the backend team and adds a new possible kind of simulation that can be available for the frontend to choose

English: 
Inside the cellsociety package, the new developer adds a new class that extends Cell to model the rules of the new simulation and implements all of the methods identified in the Cell API, both internal and external.

Java: 
implement public class NewCell extends Cell {
implement public NewCell(double... varargs)
implement public int getCurrentState()
implement public int getPreviousState()
implement public void updateCellColor()
implement public boolean checkGameOn(ArrayList<ArrayList<Cell>> gridOfCells)
implement public int getxVal()
implement public int getyVal()
implement public Color getColor()
implement protected void resetState()
implement protected abstract void updateCellValue(Neighborhood neighborhood)
implement protected ArrayList<Cell> getNeighborArray()
}

[//]: # (https://hackmd.io/@b7D19V2FSWulY5D11map5w/ryA9yHQQL)
