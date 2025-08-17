package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

import java.time.format.DateTimeFormatter;


public class ConsultaViewController {

    private App app;
    private Veterinaria veterinaria;

    // ======= Filtros
    @FXML private TextField txtBuscarMascota;
    private String filtroMascota = "";

    // ======= Tablas (ajusta ids a tu FXML)
    @FXML private TableView<Propietario> tblPropietarios;
    @FXML private TableColumn<Propietario, String> colNombrePropietario;
    @FXML private TableColumn<Propietario, String> colIdPropietario;
    @FXML private TableColumn<Propietario, String> colTelefonoPropietario;
    @FXML private TableColumn<Propietario, String> colDireccionPropietario;
    @FXML private TableColumn<Propietario, String> colGmailPropietario;
    @FXML private TableColumn<Propietario, String> colNombreMascota;
    @FXML private TableColumn<Propietario, String> colEspecieMascota;
    @FXML private TableColumn<Propietario, String> colEdadMascota;

    @FXML private TableView<Veterinario> tblVeterinarios;
    @FXML private TableColumn<Veterinario, String> colNombreVet;
    @FXML private TableColumn<Veterinario, String> colIdVet;
    @FXML private TableColumn<Veterinario, String> colTelefonoVet;
    @FXML private TableColumn<Veterinario, String> colDireccionVet;
    @FXML private TableColumn<Veterinario, String> colGmailVet;
    @FXML private TableColumn<Veterinario, String> colLicenciaVet;
    @FXML private TableColumn<Veterinario, String> colEspecialidadVet;

    @FXML private TableView<PersonalApoyo> tblPersonalApoyo;
    @FXML private TableColumn<PersonalApoyo, String> colNombrePA;
    @FXML private TableColumn<PersonalApoyo, String> colIdPA;
    @FXML private TableColumn<PersonalApoyo, String> colTelefonoPA;
    @FXML private TableColumn<PersonalApoyo, String> colDireccionPA;
    @FXML private TableColumn<PersonalApoyo, String> colGmailPA;

    @FXML private TableView<Cita> tblCitasActivas;
    @FXML private TableColumn<Cita, String> colClienteActivo;
    @FXML private TableColumn<Cita, String> colMascotaActivo;
    @FXML private TableColumn<Cita, String> colEspecieActivo;
    @FXML private TableColumn<Cita, String> colRazaActivo;
    @FXML private TableColumn<Cita, String> colVeterinarioActivo;
    @FXML private TableColumn<Cita, String> colTratamientoActivo;
    @FXML private TableColumn<Cita, String> colFechaActivo;
    @FXML private TableColumn<Cita, String> colEstadoActivo;

    @FXML private TableView<Consulta> tblHistorialCitas;
    @FXML private TableColumn<Consulta, String> colClienteCita;
    @FXML private TableColumn<Consulta, String> colMascotaCita;
    @FXML private TableColumn<Consulta, String> colEspecieCita;
    @FXML private TableColumn<Consulta, String> colRazaCita;
    @FXML private TableColumn<Consulta, String> colVeterinarioCita;
    @FXML private TableColumn<Consulta, String> colTratamientoCita;
    @FXML private TableColumn<Consulta, String> colFechaCita;
    @FXML private TableColumn<Consulta, String> colEstadoCita;

    // ======= Datos
    private final ObservableList<Propietario> propietariosObs = FXCollections.observableArrayList();
    private final ObservableList<Veterinario> vetsObs = FXCollections.observableArrayList();
    private final ObservableList<PersonalApoyo> paObs = FXCollections.observableArrayList();
    private final ObservableList<Cita> citasObs = FXCollections.observableArrayList();
    private final ObservableList<Consulta> consultasObs = FXCollections.observableArrayList();

