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

    // ✅ Getters compatibles con los controladores
    public LinkedList<Cita> getListaCitas() {
        return citas;
    }

    public LinkedList<Propietario> getListaPropietarios() {
        return propietarios;
    }

    public LinkedList<Veterinario> getListaVeterinarios() {
        return veterinarios;
    }

    public LinkedList<PersonalApoyo> getListaPersonalApoyo() {
        return personalesApoyo;
    }

    public LinkedList<Mascota> getListaMascotas() {
        return mascotas;
    }

    public LinkedList<Consulta> getListaConsultas() {
        return consultas;
    }

    // ✅ Agregar entidades
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

    public boolean agregarVeterinario(Veterinario veterinario) {
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

    // ✅ Verificaciones
    public boolean verificarVeterinario(String cedula) {
        for (Veterinario veterinario : veterinarios) {
            if (veterinario.getId().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarPersonalApoyo(String cedula) {
        for (PersonalApoyo personalApoyo : personalesApoyo) {
            if (personalApoyo.getId().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarPropietarios(String cedula) {
        for (Propietario propietario : propietarios) {
            if (propietario.getId().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarCita(String id) {
        for (Cita cita : citas) {
            if (cita.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // ✅ Eliminaciones
    public boolean eliminarPropietario(String cedula) {
        return propietarios.removeIf(propietario -> propietario.getId().equals(cedula));
    }

    public boolean eliminarVeterinario(String cedula) {
        return veterinarios.removeIf(veterinario -> veterinario.getId().equals(cedula));
    }

    public boolean eliminarPersonalApoyo(String cedula) {
        return personalesApoyo.removeIf(personalApoyo -> personalApoyo.getId().equals(cedula));
    }

    public boolean eliminarCita(String id) {
        return citas.removeIf(cita -> cita.getId().equals(id));
    }

    // ✅ Actualizaciones
    public boolean actualiazarPersonalApoyo(String cedula, PersonalApoyo actualizado) {
        for (PersonalApoyo personalApoyo : personalesApoyo) {
            if (personalApoyo.getId().equals(cedula)) {
                personalApoyo.setNombre(actualizado.getNombre());
                personalApoyo.setDireccion(actualizado.getDireccion());
                personalApoyo.setTelefono(actualizado.getTelefono());
                personalApoyo.setGmail(actualizado.getGmail());
                return true;
            }
        }
        return false;
    }

    public boolean actualiazarVeterinario(String cedula, Veterinario actualizado) {
        for (Veterinario veterinario : veterinarios) {
            if (veterinario.getId().equals(cedula)) {
                veterinario.setNombre(actualizado.getNombre());
                veterinario.setDireccion(actualizado.getDireccion());
                veterinario.setTelefono(actualizado.getTelefono());
                veterinario.setGmail(actualizado.getGmail());
                return true;
            }
        }
        return false;
    }

    public boolean actualiazarPropietario(String cedula, Propietario actualizado) {
        for (Propietario propietario : propietarios) {
            if (propietario.getId().equals(cedula)) {
                propietario.setNombre(actualizado.getNombre());
                propietario.setDireccion(actualizado.getDireccion());
                propietario.setTelefono(actualizado.getTelefono());
                propietario.setGmail(actualizado.getGmail());
                propietario.setMascotas(actualizado.getMascotas());
                for (Mascota mascota : propietario.getMascotas()) {
                    if (!mascotas.contains(mascota)) {
                        mascotas.add(mascota);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
