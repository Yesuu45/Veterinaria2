package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.ClienteController;
import co.edu.uniquindio.poo.veterinaria.model.Especie;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class ClienteViewController {

    private App app;
    private ClienteController clienteController;
    private ObservableList<Propietario> listaPropietarios = FXCollections.observableArrayList();
    private Propietario selectedPropietario;
    private ObservableList<Mascota> listMascotas = FXCollections.observableArrayList();
    private Mascota selectedMascota;

    @FXML
    private TableView<Propietario> tblListCliente;

    @FXML
    private TableColumn<Propietario, String> ColTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombreMascota;

    @FXML
    private TableColumn<Mascota, String> ColNombreMascota;

    @FXML
    private ComboBox<Especie> selectedBoxEspecie;

    @FXML
    private TextField txtEdad;

    @FXML
    private Button btbAgregarCliente;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private TextField txtGmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableColumn<Mascota, Especie> ColEspecie;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TableColumn<Propietario, String> ColCedula;

    @FXML
    private TableColumn<Propietario, String> ColGmail;

    @FXML
    private TableColumn<Mascota, String> ColEdad;

    @FXML
    private TableColumn<Propietario, String> ColNombrePropietario;

    @FXML
    private TableColumn<Propietario, String> ColDireccion;

    @FXML
    private TableColumn<Mascota, String> ColIdentificacion;

    @FXML
    void crearPropietario(ActionEvent event) {
        agregarCliente();
    }

    private void initView() {
        initDataBinding();
        obtenerPropietarios();
        tblListCliente.getItems().clear();
        tblListCliente.setItems(listaPropietarios);
        listenerSelection();
    }

    private void initDataBinding() {
        ColNombrePropietario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        ColCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        ColDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        ColTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        ColGmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGmail()));
        ColNombreMascota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreMascota()));
        ColEspecie.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEspecie()));
        ColEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        ColIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdVeterinaria()));
    }

    private void obtenerPropietarios() {
        listaPropietarios.addAll(clienteController.obtenerPropietarios());
    }

    private void mostrarInformacionCliente(Propietario propietario) {
        if (propietario != null) {
            txtNombre.setText(propietario.getNombre());
            txtCedula.setText(propietario.getId());
            txtDireccion.setText(propietario.getDireccion());
            txtTelefono.setText(propietario.getTelefono());
            txtGmail.setText(propietario.getGmail());
        }
    }

    private void agregarCliente() {
        Propietario propietario = buildPropietario();
        if (clienteController.crearPropietario(propietario, selectedMascota)) {
            listaPropietarios.add(propietario);
            limpiarCamposCliente();
        }
    }

    private Propietario buildPropietario() {
        return new Propietario(txtNombre.getText(), txtCedula.getText(), txtDireccion.getText(), txtTelefono.getText(), txtGmail.getText());
    }

    private void limpiarCamposCliente() {
        txtNombre.clear();
        txtCedula.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGmail.clear();
    }

    private void listenerSelection() {
        tblListCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPropietario = newSelection;
            mostrarInformacionCliente(selectedPropietario);
        });
    }
    public void setApp(App app) {
        this.app = app;
    }
}