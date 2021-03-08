package com.bejeweled.ui.views.game;

import com.bejeweled.Game;
import com.bejeweled.app.SquareBoard;
import com.bejeweled.app.SquareTilesCollection;
import com.bejeweled.ui.views.game.dialog.LostDialog;
import com.bejeweled.ui.views.game.dialog.WinDialog;
import com.bejeweled.ui.views.menu.MenuView;
import com.bejeweled.ui.views.menu.dialog.StartGameOptions;
import com.tmge.app.player.DefaultPlayer;
import com.tmge.app.tile.DefaultTile;
import com.tmge.app.tile.TileChangeType;
import com.tmge.ui.components.StyleClass;
import com.tmge.ui.components.UIComponents;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.awt.Point;
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
    private final SimpleObjectProperty<DefaultPlayer> currentPlayer;

    public GameView(StartGameOptions startGameOptions) {
        this.startGameOptions = startGameOptions;
        this.squareBoard = new SquareBoard(startGameOptions.getLevel());
        this.boardGridPane = new TileGridPane(startGameOptions.getLevel());
        this.selectedTileViewComponent = new SimpleObjectProperty<>();
        this.selectedTileNeighbours = new HashSet<>();
        this.currentPlayer = new SimpleObjectProperty<>();
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
                                int score = getSquareBoard().getTilesCollection().swap(oldTilePoint, newTilePoint);
                                getSelectedTileNeighbours().clear();
                                setSelectedTileViewComponent(null);
                                swapped = true;
                                getCurrentPlayer().setCurrentMoves(getCurrentPlayer().getCurrentMoves() + 1);
                                getCurrentPlayer().setCurrentScore(getCurrentPlayer().getCurrentScore() + score);
                                toggleCurrentPlayer();
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
        setRight(createPlayerView(getStartGameOptions().getFirstPlayer()));
        if (getStartGameOptions().getSecondPlayer() != null) {
            setLeft(createPlayerView(getStartGameOptions().getSecondPlayer()));
        }
        HBox top = new HBox(10);
        top.setPrefHeight(50);
        top.setAlignment(Pos.CENTER);
        top.setStyle("-fx-background-color: rgb(100, 100, 150)");
        Button giveUpButton = UIComponents.createDangerButton("Give UP");
        giveUpButton.setOnAction(actionEvent -> {
            Game.getInstance().getStageManager().loadView(new MenuView().init());
        });
        top.getChildren().addAll(UIComponents.createTitleLabel("Objective: Remove All Tiles (Tiles amount below " + getStartGameOptions().getLevel().getTilesLeft() + ")"),
                giveUpButton);
        setTop(top);
        setCurrentPlayer(getStartGameOptions().getFirstPlayer());
        if (getStartGameOptions().getSecondPlayer() != null) {
            if (Math.random() < .5) {
                setCurrentPlayer(getStartGameOptions().getSecondPlayer());
            }
        }
        getStartGameOptions().getFirstPlayer().setCurrentScore(0);
        if (getStartGameOptions().getSecondPlayer() != null) {
            getStartGameOptions().getSecondPlayer().setCurrentScore(0);
        }
        return this;
    }

    private void toggleCurrentPlayer() {
        if (getStartGameOptions().getSecondPlayer() != null) {
            setCurrentPlayer(getCurrentPlayer().equals(getStartGameOptions().getFirstPlayer()) ?
                    getStartGameOptions().getSecondPlayer() :
                    getStartGameOptions().getFirstPlayer());
        }
    }

    private Node createPlayerView(DefaultPlayer player) {
        VBox content = new VBox(10);
        content.setPrefWidth(200);
        currentPlayerProperty().addListener((observableValue, player1, t1) -> {
            if (t1 != null) {
                if (t1.equals(player)) {
                    content.setStyle("-fx-background-color: rgb(100, 100, 150); -fx-border-width: 2px; -fx-border-color: red");
                } else {
                    content.setStyle("-fx-background-color: rgb(100, 100, 150);");
                }
            }
        });
        content.setAlignment(Pos.CENTER);
        Label scoreLabel = UIComponents.createLabel("Tiles Removed: 0");
        player.currentScoreProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                scoreLabel.setText("Tiles Removed: " + t1.intValue());
                if (((SquareTilesCollection) getSquareBoard().getTilesCollection()).getTilesLeft() <
                        getStartGameOptions().getLevel().getTilesLeft()) {
                    if (isSinglePlayer()) {
                        int currentLevel = getStartGameOptions().getLevel().getId();
                        if (getStartGameOptions().getFirstPlayer().getLevel() <= currentLevel) {
                            getStartGameOptions().getFirstPlayer().setLevel(currentLevel + 1);
                        }
                        new WinDialog(player, Game.getInstance().getStageManager().getStage()).init().showAndWait();
                    } else {
                        if (getStartGameOptions().getFirstPlayer().getCurrentScore() < getStartGameOptions().getSecondPlayer().getCurrentScore()) {
                            new WinDialog(getStartGameOptions().getSecondPlayer(), Game.getInstance().getStageManager().getStage()).init().showAndWait();
                        } else {
                            new WinDialog(getStartGameOptions().getFirstPlayer(), Game.getInstance().getStageManager().getStage()).init().showAndWait();
                        }
                    }
                    Game.getInstance().getStageManager().loadView(new MenuView().init());
                }
            }
        });
        content.getChildren().addAll(UIComponents.createTitleLabel(player.getUsername()), scoreLabel);
        return content;
    }

    private boolean isSinglePlayer() {
        return getStartGameOptions().getSecondPlayer() == null;
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

    public DefaultPlayer getCurrentPlayer() {
        return currentPlayer.get();
    }

    public SimpleObjectProperty<DefaultPlayer> currentPlayerProperty() {
        return currentPlayer;
    }

    public void setCurrentPlayer(DefaultPlayer currentPlayer) {
        this.currentPlayer.set(currentPlayer);
    }
}
