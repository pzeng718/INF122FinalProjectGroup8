package com.candycrush.ui.views.statistics;


import com.tmge.app.player.DefaultPlayer;
import com.tmge.ui.components.UIComponents;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import lombok.Getter;

@Getter
public class StatisticsView extends BorderPane {
    private final DefaultPlayer player;
    private final GridPane layout;
    private final Label headingLabel;
    private final Label levelLabel;
    private final Label scoreLabel;
    private final Label levelValueLabel;
    private final Label scoreValueLabel;



    public StatisticsView(DefaultPlayer player) {
        this.player = player;
        this.layout = new GridPane();
        this.headingLabel = UIComponents.createTitleLabel("PLAYER'S STATISTICS");
        this.levelLabel = UIComponents.createLabel("Player's Level");
        this.scoreLabel = UIComponents.createLabel("Player's Score");
        this.scoreValueLabel = UIComponents.createLabel(String.valueOf(player.getScore()));
        this.levelValueLabel = UIComponents.createLabel(String.valueOf(player.getLevel()));

    }

    public StatisticsView init() {
        getLayout().setHgap(30);
        getLayout().setVgap(50);
        setAlignment(getLayout(), Pos.CENTER);

        getLayout().add(headingLabel, 0, 0);
        getLayout().add(levelLabel, 0, 1);
        getLayout().add(scoreLabel, 0, 2);
        getLayout().add(levelValueLabel, 1, 1);
        getLayout().add(scoreValueLabel, 1, 2);
        setCenter(getLayout());
        return this;
    }
}
