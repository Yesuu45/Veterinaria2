package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Controlador de la vista de gestión de {@link Cita}.
 * Maneja la interacción entre la UI en FXML y el modelo {@link Veterinaria}.
 */
public class CitaViewController {

    // referencia a la App y al modelo compartido
    private App app;
    private Veterinaria veterinaria;

    // ====== FXML: formulario
    @FXML private TextField txtIdCita;
    @FXML private ComboBox<Propietario> ComPropietario;
    @FXML private ComboBox<Mascota> ComMascota;
    @FXML private ComboBox<Veterinario> ComVeterinario;
    @FXML private ComboBox<PersonalApoyo> ComPersonalApoyo;
    @FXML private DatePicker LocalDateFecha;
    @FXML private TextArea txtObservacione;
    @FXML private TextField txtHora; // "HH:mm"

    // ====== FXML: tabla
    @FXML private TableView<Cita> tblCitas;
    @FXML private TableColumn<Cita, String> ColdIdentificacion;
    @FXML private TableColumn<Cita, String> ColdPropietarios;
    @FXML private TableColumn<Cita, String> ColdMascota;
    @FXML private TableColumn<Cita, String> ColdVeterinario;
    @FXML private TableColumn<Cita, String> ColdPersonalApoyo;
    @FXML private TableColumn<Cita, String> ColdFecha;
    @FXML private TableColumn<Cita, String> ColdObservaciones;

    // datos observables
    private final ObservableList<Cita> citasObs = FXCollections.observableArrayList();

    /**
     * Inicializa la vista luego de cargar el FXML.
     * Configura convertidores, listeners y columnas de la tabla.
     */
    @FXML
    public void initialize() {
        // Convertidores para combos
        ComPropietario.setConverter(new StringConverter<>() {
            @Override public String toString(Propietario p) { return p == null ? "" : p.getNombre() + " (" + p.getId() + ")"; }
            @Override public Propietario fromString(String s) { return null; }
        });
        ComMascota.setConverter(new StringConverter<>() {
            @Override public String toString(Mascota m) { return m == null ? "" : m.getNombreMascota() + " (" + (m.getEspecie()==null?"":m.getEspecie()) + ")"; }
            @Override public Mascota fromString(String s) { return null; }
        });
        ComVeterinario.setConverter(new StringConverter<>() {
            @Override public String toString(Veterinario v) { return v == null ? "" : v.getNombre() + " (Lic: " + v.getNumLicencia() + ")"; }
            @Override public Veterinario fromString(String s) { return null; }
        });
        ComPersonalApoyo.setConverter(new StringConverter<>() {
            @Override public String toString(PersonalApoyo p) { return p == null ? "" : p.getNombre(); }
            @Override public PersonalApoyo fromString(String s) { return null; }
        });

        // Propietario -> Mascotas dependientes
        ComPropietario.getSelectionModel().selectedItemProperty().addListener((obs, oldP, newP) -> {
            if (newP == null) {
                ComMascota.setItems(FXCollections.emptyObservableList());
                ComMascota.getSelectionModel().clearSelection();
            } else {
                ComMascota.setItems(FXCollections.observableArrayList(newP.getMascotas()));
                ComMascota.getSelectionModel().clearSelection();
            }
        });

        // Configuración de tabla
        tblCitas.setItems(citasObs);
        ColdIdentificacion.setCellValueFactory(c -> fxStr(c.getValue()==null?null:c.getValue().getId()));
        ColdPropietarios.setCellValueFactory(c -> fxStr(
                (c.getValue()!=null && !c.getValue().getPropietarios().isEmpty() && c.getValue().getPropietarios().get(0)!=null)
                        ? c.getValue().getPropietarios().get(0).getNombre() : ""
        ));
        ColdMascota.setCellValueFactory(c -> fxStr(
                (c.getValue()!=null && !c.getValue().getMascotas().isEmpty() && c.getValue().getMascotas().get(0)!=null)
                        ? c.getValue().getMascotas().get(0).getNombreMascota() : ""
        ));
        ColdVeterinario.setCellValueFactory(c -> fxStr(
                (c.getValue()!=null && !c.getValue().getVeterinarios().isEmpty() && c.getValue().getVeterinarios().get(0)!=null)
                        ? c.getValue().getVeterinarios().get(0).getNombre() : ""
        ));
        ColdPersonalApoyo.setCellValueFactory(c -> fxStr(
                (c.getValue()!=null && !c.getValue().getPersonalApoyo().isEmpty() && c.getValue().getPersonalApoyo().get(0)!=null)
                        ? c.getValue().getPersonalApoyo().get(0).getNombre() : ""
        ));
        ColdFecha.setCellValueFactory(c -> fxStr(
                c.getValue()!=null && c.getValue().getFecha()!=null
                        ? c.getValue().getFecha().toString() : ""
        ));
        ColdObservaciones.setCellValueFactory(c -> fxStr(
                c.getValue()!=null ? nullSafe(c.getValue().getDescripcion()) : ""
        ));

        // Defaults
        LocalDateFecha.setValue(LocalDate.now());
        txtIdCita.setPromptText("Auto si lo dejas vacío");
        if (txtHora != null) txtHora.setPromptText("HH:mm");
    }

