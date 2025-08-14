package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Persona {
    private String nombre ;
    private String id ;
    private String direccion;
    private String telefono;
    private String gmail;

    public Persona(String nombre, String id, String direccion, String telefono, String gmail) {
        this.nombre = nombre;
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gmail = gmail;
    }

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
