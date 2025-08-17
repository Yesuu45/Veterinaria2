package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Consulta}.
 * Se validan toString, equals/hashCode y el acceso/mutación de campos.
 */
class ConsultaTest {

    private Cita citaBase;
    private Tratamiento ttoBase;
    private Consulta consulta;

    @BeforeEach
    void setUp() {
        citaBase = new Cita("C1", LocalDateTime.now().plusDays(1), "Control");
        ttoBase = new Tratamiento("Amoxicilina", "250 mg", "cada 12 horas", 7, "Con comida");
        consulta = new Consulta(citaBase, "Otitis", ttoBase, "Paciente estable");
    }

    /** Debe incluir id de cita y campos principales. */
    @Test
    void testToString() {
        String s = consulta.toString();
        assertTrue(s.contains("C1"));
        assertTrue(s.contains("Otitis"));
        assertTrue(s.contains("Amoxicilina"));
        assertTrue(s.contains("Paciente estable"));
    }

    /** Igualdad basada en la Cita (id de la cita). */
    @Test
    void testEquals() {
        // Misma id de cita => iguales
        Cita mismaId = new Cita("C1", LocalDateTime.now().plusDays(2), "Otra desc");
        Consulta c2 = new Consulta(mismaId, "Dx", null, null);
        assertEquals(consulta, c2);

        // Distinta id de cita => distintos
        Cita distintaId = new Cita("C2", LocalDateTime.now().plusDays(2), "Otra desc");
        Consulta c3 = new Consulta(distintaId, "Dx", null, null);
        assertNotEquals(consulta, c3);

        // Comparación con null y otro tipo
        assertNotEquals(consulta, null);
        assertNotEquals(consulta, "no-consulta");
    }

    /** hashCode consistente con equals (por id de cita). */
    @Test
    void testHashCode() {
        Cita mismaId = new Cita("C1", LocalDateTime.now().plusDays(3), "desc");
        Consulta c2 = new Consulta(mismaId, "Dx", null, null);
        assertEquals(consulta.hashCode(), c2.hashCode());
    }

    // ========= Getters =========

    @Test
    void getCita() {
        assertSame(citaBase, consulta.getCita());
    }

    @Test
    void getDiagnostico() {
        assertEquals("Otitis", consulta.getDiagnostico());
    }

    @Test
    void getTratamiento() {
        assertSame(ttoBase, consulta.getTratamiento());
    }

    @Test
    void getNotasAdicionales() {
        assertEquals("Paciente estable", consulta.getNotasAdicionales());
    }

    @Test
    void getFechaRegistro() {
        assertNotNull(consulta.getFechaRegistro());
        // (Opcional) asegurar que no sea futuro lejano
        assertTrue(consulta.getFechaRegistro().isBefore(LocalDateTime.now().plusMinutes(1)));
    }

    // ========= Setters =========

    @Test
    void setCita() {
        Cita nueva = new Cita("C9", LocalDateTime.now().plusDays(5), "Desc");
        consulta.setCita(nueva);
        assertSame(nueva, consulta.getCita());
    }

    @Test
    void setDiagnostico() {
        consulta.setDiagnostico("Dermatitis");
        assertEquals("Dermatitis", consulta.getDiagnostico());
    }

    @Test
    void setTratamiento() {
        Tratamiento t2 = new Tratamiento("Ibuprofeno", "100 mg", "cada 8 horas", 3, "Después de comer");
        consulta.setTratamiento(t2);
        assertSame(t2, consulta.getTratamiento());
    }

    @Test
    void setNotasAdicionales() {
        consulta.setNotasAdicionales("Revisar en 10 días");
        assertEquals("Revisar en 10 días", consulta.getNotasAdicionales());
    }

    @Test
    void setFechaRegistro() {
        LocalDateTime nueva = LocalDateTime.now().minusHours(2);
        consulta.setFechaRegistro(nueva);
        assertEquals(nueva, consulta.getFechaRegistro());
    }
}
