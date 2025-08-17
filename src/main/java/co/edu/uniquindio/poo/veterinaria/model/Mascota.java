package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter

public class Mascota {

    /** Identificación veterinaria única en la clínica. */
    private String idVeterinaria;

    /** Nombre de la mascota. */
    private String nombreMascota;

    /** Especie de la mascota. */
    private Especie especie;

    /** Raza de la mascota. */
    private String raza;

    /** Propietario asociado. */
    private Propietario propietario;

    /** Edad de la mascota (>=0). */
    private int edad;

    /** Historial clínico de la mascota. */
    private Historial historial = new Historial();

    /**
     * Crea una mascota con sus datos básicos.
     * @param idVeterinaria identificador único en la clínica
     * @param nombreMascota nombre de la mascota
     * @param especie especie
     * @param raza raza
     * @param propietario dueño
     * @param edad edad (se corrige a 0 si es negativa)
     * SOLID: SRP, LSP.
     */
    public Mascota(String idVeterinaria, String nombreMascota, Especie especie,
                   String raza, Propietario propietario, int edad) {
        this.idVeterinaria = idVeterinaria;
        this.nombreMascota = nombreMascota;
        this.especie = especie;
        this.raza = raza;
        this.propietario = propietario;
        this.edad = Math.max(0, edad); // asegura que no sea negativo
    }

    /**
     * Representación legible: nombre + especie + raza.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return nombreMascota + " (" + especie + ", " + raza + ")";
    }

    /**
     * Igualdad basada en idVeterinaria.
     * SOLID: SRP, LSP.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mascota)) return false;
        Mascota mascota = (Mascota) o;
        return Objects.equals(idVeterinaria, mascota.idVeterinaria);
    }

    /** Hash consistente con equals (usa idVeterinaria). SOLID: SRP. */
    @Override
    public int hashCode() {
        return Objects.hash(idVeterinaria);
    }

    /** Devuelve el identificador único (alias de idVeterinaria). */
    public String getId() {
        return idVeterinaria;
    }

    /**
     * Registra una consulta en el historial de esta mascota.
     * Valida que la Cita de la consulta tenga a esta mascota en la primera posición.
     * @param consulta consulta a registrar
     * @return true si se registró, false si no corresponde
     * SOLID: SRP, ISP.
     */
    public boolean registrarConsulta(Consulta consulta) {
        if (consulta == null || consulta.getCita() == null) return false;
        boolean coincide = !consulta.getCita().getMascotas().isEmpty()
                && this.equals(consulta.getCita().getMascotas().get(0));
        if (!coincide) return false;
        return historial.agregarConsulta(consulta);
    }
}
