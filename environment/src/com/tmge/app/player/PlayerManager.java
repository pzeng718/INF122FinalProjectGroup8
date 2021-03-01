package com.tmge.app.player;

import com.tmge.app.Manager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
public class PlayerManager implements Manager {

    private final List<DefaultPlayer> players;
    @Setter
    private DefaultPlayer currentPlayer;

    public PlayerManager() {
        this.players = new ArrayList<>();
    }

    @Override
    public void load() {

    }

    @Override
    public void close() {

    }

    @Override
    public void save() {

    }

    public Optional<DefaultPlayer> getCurrentPlayer() {
        return Optional.ofNullable(this.currentPlayer);
    }

}
