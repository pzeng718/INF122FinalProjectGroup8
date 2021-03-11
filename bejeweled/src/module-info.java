/**
 * @author vladshaternik on 2/21/21
 */module bejeweled {
    requires lombok;
    requires javafx.controls;
    requires javafx.base;
    requires environment;
    requires java.desktop;
    requires java.logging;

    exports com.bejeweled.ui;
    exports com.bejeweled to games;
}
