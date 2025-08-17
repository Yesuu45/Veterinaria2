package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter

public class Consulta {

    /** Cita de la que deriva esta consulta. */
    @NonNull
    private Cita cita;

    /** Diagnóstico clínico . */
    private String diagnostico;

    /** Tratamiento propuesto/realizado. */
    private Tratamiento tratamiento;

    /** Notas adicionales del profesional. */
    private String notasAdicionales;

    /** Fecha/hora de registro de la consulta. */
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    /**
     * Crea una Consulta asociada a una Cita.
     * @param cita            cita fuente (no null)
     * @param diagnostico     diagnóstico libre (opcional)
     * @param tratamiento     tratamiento (opcional)
     * @param notasAdicionales notas adicionales (opcional)
     * SOLID: SRP, LSP.
     */
    public Consulta(@NonNull Cita cita, String diagnostico, Tratamiento tratamiento, String notasAdicionales) {
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasAdicionales = notasAdicionales;
    }

    /**
     * Representación legible con id de la cita y campos principales.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return "Consulta{cita=" + (cita != null ? cita.getId() : "Sin cita") +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento=" + (tratamiento != null ? tratamiento : "Sin tratamiento") +
                ", notasAdicionales='" + notasAdicionales + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }

    /**
     * Igualdad basada en la Cita .
     * SOLID: SRP, LSP.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consulta)) return false;
        Consulta consulta = (Consulta) o;
        return consulta.cita != null && this.cita != null &&
                Objects.equals(consulta.cita.getId(), this.cita.getId());
    }

    /** Hash consistente con equals (usa id de la Cita). SOLID: SRP. */
    @Override
    public int hashCode() {
        return Objects.hash(cita != null ? cita.getId() : null);
    }
}
