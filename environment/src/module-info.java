/**
 * @author vladshaternik on 2/21/21
 */module environment {
    requires lombok;
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.base;

    exports com.tmge.ui;
    exports com.tmge.app;
    exports com.tmge.ui.views.select_player;
    exports com.tmge.app.player;
    exports com.tmge.ui.views.menu;
}
