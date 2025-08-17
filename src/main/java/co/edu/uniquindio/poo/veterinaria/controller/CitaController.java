package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class CitaController {

    /** Fachada de dominio (agregado) contra la que opera el controlador. */
    private final Veterinaria veterinaria;

    /**
     * Crea el controlador con la veterinaria de trabajo.
     * @param veterinaria agregado de dominio (no null)
     * @throws NullPointerException si veterinaria es null
     * SOLID: DIP.
     */
    public CitaController(Veterinaria veterinaria) {
        this.veterinaria = Objects.requireNonNull(veterinaria, "Veterinaria no puede ser null");
    }

    // ===== Lecturas =====

    /** Lista inmutable de citas para UI. SOLID: ISP. */
    public List<Cita> obtenerCitas() {
        return veterinaria.getListaCitas();
    }

    /** Lista inmutable de propietarios. SOLID: ISP. */
    public List<Propietario> obtenerPropietarios() {
        return veterinaria.getListaPropietarios();
    }

    /** Lista inmutable de veterinarios. SOLID: ISP. */
    public List<Veterinario> obtenerVeterinarios() {
        return veterinaria.getListaVeterinarios();
    }

    /** Lista inmutable de personal de apoyo. SOLID: ISP. */
    public List<PersonalApoyo> obtenerPersonalApoyo() {
        return veterinaria.getListaPersonalApoyo();
    }

    /**
     * Mascotas del propietario (o vacío si null/sin mascotas).
     * @param propietario propietario origen
     * @return lista (posible vacía)
     * SOLID: ISP.
     */
    public List<Mascota> obtenerMascotasDePropietario(Propietario propietario) {
        return (propietario != null && propietario.getMascotas() != null)
                ? propietario.getMascotas()
                : Collections.emptyList();
    }

    /**
     * Citas del día dado, ordenadas por hora.
     * @param dia fecha filtro
     * @return lista inmutable (posible vacía)
     * SOLID: SRP.
     */
    public List<Cita> citasDelDia(LocalDate dia) {
        if (dia == null) return List.of();
        return veterinaria.buscarCitasPorDia(dia);
    }

    // ===== Escrituras (con validación) =====

    /**
     * Crea y agenda una cita a partir de campos simples (útil para UI).
     * Valida: requeridos, existencia en sistema, estructura 1:1 y choques de horario (delegado).
     * @throws IllegalArgumentException si faltan campos requeridos o estructura inválida
     * @throws IllegalStateException si la mascota o el veterinario no existen en el sistema
     * SOLID: SRP (orquestación), DIP (delegación al dominio).
     */
    public boolean agendarCita(LocalDate fecha, LocalTime hora,
                               Mascota mascota, Veterinario veterinario,
                               String descripcion) {

        if (fecha == null || hora == null)
            throw new IllegalArgumentException("Debes seleccionar fecha y hora.");
        if (mascota == null)
            throw new IllegalArgumentException("Debes seleccionar la mascota.");
        if (veterinario == null)
            throw new IllegalArgumentException("Debes seleccionar el veterinario.");

        // Validar existencia en el sistema
        boolean mascotaExiste = veterinaria.getListaMascotas().contains(mascota);
        boolean vetExiste = veterinaria.getListaVeterinarios().contains(veterinario);
        if (!mascotaExiste) throw new IllegalStateException("La mascota no está registrada en la veterinaria.");
        if (!vetExiste) throw new IllegalStateException("El veterinario no está registrado en la veterinaria.");

        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

        // Construir Cita coherente con el modelo (listas pero 1:1)
        Cita cita = new Cita(UUID.randomUUID().toString(), fechaHora, descripcion);
        cita.setMascotaUnica(mascota);
        cita.setVeterinarioUnico(veterinario);
        cita.setPropietarioUnico(mascota.getPropietario());

        return agregarCita(cita); // delega a la validación común
    }

    /**
     * Agrega una Cita ya construida, asegurando estructura 1:1 y sin choques (delegado al dominio).
     * @param cita cita a agregar (no null)
     * @return true si se agenda; false si el dominio la rechaza
     * @throws NullPointerException si la cita o su fecha son null
     * @throws IllegalArgumentException si la estructura 1:1 no se cumple
     * @throws IllegalStateException si mascota o veterinario no existen en el sistema
     * SOLID: SRP, DIP.
     */
    public boolean agregarCita(Cita cita) {
        Objects.requireNonNull(cita, "La cita no puede ser null");
        Objects.requireNonNull(cita.getFecha(), "La fecha/hora de la cita no puede ser null");

        if (!cita.validarEstructuraUnoAUno())
            throw new IllegalArgumentException("La cita debe tener exactamente una mascota y un veterinario.");

        Mascota mascota = cita.getMascota().orElse(null);
        Veterinario vet = cita.getVeterinario().orElse(null);
        if (mascota == null || vet == null)
            throw new IllegalArgumentException("La cita debe tener mascota y veterinario.");

        if (!veterinaria.getListaMascotas().contains(mascota))
            throw new IllegalStateException("La mascota no está registrada en la veterinaria.");
        if (!veterinaria.getListaVeterinarios().contains(vet))
            throw new IllegalStateException("El veterinario no está registrado en la veterinaria.");

        // ✅ delega al método con validaciones del modelo
        return veterinaria.agendarCita(cita);
    }

    /**
     * Elimina una cita por su id (usar la operación del modelo, no la lista inmutable).
     * @param idCita id de la cita
     * @return true si se eliminó
     * SOLID: SRP (orquesta eliminación), DIP (delegación al dominio).
     */
    public boolean eliminarCitaPorId(String idCita) {
        if (idCita == null || idCita.isBlank()) return false;
        return veterinaria.eliminarCita(idCita);
        // Si tu método se llama distinto: veterinaria.eliminarCitaPorId(idCita);
    }
}
