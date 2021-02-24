package com.tmge.app.player;

import com.tmge.app.Manager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vladshaternik on 2/21/21
 */
@Getter
public class PlayerManager implements Manager {

    private final List<DefaultPlayer> players;

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
}
