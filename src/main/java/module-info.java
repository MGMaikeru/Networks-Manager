module com.example.graphdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphdemo to javafx.fxml;
    exports com.example.graphdemo;
}