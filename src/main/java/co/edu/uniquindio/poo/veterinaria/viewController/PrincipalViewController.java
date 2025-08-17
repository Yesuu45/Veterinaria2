package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrincipalViewController {

    /** Referencia a la aplicación principal. */
    private App app;

    // ================== Tabs principales ==================
    @FXML private TabPane maiTabPane;
    @FXML private Tab TabCliente;
    @FXML private Tab TabVeterinarios;
    @FXML private Tab TabPersonalApoo;
    @FXML private Tab TabCitas;
    @FXML private Tab TabConsulta;

    // ================== Subcontroladores (FXML includes) ==================
    @FXML private ClienteViewController clientesIncludeController;
    @FXML private VeterinarioViewController veterinarioIncludeController;
    @FXML private PersonalApoyoViewController personalApoyoIncludeController;
    @FXML private CitaViewController citasIncludeController;
    @FXML private ConsultaViewController consultaIncludeController;

    /**
     * Inicializa el controlador principal.
     * Configura el listener de cambio de pestañas y realiza un primer refresh.
     */
    @FXML
    public void initialize() {
        // 1) Listener de pestañas: refresca al cambiar
        maiTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == null) return;
            if (newTab == TabCliente) {
                if (clientesIncludeController != null) clientesIncludeController.refresh();
            } else if (newTab == TabVeterinarios) {
                if (veterinarioIncludeController != null) veterinarioIncludeController.refresh();
            } else if (newTab == TabPersonalApoo) {
                if (personalApoyoIncludeController != null) personalApoyoIncludeController.refresh();
            } else if (newTab == TabCitas) {
                if (citasIncludeController != null) citasIncludeController.refresh();
            } else if (newTab == TabConsulta) {
                if (consultaIncludeController != null) consultaIncludeController.refreshAll(); // ✅ clave
            }
        });

        // 2) Primer refresh: refresca la pestaña activa al abrir
        Tab selected = maiTabPane.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selected == TabCliente) {
                if (clientesIncludeController != null) clientesIncludeController.refresh();
            } else if (selected == TabVeterinarios) {
                if (veterinarioIncludeController != null) veterinarioIncludeController.refresh();
            } else if (selected == TabPersonalApoo) {
                if (personalApoyoIncludeController != null) personalApoyoIncludeController.refresh();
            } else if (selected == TabCitas) {
                if (citasIncludeController != null) citasIncludeController.refresh();
            } else if (selected == TabConsulta) {
                if (consultaIncludeController != null) consultaIncludeController.refreshAll();
            }
        }
    }

    /**
     * Llamado desde {@link App} después de cargar el FXML principal.
     * Pasa la referencia de la aplicación a los subcontroladores y realiza la primera carga.
     */
    public void setApp(App app) {
        this.app = app;

        // Pasar App a cada subcontrolador (para que todos compartan el mismo modelo)
        if (clientesIncludeController != null) clientesIncludeController.setApp(app);
        if (veterinarioIncludeController != null) veterinarioIncludeController.setApp(app);
        if (personalApoyoIncludeController != null) personalApoyoIncludeController.setApp(app);
        if (citasIncludeController != null) citasIncludeController.setApp(app);
        if (consultaIncludeController != null) consultaIncludeController.setApp(app);

        // Primera carga: refrescar consultas (u otra pestaña activa)
        if (consultaIncludeController != null) consultaIncludeController.refreshAll();
    }
}
