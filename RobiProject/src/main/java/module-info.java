module defalt.robiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports defalt.robiproject.ui;
    opens defalt.robiproject.ui to javafx.fxml;
}