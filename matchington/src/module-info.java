/**
 * @author vladshaternik on 2/21/21
 */module matchington {
    requires lombok;
    requires javafx.controls;
    requires javafx.base;
    requires environment;
    requires java.desktop;
    requires java.logging;

    exports com.matchington.ui;
    exports com.matchington to games;
}
