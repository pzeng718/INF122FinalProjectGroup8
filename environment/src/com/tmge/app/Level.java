//Tutorial on Enum: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html 
import lombok.Getter;

@Getter
public enum Level {
    //NEED TO FIX THESE ENUM VALUES SO THAT THE ACTUAL ARGUMENT TYPE FOR objectiveTileType IS TileType. Right now objectiveTileType argument is a string as a placeholder. 
    //You may also need to adjust the actual int values to suit the level difficulty. 
    ONE (1, "Diamond", 10, 20, 20, 20),
    TWO (2, "Crystal", 15, 15, 15, 15),
    THREE (3, "Coin", 20, 10, 10, 10); 

    private final int levelNum; 
    private final TileType objectiveTileType; 
    private final int objectiveQuantity; 
    private final int turns; 
    private final int height; 
    private final int width; 

    Level(int levelNum, TileType objectiveTileType, int objectiveQuantity, int turns, int height, int width) {
        this.levelNum = levelNum;
        this.objectiveTileType = objectiveTileType; 
        this.objectiveQuantity = objectiveQuantity; 
        this.turns = turns; 
        this.height = height; 
        this.width = width; 
    }
}