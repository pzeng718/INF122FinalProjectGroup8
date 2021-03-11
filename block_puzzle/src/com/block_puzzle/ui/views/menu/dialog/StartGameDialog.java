package com.block_puzzle.ui.views.menu.dialog;

import com.block_puzzle.Game;
import com.block_puzzle.app.Level;
import com.block_puzzle.app.player.Player;
import com.tmge.ui.components.AbstractDialog;
import com.tmge.ui.components.UIComponents;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vladshaternik on 2/28/21
 */
@Getter
public class StartGameDialog extends AbstractDialog<StartGameOptions> {

    private final ComboBox<Level> levelComboBox;
    private final ComboBox<Player> secondPlayerComboBox;
    private final CheckBox twoPlayersCheckBox;
    private final Player player;

    public StartGameDialog(Player player) {
        super(Game.getInstance().getStageManager().getStage());
        this.player = player;
        this.levelComboBox = new ComboBox<>();
        this.secondPlayerComboBox = new ComboBox<>();
        this.twoPlayersCheckBox = new CheckBox("Two Players");
    }

    @Override
    protected Node buildContent() {
        getLevelComboBox().getItems().addAll(Level.getLevelsBelowOrEqualToId(getPlayer().getLevel()));
        Level.findLevelById(getPlayer().getLevel()).ifPresent(level ->
                getLevelComboBox().getSelectionModel().select(level));
        List<Player> players = Game.getInstance().getPlayerManager().getPlayers().stream()
                .filter(p -> !p.equals(getPlayer()))
                .collect(Collectors.toList());
        getSecondPlayerComboBox().getItems().addAll(players);
        getSecondPlayerComboBox().setDisable(true);
        getSecondPlayerComboBox().disableProperty().bind(getTwoPlayersCheckBox().selectedProperty().not());
        return createVBoxForNodes(UIComponents.createLabelWithNodePane("Level", getLevelComboBox(), Orientation.VERTICAL),
                getTwoPlayersCheckBox(),
                UIComponents.createLabelWithNodePane("Second Player", getSecondPlayerComboBox(), Orientation.VERTICAL));
    }

    @Override
    protected void addOkButtonFilter(ActionEvent event) {
        if (getLevelComboBox().getSelectionModel().getSelectedItem() == null) {
            UIComponents.showTextOnlyDialog("Please select level.", Game.getInstance().getStageManager().getStage());
            event.consume();
        } else if (getTwoPlayersCheckBox().isSelected() && getSecondPlayerComboBox().getSelectionModel().getSelectedItem() == null) {
            UIComponents.showTextOnlyDialog("Please select a second player.", Game.getInstance().getStageManager().getStage());
            event.consume();
        }
    }

    @Override
    protected Optional<StartGameOptions> buildResult() {
        StartGameOptions.StartGameOptionsBuilder startGameOptionsBuilder = StartGameOptions.builder()
                .firstPlayer(getPlayer())
                .level(getLevelComboBox().getSelectionModel().getSelectedItem());
        if (getTwoPlayersCheckBox().isSelected()) {
            startGameOptionsBuilder.secondPlayer(getSecondPlayerComboBox().getSelectionModel().getSelectedItem());
        }
        return Optional.of(startGameOptionsBuilder.build());
    }

    @Override
    protected String getTitleString() {
        return "Start Game";
    }
}
