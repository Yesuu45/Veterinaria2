package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Tratamiento {

    /** Nombre del medicamento (ej: "Amoxicilina"). */
    private String medicamento;

    /** Dosis del medicamento (ej: "250 mg"). */
    private String dosis;

    /** Frecuencia de administración (ej: "cada 12 horas"). */
    private String frecuencia;

    /** Duración en días (>=0). */
    private int dias;

    /** Indicaciones adicionales (ej: "con comida"). */
    private String indicaciones;

    /**
     * Crea un tratamiento con sus datos principales.
     * @param medicamento medicamento recetado
     * @param dosis dosis indicada
     * @param frecuencia frecuencia de administración
     * @param dias duración en días (se corrige a 0 si es negativo)
     * @param indicaciones notas adicionales
     * SOLID: SRP.
     */
    public Tratamiento(String medicamento, String dosis, String frecuencia, int dias, String indicaciones) {
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.dias = Math.max(0, dias);
        this.indicaciones = indicaciones;
    }

    /**
     * Representación legible del tratamiento: medicamento, dosis, frecuencia, días e indicaciones.
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        String base = (medicamento != null ? medicamento : "Tratamiento")
                + (dosis != null ? " " + dosis : "")
                + (frecuencia != null ? " • " + frecuencia : "")
                + (dias > 0 ? " • " + dias + " días" : "");
        return (indicaciones != null && !indicaciones.isBlank())
                ? base + " — " + indicaciones
                : base;
    }
}
