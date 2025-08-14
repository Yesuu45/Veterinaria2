package co.edu.uniquindio.poo.veterinaria;

import co.edu.uniquindio.poo.veterinaria.controller.ClienteController;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;
import co.edu.uniquindio.poo.veterinaria.viewController.ClienteViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    public static Veterinaria veterinaria = new Veterinaria("Canino", "cr40-42-16", "21212121", "232323");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión de Clientes");
        openViewPrincipal();
    }

    private void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cliente.fxml"));
            Parent rootLayout = loader.load(); // ✅ Ahora usamos Parent para evitar errores de casteo

            // Obtiene el controlador de la vista
            ClienteViewController clienteViewController = loader.getController();
            clienteViewController.setApp(this);
            clienteViewController.setClienteController(new ClienteController(veterinaria));

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista Cliente.fxml");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
