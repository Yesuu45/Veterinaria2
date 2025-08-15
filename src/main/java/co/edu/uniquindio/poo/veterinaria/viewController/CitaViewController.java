package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.CitaController;
import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class CitaViewController {

    private App app;
    private CitaController citaController;
    private ObservableList<Cita> listaCitas = FXCollections.observableArrayList();
    private ObservableList<Propietario> listaPropietarios = FXCollections.observableArrayList();
    private ObservableList<Veterinario> listaVeterinarios = FXCollections.observableArrayList();
    private ObservableList<PersonalApoyo> listaPersonalApoyo = FXCollections.observableArrayList();

    @FXML
    private TableView<Cita> tblCitas;
    @FXML
    private TableColumn<Cita, String> ColdIdentificacion;
    @FXML
    private TableColumn<Cita, String> ColdFecha;
    @FXML
    private TableColumn<Cita, String> ColdObservaciones;
    @FXML
    private TableColumn<Cita, String> ColdVeterinario;
    @FXML
    private TableColumn<Cita, String> ColdPersonalApoyo;
    @FXML
    private TableColumn<Cita, String> ColdPropietarios;

    @FXML
    private ComboBox<Propietario> ComPropietario;
    @FXML
    private ComboBox<Veterinario> ComVeterinario;
    @FXML
    private ComboBox<PersonalApoyo> ComPersonalApoyo;

    @FXML
    private DatePicker LocalDateFecha;
    @FXML
    private TextArea txtObservacione;

    @FXML
    private TextField txtId;
    @FXML
    private Button buttonAgregar;

    public void setApp(App app) {
        this.app = app;
        this.citaController = new CitaController(app.veterinaria);
        initView();
    }

    @FXML
    private void initialize() {
        // No es necesario setear ComboBox aquí porque lo hacemos en initView()
    }

    private void initView() {
        // Inicializar binding de tabla
        ColdIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        ColdFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        ColdObservaciones.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservaciones()));
        ColdVeterinario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVeterinario().getNombre()));
        ColdPersonalApoyo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalApoyo().getNombre()));
        ColdPropietarios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMascota().getPropietario().getNombre()));

        // Cargar listas
        listaCitas.setAll(citaController.obtenerCitas());
        tblCitas.setItems(listaCitas);

        listaPropietarios.setAll(citaController.obtenerPropietarios());
        ComPropietario.setItems(listaPropietarios);

        listaVeterinarios.setAll(citaController.obtenerVeterinarios());
        ComVeterinario.setItems(listaVeterinarios);

        listaPersonalApoyo.setAll(citaController.obtenerPersonalApoyo());
        ComPersonalApoyo.setItems(listaPersonalApoyo);
    }

    private void obtenerPropietarios(){
        listaPropietarios.addAll(citaController.obtenerPropietarios());
    }

    private void obtenerVeterinarios(){
        listaVeterinarios.addAll(citaController.obtenerVeterinarios());
    }

    private void obtenerPersonalApoyo(){
        listaPersonalApoyo.addAll(citaController.obtenerPersonalApoyo());
    }

    private void obtenerCitas(){
        listaCitas.addAll(citaController.obtenerCitas());
    }


    @FXML
    private void AgregarCita(ActionEvent event) {

    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        ComPropietario.getSelectionModel().clearSelection();
        ComVeterinario.getSelectionModel().clearSelection();
        ComPersonalApoyo.getSelectionModel().clearSelection();
        LocalDateFecha.setValue(null);
        txtObservacione.clear();
        txtId.clear();
    }
}
