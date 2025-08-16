package co.edu.uniquindio.poo.veterinaria;

import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;
import co.edu.uniquindio.poo.veterinaria.viewController.*;
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
        this.primaryStage.setTitle("Gestión de Veterinaria");
        openViewPersonalApoyo();
    }

    public void openViewVeterinario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/veterinaria/Veterinario.fxml"));
            Parent root = loader.load();
            VeterinarioViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openViewPersonalApoyo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonalApoyo.fxml"));
            Parent root = loader.load();
            PersonalApoyoViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openViewCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/veterinaria/Citas.fxml"));
            Parent root = loader.load();
            CitaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openViewPropietario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/veterinaria/Cliente.fxml"));
            Parent root = loader.load();
            ClienteViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}