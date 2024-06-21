module com.example.demo5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.HexGamee to javafx.fxml;
    exports com.example.HexGamee;
}