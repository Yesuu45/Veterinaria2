package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Historial}.
 * Se validan operaciones CRUD sobre consultas,
 * búsquedas y estadísticas simples.
 */
class HistorialTest {

    private Historial historial;
    private Cita cita1, cita2, cita3;
    private Consulta consulta1, consulta2, consulta3;

    @BeforeEach
    void setUp() {
        historial = new Historial();

        cita1 = new Cita("C1", LocalDateTime.now().minusDays(2), "Chequeo");
        cita2 = new Cita("C2", LocalDateTime.now().minusDays(1), "Vacunación");
        cita3 = new Cita("C3", LocalDateTime.now(), "Control");

        consulta1 = new Consulta(cita1, "Otitis", new Tratamiento("Amoxicilina", "250 mg", "cada 12h", 7, ""), "Obs1");
        consulta2 = new Consulta(cita2, "Dermatitis", null, "Obs2");
        consulta3 = new Consulta(cita3, "Gastroenteritis", null, "Obs3");

        historial.agregarConsulta(consulta1);
        historial.agregarConsulta(consulta2);
        historial.agregarConsulta(consulta3);
    }

    /** Debe retornar todas las consultas agregadas. */
    @Test
    void getConsultas() {
        assertEquals(3, historial.getConsultas().size());
        assertTrue(historial.getConsultas().contains(consulta1));
    }

    /** Al agregar una consulta, debe aumentar el tamaño de la lista. */
    @Test
    void agregarConsulta() {
        Consulta nueva = new Consulta(
                new Cita("C4", LocalDateTime.now().plusDays(1), "Nueva"),
                "Resfriado", null, "Sin fiebre"
        );
        historial.agregarConsulta(nueva);
        assertEquals(4, historial.getConsultas().size());
        assertTrue(historial.getConsultas().contains(nueva));
    }

    /** Al eliminar por objeto, debe desaparecer del historial. */
    @Test
    void eliminarConsulta() {
        boolean eliminado = historial.eliminarConsulta(consulta2);
        assertTrue(eliminado);
        assertEquals(2, historial.getConsultas().size());
        assertFalse(historial.getConsultas().contains(consulta2));
    }

    /** Buscar por id de cita asociada. */
    @Test
    void buscarPorCitaId() {
        assertEquals(consulta1, historial.buscarPorCitaId("C1").orElse(null));
        assertTrue(historial.buscarPorCitaId("C9").isEmpty());
    }

    /** La última consulta debe corresponder a la de la fecha más reciente. */
    @Test
    void getUltimaConsulta() {
        assertEquals(consulta3, historial.getUltimaConsulta().orElse(null));
    }

    /** Consultas en un día específico. */
    @Test
    void consultasEnDia() {
        LocalDate hoy = LocalDate.now();
        List<Consulta> enHoy = historial.consultasEnDia(hoy);
        assertEquals(1, enHoy.size());
        assertEquals("Gastroenteritis", enHoy.get(0).getDiagnostico());
    }

    /** Consultas entre dos fechas (rango inclusivo). */
    @Test
    void consultasEntre() {
        LocalDate inicio = LocalDate.now().minusDays(2);
        LocalDate fin = LocalDate.now();
        List<Consulta> rango = historial.consultasEntre(inicio, fin);
        assertEquals(3, rango.size());
    }

    /** Total acumulado de consultas. */
    @Test
    void getTotalConsultas() {
        assertEquals(3, historial.getTotalConsultas());
    }

    /** toString debe contener el número de consultas. */
    @Test
    void testToString() {
        String s = historial.toString();
        assertTrue(s.contains("3"));
        assertTrue(s.toLowerCase().contains("consultas"));
    }
}
