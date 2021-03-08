/**
 * @author vladshaternik on 2/21/21
 */module bejeweled {
    requires lombok;
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.base;
    requires environment;
    requires java.desktop;

    exports com.bejeweled.ui;
    exports com.bejeweled to games;
}
