package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.*;

import java.util.Collection;

public class CitaController {

    Veterinaria veterinaria;

    public Collection<Cita> obtenerCitas() {
        return veterinaria.getCitas();
    }

    public Collection<Propietario> obtenerPropietarios() {
        return veterinaria.getPropietarios();
    }

    public Collection<Veterinario> obtenerVeterinarios() {
        return veterinaria.getVeterinarios();
    }

    public Collection<PersonalApoyo> obtenerPersonalApoyo() {
        return veterinaria.getPersonalesApoyo();
    }

    public CitaController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }

    public boolean crearCita(Cita cita, Veterinario veterinario, Mascota mascota, PersonalApoyo personalApoyo) {
        return veterinaria.agregarCita(cita);
    }

}