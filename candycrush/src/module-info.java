/**
 * @author vladshaternik on 2/21/21
 */module candycrush {
    requires lombok;
    requires javafx.controls;
    requires javafx.base;
    requires environment;
    requires java.desktop;
    requires java.logging;

    exports com.candycrush.ui;
    exports com.candycrush to games;
}
