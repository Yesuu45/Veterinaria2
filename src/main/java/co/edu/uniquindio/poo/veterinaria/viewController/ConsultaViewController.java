package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.ConsultaController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ConsultaViewController {

    private ConsultaController consultaController;
    private App app;

    // Constructor vacío necesario para FXML
    public ConsultaViewController() { }

    // Método para pasar la instancia de App después de cargar el FXML
    public void setApp(App app) {
        this.app = app;
        this.consultaController = new ConsultaController(app.veterinaria);
        // Inicializar tablas o cargar datos aquí si es necesario
    }

    @FXML
    private TableColumn<?, ?> colRazaCita;
    @FXML
    private TableColumn<?, ?> colFechaActivo;
    @FXML
    private TableColumn<?, ?> colVeterinarioCita;
    @FXML
    private TableView<?> tblPropietarios;
    @FXML
    private TableColumn<?, ?> colVeterinarioActivo;
    @FXML
    private TableColumn<?, ?> colEstadoActivo;
    @FXML
    private TableColumn<?, ?> colIdVet;
    @FXML
    private TableColumn<?, ?> colFechaCita;
    @FXML
    private TableColumn<?, ?> colTratamientoCita;
    @FXML
    private TableColumn<?, ?> colGmailPropietario;
    @FXML
    private TableColumn<?, ?> colEspecieMascota;
    @FXML
    private TableColumn<?, ?> colLicenciaVet;
    @FXML
    private TableView<?> tblCitasActivas;
    @FXML
    private TableColumn<?, ?> colTelefonoVet;
    @FXML
    private TableColumn<?, ?> colClienteCita;
    @FXML
    private TableColumn<?, ?> colGmailPA;
    @FXML
    private TableColumn<?, ?> colEspecieCita;
    @FXML
    private TableColumn<?, ?> colTratamientoActivo;
    @FXML
    private TableColumn<?, ?> colIdPropietario;
    @FXML
    private TableColumn<?, ?> colTelefonoPropietario;
    @FXML
    private TableColumn<?, ?> colDireccionPropietario;
    @FXML
    private TableColumn<?, ?> colDireccionVet;
    @FXML
    private TableColumn<?, ?> colRazaActivo;
    @FXML
    private TableColumn<?, ?> colGmailVet;
    @FXML
    private TableColumn<?, ?> colEstadoCita;
    @FXML
    private Button btnCitaTerminada;
    @FXML
    private TableColumn<?, ?> colNombreMascota;
    @FXML
    private TableColumn<?, ?> colNombrePA;
    @FXML
    private TableColumn<?, ?> colIdPA;
    @FXML
    private TableColumn<?, ?> colDireccionPA;
    @FXML
    private TableColumn<?, ?> colEspecialidadVet;
    @FXML
    private TableView<?> tblHistorialCitas;
    @FXML
    private TableColumn<?, ?> colNombreVet;
    @FXML
    private TableColumn<?, ?> colClienteActivo;
    @FXML
    private TableColumn<?, ?> colEdadMascota;
    @FXML
    private TableColumn<?, ?> colEspecieActivo;
    @FXML
    private TableColumn<?, ?> colMascotaActivo;
    @FXML
    private TableColumn<?, ?> colMascotaCita;
    @FXML
    private TableColumn<?, ?> colNombrePropietario;
    @FXML
    private TableView<?> tblPersonalApoyo;
    @FXML
    private TableColumn<?, ?> colTelefonoPA;
    @FXML
    private TableView<?> tblVeterinarios;

}
