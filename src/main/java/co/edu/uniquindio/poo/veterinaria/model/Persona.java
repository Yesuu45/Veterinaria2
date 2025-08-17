package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Persona {

    /** Nombre completo de la persona. */
    private String nombre;

    /** Identificación única (cédula o equivalente). */
    private String id;

    /** Dirección de residencia. */
    private String direccion;

    /** Teléfono de contacto. */
    private String telefono;

    /** Correo electrónico. */
    private String gmail;

    /**
     * Crea una persona con sus datos básicos.
     * @param nombre nombre completo
     * @param id identificación única
     * @param direccion dirección de residencia
     * @param telefono número telefónico
     * @param gmail correo electrónico
     * SOLID: SRP, LSP.
     */
    public Persona(String nombre, String id, String direccion, String telefono, String gmail) {
        this.nombre = nombre;
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gmail = gmail;
    }

    /**
     * Representación legible de la persona con todos sus campos.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", gmail='" + gmail + '\'' +
                '}';
    }
}
