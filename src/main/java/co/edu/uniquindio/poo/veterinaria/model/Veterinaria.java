package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Veterinaria {
    private String nombre;
    private String direccion;
    private String telefono;
    private String rut;
    private LinkedList<Propietario> propietarios;
    private LinkedList<Mascota> mascotas;
    private LinkedList<Cita> citas;

    public Veterinaria(String nombre, String direccion, String telefono, String rut) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rut = rut;
        this.propietarios = new LinkedList<>();
        this.mascotas = new LinkedList<>();
        this.citas = new LinkedList<>();
    }

    public boolean agregarCliente(Propietario propietario) {
        boolean centinela = false;
        if (!verificarPropietarios(propietario.getId())) {
            propietarios.add(propietario);
            for (Mascota mascota : propietario.getMascotas()) {
                mascotas.add(mascota);
            }
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarPropietarios(String cedula) {
        boolean centinela = false;
        for (Propietario propietario : propietarios) {
            if (propietario.getId().equals(cedula)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean agregarCita(Cita cita) {
        return citas.add(cita);
    }
}