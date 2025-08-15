package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Cita {

    private PersonalApoyo personalApoyo;
    private Veterinario veterinario;
    private Mascota mascota;
    private LocalDateTime fecha;
    private String id;
    private String observaciones;


    public Cita(Veterinario veterinario, PersonalApoyo personalApoyo, Mascota mascota, LocalDateTime fecha, String observaciones, String id) {
        this.veterinario = veterinario;
        this.personalApoyo = personalApoyo;
        this.mascota = mascota;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.id = id;
    }
}
