package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

/**
 * La clase Tratamiento representa la terapia, medicamento o procedimiento
 * recomendado/aplicado a una mascota en una consulta veterinaria.
 */
@Getter
@Setter
public class Tratamiento {

    @NonNull
    private String nombre;   // Nombre del tratamiento (ej: Vacuna, Desparasitación)

    private String descripcion;  // Detalle del tratamiento

    private String dosis;   // Dosis y frecuencia (ej: 5ml cada 12h)

    private int duracionDias; // Duración en días del tratamiento


    // Constructor
    public Tratamiento(@NonNull String nombre,
                       String descripcion,
                       String dosis,
                       int duracionDias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dosis = dosis;
        this.duracionDias = duracionDias;
    }

    @Override
    public String toString() {
        return "Tratamiento{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", dosis='" + dosis + '\'' +
                ", duracionDias=" + duracionDias +
                '}';
    }
}
