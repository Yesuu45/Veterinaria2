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

    public  boolean eliminarVeterinario(String id){
        return veterinaria.eliminarVeterinario(id);
    }

    public boolean actualizarVeterinario(String id, Veterinario veterinario){
        return veterinaria.actualiazarVeterinario(id, veterinario);
    }

    public boolean crearVeterinario(Veterinario veterinario){
        return veterinaria.agregarVeterinario(veterinario);

    }

    public Collection<Veterinario>ObtenerVeterinarios() {
        return veterinaria.getVeterinarios();
    }


}
