module co.edu.uniquindio.poo.veterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;


    opens co.edu.uniquindio.poo.veterinaria to javafx.fxml;
    opens co.edu.uniquindio.poo.veterinaria.viewController to javafx.fxml;
    exports co.edu.uniquindio.poo.veterinaria;
}