package com.tmge.app.tile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

/**
 * @author vladshaternik on 2/28/21
 */
@Getter
@RequiredArgsConstructor
public class TileChange {
    private final DefaultTile tile;
    private final Point point;
    private final TileChangeType changeType;
}
