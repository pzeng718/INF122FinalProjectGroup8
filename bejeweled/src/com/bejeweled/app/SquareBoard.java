package com.bejeweled.app;

import com.tmge.app.AbstractBoard;
import lombok.Getter;

@Getter
public class SquareBoard extends AbstractBoard {
    private final Level level;

    public SquareBoard(Level level) {
        super();
        this.level = level;
    }

    public void init() {
        ((SquareTilesCollection) getTilesCollection()).init(getLevel());
    }

    @Override
    protected SquareTilesCollection createTilesCollection() {
        return new SquareTilesCollection();
    }
}