    /**
     * Inicializa columnas, data bindings y formatos de tablas.
     * Se invoca automáticamente al cargar el FXML.
     */
    @FXML
    private void initialize() {
        // Propietarios
        if (tblPropietarios != null) {
            tblPropietarios.setItems(propietariosObs);
            colNombrePropietario.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNombre())));
            colIdPropietario.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getId())));
            colTelefonoPropietario.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getTelefono())));
            colDireccionPropietario.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getDireccion())));
            colGmailPropietario.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getGmail())));
            // Mostrar 1ra mascota del propietario (si existe)
            colNombreMascota.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getMascotas().isEmpty() ? "" : nz(c.getValue().getMascotas().get(0).getNombreMascota()))
            ));
            colEspecieMascota.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getMascotas().isEmpty() || c.getValue().getMascotas().get(0).getEspecie()==null) ?
                            "" : c.getValue().getMascotas().get(0).getEspecie().toString()
            ));
            colEdadMascota.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getMascotas().isEmpty() ? "" : String.valueOf(c.getValue().getMascotas().get(0).getEdad()))
            ));
        }

        // Veterinarios
        if (tblVeterinarios != null) {
            tblVeterinarios.setItems(vetsObs);
            colNombreVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNombre())));
            colIdVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getId())));
            colTelefonoVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getTelefono())));
            colDireccionVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getDireccion())));
            colGmailVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getGmail())));
            colLicenciaVet.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNumLicencia())));
            colEspecialidadVet.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getEspecialidad()==null ? "" : c.getValue().getEspecialidad().toString()
            ));
        }

        // Personal de apoyo
        if (tblPersonalApoyo != null) {
            tblPersonalApoyo.setItems(paObs);
            colNombrePA.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getNombre())));
            colIdPA.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getId())));
            colTelefonoPA.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getTelefono())));
            colDireccionPA.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getDireccion())));
            colGmailPA.setCellValueFactory(c -> new SimpleStringProperty(nz(c.getValue().getGmail())));
        }

        // Citas activas
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (tblCitasActivas != null) {
            tblCitasActivas.setItems(citasObs);
            colClienteActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getPropietarios().isEmpty() ? "" : nz(c.getValue().getPropietarios().get(0).getNombre())
            ));
            colMascotaActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getMascotas().isEmpty() ? "" : nz(c.getValue().getMascotas().get(0).getNombreMascota())
            ));
            colEspecieActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getMascotas().isEmpty() || c.getValue().getMascotas().get(0).getEspecie()==null) ?
                            "" : c.getValue().getMascotas().get(0).getEspecie().toString()
            ));
            colRazaActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getMascotas().isEmpty() ? "" : nz(c.getValue().getMascotas().get(0).getRaza())
            ));
            colVeterinarioActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getVeterinarios().isEmpty() ? "" : nz(c.getValue().getVeterinarios().get(0).getNombre())
            ));
            colFechaActivo.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getFecha()==null ? "" : c.getValue().getFecha().format(fmt)
            ));
        }

        // Historial (consultas)
        if (tblHistorialCitas != null) {
            tblHistorialCitas.setItems(consultasObs);
            colClienteCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getPropietarios().isEmpty()) ?
                            "" : nz(c.getValue().getCita().getPropietarios().get(0).getNombre())
            ));
            colMascotaCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getMascotas().isEmpty()) ?
                            "" : nz(c.getValue().getCita().getMascotas().get(0).getNombreMascota())
            ));
            colEspecieCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getMascotas().isEmpty()
                            || c.getValue().getCita().getMascotas().get(0).getEspecie()==null)
                            ? "" : c.getValue().getCita().getMascotas().get(0).getEspecie().toString()
            ));
            colRazaCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getMascotas().isEmpty()) ?
                            "" : nz(c.getValue().getCita().getMascotas().get(0).getRaza())
            ));
            colVeterinarioCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getVeterinarios().isEmpty()) ?
                            "" : nz(c.getValue().getCita().getVeterinarios().get(0).getNombre())
            ));
            colTratamientoCita.setCellValueFactory(c -> new SimpleStringProperty(
                    c.getValue().getTratamiento()==null ? "" : c.getValue().getTratamiento().toString()
            ));
            colFechaCita.setCellValueFactory(c -> new SimpleStringProperty(
                    (c.getValue().getCita()==null || c.getValue().getCita().getFecha()==null) ?
                            "" : c.getValue().getCita().getFecha().format(fmt)
            ));
            colEstadoCita.setCellValueFactory(c -> new SimpleStringProperty(
                    ((c.getValue().getCita() == null) || (c.getValue().getCita().getEstado() == null))
                            ? ""
                            : c.getValue().getCita().getEstado().toString()
            ));

        }
    }

    /**
     * Es llamado por la vista principal para inyectar la {@link App} y el modelo compartido.
     * @param app referencia a la aplicación principal
     */
    public void setApp(App app) {
        this.app = app;
        this.veterinaria = App.veterinaria;
        refreshAll();
    }

    /**
     * Refresca todos los listados desde el modelo y aplica el filtro activo al historial.
     * Actualiza y refresca las tablas visibles.
     */
    public void refreshAll() {
        if (veterinaria == null) return;
        propietariosObs.setAll(veterinaria.getListaPropietarios());
        vetsObs.setAll(veterinaria.getListaVeterinarios());
        paObs.setAll(veterinaria.getListaPersonalApoyo());
        citasObs.setAll(veterinaria.getListaCitas());

        // En lugar de setear consultas directamente, aplicamos el filtro activo
        aplicarFiltroHistorial();

        if (tblPropietarios != null) tblPropietarios.refresh();
        if (tblVeterinarios != null) tblVeterinarios.refresh();
        if (tblPersonalApoyo != null) tblPersonalApoyo.refresh();
        if (tblCitasActivas != null) tblCitasActivas.refresh();
        if (tblHistorialCitas != null) tblHistorialCitas.refresh();
    }

    /**
     * Finaliza la cita seleccionada en "citas activas":
     * solicita datos (diagnóstico, notas, tratamiento), marca la cita como TERMINADA,
     * registra la {@link Consulta} y elimina la cita del listado activo.
     */
    @FXML
    private void onTerminarCita() {
        if (tblCitasActivas == null) return;

        Cita sel = tblCitasActivas.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona una cita activa en la tabla.").showAndWait();
            return;
        }

        // === 1) Diagnóstico (requerido) ===
        TextInputDialog diagDlg = new TextInputDialog();
        diagDlg.setTitle("Terminar cita");
        diagDlg.setHeaderText("Diagnóstico");
        diagDlg.setContentText("Escribe el diagnóstico:");
        Optional<String> diagOpt = diagDlg.showAndWait();
        if (diagOpt.isEmpty() || diagOpt.get().isBlank()) {
            return;
        }
        String diagnostico = diagOpt.get().trim();

        // === 2) Notas (opcional) ===
        TextInputDialog notasDlg = new TextInputDialog();
        notasDlg.setTitle("Terminar cita");
        notasDlg.setHeaderText("Notas adicionales (opcional)");
        notasDlg.setContentText("Escribe notas (o deja vacío):");
        String notas = notasDlg.showAndWait().orElse("").trim();

        // === 3) Tratamiento (opcional) ===
        TextInputDialog medDlg = new TextInputDialog();
        medDlg.setTitle("Tratamiento");
        medDlg.setHeaderText("Medicamento (opcional)");
        medDlg.setContentText("Medicamento:");
        String medicamento = medDlg.showAndWait().orElse("").trim();

        TextInputDialog dosisDlg = new TextInputDialog();
        dosisDlg.setTitle("Tratamiento");
        dosisDlg.setHeaderText("Dosis (opcional)");
        dosisDlg.setContentText("Dosis (ej: 250 mg):");
        String dosis = dosisDlg.showAndWait().orElse("").trim();

        TextInputDialog freqDlg = new TextInputDialog();
        freqDlg.setTitle("Tratamiento");
        freqDlg.setHeaderText("Frecuencia (opcional)");
        freqDlg.setContentText("Frecuencia (ej: cada 12h):");
        String frecuencia = freqDlg.showAndWait().orElse("").trim();

        TextInputDialog diasDlg = new TextInputDialog();
        diasDlg.setTitle("Tratamiento");
        diasDlg.setHeaderText("Duración (días, opcional)");
        diasDlg.setContentText("Días:");
        int dias = 0;
        try {
            String txt = diasDlg.showAndWait().orElse("").trim();
            if (!txt.isBlank()) dias = Integer.parseInt(txt);
        } catch (NumberFormatException ignored) {}

        TextInputDialog indDlg = new TextInputDialog();
        indDlg.setTitle("Tratamiento");
        indDlg.setHeaderText("Indicaciones (opcional)");
        indDlg.setContentText("Indicaciones (ej: con comida):");
        String indicaciones = indDlg.showAndWait().orElse("").trim();

        Tratamiento tratamiento = null;
        boolean hayTratamiento =
                !(medicamento.isBlank() && dosis.isBlank() && frecuencia.isBlank() && indicaciones.isBlank() && dias <= 0);
        if (hayTratamiento) {
            tratamiento = new Tratamiento(medicamento, dosis, frecuencia, Math.max(0, dias), indicaciones);
        }

        // === 4) Marcar cita como terminada y registrar consulta ===
        sel.setEstado(EstadoCita.TERMINADA); // 👈 Se marca como TERMINADA

        Consulta cons = new Consulta(sel, diagnostico, tratamiento, notas);
        boolean okReg = veterinaria.registrarConsulta(cons);
        if (!okReg) {
            new Alert(Alert.AlertType.WARNING,
                    "No se pudo registrar la consulta. ¿Ya existe una consulta para esta cita?").showAndWait();
            return;
        }

        veterinaria.eliminarCita(sel.getId()); // se quita de activas
        refreshAll();
        new Alert(Alert.AlertType.INFORMATION, "Cita terminada y guardada en el historial.").showAndWait();
    }

    /* ====== Handlers filtro ====== */

    /**
     * Aplica el texto del campo de búsqueda como filtro por nombre de mascota
     * en el historial de consultas.
     */
    @FXML
    private void onBuscarMascota() {
        filtroMascota = (txtBuscarMascota == null || txtBuscarMascota.getText() == null)
                ? "" : txtBuscarMascota.getText().trim();
        aplicarFiltroHistorial();
    }

    /**
     * Limpia el filtro activo de nombre de mascota y refresca el historial completo.
     */
    @FXML
    private void onQuitarFiltro() {
        filtroMascota = "";
        if (txtBuscarMascota != null) txtBuscarMascota.clear();
        aplicarFiltroHistorial();
    }

    /**
     * Aplica el filtro actual al historial de {@link Consulta} por nombre de mascota.
     * Si no hay filtro, muestra todas las consultas del modelo.
     */
    private void aplicarFiltroHistorial() {
        if (veterinaria == null) return;
        var base = veterinaria.getListaConsultas(); // fuente

        if (filtroMascota == null || filtroMascota.isBlank()) {
            consultasObs.setAll(base);
        } else {
            String needle = filtroMascota.toLowerCase();
            var filtradas = base.stream().filter(con -> {
                if (con == null || con.getCita() == null) return false;
                var mascotas = con.getCita().getMascotas();
                if (mascotas == null || mascotas.isEmpty()) return false;
                return mascotas.stream().anyMatch(m ->
                        m != null &&
                                m.getNombreMascota() != null &&
                                m.getNombreMascota().toLowerCase().contains(needle)
                );
            }).toList();
            consultasObs.setAll(filtradas);
        }
        if (tblHistorialCitas != null) tblHistorialCitas.refresh();
    }

    /** Devuelve cadena vacía si la entrada es null. */
    private static String nz(String s) { return s == null ? "" : s; }
}
