/**
 * @author vladshaternik on 2/21/21
 */module environment {
    requires lombok;
    requires javafx.controls;
    requires javafx.base;
    requires java.desktop;
    requires javafx.graphics;

    exports com.tmge.ui;
    exports com.tmge.app;
    exports com.tmge.ui.views.select_player;
    exports com.tmge.app.player;
    exports com.tmge.ui.views.menu;
    exports com.tmge.app.tile;
    exports com.tmge.ui.components;
}