    /**
     * Es llamado desde {@link App} luego de cargar el FXML.
     * @param app referencia a la aplicación
     */
    public void setApp(App app) {
        this.app = app;
        this.veterinaria = App.veterinaria;
        cargarDatosIniciales();
    }

    /** Carga datos iniciales en combos y tabla desde el modelo. */
    private void cargarDatosIniciales() {
        ComPropietario.setItems(FXCollections.observableArrayList(veterinaria.getListaPropietarios()));
        ComVeterinario.setItems(FXCollections.observableArrayList(veterinaria.getListaVeterinarios()));
        ComPersonalApoyo.setItems(FXCollections.observableArrayList(veterinaria.getListaPersonalApoyo()));
        citasObs.setAll(veterinaria.getListaCitas());
    }

    // ========= Handlers =========

    /**
     * Handler para agregar una cita desde el formulario.
     * Valida campos, crea la cita y la registra en {@link Veterinaria}.
     */
    @FXML
    public void AgregarCita() {
        try {
            String id = txtIdCita.getText();
            if (id == null || id.isBlank()) id = "CITA-" + UUID.randomUUID();

            LocalDate dia = LocalDateFecha.getValue();
            if (dia == null) throw new IllegalArgumentException("Selecciona una fecha.");

            String horaStr = txtHora == null ? "" : txtHora.getText().trim();
            if (horaStr.isEmpty()) throw new IllegalArgumentException("Ingresa la hora en formato HH:mm.");
            LocalTime hora = LocalTime.parse(horaStr);

            Propietario propietario = ComPropietario.getValue();
            Mascota mascota = ComMascota.getValue();
            Veterinario vet = ComVeterinario.getValue();
            PersonalApoyo pa = ComPersonalApoyo.getValue(); // opcional
            String obs = txtObservacione.getText();

            if (propietario == null) throw new IllegalArgumentException("Selecciona el propietario.");
            if (mascota == null) throw new IllegalArgumentException("Selecciona la mascota.");
            if (vet == null) throw new IllegalArgumentException("Selecciona el veterinario.");

            LocalDateTime fechaHora = LocalDateTime.of(dia, hora);

            Cita cita = new Cita(id, fechaHora, obs);
            cita.getPropietarios().clear();
            cita.getMascotas().clear();
            cita.getVeterinarios().clear();
            cita.agregarPropietario(propietario);
            cita.agregarMascota(mascota);
            cita.agregarVeterinario(vet);
            if (pa != null) {
                cita.getPersonalApoyo().clear();
                cita.agregarPersonalApoyo(pa);
            }

            boolean ok = veterinaria.agendarCita(cita);
            if (!ok) throw new IllegalStateException("No se pudo agendar la cita (choque u otros datos).");

            citasObs.setAll(veterinaria.getListaCitas());
            limpiarCampos();
            alertaInfo("Cita agregada con éxito.");
        } catch (Exception ex) {
            alertaError(ex.getMessage());
        }
    }

    /** Limpia los campos del formulario de cita. */
    @FXML
    public void limpiarCampos() {
        txtIdCita.clear();
        ComPropietario.getSelectionModel().clearSelection();
        ComMascota.getSelectionModel().clearSelection();
        ComVeterinario.getSelectionModel().clearSelection();
        ComPersonalApoyo.getSelectionModel().clearSelection();
        LocalDateFecha.setValue(LocalDate.now());
        if (txtHora != null) txtHora.clear();
        txtObservacione.clear();
    }

    // ========= Helpers =========
    private static String nullSafe(String s) { return s == null ? "" : s; }
    private static javafx.beans.property.SimpleStringProperty fxStr(String s) {
        return new javafx.beans.property.SimpleStringProperty(s == null ? "" : s);
    }
    private void alertaError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
    private void alertaInfo(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    /**
     * Handler para eliminar la cita seleccionada en la tabla.
     * Pide confirmación y actualiza la tabla si la eliminación fue exitosa.
     */
    @FXML
    private void onEliminarCita() {
        try {
            Cita seleccion = tblCitas.getSelectionModel().getSelectedItem();
            if (seleccion == null) {
                new Alert(Alert.AlertType.WARNING, "Selecciona una cita de la tabla.").showAndWait();
                return;
            }
            boolean ok = veterinaria.eliminarCita(seleccion.getId());
            if (!ok) {
                new Alert(Alert.AlertType.ERROR, "No se pudo eliminar la cita.").showAndWait();
                return;
            }
            // refrescar tabla observable
            citasObs.setAll(veterinaria.getListaCitas());
            tblCitas.refresh();
            new Alert(Alert.AlertType.INFORMATION, "Cita eliminada.").showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
        }
    }

    /**
     * Permite refrescar combos y tabla desde el modelo,
     * usado cuando se cambia de pestaña en la vista principal.
     */
    public void refresh() {
        ComPropietario.setItems(FXCollections.observableArrayList(veterinaria.getListaPropietarios()));
        ComVeterinario.setItems(FXCollections.observableArrayList(veterinaria.getListaVeterinarios()));
        ComPersonalApoyo.setItems(FXCollections.observableArrayList(veterinaria.getListaPersonalApoyo()));
        citasObs.setAll(veterinaria.getListaCitas());
    }
}
