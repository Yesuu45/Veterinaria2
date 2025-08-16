package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrimaryViewControler {

    private App app;

    @FXML
    private TabPane maiTabPane;

    @FXML
    private Tab TabCliente;

    @FXML
    private Tab TabVeterinarios;

    @FXML
    private Tab TabPersonalApoo;

    @FXML
    private Tab TabCitas;

    @FXML
    private Tab TabConsulta;

    /**
     * Este método se ejecuta automáticamente cuando se carga el FXML.
     * Aquí agregamos el listener para saber cuándo cambia de pestaña.
     */
    @FXML
    public void initialize() {
// Agregar listener para saber en qué pestaña estamos y ejecutar lógica
        maiTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                System.out.println("Pestaña seleccionada: " + newTab.getText());
                manejarCambioPestana(newTab);
            }
        });
    }

    private void manejarCambioPestana(Tab tabSeleccionada) {
        if (tabSeleccionada == TabCliente) {
            System.out.println("Cargando datos de Clientes...");
            // Aquí puedes llamar al controlador de Cliente.fxml para actualizar la info
        } else if (tabSeleccionada == TabVeterinarios) {
            System.out.println("Cargando datos de Veterinarios...");
        } else if (tabSeleccionada == TabPersonalApoo) {
            System.out.println("Cargando datos de Personal de Apoyo...");
        } else if (tabSeleccionada == TabCitas) {
            System.out.println("Cargando datos de Citas...");
        } else if (tabSeleccionada == TabConsulta) {
            System.out.println("Cargando datos de Consultas...");
        }
    }

}
