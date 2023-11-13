module com.example.mines1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.mines1 to javafx.fxml;
    exports com.example.mines1;
}