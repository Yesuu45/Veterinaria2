package co.edu.uniquindio.poo.veterinaria;

import co.edu.uniquindio.poo.veterinaria.model.*;
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
        openViewPrincipal();
    }


    public void openViewPrincipal() {
        inicializarVeterinaria();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
            Parent root = loader.load();

            PrimaryViewControler controller = loader.getController(); // ✔ Correcto
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema Veterinaria");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void openViewVeterinario() {
        inicializarVeterinaria();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Veterinario.fxml"));
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
        inicializarVeterinaria();
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
        inicializarVeterinaria();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Citas.fxml"));
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
        inicializarVeterinaria();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cliente.fxml"));
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


    public void openViewConsulta() {
        inicializarVeterinaria();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Consulta.fxml"));
            Parent root = loader.load();
            ConsultaViewController controller = loader.getController();
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

    public void inicializarVeterinaria() {

        // Crear personal de apoyo y veterinario
        PersonalApoyo personalApoyo = new PersonalApoyo(
                "Juan", "12345678", "Calle 123", "45678912", "Juan@gmail.com");
        Veterinario veterinario = new Veterinario(
                "Maicol", "1011201431", "cr 40 #42-16", "3238264868", "Maicol@gmail.com", "001", Especialidad.ACUATICOS);

        // Agregar personal y veterinario a la veterinaria
        veterinaria.agregarPersonalApoyo(personalApoyo);
        veterinaria.agregarVeterinario(veterinario);

        // Crear propietario
        Propietario propietario = new Propietario(
                "Yesuu", "10214321", "cr40#45#17", "21121212", "yesuaesteban@gmail.com"
        );

        // Crear mascotas del propietario
        Mascota mascota = new Mascota("mani",Especie.GATO,propietario , 2, "13131313");
        Mascota mascota2 = new Mascota("juan",Especie.PERRO, "siames","10214321",22,"3");

        // Agregar mascotas al propietario
        propietario.agregarMascota(mascota);
        propietario.agregarMascota(mascota2);

        // Agregar propietario a la veterinaria 
        veterinaria.agregarCliente(propietario);
    }

}