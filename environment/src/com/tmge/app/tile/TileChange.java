package com.tmge.app.tile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Point;

/**
 * @author vladshaternik on 2/28/21
 */
@Getter
@RequiredArgsConstructor
public class TileChange<T extends DefaultTile> {
    private final T tile;
    private final Point point;
    private final TileChangeType changeType;
}
