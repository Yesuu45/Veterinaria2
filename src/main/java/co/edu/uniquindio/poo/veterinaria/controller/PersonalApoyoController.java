package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.PersonalApoyo;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


public class PersonalApoyoController {

    /** Agregado principal de dominio. */
    private final Veterinaria veterinaria;

    /**
     * Crea el controlador de personal de apoyo.
     * @param veterinaria agregado de dominio (no null).
     */
    public PersonalApoyoController(Veterinaria veterinaria) {
        this.veterinaria = Objects.requireNonNull(veterinaria, "Veterinaria no puede ser null");
    }

    /* =========================
       CRUD
       ========================= */

    /**
     * Registrar un nuevo personal de apoyo.
     * @param personalApoyo datos del nuevo personal
     * @return true si se registró correctamente
     */
    public boolean crearPersonalApoyo(PersonalApoyo personalApoyo) {
        return veterinaria.agregarPersonalApoyo(personalApoyo);
    }

    /**
     * Actualizar datos de personal de apoyo por ID.
     * @param idPersonalApoyo cédula del personal
     * @param datos nuevos datos
     * @return true si se actualizó
     */
    public boolean actualizarPersonalApoyo(String idPersonalApoyo, PersonalApoyo datos) {
        return veterinaria.actualizarPersonalApoyo(idPersonalApoyo, datos);
    }

    /**
     * Eliminar personal de apoyo por su ID.
     * @param idPersonalApoyo cédula
     * @return true si se eliminó
     */
    public boolean eliminarPersonalApoyo(String idPersonalApoyo){
        return veterinaria.eliminarPersonalApoyoPorId(idPersonalApoyo);
    }

    /* =========================
       Lecturas / helpers
       ========================= */

    /**
     * Obtiene el listado de personal de apoyo (para UI).
     * @return colección inmutable desde el modelo
     */
    public Collection<PersonalApoyo> obtenerPersonalApoyo() {
        return veterinaria.getListaPersonalApoyo();
    }

    /**
     * Busca un personal de apoyo por su ID.
     * @param id cédula
     * @return Optional con el personal si existe
     */
    public Optional<PersonalApoyo> buscarPorId(String id) {
        if (id == null) return Optional.empty();
        return veterinaria.getListaPersonalApoyo()
                .stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst();
    }

    /**
     * Verifica si existe un personal de apoyo con ese ID.
     * @param id cédula
     * @return true si existe
     */
    public boolean existePorId(String id) {
        return buscarPorId(id).isPresent();
    }
}
