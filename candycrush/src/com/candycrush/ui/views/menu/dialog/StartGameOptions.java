package com.candycrush.ui.views.menu.dialog;

import com.candycrush.app.Level;
import com.tmge.app.player.DefaultPlayer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vladshaternik on 2/28/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartGameOptions {
    @Builder.Default
    private final Level level = Level.ONE;
    @Builder.Default
    private final DefaultPlayer secondPlayer = null;
}
