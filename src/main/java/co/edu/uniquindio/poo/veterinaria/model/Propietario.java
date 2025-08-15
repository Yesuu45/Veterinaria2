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


    @Override
    public String toString() {
        return "Propietario{" +
                "mascotas=" + mascotas +
                '}';
    }
}
