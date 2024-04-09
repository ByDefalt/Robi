module defalt.robiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;
	requires javafx.swing;

    exports defalt.robiproject.ui;
    opens defalt.robiproject.ui to javafx.fxml;
}