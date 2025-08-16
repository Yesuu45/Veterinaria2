package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mascota {
    private String nombreMascota;
    private Especie especie;
    private String raza;
    private Propietario propietario;
    private int edad;
    private String idVeterinaria;

    public Mascota(String nombreMascota, Especie especie, String raza,
                   Propietario propietario, int edad, String idVeterinaria) {
        this.nombreMascota = nombreMascota;
        this.especie = especie;
        this.raza = raza;
        this.propietario = propietario;
        this.edad = edad;
        this.idVeterinaria = idVeterinaria;
    }

    @Override
    public String toString() {
        return nombreMascota + " (" + especie + ", " + raza + ")";
    }
}
