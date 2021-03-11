package com.tmge.app.player;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author vladshaternik on 2/21/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPlayer {

    @Builder.Default
    private String username = "";

    @Builder.Default
    private int level = 1;

    @Builder.Default
    private int totalScore = 0;

    @Override
    public String toString() {
        return getUsername();
    }
}
