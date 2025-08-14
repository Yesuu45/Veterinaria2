package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Veterinario extends Persona{
    private String numLicencia;
    private Especialidad especialidad;

    public Veterinario(String nombre, String id, String direccion, String telefono, String gmail, String numLicencia, Especialidad especialidad) {
        super(nombre, id, direccion, telefono, gmail);
        this.numLicencia = numLicencia;
        this.especialidad = especialidad;
    }
}
