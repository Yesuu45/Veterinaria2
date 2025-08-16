package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cita {

    private String id;

    @NonNull
    private LocalDateTime fecha;


    private String descripcion;


    private List<Propietario> propietarios;


    private List<Mascota> mascotas;


    private List<Veterinario> veterinarios;


    private List<PersonalApoyo> personalApoyo;


    // Constructor principal
    public Cita(String id,
                @NonNull LocalDateTime fecha,
                String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.propietarios = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.personalApoyo = new ArrayList<>();
    }

    // Métodos
    public void agregarPropietario(Propietario propietario) {
        propietarios.add(propietario);
    }

    public void agregarMascota(Mascota mascota) {
        mascotas.add(mascota);
    }

    public void agregarVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
    }

    public void agregarPersonalApoyo(PersonalApoyo personal) {
        personalApoyo.add(personal);
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id='" + id + '\'' +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", propietarios=" + propietarios.size() +
                ", mascotas=" + mascotas.size() +
                ", veterinarios=" + veterinarios.size() +
                ", personalApoyo=" + personalApoyo.size() +
                '}';
    }
}
