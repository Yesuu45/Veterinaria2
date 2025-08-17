package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter

public class Cita {
    /** Identificador único de la cita. */
    private String id;

    /** Fecha y hora de la cita. */
    @NonNull
    private LocalDateTime fecha;

    /** Descripción libre de la cita. */
    private String descripcion;

    /** Estado actual . */
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    /**
     * Listas de compatibilidad .
     */
    private List<Propietario> propietarios;
    private List<Mascota> mascotas;
    private List<Veterinario> veterinarios;

    /** Personal de apoyo  */
    private List<PersonalApoyo> personalApoyo;

    /**
     * Crea una cita con estado inicial PROGRAMADA.
     * @param id identificador único
     * @param fecha fecha y hora (no null)
     * @param descripcion texto opcional
     * SOLID: SRP, LSP.
     */
    public Cita(String id, @NonNull LocalDateTime fecha, String descripcion) {
        this.id = id;
        this.fecha = Objects.requireNonNull(fecha);
        this.descripcion = descripcion;
        this.propietarios = new ArrayList<>();
        this.mascotas = new ArrayList<>();
        this.veterinarios = new ArrayList<>();
        this.personalApoyo = new ArrayList<>();
        this.estado = EstadoCita.PROGRAMADA;
    }



    /**
     * Establece un único propietario
     * @param p propietario o null para limpiar
     * SOLID: ISP, SRP.
     */
    public void setPropietarioUnico(Propietario p) {
        this.propietarios.clear();
        if (p != null) this.propietarios.add(p);
    }

    /**
     * Establece  mascota
     * SOLID: ISP, SRP.
     */
    public void setMascotaUnica(Mascota m) {
        this.mascotas.clear();
        if (m != null) this.mascotas.add(m);
    }

    /**
     * Establece un único veterinario
     * SOLID: ISP, SRP.
     */
    public void setVeterinarioUnico(Veterinario v) {
        this.veterinarios.clear();
        if (v != null) this.veterinarios.add(v);
    }

    /**
     * Retorna el propietario
     * SOLID: ISP, OCP.
     */
    public Optional<Propietario> getPropietario() {
        return propietarios.isEmpty() ? Optional.empty() : Optional.ofNullable(propietarios.get(0));
    }

    /**
     * Retorna la mascota
     * SOLID: ISP, OCP.
     */
    public Optional<Mascota> getMascota() {
        return mascotas.isEmpty() ? Optional.empty() : Optional.ofNullable(mascotas.get(0));
    }

    /**
     * Retorna el veterinario
     * SOLID: ISP, OCP.
     */
    public Optional<Veterinario> getVeterinario() {
        return veterinarios.isEmpty() ? Optional.empty() : Optional.ofNullable(veterinarios.get(0));
    }

    /**
     * Valida que haya a lo sumo 1 propietario, 1 mascota y 1 veterinario.
     * SOLID: SRP.
     */
    public boolean validarEstructuraUnoAUno() {
        return propietarios.size() <= 1 && mascotas.size() <= 1 && veterinarios.size() <= 1;
    }

    /* ========= Utilidades para JavaFX ========= */

    /** Convierte fecha a LocalDate para UI. SOLID: ISP. */
    public LocalDate getFechaLocalDate() { return fecha.toLocalDate(); }

    /** Convierte fecha a LocalTime para UI. SOLID: ISP. */
    public LocalTime getHoraLocalTime() { return fecha.toLocalTime(); }

    /**
     * Establece fecha y hora
     * SOLID: ISP.
     */
    public void setFechaHora(LocalDate dia, LocalTime hora) {
        if (dia != null && hora != null) {
            this.fecha = LocalDateTime.of(dia, hora);
        }
    }

    /* ========= Métodos de agregado (compatibles) ========= */

    /**
     * Agrega/reemplaza propietario
     * SOLID: ISP, SRP.
     */
    public void agregarPropietario(Propietario propietario) {
        if (propietario != null) {
            if (propietarios.isEmpty()) propietarios.add(propietario);
            else propietarios.set(0, propietario); // mantener uno a uno
        }
    }

    /** Agrega/reemplaza mascota SOLID: ISP, SRP. */
    public void agregarMascota(Mascota mascota) {
        if (mascota != null) {
            if (mascotas.isEmpty()) mascotas.add(mascota);
            else mascotas.set(0, mascota);
        }
    }

    /** Agrega/reemplaza veterinario SOLID: ISP, SRP. */
    public void agregarVeterinario(Veterinario veterinario) {
        if (veterinario != null) {
            if (veterinarios.isEmpty()) veterinarios.add(veterinario);
            else veterinarios.set(0, veterinario);
        }
    }

    /**
     * Agrega personal de apoyo
     * SOLID: SRP.
     */
    public void agregarPersonalApoyo(PersonalApoyo personal) {
        if (personal != null) {
            personalApoyo.add(personal);
        }
    }

    /* ========= equals/hashCode por id ========= */

    /**
     * Dos citas son iguales si comparten el mismo id.
     * SOLID: SRP, LSP.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return Objects.equals(id, cita.id);
    }

    /** Hash basado en id, SOLID: SRP. */
    @Override
    public int hashCode() { return Objects.hash(id); }

    /* ========= toString amigable ========= */

    /**
     * Representación legible
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        String m = getMascota().map(Mascota::getNombreMascota).orElse("¿mascota?");
        String v = getVeterinario().map(Veterinario::getNombre).orElse("¿veterinario?");
        String props = propietarios.stream().map(Propietario::getNombre).collect(Collectors.joining(", "));
        return fecha + " — " + m + " con " + v + (props.isEmpty() ? "" : " | Prop: " + props) + " [" + estado + "]";
    }
}
