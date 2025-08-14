package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mascota {
    private String nombreMascota ;
    private Especie especie;
    private Propietario propietario;
    private int edad;
    private String idVeterinaria;

    public Mascota(String nombreMascota, Especie especie, Propietario propietario, int edad, String idVeterinaria) {
        this.nombreMascota = nombreMascota;
        this.especie = especie;
        this.propietario = propietario;
        this.edad = edad;
        this.idVeterinaria = idVeterinaria;
    }
}
