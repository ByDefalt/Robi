module defalt.robiproject {
    requires javafx.controls;
    requires javafx.fxml;

    exports defalt.robiproject.ui;
    opens defalt.robiproject.ui to javafx.fxml;
}