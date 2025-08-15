package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.PersonalApoyoController;
import co.edu.uniquindio.poo.veterinaria.model.PersonalApoyo;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.util.Collection;

public class PersonalApoyoViewController {
    private App app;
    private PersonalApoyoController personalApoyoController;

    private ObservableList<PersonalApoyo> listaPersonalApoyo = FXCollections.observableArrayList();
    private PersonalApoyo selectedPersonalApoyo;

    @FXML
    private TableColumn<PersonalApoyo, String> colGmail;

    @FXML
    private TextField txtId;

    @FXML
    private Button buttonAgregar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableColumn<PersonalApoyo, String> colDireccion;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TableColumn<PersonalApoyo, String> colNombre;

    @FXML
    private TableView<PersonalApoyo> tblPersonalApoyo;

    @FXML
    private TableColumn<PersonalApoyo, String> colId;

    @FXML
    private TableColumn<PersonalApoyo, String> colTelefono;

    @FXML
    private TextField txtGmail;

    public void setPersonalApoyoController(PersonalApoyoController personalApoyoController) {
        this.personalApoyoController = personalApoyoController;
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerPersonalesApoyo();
        tblPersonalApoyo.setItems(listaPersonalApoyo);
        listesnerSelection();
    }

    private void initDataBinding() {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        colGmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGmail()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

    }


    private void obtenerPersonalesApoyo() {
        listaPersonalApoyo.addAll(personalApoyoController.obtenerPersonalApoyo());
    }

    private void mostrarInformacionPersonalApoyo(PersonalApoyo personalApoyo) {
        if (personalApoyo != null) {
            txtNombre.setText(personalApoyo.getNombre());
            txtId.setText(personalApoyo.getId());
            txtDireccion.setText(personalApoyo.getDireccion());
            txtTelefono.setText(personalApoyo.getTelefono());
            txtGmail.setText(personalApoyo.getGmail());
        }
    }

    private void listesnerSelection() {
        tblPersonalApoyo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPersonalApoyo = newSelection;
            mostrarInformacionPersonalApoyo(selectedPersonalApoyo);

        });
    }

    private PersonalApoyo buildPersonalApoyo() {
        return new PersonalApoyo(
                txtNombre.getText(),
                txtId.getText(),
                txtDireccion.getText(),
                txtTelefono.getText(),
                txtGmail.getText());
    }

    private void limpiarCamposPersonalApoyo() {
        txtNombre.clear();
        txtId.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGmail.clear();
    }

    private void agregarPersonalApoyo() {
        PersonalApoyo personalApoyo = buildPersonalApoyo();
        if (personalApoyoController.crearPersonalApoyo(personalApoyo)) {
            listaPersonalApoyo.add(personalApoyo);
            limpiarCamposPersonalApoyo();
        }
    }

    @FXML
    void agregarPersonalApoyo(ActionEvent event) {
        agregarPersonalApoyo();

    }


}