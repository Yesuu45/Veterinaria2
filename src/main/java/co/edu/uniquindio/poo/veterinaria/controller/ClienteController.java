package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Persona;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

import java.util.Collection;

public class ClienteController {

    Veterinaria veterinaria;

    public ClienteController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }

    public boolean crearPropietario(Propietario propietario, Mascota mascota) {

        return veterinaria.agregarCliente(propietario);
    }

    public Collection<Propietario> obtenerPropietarios() {
        return veterinaria.getPropietarios();
    }

    public Collection<Mascota> obtenerMascotas() {
        return veterinaria.getMascotas();
    }

}
