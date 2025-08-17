package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


public class VeterinarioController {

    /** Agregado principal de dominio. */
    private final Veterinaria veterinaria;

    /**
     * Constructor del controlador de veterinarios.
     * @param veterinaria agregado raíz (no null).
     */
    public VeterinarioController(Veterinaria veterinaria) {
        this.veterinaria = Objects.requireNonNull(veterinaria, "Veterinaria no puede ser null");
    }

    /* =========================
       CRUD
       ========================= */

    /**
     * Registra un nuevo veterinario en el sistema.
     * Valida unicidad por número de licencia.
     * @param veterinario objeto veterinario
     * @return true si se creó correctamente
     */
    public boolean crearVeterinario(Veterinario veterinario){
        return veterinaria.agregarVeterinario(veterinario);
    }

    /**
     * Actualiza un veterinario identificado por su licencia actual.
     * @param licenciaActual número de licencia a modificar
     * @param datos nuevos datos del veterinario
     * @return true si se actualizó correctamente
     */
    public boolean actualizarVeterinario(String licenciaActual, Veterinario datos){
        return veterinaria.actualizarVeterinarioPorLicencia(licenciaActual, datos);
    }

    /**
     * Elimina un veterinario del sistema por su licencia.
     * @param licencia número de licencia
     * @return true si se eliminó correctamente
     */
    public boolean eliminarVeterinario(String licencia){
        return veterinaria.eliminarVeterinarioPorLicencia(licencia);
    }

    /* =========================
       Lecturas / helpers
       ========================= */

    /**
     * Lista todos los veterinarios registrados.
     * @return colección de veterinarios
     */
    public Collection<Veterinario> obtenerVeterinarios() {
        return veterinaria.getListaVeterinarios();
    }

    /**
     * Verifica si ya existe un veterinario con esa licencia.
     * @param licencia número de licencia
     * @return true si existe
     */
    public boolean existePorLicencia(String licencia) {
        return veterinaria.existeVeterinarioPorLicencia(licencia);
    }

    /**
     * Busca un veterinario por su licencia.
     * @param licencia número de licencia
     * @return Optional con el veterinario si existe
     */
    public Optional<Veterinario> buscarPorLicencia(String licencia) {
        return veterinaria.buscarVeterinarioPorLicencia(licencia);
    }
}
