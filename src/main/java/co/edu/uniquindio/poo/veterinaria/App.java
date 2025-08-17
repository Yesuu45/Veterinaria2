package co.edu.uniquindio.poo.veterinaria;

import co.edu.uniquindio.poo.veterinaria.model.*;
import co.edu.uniquindio.poo.veterinaria.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase principal de la aplicación de gestión veterinaria.
 * Se encarga de iniciar la aplicación, configurar la ventana principal
 * y manejar la navegación entre las distintas vistas (FXML).
 */
public class App extends Application {

    private Stage primaryStage;

    /** Instancia global de la veterinaria usada en toda la app */
    public static Veterinaria veterinaria =
            new Veterinaria("Canino", "cr40-42-16", "21212121", "232323");

    /**
     * Punto de entrada JavaFX...
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión de Veterinaria");

        // Inicializar datos de prueba solo una vez
        inicializarVeterinaria();

        // Abrir la vista principal
        openViewPrincipal();
    }

    /**
     * Configura el tamaño y restricciones de la ventana.
     */
    private void applyStageDefaults() {
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(500);
        // primaryStage.setResizable(false); // descomentar si no se quiere redimensionar
    }

    /* ======================= Navegación ======================= */

    /** Abre la vista principal con pestañas */
    public void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
            Parent root = loader.load();

            PrincipalViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema Veterinaria");
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Abre la vista de veterinarios */
    public void openViewVeterinario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Veterinario.fxml"));
            Parent root = loader.load();

            VeterinarioViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Abre la vista de personal de apoyo */
    public void openViewPersonalApoyo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonalApoyo.fxml"));
            Parent root = loader.load();

            PersonalApoyoViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Abre la vista de citas */
    public void openViewCita() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cita.fxml"));
            Parent root = loader.load();

            CitaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Abre la vista de propietarios */
    public void openViewPropietario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cliente.fxml"));
            Parent root = loader.load();

            ClienteViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Abre la vista de consultas */
    public void openViewConsulta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Consulta.fxml"));
            Parent root = loader.load();

            ConsultaViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            applyStageDefaults();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ======================= Datos de prueba ======================= */

    /**
     * Inicializa datos de prueba para que el sistema
     * arranque con veterinarios, propietarios, mascotas y citas.
     */
    public void inicializarVeterinaria() {
        // Personal de apoyo
        PersonalApoyo personalApoyo = new PersonalApoyo(
                "Juan", "12345678", "Calle 123", "45678912", "Juan@gmail.com", "Recepcionista"
        );

        // Veterinario
        Veterinario veterinario = new Veterinario(
                "Maicol", "1011201431", "cr 40 #42-16", "3238264868",
                "Maicol@gmail.com", "001", Especialidad.ACUATICOS
        );

        veterinaria.agregarPersonalApoyo(personalApoyo);
        veterinaria.agregarVeterinario(veterinario);

        // Propietario y mascotas
        Propietario propietario = new Propietario(
                "Yesuu", "10214321", "cr40#45#17", "21121212", "yesuaesteban@gmail.com"
        );

        Mascota mascota1 = new Mascota("3","juan", Especie.PERRO, "danes", propietario, 3);
        Mascota mascota2 = new Mascota("2","pedro",Especie.GATO, "Siames", propietario, 2);

        propietario.agregarMascota(mascota1);
        propietario.agregarMascota(mascota2);

        veterinaria.agregarPropietario(propietario);
        veterinaria.agregarMascota(mascota1);
        veterinaria.agregarMascota(mascota2);

        // Cita
        Cita cita = new Cita(
                "CITA-" + UUID.randomUUID(),
                LocalDateTime.now().plusDays(1),
                "Consulta de control"
        );
        cita.agregarPropietario(propietario);
        cita.agregarMascota(mascota1);
        cita.agregarVeterinario(veterinario);

        boolean ok = veterinaria.agendarCita(cita);
        System.out.println("Cita agendada: " + ok + " -> " + cita);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
