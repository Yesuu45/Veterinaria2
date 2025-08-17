package co.edu.uniquindio.poo.veterinaria.viewController;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Objects;


public class ClienteViewController {

    private App app;
    private Veterinaria veterinaria;

    private final ObservableList<Propietario> propietariosObs = FXCollections.observableArrayList();
    private final ObservableList<Mascota> mascotasObs = FXCollections.observableArrayList();

    // ---- Propietario
    @FXML private TextField txtNombre;
    @FXML private TextField txtId;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtGmail;

    @FXML private TableView<Propietario> tblPropietarios;
    @FXML private TableColumn<Propietario, String> colNombre;
    @FXML private TableColumn<Propietario, String> colId;
    @FXML private TableColumn<Propietario, String> colTelefono;
    @FXML private TableColumn<Propietario, String> colDireccion;
    @FXML private TableColumn<Propietario, String> colGmail;

    // ---- Mascotas del propietario seleccionado
    @FXML private TableView<Mascota> tblMascotas;
    @FXML private TableColumn<Mascota, String> colMascotaNombre;
    @FXML private TableColumn<Mascota, String> colMascotaEspecie;
    @FXML private TableColumn<Mascota, String> colMascotaRaza;
    @FXML private TableColumn<Mascota, String> colMascotaIdVet;
    @FXML private TableColumn<Mascota, Integer> colMascotaEdad;

    // ---- Form Mascota
    @FXML private TextField txtMascotaNombre;
    @FXML private ComboBox<Especie> cmbMascotaEspecie;
    @FXML private TextField txtMascotaRaza;
    @FXML private TextField txtMascotaEdad;
    @FXML private TextField txtMascotaIdVet;

    // Mascota seleccionada en la tabla
    private Mascota mascotaSeleccionada;

