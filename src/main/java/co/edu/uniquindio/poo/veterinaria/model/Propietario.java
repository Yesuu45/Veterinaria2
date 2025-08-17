package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Objects;

@Getter
@Setter

public class Propietario extends Persona {

    /** Lista de mascotas a cargo de este propietario. */
    private LinkedList<Mascota> mascotas;

    /**
     * Crea un propietario con sus datos básicos.
     * @param nombre nombre completo
     * @param id identificación única
     * @param direccion dirección
     * @param telefono teléfono
     * @param gmail correo electrónico
     * SOLID: SRP, LSP.
     */
    public Propietario(String nombre, String id, String direccion, String telefono, String gmail) {
        super(nombre, id, direccion, telefono, gmail);
        this.mascotas = new LinkedList<>();
    }

    /**
     * Agrega una mascota al propietario, evitando duplicados.
     * Asigna automáticamente este propietario a la mascota.
     * @param mascota mascota a agregar
     * @return true si se agregó, false si estaba duplicada o es null
     * SOLID: SRP, ISP.
     */
    public boolean agregarMascota(Mascota mascota) {
        if (mascota != null && !mascotas.contains(mascota)) {
            mascotas.add(mascota);
            mascota.setPropietario(this);
            return true;
        }
        return false;
    }

    /**
     * Elimina una mascota del propietario según su ID.
     * @param idMascota identificador de la mascota
     * @return true si se eliminó, false si no existía
     * SOLID: SRP.
     */
    public boolean eliminarMascota(String idMascota) {
        return mascotas.removeIf(m -> Objects.equals(m.getId(), idMascota));
    }

    /**
     * Representación legible: nombre + id.
     * Útil para mostrar en ComboBox o TableView.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return getNombre() + " (" + getId() + ")";
    }
}
