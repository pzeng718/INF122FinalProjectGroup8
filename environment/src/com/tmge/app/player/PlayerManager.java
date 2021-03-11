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
public class PlayerManager<T extends DefaultPlayer> implements Manager {

    private final List<T> players;
    @Setter
    private T currentPlayer;

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

    public Optional<T> getCurrentPlayer() {
        return Optional.ofNullable(this.currentPlayer);
    }

}
