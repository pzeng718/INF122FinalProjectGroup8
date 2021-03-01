package com.candycrush.app;

import com.tmge.app.AbstractBoard;
import com.tmge.app.AbstractTilesCollection;
import lombok.Getter;

@Getter
class SquareBoard extends AbstractBoard {
    Level level; 

    public SquareBoard(Level level){
        this.level = level; 
    }

    public void init(){ 
        
    }

    //I think you need to change the return type in the uml class diagram for the SquareBoard's class so that createTilesCollection() returns a AbstractTilesCollection
    @Override
    public SquareTilesCollection createTilesCollection(){
        return new SquareTilesCollection(getLevel().getWidth(), getLevel().getHeight());
    } 
}