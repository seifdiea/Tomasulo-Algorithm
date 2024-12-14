module com.example.tomasulosalgorithmgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.tomasulosalgorithmgui to javafx.fxml;
    exports com.example.tomasulosalgorithmgui;
}