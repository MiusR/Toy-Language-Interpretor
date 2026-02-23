module org.misu.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;
    requires javafx.graphics;
    requires javafx.base;

    opens org.misu.finalproject to javafx.fxml;
    opens org.misu.finalproject.controller to javafx.fxml;

    exports org.misu.finalproject.view;
    exports org.misu.finalproject.controller;
    exports org.misu.finalproject;
}