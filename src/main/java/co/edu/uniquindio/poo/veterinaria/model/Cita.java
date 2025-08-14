package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class Cita {

    private PersonalApoyo personalApoyo;
    private Veterinario veterinario;
    private Mascota mascota;
    private LocalDateTime fecha;
    private String id;
    private String observaciones;
}
