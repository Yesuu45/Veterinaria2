package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedList;

@Getter
@Setter
public class Propietario extends Persona {
    private LinkedList<Mascota> mascotas;

    public Propietario(String nombre, String id, String direccion, String telefono, String gmail) {
        super(nombre, id, direccion, telefono, gmail);
        this.mascotas = new LinkedList<>();
    }

    // Método para agregar una mascota al propietario
    public void agregarMascota(Mascota mascota) {
        if (mascota != null) {
            mascotas.add(mascota);
        }
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "nombre=" + getNombre() +
                ", id=" + getId() +
                ", mascotas=" + mascotas +
                '}';
    }
}
