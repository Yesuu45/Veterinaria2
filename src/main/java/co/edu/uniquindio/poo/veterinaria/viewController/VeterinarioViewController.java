package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.controller.VeterinarioController;
import co.edu.uniquindio.poo.veterinaria.model.Especialidad;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class VeterinarioViewController {

    /** Referencia a la aplicación principal. */
    private App app;

    /** Controlador de la lógica de negocio para veterinarios. */
    private VeterinarioController veterinarioController;

    /** Lista observable que alimenta la tabla. */
    private final ObservableList<Veterinario> listaVeterinarios = FXCollections.observableArrayList();

    /** Veterinario actualmente seleccionado en la tabla. */
    private Veterinario selectedVeterinario;

    // ================== Componentes UI ==================
    @FXML private TableView<Veterinario> tblListVeterinario;
    @FXML private TableColumn<Veterinario, String> TabNombre;
    @FXML private TableColumn<Veterinario, String> TabIdentificacion;
    @FXML private TableColumn<Veterinario, String> TabDireccion;
    @FXML private TableColumn<Veterinario, String> TabTelefono;
    @FXML private TableColumn<Veterinario, String> TabGmail;
    @FXML private TableColumn<Veterinario, String> TabLicencia;
    @FXML private TableColumn<Veterinario, String> TabEspecialidad;

    @FXML private TextField txtNombre;
    @FXML private TextField txtid;                 // Cédula del veterinario
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtGmail;
    @FXML private TextField txtNumeroLicencia;     // CLAVE ÚNICA para CRUD
    @FXML private ComboBox<Especialidad> CombEspecialidad;

    /**
     * Inicializa la vista (se ejecuta al cargar el FXML).
     * Configura columnas, tabla, combo y listeners de selección.
     */
    @FXML
    private void initialize() {
        initTableColumns();
        tblListVeterinario.setItems(listaVeterinarios);
        tblListVeterinario.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        // Configuración visual mínima y preferida
        TabNombre.setMinWidth(160); TabNombre.setPrefWidth(180);
        TabIdentificacion.setMinWidth(140); TabIdentificacion.setPrefWidth(160);
        TabDireccion.setMinWidth(220); TabDireccion.setPrefWidth(260);
        TabTelefono.setMinWidth(130); TabTelefono.setPrefWidth(150);
        TabLicencia.setMinWidth(140); TabLicencia.setPrefWidth(160);
        TabGmail.setMinWidth(240); TabGmail.setPrefWidth(280);
        TabEspecialidad.setMinWidth(160); TabEspecialidad.setPrefWidth(180);

        initSelectionListener();
        CombEspecialidad.setItems(FXCollections.observableArrayList(Especialidad.values()));
    }

    /**
     * Inyecta la aplicación principal y crea el controlador de veterinarios.
     * @param app referencia a {@link App}
     */
    public void setApp(App app) {
        this.app = app;
        this.veterinarioController = new VeterinarioController(App.veterinaria);
        refresh();
    }

    // ================== Inicialización de tabla y selección ==================

    /** Configura el mapeo de columnas de la tabla con las propiedades del modelo. */
    private void initTableColumns() {
        TabNombre.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNombre())));
        TabIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getId())));
        TabDireccion.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getDireccion())));
        TabTelefono.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getTelefono())));
        TabGmail.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getGmail())));
        TabLicencia.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNumLicencia())));
        TabEspecialidad.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEspecialidad() == null ? "" : c.getValue().getEspecialidad().toString()
        ));
    }

    /** Listener de selección en la tabla. */
    private void initSelectionListener() {
        tblListVeterinario.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            selectedVeterinario = newSel;
            mostrarInformacionVeterinario(newSel);
        });
    }

    // ================== Botones / Handlers ==================

    /** Handler: Agregar un nuevo veterinario. */
    @FXML
    private void agregarVeterinario() {
        try {
            if (!validarCampos()) return;
            Veterinario v = buildVeterinario();
            boolean ok = veterinarioController.crearVeterinario(v);
            if (!ok) {
                alertWarn("No se pudo crear. ¿Existe ya un veterinario con esa LICENCIA?");
                return;
            }
            refresh();
            seleccionarPorLicencia(v.getNumLicencia());
            limpiarCampos();
            alertInfo("Veterinario agregado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    /** Handler: Eliminar el veterinario seleccionado. */
    @FXML
    private void onEliminarVeterinario() {
        try {
            if (selectedVeterinario == null) {
                alertWarn("Selecciona un veterinario de la tabla.");
                return;
            }
            String lic = nz(selectedVeterinario.getNumLicencia());
            if (lic.isBlank()) {
                alertError("El veterinario seleccionado no tiene licencia válida.");
                return;
            }
            boolean ok = veterinarioController.eliminarVeterinario(lic);
            if (!ok) {
                alertError("No se pudo eliminar. ¿Licencia inexistente?");
                return;
            }
            refresh();
            limpiarCampos();
            alertInfo("Veterinario eliminado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    /** Handler: Actualizar el veterinario seleccionado. */
    @FXML
    private void onActualizarVeterinario() {
        try {
            if (selectedVeterinario == null) {
                alertWarn("Selecciona un veterinario de la tabla.");
                return;
            }
            String licenciaActual = nz(selectedVeterinario.getNumLicencia());
            if (licenciaActual.isBlank()) {
                alertError("El veterinario seleccionado no tiene licencia válida.");
                return;
            }
            Veterinario datos = buildVeterinario();
            boolean ok = veterinarioController.actualizarVeterinario(licenciaActual, datos);
            if (!ok) {
                alertError("No se pudo actualizar. ¿Licencia duplicada?");
                return;
            }
            refresh();
            seleccionarPorLicencia(datos.getNumLicencia() != null && !datos.getNumLicencia().isBlank()
                    ? datos.getNumLicencia()
                    : licenciaActual);
            limpiarCampos();
            alertInfo("Veterinario actualizado.");
        } catch (Exception e) {
            alertError(e.getMessage());
        }
    }

    // ================== Utilidades de vista ==================

    /** Recarga la lista de veterinarios desde el controlador. */
    public void refresh() {
        if (veterinarioController == null) return;
        listaVeterinarios.setAll(veterinarioController.obtenerVeterinarios());
        tblListVeterinario.refresh();
    }

    /** Muestra la info de un veterinario en los campos de texto. */
    private void mostrarInformacionVeterinario(Veterinario v) {
        if (v == null) {
            limpiarCampos();
            return;
        }
        txtNombre.setText(nz(v.getNombre()));
        txtid.setText(nz(v.getId()));
        txtDireccion.setText(nz(v.getDireccion()));
        txtTelefono.setText(nz(v.getTelefono()));
        txtGmail.setText(nz(v.getGmail()));
        txtNumeroLicencia.setText(nz(v.getNumLicencia()));
        CombEspecialidad.getSelectionModel().select(v.getEspecialidad());
    }

    /** Construye un objeto Veterinario a partir de los campos de texto. */
    private Veterinario buildVeterinario() {
        return new Veterinario(
                safe(txtNombre),
                safe(txtid),
                safe(txtDireccion),
                safe(txtTelefono),
                safe(txtGmail),
                safe(txtNumeroLicencia),   // CLAVE
                CombEspecialidad.getValue()
        );
    }

    /** Limpia todos los campos de la vista. */
    private void limpiarCampos() {
        txtNombre.clear();
        txtid.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtGmail.clear();
        txtNumeroLicencia.clear();
        CombEspecialidad.getSelectionModel().clearSelection();
        tblListVeterinario.getSelectionModel().clearSelection();
        selectedVeterinario = null;
    }

    /** Valida que todos los campos obligatorios estén diligenciados. */
    private boolean validarCampos() {
        if (safe(txtNombre).isBlank() ||
                safe(txtid).isBlank() ||
                safe(txtDireccion).isBlank() ||
                safe(txtTelefono).isBlank() ||
                safe(txtGmail).isBlank() ||
                safe(txtNumeroLicencia).isBlank() ||
                CombEspecialidad.getValue() == null) {
            alertWarn("Todos los campos son obligatorios (incluida la ESPECIALIDAD y la LICENCIA).");
            return false;
        }
        return true;
    }

    // ================== Helpers ==================
    private void seleccionarPorLicencia(String lic) {
        if (lic == null || lic.isBlank()) return;
        for (int i = 0; i < tblListVeterinario.getItems().size(); i++) {
            var v = tblListVeterinario.getItems().get(i);
            if (lic.equals(v.getNumLicencia())) {
                tblListVeterinario.getSelectionModel().select(i);
                tblListVeterinario.scrollTo(i);
                break;
            }
        }
    }

    private static String nz(String s) { return s == null ? "" : s; }

    private static String safe(TextField f) {
        if (f == null) return "";
        String t = f.getText();
        return t == null ? "" : t.trim();
    }

    // ================== Alertas ==================
    private void alertInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
    private void alertWarn(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private void alertError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
}
