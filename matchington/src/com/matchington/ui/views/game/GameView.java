package com.matchington.ui.views.game;

import com.matchington.Game;
import com.matchington.app.SquareBoard;
import com.matchington.app.SwapResponseImpl;
import com.matchington.app.player.Player;
import com.matchington.ui.views.game.dialog.LostDialog;
import com.matchington.ui.views.game.dialog.WinDialog;
import com.matchington.ui.views.menu.MenuView;
import com.matchington.ui.views.menu.dialog.StartGameOptions;
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
    private final SimpleObjectProperty<Player> currentPlayer;

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
                                SwapResponseImpl swapResponse = (SwapResponseImpl) getSquareBoard().getTilesCollection().swap(oldTilePoint, newTilePoint);
                                getSelectedTileNeighbours().clear();
                                setSelectedTileViewComponent(null);
                                swapped = true;
                                getCurrentPlayer().setCurrentMoves(getCurrentPlayer().getCurrentMoves() + 1);
                                getCurrentPlayer().setCurrentScore(getCurrentPlayer().getCurrentScore() + swapResponse.getScore());
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
        setRight(createPlayerView(getFirstPlayer()));
        if (getSecondPlayer() != null) {
            setLeft(createPlayerView(getSecondPlayer()));
        }
        HBox top = new HBox(10);
        top.setPrefHeight(50);
        top.setAlignment(Pos.CENTER);
        top.setStyle("-fx-background-color: rgb(100, 100, 150)");
        Button mainMenuButton = UIComponents.createDangerButton("Main Menu");
        mainMenuButton.setOnAction(actionEvent -> {
            Game.getInstance().getStageManager().loadView(new MenuView().init());
        });
        top.getChildren().addAll(UIComponents.createTitleLabel("Objective: Score-" + getStartGameOptions().getLevel().getObjectiveScore() + " | Moves-" + getStartGameOptions().getLevel().getObjectiveMoves()),
                mainMenuButton);
        setTop(top);
        setCurrentPlayer(getFirstPlayer());
        if (getSecondPlayer() != null) {
            if (Math.random() < .5) {
                setCurrentPlayer(getSecondPlayer());
            }
        }
        getFirstPlayer().setCurrentMoves(0);
        getFirstPlayer().setCurrentScore(0);
        if (getSecondPlayer() != null) {
            getSecondPlayer().setCurrentMoves(0);
            getSecondPlayer().setCurrentScore(0);
        }
        return this;
    }

    private Player getSecondPlayer() {
        return getStartGameOptions().getSecondPlayer();
    }

    private void toggleCurrentPlayer() {
        if (getSecondPlayer() != null) {
            setCurrentPlayer(getCurrentPlayer().equals(getFirstPlayer()) ?
                    getSecondPlayer() :
                    getFirstPlayer());
        }
    }

    private Node createPlayerView(Player player) {
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
        Label scoreLabel = UIComponents.createLabel("Score: 0");
        player.currentScoreProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                scoreLabel.setText("Score: " + t1.intValue());
                if (t1.intValue() >= getStartGameOptions().getLevel().getObjectiveScore()) {
                    if (isSinglePlayer()) {
                        int currentLevel = getStartGameOptions().getLevel().getId();
                        if (getFirstPlayer().getLevel() <= currentLevel) {
                            getFirstPlayer().setLevel(currentLevel + 1);
                            getFirstPlayer().setTotalScore(getFirstPlayer().getTotalScore() + getFirstPlayer().getCurrentScore());
                        }
                    }
                    new WinDialog(player, Game.getInstance().getStageManager().getStage()).init().showAndWait();
                    Game.getInstance().getStageManager().loadView(new MenuView().init());
                }
            }
        });
        Label movesLabel = UIComponents.createLabel("Moves: 0");
        player.currentMovesProperty().addListener((observableValue, s, t1) -> {
            if (t1 != null) {
                movesLabel.setText("Moves: " + t1.intValue());
                if (t1.intValue() >= getStartGameOptions().getLevel().getObjectiveMoves()) {
                    if (isSinglePlayer() ||
                            (getSecondPlayer().getCurrentMoves() >= getStartGameOptions().getLevel().getObjectiveMoves() &&
                                    getFirstPlayer().getCurrentMoves() >= getStartGameOptions().getLevel().getObjectiveMoves())) {
                        new LostDialog(Game.getInstance().getStageManager().getStage()).init().showAndWait();
                        Game.getInstance().getStageManager().loadView(new MenuView().init());
                    }
                }
            }
        });
        content.getChildren().addAll(UIComponents.createTitleLabel(player.getUsername()),
                scoreLabel, movesLabel);
        return content;
    }

    private Player getFirstPlayer() {
        return getStartGameOptions().getFirstPlayer();
    }

    private boolean isSinglePlayer() {
        return getSecondPlayer() == null;
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

    public Player getCurrentPlayer() {
        return currentPlayer.get();
    }

    public SimpleObjectProperty<Player> currentPlayerProperty() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer.set(currentPlayer);
    }
}
