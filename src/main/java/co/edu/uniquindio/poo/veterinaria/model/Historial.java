package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Historial {

    /** Lista interna mutable, no se expone directamente. */
    private final LinkedList<Consulta> consultas = new LinkedList<>();

    /** Vista inmutable de las consultas. SOLID: ISP, SRP. */
    public List<Consulta> getConsultas() {
        return Collections.unmodifiableList(consultas);
    }

    /**
     * Agrega una consulta al historial (ignora null).
     * @param consulta consulta a agregar
     * @return true si se agregó
     * SOLID: SRP.
     */
    public boolean agregarConsulta(Consulta consulta) {
        if (consulta == null) return false;
        return consultas.add(consulta);
    }

    /**
     * Elimina una consulta específica .
     * @param consulta consulta a eliminar
     * @return true si se eliminó
     * SOLID: SRP.
     */
    public boolean eliminarConsulta(Consulta consulta) {
        if (consulta == null) return false;
        return consultas.remove(consulta);
    }

    /**
     * Busca una consulta por ID de la cita asociada.
     * @param citaId id de la cita
     * @return consulta encontrada o vacía
     * SOLID: SRP, OCP.
     */
    public Optional<Consulta> buscarPorCitaId(String citaId) {
        if (citaId == null) return Optional.empty();
        return consultas.stream()
                .filter(c -> c.getCita() != null &&
                        c.getCita().getId() != null &&
                        c.getCita().getId().equals(citaId))
                .findFirst();
    }

    /**
     * Retorna la última consulta registrada.
     * @return consulta o vacío
     * SOLID: ISP, SRP.
     */
    public Optional<Consulta> getUltimaConsulta() {
        return consultas.isEmpty() ? Optional.empty() : Optional.of(consultas.getLast());
    }

    /**
     * Consultas realizadas en un día exacto.
     * @param dia fecha buscada
     * @return lista inmutable de consultas
     * SOLID: SRP.
     */
    public List<Consulta> consultasEnDia(LocalDate dia) {
        if (dia == null) return List.of();
        return consultas.stream()
                .filter(c -> c.getCita() != null &&
                        c.getCita().getFecha() != null &&
                        c.getCita().getFecha().toLocalDate().equals(dia))
                .sorted(Comparator.comparing(c -> c.getCita().getFecha()))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Consultas entre dos fechas
     * @param desde fecha inicial
     * @param hasta fecha final
     * @return lista inmutable ordenada
     * SOLID: SRP, OCP.
     */
    public List<Consulta> consultasEntre(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) return List.of();
        return consultas.stream()
                .filter(c -> c.getCita() != null && c.getCita().getFecha() != null)
                .filter(c -> {
                    LocalDate d = c.getCita().getFecha().toLocalDate();
                    return (d.isEqual(desde) || d.isAfter(desde)) &&
                            (d.isEqual(hasta) || d.isBefore(hasta));
                })
                .sorted(Comparator.comparing(c -> c.getCita().getFecha()))
                .collect(Collectors.toUnmodifiableList());
    }

    /** Cantidad total de consultas. SOLID: ISP. */
    public int getTotalConsultas() {
        return consultas.size();
    }

    /**
     * Representación legible del historial (total de consultas).
     * SOLID: SRP.
     */
    @Override
    public String toString() {
        return "Historial{totalConsultas=" + consultas.size() + "}";
    }
}
