module com.example.quidon.gamejava {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.quidon.gamejava to javafx.fxml;
    exports com.example.quidon.gamejava;
}