    /**
     * Inicializa tablas, convertidores y listeners de selección.
     * Se llama automáticamente al cargar el FXML.
     */
    @FXML
    public void initialize() {
        // Propietarios
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getNombre())));
        colId.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getId())));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getTelefono())));
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getDireccion())));
        colGmail.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getGmail())));
        tblPropietarios.setItems(propietariosObs);

        // Mascotas
        colMascotaNombre.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getNombreMascota())));
        colMascotaEspecie.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEspecie() == null ? "" : c.getValue().getEspecie().toString()
        ));
        colMascotaRaza.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getRaza())));
        colMascotaIdVet.setCellValueFactory(c -> new SimpleStringProperty(nullSafe(c.getValue().getIdVeterinaria())));
        colMascotaEdad.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getEdad()));
        tblMascotas.setItems(mascotasObs);

        // Selección de propietario: muestra datos + mascotas
        tblPropietarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            mostrarPropietario(newSel);
            cargarMascotasDe(newSel);
            limpiarFormularioMascota();
        });

        // Selección de mascota: cargar al form para editar/eliminar
        tblMascotas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            mascotaSeleccionada = newSel;
            mostrarMascota(newSel);
        });

        // Especies para combo mascota
        if (cmbMascotaEspecie != null) {
            cmbMascotaEspecie.setItems(FXCollections.observableArrayList(Especie.values()));
        }
    }

    /**
     * Inyecta la App y obtiene el agregado de dominio compartido.
     * @param app instancia principal de la aplicación
     */
    public void setApp(App app) {
        this.app = app;
        this.veterinaria = App.veterinaria;
        refrescarPropietarios();
    }

    /* ================== Handlers propietario ================== */

    /**
     * Handler del botón "Guardar": crea un propietario desde el formulario
     * y lo registra en el modelo.
     */
    @FXML
    private void onGuardar() {
        try {
            Propietario p = leerFormularioPropietario();
            boolean ok = veterinaria.agregarPropietario(p);
            if (!ok) { showError("Ya existe un propietario con ese documento."); return; }
            showInfo("Propietario registrado.");
            limpiarFormularioPropietario();
            refrescarPropietarios();
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Handler del botón "Actualizar": actualiza el propietario seleccionado
     * con los datos del formulario.
     */
    @FXML
    private void onActualizar() {
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona un propietario."); return; }

        try {
            Propietario datos = leerFormularioPropietario();
            boolean ok = veterinaria.actualizarPropietario(sel.getId(), datos);
            if (!ok) { showError("No se pudo actualizar."); return; }
            showInfo("Propietario actualizado.");
            refrescarPropietarios();
            seleccionarPorId(sel.getId());
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Handler del botón "Eliminar": elimina el propietario seleccionado.
     * Limpia formularios y refresca tablas.
     */
    @FXML
    private void onEliminar() {
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona un propietario."); return; }
        boolean ok = veterinaria.eliminarPropietarioPorId(sel.getId());
        if (!ok) { showError("No se pudo eliminar."); return; }
        showInfo("Propietario eliminado.");
        limpiarFormularioPropietario();
        refrescarPropietarios();
        mascotasObs.clear();
        limpiarFormularioMascota();
    }

    /**
     * Handler del botón "Limpiar": limpia el formulario y la selección,
     * y vacía la tabla de mascotas mostrada.
     */
    @FXML
    private void onLimpiar() {
        limpiarFormularioPropietario();
        tblPropietarios.getSelectionModel().clearSelection();
        mascotasObs.clear();
        limpiarFormularioMascota();
    }

    /* ================== Handlers mascota ================== */

    /**
     * Handler "Agregar mascota": crea una mascota a partir del formulario
     * y la registra vinculándola al propietario seleccionado.
     */
    @FXML
    private void onAgregarMascota() {
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona un propietario primero."); return; }

        Mascota m = leerFormularioMascota(sel, true);
        boolean ok = veterinaria.agregarMascota(m);
        if (!ok) {
            showError("No se pudo agregar la mascota. Verifica que el ID veterinaria sea único y que el propietario exista.");
            return;
        }
        showInfo("Mascota agregada.");
        cargarMascotasDe(sel);
        limpiarFormularioMascota();
    }

    /**
     * Handler "Actualizar mascota": modifica los datos de la mascota seleccionada
     * usando los valores del formulario.
     */
    @FXML
    private void onActualizarMascota() {
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona un propietario."); return; }

        if (mascotaSeleccionada == null) { showError("Selecciona una mascota en la tabla."); return; }

        Mascota datos = leerFormularioMascota(sel, false);
        boolean ok = veterinaria.actualizarMascota(mascotaSeleccionada.getIdVeterinaria(), datos);
        if (!ok) {
            showError("No se pudo actualizar la mascota (revisa ID veterinaria único y datos válidos).");
            return;
        }
        showInfo("Mascota actualizada.");
        cargarMascotasDe(sel);
        // Re-selecciona la misma por su id (si no cambió), si cambió el ID intenta buscarla
        seleccionarMascotaPorIdVet(datos.getIdVeterinaria() != null ? datos.getIdVeterinaria()
                : mascotaSeleccionada.getIdVeterinaria());
    }

    /**
     * Handler "Eliminar mascota": elimina la mascota seleccionada del propietario
     * y de la lista global del modelo.
     */
    @FXML
    private void onEliminarMascota() {
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel == null) { showError("Selecciona un propietario."); return; }

        if (mascotaSeleccionada == null) { showError("Selecciona una mascota en la tabla."); return; }

        boolean ok = veterinaria.eliminarMascotaPorIdVet(mascotaSeleccionada.getIdVeterinaria());
        if (!ok) { showError("No se pudo eliminar la mascota."); return; }

        showInfo("Mascota eliminada.");
        cargarMascotasDe(sel);
        limpiarFormularioMascota();
    }

    /* ================== Helpers UI ================== */

    /** Refresca la tabla de propietarios desde el modelo compartido. */
    private void refrescarPropietarios() { propietariosObs.setAll(veterinaria.getListaPropietarios()); }

    /**
     * Carga en la tabla las mascotas del propietario dado.
     * @param p propietario seleccionado (puede ser null)
     */
    private void cargarMascotasDe(Propietario p) {
        if (p == null || p.getMascotas() == null) mascotasObs.clear();
        else mascotasObs.setAll(p.getMascotas());
    }

    /**
     * Refresca ambas tablas (propietarios y mascotas) manteniendo la selección.
     * Útil cuando se cambia de pestaña o se vuelve a la vista.
     */
    public void refresh() {
        if (veterinaria == null) return;
        // recarga propietarios desde el modelo compartido
        propietariosObs.setAll(veterinaria.getListaPropietarios());
        tblPropietarios.refresh();

        // actualizar tabla de mascotas según selección
        Propietario sel = tblPropietarios.getSelectionModel().getSelectedItem();
        if (sel != null && sel.getMascotas() != null) {
            mascotasObs.setAll(sel.getMascotas());
        } else {
            mascotasObs.clear();
        }
        tblMascotas.refresh();
    }

    /** Muestra los datos del propietario en el formulario (si no es null). */
    private void mostrarPropietario(Propietario p) {
        if (p == null) return;
        txtNombre.setText(nullSafe(p.getNombre()));
        txtId.setText(nullSafe(p.getId()));
        txtDireccion.setText(nullSafe(p.getDireccion()));
        txtTelefono.setText(nullSafe(p.getTelefono()));
        txtGmail.setText(nullSafe(p.getGmail()));
    }

    /** Muestra la mascota seleccionada en el formulario (o limpia si es null). */
    private void mostrarMascota(Mascota m) {
        if (m == null) { limpiarFormularioMascota(); return; }
        txtMascotaNombre.setText(nullSafe(m.getNombreMascota()));
        if (cmbMascotaEspecie != null) cmbMascotaEspecie.getSelectionModel().select(m.getEspecie());
        txtMascotaRaza.setText(nullSafe(m.getRaza()));
        txtMascotaEdad.setText(String.valueOf(Math.max(0, m.getEdad())));
        txtMascotaIdVet.setText(nullSafe(m.getIdVeterinaria()));
    }

    /** Limpia todos los campos del formulario de propietario. */
    private void limpiarFormularioPropietario() {
        txtNombre.clear(); txtId.clear(); txtDireccion.clear(); txtTelefono.clear(); txtGmail.clear();
    }

    /** Limpia todos los campos del formulario de mascota y la selección de la tabla. */
    private void limpiarFormularioMascota() {
        txtMascotaNombre.clear();
        if (cmbMascotaEspecie != null) cmbMascotaEspecie.getSelectionModel().clearSelection();
        txtMascotaRaza.clear();
        txtMascotaEdad.clear();
        txtMascotaIdVet.clear();
        tblMascotas.getSelectionModel().clearSelection();
        mascotaSeleccionada = null;
    }

    /**
     * Lee el formulario de propietario y construye un objeto {@link Propietario}.
     * @return propietario con los datos del formulario
     * @throws IllegalArgumentException si nombre o ID están vacíos
     */
    private Propietario leerFormularioPropietario() {
        String nombre = safe(txtNombre);
        String id     = safe(txtId);
        String dir    = safe(txtDireccion);
        String tel    = safe(txtTelefono);
        String mail   = safe(txtGmail);
        if (nombre.isBlank() || id.isBlank())
            throw new IllegalArgumentException("Nombre e ID (documento) son obligatorios.");
        return new Propietario(nombre, id, dir, tel, mail);
    }

    /**
     * Lee el formulario de mascota y construye un objeto {@link Mascota}.
     * Si {@code isNew} es true, exige ID veterinaria no vacío.
     * @param propietario propietario dueño de la mascota
     * @param isNew indica si es creación (true) o edición (false)
     * @return mascota con los datos del formulario
     * @throws IllegalArgumentException si faltan campos obligatorios
     */
    private Mascota leerFormularioMascota(Propietario propietario, boolean isNew) {
        String nombre = safe(txtMascotaNombre);
        String idVet  = safe(txtMascotaIdVet);
        if (nombre.isBlank()) throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        if (isNew && idVet.isBlank()) throw new IllegalArgumentException("El ID veterinaria es obligatorio para nuevas mascotas.");

        Especie especie = cmbMascotaEspecie.getValue();
        String raza = safe(txtMascotaRaza);
        int edad = 0;
        try { String e = safe(txtMascotaEdad); if (!e.isBlank()) edad = Integer.parseInt(e); } catch (NumberFormatException ignored) {}

        return new Mascota(
                idVet,
                nombre,
                especie,
                raza,
                propietario,
                Math.max(0, edad)

        );
    }

    /** Selecciona en la tabla de propietarios el registro cuyo id coincida. */
    private void seleccionarPorId(String id) {
        if (id == null) return;
        for (int i = 0; i < tblPropietarios.getItems().size(); i++) {
            Propietario p = tblPropietarios.getItems().get(i);
            if (Objects.equals(p.getId(), id)) {
                tblPropietarios.getSelectionModel().select(i);
                tblPropietarios.scrollTo(i);
                break;
            }
        }
    }

    /** Selecciona en la tabla de mascotas por ID de veterinaria y hace scroll. */
    private void seleccionarMascotaPorIdVet(String idVet) {
        if (idVet == null) return;
        for (int i = 0; i < tblMascotas.getItems().size(); i++) {
            Mascota m = tblMascotas.getItems().get(i);
            if (Objects.equals(m.getIdVeterinaria(), idVet)) {
                tblMascotas.getSelectionModel().select(i);
                tblMascotas.scrollTo(i);
                break;
            }
        }
    }

    /** Devuelve cadena vacía si es null. */
    private static String nullSafe(String s) { return s == null ? "" : s; }

    /** Lee y recorta el texto de un TextField; devuelve vacío si es null. */
    private static String safe(TextField f) { return f == null || f.getText() == null ? "" : f.getText().trim(); }

    /** Muestra un diálogo de error con el mensaje indicado. */
    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }

    /** Muestra un diálogo informativo con el mensaje indicado. */
    private void showInfo(String msg)  { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}
