module com.example.sdev425hw2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.sdev425hw2 to javafx.fxml;
    exports com.example.sdev425hw2;
}