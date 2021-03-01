package com.candycrush.ui.views.game;

import com.candycrush.Game;
import com.candycrush.app.SquareBoard;
import com.candycrush.ui.views.menu.dialog.StartGameOptions;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChangeType;
import com.tmge.ui.components.StyleClass;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

/**
 * @author vladshaternik on 2/28/21
 */
@Getter
public class GameView extends BorderPane {
    private final StartGameOptions startGameOptions;
    private final SquareBoard squareBoard;
    private final TileGridPane boardGridPane;
    private final SimpleObjectProperty<TileViewComponent> selectedTileViewComponent;
    private final Set<Point> selectedTileNeighbours;

    public GameView(StartGameOptions startGameOptions) {
        this.startGameOptions = startGameOptions;
        this.squareBoard = new SquareBoard(startGameOptions.getLevel());
        this.boardGridPane = new TileGridPane(startGameOptions.getLevel());
        this.selectedTileViewComponent = new SimpleObjectProperty<>();
        this.selectedTileNeighbours = new HashSet<>();
    }

    public GameView init() {
        getBoardGridPane().init();
        getSquareBoard().getTilesCollection().addTileChangeListener(tileChange -> {
            TileChangeType changeType = tileChange.getChangeType();
            ObservableList<Node> children = getBoardGridPane().getChildren();
            Point point = tileChange.getPoint();
            switch (changeType) {
                case CREATED:
                    TileViewComponent component = new TileViewComponent(tileChange.getTile().getTileTypeInfo().getImage(), tileChange.getTile()).init();
                    getBoardGridPane().add(component, point.x, point.y);
                    component.setOnMouseClicked(mouseEvent -> setSelectedTileViewComponent(component));
                    break;
                case REMOVED:
                    for (Iterator<Node> iterator = children.iterator(); iterator.hasNext(); ) {
                        Node child = iterator.next();
                        if (child instanceof TileViewComponent) {
                            TileViewComponent tileViewComponent = (TileViewComponent) child;
                            if (tileViewComponent.getTile().equals(tileChange.getTile())) {
                                iterator.remove();
                            }
                        }
                    }
                    break;
                case MOVED:
                    Optional<Node> nodeToMoveOptional = children.stream().filter(node -> {
                        if (node instanceof TileViewComponent) {
                            TileViewComponent tileViewComponent = (TileViewComponent) node;
                            return tileViewComponent.getTile().equals(tileChange.getTile());
                        }
                        return false;
                    }).findFirst();
                    if (nodeToMoveOptional.isPresent()) {
                        Node nodeToMove = nodeToMoveOptional.get();
                        Optional<Node> nodeByRowColumn = getBoardGridPane().getNodeByRowColumn(point.x, point.y);
                        if (nodeByRowColumn.isEmpty()) {
                            children.remove(nodeToMove);
                            getBoardGridPane().add(nodeToMove, point.x, point.y);
                        }
                    }
                    break;
                case SWAPPED:
                    Optional<Node> firstNodeOptional = children.stream().filter(node -> {
                        if (node instanceof TileViewComponent) {
                            TileViewComponent tileViewComponent = (TileViewComponent) node;
                            return tileViewComponent.getTile().equals(tileChange.getTile());
                        }
                        return false;
                    }).findFirst();
                    Optional<Node> secondNodeOptional = getBoardGridPane().getNodeByRowColumn(point.x, point.y);
                    if (firstNodeOptional.isPresent() && secondNodeOptional.isPresent()) {
                        TileViewComponent firstNode = (TileViewComponent) firstNodeOptional.get();
                        TileViewComponent secondNode = (TileViewComponent) secondNodeOptional.get();
                        DefaultTile secondNodeTile = secondNode.getTile();
                        Optional<Point> secondPointOptional = getSquareBoard().getTilesCollection().getPointByTile(secondNodeTile);
                        if (secondPointOptional.isPresent()) {
                            Point secondPoint = secondPointOptional.get();
                            getBoardGridPane().getChildren().remove(firstNode);
                            getBoardGridPane().getChildren().remove(secondNode);
                            getBoardGridPane().add(firstNode, point.x, point.y);
                            getBoardGridPane().add(secondNode, secondPoint.x, secondPoint.y);
                        }
                    }
                    break;
            }
        });
        getSquareBoard().init();
        selectedTileViewComponentProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                ObservableList<Node> children = getBoardGridPane().getChildren();
                for (Node child : children) {
                    child.getStyleClass().remove(StyleClass.TILE_GLOW);
                    child.getStyleClass().remove(StyleClass.TILE_GLOW_MAIN);
                }
                Optional<Point> newTilePointOptional = getSquareBoard().getTilesCollection().getPointByTile(newValue.getTile());
                if (newTilePointOptional.isPresent()) {
                    boolean swapped = false;
                    Point newTilePoint = newTilePointOptional.get();
                    if (oldValue != null && oldValue.getTile().getTileTypeInfo() != newValue.getTile().getTileTypeInfo()) {
                        Optional<Point> oldTilePointOptional = getSquareBoard().getTilesCollection().getPointByTile(oldValue.getTile());
                        if (oldTilePointOptional.isPresent()) {
                            Point oldTilePoint = oldTilePointOptional.get();
                            if (getSelectedTileNeighbours().contains(newTilePoint)) {
                                getSquareBoard().getTilesCollection().swap(oldTilePoint, newTilePoint);
                                getSelectedTileNeighbours().clear();
                                setSelectedTileViewComponent(null);
                                swapped = true;
                            }
                        }
                    }
                    if (!swapped) {
                        Point topPoint = new Point(newTilePoint.x, newTilePoint.y + 1);
                        Point bottomPoint = new Point(newTilePoint.x, newTilePoint.y - 1);
                        Point rightPoint = new Point(newTilePoint.x + 1, newTilePoint.y);
                        Point leftPoint = new Point(newTilePoint.x - 1, newTilePoint.y);
                        getSelectedTileNeighbours().clear();
                        getSelectedTileNeighbours().add(topPoint);
                        getSelectedTileNeighbours().add(bottomPoint);
                        getSelectedTileNeighbours().add(rightPoint);
                        getSelectedTileNeighbours().add(leftPoint);
                        newValue.getStyleClass().add(StyleClass.TILE_GLOW_MAIN);
                        addGlowStyle(topPoint);
                        addGlowStyle(bottomPoint);
                        addGlowStyle(rightPoint);
                        addGlowStyle(leftPoint);
                    }
                }
            }
        });
        getBoardGridPane().setAlignment(Pos.CENTER);
        getBoardGridPane().setVgap(5);
        getBoardGridPane().setHgap(5);
        setCenter(getBoardGridPane());
        return this;
    }

    private void addGlowStyle(Point point) {
        getSquareBoard().getTilesCollection().getTileByPoint(point)
                .flatMap(t -> getBoardGridPane().getNodeByRowColumn(point.x, point.y)).ifPresent(node -> {
            node.getStyleClass().add(StyleClass.TILE_GLOW);
        });
    }

    public TileViewComponent getSelectedTileViewComponent() {
        return selectedTileViewComponent.get();
    }

    public SimpleObjectProperty<TileViewComponent> selectedTileViewComponentProperty() {
        return selectedTileViewComponent;
    }

    public void setSelectedTileViewComponent(TileViewComponent selectedTileViewComponent) {
        this.selectedTileViewComponent.set(selectedTileViewComponent);
    }
}
