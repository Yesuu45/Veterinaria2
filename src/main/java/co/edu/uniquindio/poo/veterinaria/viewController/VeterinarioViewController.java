package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.VeterinarioController;
import co.edu.uniquindio.poo.veterinaria.model.Especialidad;
import co.edu.uniquindio.poo.veterinaria.model.PersonalApoyo;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VeterinarioViewController {

    private VeterinarioController veterinarioController;
    private App app;


    private ObservableList<Veterinario> listaVeterinarios = FXCollections.observableArrayList();
    private Veterinario selectedVeterinario;

    @FXML
    private TableView<Veterinario> tblListVeterinario;

    @FXML
    private ComboBox<Especialidad> CombEspecialidad;

    @FXML
    private TableColumn<Veterinario, String> TabDireccion;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TableColumn<Veterinario, String> TabIdentificacion;

    @FXML
    private TextField txtNumeroLicencia;

    @FXML
    private TableColumn<Veterinario, String> TabLicencia;

    @FXML
    private TableColumn<Veterinario, String> TabNombre;

    @FXML
    private TextField txtGmail;

    @FXML
    private TextField txtid;

    @FXML
    private Button buttonAgregar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TableColumn<Veterinario, String> TabTelefono;

    @FXML
    private TableColumn<Veterinario, String> TabGmail;

    @FXML
    private TableColumn<Veterinario, String> TabEspecialidad;


    @FXML
    void onEliminarVeterinario() {
        eliminarVeterinario();
    }

    @FXML
    void onActualizarVeterinario() {
        actualizarVeterinario();
    }


    @FXML
    void initialize(){
        veterinarioController = new VeterinarioController(app.veterinaria);
        initView();
    }

    private void initView() {
        initComboBox();
        initDataBinding();
        obtenerVeterinarios();
        tblListVeterinario.setItems(listaVeterinarios);
        listenerSelection();
    }

    private void initDataBinding() {
        TabNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        TabIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        TabDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        TabTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        TabGmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGmail()));
        TabLicencia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumLicencia()));
        TabEspecialidad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecialidad().toString()));
    }

    private void obtenerVeterinarios() {
        listaVeterinarios.addAll(veterinarioController.ObtenerVeterinarios());
    }

    private void mostrarInformacionVeterinario(Veterinario veterinario) {
        if (veterinario != null) {
            txtNombre.setText(veterinario.getNombre());
            txtid.setText(veterinario.getId());
            txtDireccion.setText(veterinario.getDireccion());
            txtTelefono.setText(veterinario.getTelefono());
            txtNumeroLicencia.setText(veterinario.getNumLicencia());
            txtGmail.setText(veterinario.getGmail());
            CombEspecialidad.getSelectionModel().select(veterinario.getEspecialidad());
        }
    }


    private void listenerSelection() {
        tblListVeterinario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedVeterinario = newSelection;
            mostrarInformacionVeterinario(selectedVeterinario);
        });
    }

    private Veterinario buildVeterinario() {
        return new Veterinario(
                txtNombre.getText(),
                txtid.getText(),
                txtDireccion.getText(),
                txtTelefono.getText(),
                txtGmail.getText(),
                txtNumeroLicencia.getText(),
                CombEspecialidad.getValue()
        );
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtid.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtNumeroLicencia.clear();
        txtGmail.clear();
        CombEspecialidad.getSelectionModel().clearSelection();
    }

    private void limpiarSelection(){
        tblListVeterinario.getSelectionModel().clearSelection();
        limpiarCampos();
    }




    public void setVeterinarioController(VeterinarioController veterinarioController) {
        this.veterinarioController = veterinarioController;
        initView();
    }

    private void initComboBox() {
        CombEspecialidad.setItems(FXCollections.observableArrayList(Especialidad.values()));
    }

    private void agregarVeterinario() {
        if (validarCampos()) {
            Veterinario veterinario = buildVeterinario();
            if (veterinarioController.crearVeterinario(veterinario)) {
                listaVeterinarios.add(veterinario);
                limpiarCampos();
                limpiarSelection();
            } else {
                mostrarAlerta("Error", "Ya existe un veterinario con esta identificación");
            }
        }
    }

    private void eliminarVeterinario(){
        if(veterinarioController.eliminarVeterinario(txtid.getText())){
            listaVeterinarios.remove(selectedVeterinario);
            limpiarCampos();

        }
    }

    private void actualizarVeterinario(){
        if(selectedVeterinario != null && veterinarioController.actualizarVeterinario(selectedVeterinario.getId(), buildVeterinario())){
            int index = listaVeterinarios.indexOf(selectedVeterinario);
            if(index >=0) {
                listaVeterinarios.set(index, buildVeterinario());
            }
            tblListVeterinario.refresh();
            limpiarSelection();
            limpiarCampos();
        }
    }


    @FXML
    void agregarVeterinario(ActionEvent event) {
        agregarVeterinario();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtid.getText().isEmpty() ||
                txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
                txtNumeroLicencia.getText().isEmpty() || txtGmail.getText().isEmpty() ||
                CombEspecialidad.getValue() == null) {
            mostrarAlerta("Validación", "Todos los campos son obligatorios");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}