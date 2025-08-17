package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter

public class Veterinario extends Persona {

    /** Número de licencia profesional (único). */
    private String numLicencia;

    /** Especialidad del veterinario. */
    private Especialidad especialidad;

    /**
     * Crea un veterinario con sus datos completos.
     * @param nombre nombre del veterinario
     * @param id identificación (cédula o equivalente)
     * @param direccion dirección de contacto
     * @param telefono número de teléfono
     * @param gmail correo electrónico
     * @param numLicencia número de licencia profesional
     * @param especialidad especialidad veterinaria
     * SOLID: SRP, LSP.
     */
    public Veterinario(String nombre, String id, String direccion, String telefono, String gmail,
                       String numLicencia, Especialidad especialidad) {
        super(nombre, id, direccion, telefono, gmail);
        this.numLicencia = numLicencia;
        this.especialidad = especialidad;
    }

    /**
     * Representación legible: nombre + especialidad + licencia.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return getNombre() + " - " + especialidad + " (Lic: " + numLicencia + ")";
    }

    /**
     * Igualdad basada en el número de licencia (único).
     * SOLID: SRP, LSP.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veterinario)) return false;
        Veterinario that = (Veterinario) o;
        return Objects.equals(numLicencia, that.numLicencia);
    }

    /** Hash consistente con equals (usa numLicencia). SOLID: SRP. */
    @Override
    public int hashCode() {
        return Objects.hash(numLicencia);
    }
}
