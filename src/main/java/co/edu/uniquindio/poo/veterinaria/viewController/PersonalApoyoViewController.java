package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.PersonalApoyoController;
import co.edu.uniquindio.poo.veterinaria.model.PersonalApoyo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class PersonalApoyoViewController {

    private App app;
    private PersonalApoyoController personalApoyoController;

    /** Lista observable que alimenta la tabla de personal de apoyo. */
    private final ObservableList<PersonalApoyo> listaPersonalApoyo = FXCollections.observableArrayList();

    /** Objeto seleccionado actualmente en la tabla. */
    private PersonalApoyo selectedPersonalApoyo;

    // ================== Componentes de UI ==================
    @FXML private TableView<PersonalApoyo> tblPersonalApoyo;
    @FXML private TableColumn<PersonalApoyo, String> colNombre;
    @FXML private TableColumn<PersonalApoyo, String> colId;
    @FXML private TableColumn<PersonalApoyo, String> colDireccion;
    @FXML private TableColumn<PersonalApoyo, String> colTelefono;
    @FXML private TableColumn<PersonalApoyo, String> colGmail;

    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtGmail;

    /**
     * Llamado desde la clase principal luego de cargar el FXML.
     * Inicializa el controlador con acceso a la aplicación y al modelo.
     */
    public void setApp(App app) {
        this.app = app;
        this.personalApoyoController = new PersonalApoyoController(App.veterinaria);
        // Primera carga de datos
        refresh();
    }

    /**
     * Inicialización automática al cargar el FXML.
     * Configura las columnas y listeners de selección.
     */
    @FXML
    private void initialize() {
        // Configuración de columnas de la tabla
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getNombre())));
        colId.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getId())));
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getDireccion())));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getTelefono())));
        colGmail.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getGmail())));

        tblPersonalApoyo.setItems(listaPersonalApoyo);

        // Listener de selección en la tabla
        tblPersonalApoyo.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedPersonalApoyo = newSel;
            mostrarInformacionPersonalApoyo(newSel);
        });
    }

    /* =========================
       Handlers de botones
       ========================= */

    /** Agregar nuevo personal de apoyo a la lista. */
    @FXML
    private void onAgregar() {
        try {
            PersonalApoyo pa = buildPersonalApoyo();
            if (pa.getId().isBlank()) throw new IllegalArgumentException("ID es obligatorio.");
            if (pa.getNombre().isBlank()) throw new IllegalArgumentException("Nombre es obligatorio.");

            boolean ok = personalApoyoController.crearPersonalApoyo(pa);
            if (!ok) {
                alertError("No se pudo crear. ¿Existe ya un personal con ese ID?");
                return;
            }
            refresh();
            limpiarCampos();
            alertInfo("Personal de apoyo agregado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    /** Eliminar el personal seleccionado en la tabla. */
    @FXML
    private void onEliminar() {
        try {
            if (selectedPersonalApoyo == null) {
                alertError("Selecciona un registro en la tabla.");
                return;
            }
            boolean ok = personalApoyoController.eliminarPersonalApoyo(selectedPersonalApoyo.getId());
            if (!ok) {
                alertError("No se pudo eliminar (no encontrado).");
                return;
            }
            refresh();
            limpiarCampos();
            alertInfo("Personal de apoyo eliminado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    /** Actualizar los datos del personal seleccionado con lo ingresado en el formulario. */
    @FXML
    private void onActualizar() {
        try {
            if (selectedPersonalApoyo == null) {
                alertError("Selecciona un registro en la tabla.");
                return;
            }
            PersonalApoyo datos = buildPersonalApoyo();
            boolean ok = personalApoyoController.actualizarPersonalApoyo(selectedPersonalApoyo.getId(), datos);
            if (!ok) {
                alertError("No se pudo actualizar.");
                return;
            }
            refresh();
            limpiarCampos();
            alertInfo("Personal de apoyo actualizado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    /* =========================
       Utilidades de vista
       ========================= */

    /** Recarga la tabla desde el modelo. */
    public void refresh() {
        if (personalApoyoController == null) return;
        listaPersonalApoyo.setAll(personalApoyoController.obtenerPersonalApoyo());
        tblPersonalApoyo.refresh();
    }

    /** Muestra los datos de un personal en el formulario. */
    private void mostrarInformacionPersonalApoyo(PersonalApoyo p) {
        if (p == null) {
            limpiarCampos();
            return;
        }
        txtNombre.setText(nullSafe(p.getNombre()));
        txtId.setText(nullSafe(p.getId()));
        txtDireccion.setText(nullSafe(p.getDireccion()));
        txtTelefono.setText(nullSafe(p.getTelefono()));
        txtGmail.setText(nullSafe(p.getGmail()));
    }

    /** Extrae texto seguro de un TextField. */
    private String safe(TextField f) {
        if (f == null) {
            System.err.println("[PersonalApoyoVC] Un TextField no fue inyectado (fx:id o fx:controller).");
            return "";
        }
        String t = f.getText();
        return t == null ? "" : t.trim();
    }

    /** Construye un objeto PersonalApoyo con los datos ingresados en el formulario. */
    private PersonalApoyo buildPersonalApoyo() {
        return new PersonalApoyo(
                safe(txtNombre),
                safe(txtId),
                safe(txtDireccion),
                safe(txtTelefono),
                safe(txtGmail)
        );
    }

    /** Limpia los campos de texto y la selección en la tabla. */
    private void limpiarCampos() {
        txtNombre.clear();
        txtId.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGmail.clear();
        tblPersonalApoyo.getSelectionModel().clearSelection();
        selectedPersonalApoyo = null;
    }

    /** Devuelve string seguro ("" si es null). */
    private static String nullSafe(String s) { return s == null ? "" : s; }

    /** Muestra un mensaje de información. */
    private void alertInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    /** Muestra un mensaje de error. */
    private void alertError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
