package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class ConsultaController {

    /** Agregado de dominio. */
    private final Veterinaria veterinaria;

    /**
     * Crea el controlador de consultas.
     * @param veterinaria agregado de dominio (no null)
     * SOLID: DIP.
     */
    public ConsultaController(Veterinaria veterinaria) {
        this.veterinaria = Objects.requireNonNull(veterinaria, "Veterinaria no puede ser null");
    }

    /* ========= Lecturas ========= */

    /** Todas las consultas registradas (solo lectura). SOLID: ISP. */
    public List<Consulta> obtenerConsultas() {
        return veterinaria.getListaConsultas();
    }

    /** Todas las citas (para seleccionar en UI sobre cuál registrar consulta). SOLID: ISP. */
    public List<Cita> obtenerCitas() {
        return veterinaria.getListaCitas();
    }

    /** Historial clínico por mascota (consultas registradas). SOLID: ISP. */
    public List<Consulta> historialPorMascota(Mascota mascota) {
        return veterinaria.historialPorMascota(mascota);
    }

    /** Consultas registradas en un día específico (fecha de la cita). SOLID: ISP. */
    public List<Consulta> consultasPorDia(LocalDate dia) {
        return veterinaria.consultasPorDia(dia);
    }

    /**
     * Registra una consulta sobre una cita existente construyendo un {@link Tratamiento} básico.
     * Valida: existencia de cita, unicidad 1:1 (delegado), y campos mínimos.
     * @throws IllegalArgumentException si faltan citaId o diagnóstico
     * @throws IllegalStateException si la cita no existe
     * SOLID: SRP (orquesta), DIP (delegación al dominio).
     */
    public boolean registrarConsulta(
            String citaId,
            String diagnostico,
            String medicamento,
            String dosis,
            String frecuencia,
            int dias,
            String indicaciones,
            String notasAdicionales
    ) {

        if (citaId == null || citaId.isBlank())
            throw new IllegalArgumentException("Debes seleccionar la cita.");
        if (diagnostico == null || diagnostico.isBlank())
            throw new IllegalArgumentException("Debes ingresar el diagnóstico.");

        // Buscar la cita
        Optional<Cita> citaOpt = veterinaria.buscarCitaPorId(citaId);
        if (citaOpt.isEmpty())
            throw new IllegalStateException("La cita no existe en el sistema.");
        Cita cita = citaOpt.get();

        // Construir tratamiento solo si hay información relevante
        Tratamiento tratamiento = null;
        boolean hayAlgunaParteDeTratamiento =
                (medicamento != null && !medicamento.isBlank()) ||
                        (dosis != null && !dosis.isBlank()) ||
                        (frecuencia != null && !frecuencia.isBlank()) ||
                        dias > 0 ||
                        (indicaciones != null && !indicaciones.isBlank());

        if (hayAlgunaParteDeTratamiento) {
            tratamiento = new Tratamiento(medicamento, dosis, frecuencia, dias, indicaciones);
        }

        // Construir y registrar la consulta
        Consulta consulta = new Consulta(cita, diagnostico, tratamiento, notasAdicionales);
        return veterinaria.registrarConsulta(consulta);
    }

    /**
     * Variante cuando la UI ya construyó el {@link Tratamiento}.
     * @throws IllegalArgumentException si faltan citaId o diagnóstico
     * @throws IllegalStateException si la cita no existe
     * SOLID: SRP, DIP.
     */
    public boolean registrarConsulta(String citaId, String diagnostico, Tratamiento tratamiento, String notasAdicionales) {
        if (citaId == null || citaId.isBlank())
            throw new IllegalArgumentException("Debes seleccionar la cita.");
        if (diagnostico == null || diagnostico.isBlank())
            throw new IllegalArgumentException("Debes ingresar el diagnóstico.");

        Cita cita = veterinaria.buscarCitaPorId(citaId)
                .orElseThrow(() -> new IllegalStateException("La cita no existe en el sistema."));

        Consulta consulta = new Consulta(cita, diagnostico, tratamiento, notasAdicionales);
        return veterinaria.registrarConsulta(consulta);
    }

    /**
     * Elimina la consulta asociada a una cita (1:1).
     * @param citaId id de la cita
     * @return true si se eliminó
     * @throws IllegalArgumentException si citaId es vacío
     * SOLID: SRP, DIP.
     */
    public boolean eliminarConsultaPorCita(String citaId) {
        if (citaId == null || citaId.isBlank())
            throw new IllegalArgumentException("Debes indicar el ID de la cita.");

        boolean ok = veterinaria.eliminarConsultaPorCitaId(citaId);
        if (!ok) {
            // Aquí puedes notificar a la UI: no existía consulta para esa cita o no se pudo eliminar.
        }
        return ok;
    }
}
