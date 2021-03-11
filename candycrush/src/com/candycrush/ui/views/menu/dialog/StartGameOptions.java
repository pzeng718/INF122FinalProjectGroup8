package com.candycrush.ui.views.menu.dialog;

import com.candycrush.app.Level;
import com.candycrush.app.player.Player;
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
    private final Player firstPlayer = null;
    @Builder.Default
    private final Player secondPlayer = null;
}
