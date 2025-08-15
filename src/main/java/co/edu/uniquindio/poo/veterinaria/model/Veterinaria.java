package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.UUID;

@Getter
@Setter
public class Veterinaria {
    private String nombre;
    private String direccion;
    private String telefono;
    private String rut;
    private LinkedList<Veterinario> veterinarios;
    private LinkedList<PersonalApoyo> personalesApoyo;
    private LinkedList<Propietario> propietarios;
    private LinkedList<Mascota> mascotas;
    private LinkedList<Cita> citas;
    private LinkedList<Consulta> consultas;

    public Veterinaria(String nombre, String direccion, String telefono, String rut) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rut = rut;
        this.veterinarios = new LinkedList<>();
        this.personalesApoyo = new LinkedList<>();
        this.propietarios = new LinkedList<>();
        this.mascotas = new LinkedList<>();
        this.citas = new LinkedList<>();
        this.consultas = new LinkedList<>();
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

    public boolean agregarVeterianario(Veterinario veterinario) {
        boolean centinela = false;
        if (!verificarVeterinario(veterinario.getId())) {
            veterinarios.add(veterinario);
            centinela = true;
        }
        return centinela;
    }

    public boolean agregarPersonalApoyo(PersonalApoyo personalApoyo) {
        boolean centinela = false;
        if (!verificarPersonalApoyo(personalApoyo.getId())) {
            personalesApoyo.add(personalApoyo);
            centinela = true;
        }
        return centinela;
    }

    public boolean agregarCita(Cita cita) {

        boolean centinela = false;
        if (!verificarCita(cita.getId())) {
            citas.add(cita);
            centinela = true;
        }

        return centinela;
    }


    public boolean verificarVeterinario(String cedula) {
        boolean centinela = false;
        for (Veterinario veterinario : veterinarios) {
            if (veterinario.getId().equals(cedula)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean verificarPersonalApoyo(String cedula) {
        boolean centinela = false;
        for (PersonalApoyo personalApoyo : personalesApoyo) {
            if (personalApoyo.getId().equals(cedula)) {
                centinela = true;
            }
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


    public boolean verificarCita(String id) {
        boolean centinela = false;
        for (Cita cita : citas) {
            if (cita.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;


    }
}