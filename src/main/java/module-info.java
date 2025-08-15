module co.edu.uniquindio.poo.veterinaria {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;

    // Abrimos el paquete principal a FXML y Graphics (para App)
    opens co.edu.uniquindio.poo.veterinaria to javafx.fxml, javafx.graphics;

    // Abrimos el paquete de controladores FXML
    opens co.edu.uniquindio.poo.veterinaria.viewController to javafx.fxml;

    // Exportamos si otros módulos usan estas clases
}
