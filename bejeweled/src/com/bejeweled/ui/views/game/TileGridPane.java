package com.bejeweled.ui.views.game;

import com.bejeweled.app.Level;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.Getter;

import java.util.Optional;

/**
 * @author vladshaternik on 2/28/21
 */
@Getter
public class TileGridPane extends GridPane {

    private final int boardWidth;
    private final int boardHeight;

    public TileGridPane(Level level) {
        super();
        this.boardWidth = level.getWidth();
        this.boardHeight = level.getHeight();
        setGridLinesVisible(true);
    }

    public TileGridPane init() {
        for (int i = 0; i < getBoardWidth(); i++) {
            getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i = 0; i < getBoardHeight(); i++) {
            getRowConstraints().add(new RowConstraints(50));
        }
        return this;
    }

    @Override
    public void add(Node child, int columnIndex, int rowIndex) {
        super.add(child, columnIndex, getBoardHeight() - rowIndex);
    }

    public Optional<Node> getNodeByRowColumn(int columnIndex, int rowIndex) {
        ObservableList<Node> children = getChildren();
        for (Node node : children) {
            Integer currentColumnIndex = getColumnIndex(node);
            Integer currentRowIndex = getRowIndex(node);
            if (currentColumnIndex != null && currentRowIndex != null) {
                if (currentColumnIndex == columnIndex && currentRowIndex == (getBoardHeight() - rowIndex)) {
                    return Optional.of(node);
                }
            }
        }
        return Optional.empty();
    }
}
