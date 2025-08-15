package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.App;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;
import co.edu.uniquindio.poo.veterinaria.model.Veterinario;

import java.util.Collection;

import static co.edu.uniquindio.poo.veterinaria.App.veterinaria;

public class VeterinarioController {
    Veterinaria veterinaria;
    public VeterinarioController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }
    public boolean crearVeterinario(Veterinario veterinario){
        return veterinaria.agregarVeterianario(veterinario);

    }

    public Collection<Veterinario>ObtenerVeterinarios() {
        return veterinaria.getVeterinarios();
    }


}
