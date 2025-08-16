package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.*;

import java.util.Collections;
import java.util.List;

public class CitaController {

    private Veterinaria veterinaria;

    public CitaController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }

    // ✅ Obtener todas las citas
    public List<Cita> obtenerCitas() {
        return veterinaria.getListaCitas();
    }

    // ✅ Obtener propietarios
    public List<Propietario> obtenerPropietarios() {
        return veterinaria.getListaPropietarios();
    }

    // ✅ Obtener veterinarios
    public List<Veterinario> obtenerVeterinarios() {
        return veterinaria.getListaVeterinarios();
    }

    // ✅ Obtener personal de apoyo
    public List<PersonalApoyo> obtenerPersonalApoyo() {
        return veterinaria.getListaPersonalApoyo();
    }

    // ✅ Obtener mascotas de un propietario
    public List<Mascota> obtenerMascotasDePropietario(Propietario propietario) {
        if (propietario != null) {
            return propietario.getMascotas();
        }
        return Collections.emptyList();
    }

    // ✅ Agregar una nueva cita
    public void agregarCita(Cita cita) {
        veterinaria.agregarCita(cita);
    }

    // (Opcional) Eliminar cita
    public void eliminarCita(Cita cita) {
        veterinaria.getListaCitas().remove(cita);
    }
}
