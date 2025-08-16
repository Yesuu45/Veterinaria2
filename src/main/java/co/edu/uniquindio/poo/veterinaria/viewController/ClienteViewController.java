package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.ClienteController;
import co.edu.uniquindio.poo.veterinaria.controller.VeterinarioController;
import co.edu.uniquindio.poo.veterinaria.model.Especie;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteViewController {

    private ClienteController clienteController;
    private App app;



    private ObservableList<Propietario> listaPropietarios = FXCollections.observableArrayList();
    private ObservableList<Mascota> listaMascotas = FXCollections.observableArrayList();
    private Propietario selectedPropietario;

    @FXML
    private TableView<Propietario> tblListCliente;

    @FXML
    private TableColumn<Propietario, String> ColTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombreMascota;

    @FXML
    private TableColumn<Propietario, String> ColNombreMascota;

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
    private TableColumn<Propietario, String> ColEspecie;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TableColumn<Propietario, String> ColCedula;

    @FXML
    private TableColumn<Propietario, String> ColGmail;

    @FXML
    private TableColumn<Propietario, String> ColEdad;

    @FXML
    private TableColumn<Propietario, String> ColNombrePropietario;

    @FXML
    private TableColumn<Propietario, String> ColDireccion;

    @FXML
    private TableColumn<Propietario, String> ColIdentificacion;


    @FXML
    void eliminarPropietario() {
        eliminarCliente();
    }

    @FXML
    void actualizarPropietario() {
        actualizarCliente();
    }

    @FXML
    void initialize(){
        clienteController = new ClienteController(app.veterinaria);
        initView();
    }

    private void initView() {
        initComboBox();
        initDataBinding();
        obtenerPropietarios();
        tblListCliente.setItems(listaPropietarios);
        obtenerMascotas();
        listenerSelection();
    }


    private void initDataBinding() {
        ColNombrePropietario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        ColCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        ColDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        ColTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        ColGmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGmail()));

        ColNombreMascota.setCellValueFactory(cellData -> {
            if (!cellData.getValue().getMascotas().isEmpty()) {
                return new SimpleStringProperty(cellData.getValue().getMascotas().get(0).getNombreMascota());
            }
            return new SimpleStringProperty("");
        });

        ColEspecie.setCellValueFactory(cellData -> {
            if (!cellData.getValue().getMascotas().isEmpty()) {
                return new SimpleStringProperty(cellData.getValue().getMascotas().get(0).getEspecie().toString());
            }
            return new SimpleStringProperty("");
        });

        ColEdad.setCellValueFactory(cellData -> {
            if (!cellData.getValue().getMascotas().isEmpty()) {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getMascotas().get(0).getEdad()));
            }
            return new SimpleStringProperty("");
        });

        ColIdentificacion.setCellValueFactory(cellData -> {
            if (!cellData.getValue().getMascotas().isEmpty()) {
                return new SimpleStringProperty(cellData.getValue().getMascotas().get(0).getIdVeterinaria());
            }
            return new SimpleStringProperty("");
        });
    }

    private void obtenerPropietarios() {
        listaPropietarios.addAll(clienteController.obtenerPropietarios());
    }

    private void obtenerMascotas(){
        listaMascotas.addAll(clienteController.obtenerMascotas());
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

    private void mostrarInformacionMascota(Mascota mascota){
        if(mascota != null){
            txtNombreMascota.setText(mascota.getNombreMascota());
            selectedBoxEspecie.getSelectionModel().select(mascota.getEspecie());
            txtEdad.setText(String.valueOf(mascota.getEdad()));
            txtIdentificacion.setText(mascota.getIdVeterinaria());
        }
    }

    private void listenerSelection() {
        tblListCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedPropietario = newSelection;
            if (selectedPropietario != null) {
                mostrarInformacionCliente(selectedPropietario);
                if (!selectedPropietario.getMascotas().isEmpty()) {
                    mostrarInformacionMascota(selectedPropietario.getMascotas().get(0));
                } else {
                    // Limpia los campos de mascota si no tiene mascotas
                    txtNombreMascota.clear();
                    selectedBoxEspecie.getSelectionModel().clearSelection();
                    txtEdad.clear();
                    txtIdentificacion.clear();
                }
            } else {
                limpiarCamposCliente();
            }
        });
    }


    private Propietario buildPropietario() {
        return new Propietario(
                txtNombre.getText(),
                txtCedula.getText(),
                txtDireccion.getText(),
                txtTelefono.getText(),
                txtGmail.getText());
    }

    private Mascota buildMascota(Propietario propietario) {
        return new Mascota(
                txtNombreMascota.getText(),
                selectedBoxEspecie.getValue(),
                propietario,
                Integer.parseInt(txtEdad.getText()),
                txtIdentificacion.getText());
    }

    private void limpiarCamposCliente() {
        txtNombre.clear();
        txtCedula.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGmail.clear();
        txtNombreMascota.clear();
        txtEdad.clear();
        txtIdentificacion.clear();
        selectedBoxEspecie.getSelectionModel().clearSelection();
    }

    private void limpiarSelection(){
        tblListCliente.getSelectionModel().clearSelection();
        limpiarCamposCliente();
    }




    @FXML
    void crearPropietario(ActionEvent event) {
        agregarCliente();
    }



    private void initComboBox() {
        selectedBoxEspecie.setItems(FXCollections.observableArrayList(Especie.values()));
    }





    private void agregarCliente() {
        Propietario propietario = buildPropietario();
        Mascota mascota = buildMascota(propietario);
        propietario.getMascotas().add(mascota);

        if (clienteController.crearPropietario(propietario, mascota)) {
            listaPropietarios.add(propietario);
            limpiarCamposCliente();
        }
    }

    private void eliminarCliente(){
        if(clienteController.eliminarPropietario(txtCedula.getText())){
            listaPropietarios.remove(selectedPropietario);
            limpiarCamposCliente();

        }
    }

    private void actualizarCliente(){
        if(selectedPropietario != null && clienteController.actualiazarPropietario(selectedPropietario.getId(), buildPropietario())){
            int index = listaPropietarios.indexOf(selectedPropietario);
            if(index >=0) {
                listaPropietarios.set(index, buildPropietario());
            }
            tblListCliente.refresh();
            limpiarSelection();
            limpiarCamposCliente();
        }
    }






}