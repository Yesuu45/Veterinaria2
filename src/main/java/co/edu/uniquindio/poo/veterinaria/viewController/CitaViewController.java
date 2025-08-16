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

import java.time.LocalDate;
import java.util.UUID;

public class CitaViewController {

    private App app;
    private CitaController citaController;

    private ObservableList<Cita> listaCitas = FXCollections.observableArrayList();
    private ObservableList<Propietario> listaPropietarios = FXCollections.observableArrayList();
    private ObservableList<Veterinario> listaVeterinarios = FXCollections.observableArrayList();
    private ObservableList<PersonalApoyo> listaPersonalApoyo = FXCollections.observableArrayList();
    private ObservableList<Mascota> listaMascotas = FXCollections.observableArrayList(); // ✅ ahora sí tenemos mascotas dinámicas

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
    private TableColumn<Cita, String> ColdMascota;

    @FXML
    private ComboBox<Propietario> ComPropietario;
    @FXML
    private ComboBox<Veterinario> ComVeterinario;
    @FXML
    private ComboBox<PersonalApoyo> ComPersonalApoyo;
    @FXML
    private ComboBox<Mascota> ComMascota; // ✅ Nuevo combo de mascotas

    @FXML
    private DatePicker LocalDateFecha;
    @FXML
    private TextArea txtObservacione;
    @FXML
    private TextField txtIdCita;
    @FXML
    private Button buttonAgregar;

    public void setApp(App app) {
        this.app = app;
        this.citaController = new CitaController(app.veterinaria);
        initView();
    }

    private void initView() {
        // ✅ Configurar tabla
        ColdIdentificacion.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));
        ColdFecha.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        ColdObservaciones.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescripcion()));
        ColdVeterinario.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getVeterinarios().isEmpty() ? "" :
                                cellData.getValue().getVeterinarios().get(0).getNombre()
                ));
        ColdPersonalApoyo.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPersonalApoyo().isEmpty() ? "" :
                                cellData.getValue().getPersonalApoyo().get(0).getNombre()
                ));
        ColdPropietarios.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getPropietarios().isEmpty() ? "" :
                                cellData.getValue().getPropietarios().get(0).getNombre()
                ));
        ColdMascota.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getMascotas().isEmpty() ? "" :
                                cellData.getValue().getMascotas().get(0).getNombreMascota()
                ));

        // ✅ Cargar listas iniciales
        listaCitas.setAll(citaController.obtenerCitas());
        tblCitas.setItems(listaCitas);

        listaPropietarios.setAll(citaController.obtenerPropietarios());
        ComPropietario.setItems(listaPropietarios);

        listaVeterinarios.setAll(citaController.obtenerVeterinarios());
        ComVeterinario.setItems(listaVeterinarios);

        listaPersonalApoyo.setAll(citaController.obtenerPersonalApoyo());
        ComPersonalApoyo.setItems(listaPersonalApoyo);

        ComMascota.setItems(listaMascotas);

        // ✅ Evento: cuando se selecciona un propietario → cargar sus mascotas
        ComPropietario.setOnAction(event -> {
            Propietario propietario = ComPropietario.getValue();
            listaMascotas.setAll(citaController.obtenerMascotasDePropietario(propietario));
        });
    }

    @FXML
    private void AgregarCita(ActionEvent event) {
        try {
            String id = txtIdCita.getText().isEmpty() ? UUID.randomUUID().toString() : txtIdCita.getText();
            Propietario propietario = ComPropietario.getValue();
            Mascota mascota = ComMascota.getValue();
            Veterinario veterinario = ComVeterinario.getValue();
            PersonalApoyo personalApoyo = ComPersonalApoyo.getValue();
            LocalDate fecha = LocalDateFecha.getValue();
            String observaciones = txtObservacione.getText();

            if (propietario == null || mascota == null || veterinario == null || personalApoyo == null || fecha == null) {
                mostrarAlerta("Error", "Debe seleccionar todos los campos obligatorios.");
                return;
            }

            // ✅ Crear nueva cita
            Cita cita = new Cita(id, fecha.atStartOfDay(), observaciones);
            cita.agregarPropietario(propietario);
            cita.agregarMascota(mascota);
            cita.agregarVeterinario(veterinario);
            cita.agregarPersonalApoyo(personalApoyo);

            citaController.agregarCita(cita);
            listaCitas.add(cita);

            limpiarCampos(null);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar la cita: " + e.getMessage());
        }
    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        ComPropietario.getSelectionModel().clearSelection();
        ComMascota.getSelectionModel().clearSelection();
        ComVeterinario.getSelectionModel().clearSelection();
        ComPersonalApoyo.getSelectionModel().clearSelection();
        LocalDateFecha.setValue(null);
        txtObservacione.clear();
        txtIdCita.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
