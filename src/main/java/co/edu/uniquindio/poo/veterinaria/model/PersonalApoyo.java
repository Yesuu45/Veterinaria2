package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalApoyo extends Persona {
    public PersonalApoyo(String nombre, String id, String direccion, String telefono, String gmail) {
        super(nombre, id, direccion, telefono, gmail);
    }
}
