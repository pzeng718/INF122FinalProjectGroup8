/**
 * @author vladshaternik on 2/21/21
 */module block.puzzle {
    requires lombok;
    requires javafx.controls;
    requires javafx.base;
    requires environment;
    requires java.desktop;
    requires java.logging;

    exports com.block_puzzle.ui;
    exports com.block_puzzle to games;
}
