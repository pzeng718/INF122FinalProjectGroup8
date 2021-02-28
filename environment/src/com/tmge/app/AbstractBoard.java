import lombok.Getter;

@Getter
abstract class AbstractBoard {
    AbstractTilesCollection tiles; 

    //Would it make more sense for this to be a setter? 
    public abstract AbstractTilesCollection createTilesCollection(){

    } 
}