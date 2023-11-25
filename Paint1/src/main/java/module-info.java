module com.example.paint1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires java.logging;


    opens com.example.paint1 to javafx.fxml;
    exports com.example.paint1;
}