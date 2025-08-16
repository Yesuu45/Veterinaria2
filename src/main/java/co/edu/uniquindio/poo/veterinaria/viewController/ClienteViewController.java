package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.controller.CitaController;
import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class CitaViewController {

    @FXML
    private TextField txtIdCita;

    @FXML
    private DatePicker LocalDateFecha;

    @FXML
    private TextArea txtObservacione;

    @FXML
    private ComboBox<Propietario> ComPropietario;

    @FXML
    private ComboBox<Mascota> ComMascota;

    @FXML
    private ComboBox<Veterinario> ComVeterinario;

    @FXML
    private ComboBox<PersonalApoyo> ComPersonalApoyo;

    @FXML
    private TableView<Cita> tblCitas;

    @FXML
    private TableColumn<Cita, String> ColdIdentificacion;

    @FXML
    private TableColumn<Cita, String> ColdPropietarios;

    @FXML
    private TableColumn<Cita, String> ColdMascota;

    @FXML
    private TableColumn<Cita, String> ColdVeterinario;

    @FXML
    private TableColumn<Cita, String> ColdPersonalApoyo;

    @FXML
    private TableColumn<Cita, String> ColdFecha;

    @FXML
    private TableColumn<Cita, String> ColdObservaciones;

    private ObservableList<Cita> listaCitas;
    private CitaController citaController;

    public CitaViewController() {
        this.citaController = new CitaController(new Veterinaria("Mi Vet")); // ⚡ ojo: aquí luego inyectamos tu instancia real
        this.listaCitas = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Inicializar ComboBox con datos del modelo
        ComPropietario.setItems(FXCollections.observableArrayList(citaController.obtenerPropietarios()));
        ComVeterinario.setItems(FXCollections.observableArrayList(citaController.obtenerVeterinarios()));
        ComPersonalApoyo.setItems(FXCollections.observableArrayList(citaController.obtenerPersonalApoyo()));

        // Cuando se selecciona un propietario, cargar sus mascotas
        ComPropietario.setOnAction(event -> {
            Propietario propietario = ComPropietario.getValue();
            if (propietario != null) {
                ComMascota.setItems(FXCollections.observableArrayList(propietario.getMascotas()));
            } else {
                ComMascota.getItems().clear();
            }
        });

        // Configurar columnas de la tabla
        ColdIdentificacion.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId()));

        ColdPropietarios.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getPropietarios().isEmpty() ? "" : cell.getValue().getPropietarios().get(0).getNombre()
                )
        );

        ColdMascota.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getMascotas().isEmpty() ? "" : cell.getValue().getMascotas().get(0).getNombre()
                )
        );

        ColdVeterinario.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getVeterinarios().isEmpty() ? "" : cell.getValue().getVeterinarios().get(0).getNombre()
                )
        );

        ColdPersonalApoyo.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getPersonalApoyo().isEmpty() ? "" : cell.getValue().getPersonalApoyo().get(0).getNombre()
                )
        );

        ColdFecha.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getFecha().toLocalDate().toString())
        );

        ColdObservaciones.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDescripcion())
        );

        tblCitas.setItems(listaCitas);

        // Selección en tabla → mostrar datos en formulario
        tblCitas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtIdCita.setText(newSel.getId());
                LocalDateFecha.setValue(newSel.getFecha().toLocalDate());
                txtObservacione.setText(newSel.getDescripcion());

                if (!newSel.getPropietarios().isEmpty())
                    ComPropietario.setValue(newSel.getPropietarios().get(0));

                if (!newSel.getMascotas().isEmpty())
                    ComMascota.setValue(newSel.getMascotas().get(0));

                if (!newSel.getVeterinarios().isEmpty())
                    ComVeterinario.setValue(newSel.getVeterinarios().get(0));

                if (!newSel.getPersonalApoyo().isEmpty())
                    ComPersonalApoyo.setValue(newSel.getPersonalApoyo().get(0));
            }
        });
    }

    @FXML
    void AgregarCita() {
        try {
            String id = txtIdCita.getText();
            LocalDate fecha = LocalDateFecha.getValue();
            String descripcion = txtObservacione.getText();

            if (id.isEmpty() || fecha == null || ComPropietario.getValue() == null ||
                    ComMascota.getValue() == null || ComVeterinario.getValue() == null ||
                    ComPersonalApoyo.getValue() == null) {
                mostrarAlerta("Error", "Debes llenar todos los campos");
                return;
            }

            // Crear la cita
            Cita cita = new Cita(id, fecha.atStartOfDay(), descripcion);

            // Agregar solo UN propietario, mascota, veterinario y personal apoyo
            cita.agregarPropietario(ComPropietario.getValue());
            cita.agregarMascota(ComMascota.getValue());
            cita.agregarVeterinario(ComVeterinario.getValue());
            cita.agregarPersonalApoyo(ComPersonalApoyo.getValue());

            // Guardar en el controlador
            citaController.agregarCita(cita);
            listaCitas.add(cita);
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un problema: " + e.getMessage());
        }
    }

    @FXML
    void limpiarCampos() {
        txtIdCita.clear();
        LocalDateFecha.setValue(null);
        txtObservacione.clear();
        ComPropietario.setValue(null);
        ComMascota.getItems().clear();
        ComVeterinario.setValue(null);
        ComPersonalApoyo.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
