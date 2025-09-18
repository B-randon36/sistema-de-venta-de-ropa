module com.example.registroproductoropa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.registroproductoropa to javafx.fxml;
    exports com.example.registroproductoropa;
}