package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PersonalApoyo extends Persona {

    /** Cargo que desempeña en la clínica (ej: Recepcionista, Auxiliar, etc.). */
    private String cargo;

    /**
     * Constructor completo con cargo explícito.
     * @param nombre nombre del personal
     * @param id identificación
     * @param direccion dirección
     * @param telefono teléfono
     * @param gmail correo electrónico
     * @param cargo cargo que ocupa
     * SOLID: SRP, LSP.
     */
    public PersonalApoyo(String nombre, String id, String direccion, String telefono, String gmail, String cargo) {
        super(nombre, id, direccion, telefono, gmail);
        this.cargo = cargo;
    }

    /**
     * Constructor alternativo: cargo por defecto vacío.
     * @param nombre nombre del personal
     * @param id identificación
     * @param direccion dirección
     * @param telefono teléfono
     * @param gmail correo electrónico
     * SOLID: OCP (sobrecarga de constructores).
     */
    public PersonalApoyo(String nombre, String id, String direccion, String telefono, String gmail) {
        this(nombre, id, direccion, telefono, gmail, "");
    }

    /**
     * Representación legible: nombre y cargo (si existe).
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return getNombre() + (cargo == null || cargo.isBlank() ? "" : " (" + cargo + ")");
    }
}
