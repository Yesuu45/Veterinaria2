package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Tratamiento}.
 * Se validan getters, setters y toString.
 */
class TratamientoTest {

    private Tratamiento tratamiento;

    @BeforeEach
    void setUp() {
        tratamiento = new Tratamiento("Amoxicilina", "500mg", "Cada 8h", 7, "Tomar con agua");
    }

    /** Validar que toString incluya nombre del medicamento. */
    @Test
    void testToString() {
        String s = tratamiento.toString();
        assertTrue(s.contains("Amoxicilina"));
        assertTrue(s.contains("500mg"));
    }

    /** Getter de medicamento. */
    @Test
    void getMedicamento() {
        assertEquals("Amoxicilina", tratamiento.getMedicamento());
    }

    /** Getter de dosis. */
    @Test
    void getDosis() {
        assertEquals("500mg", tratamiento.getDosis());
    }

    /** Getter de frecuencia. */
    @Test
    void getFrecuencia() {
        assertEquals("Cada 8h", tratamiento.getFrecuencia());
    }

    /** Duplicado de prueba de toString (consistencia). */
    @Test
    void testToString1() {
        assertEquals(tratamiento.toString(), tratamiento.toString()); // mismo valor
    }

    /** Duplicado de getter de medicamento. */
    @Test
    void testGetMedicamento() {
        assertEquals("Amoxicilina", tratamiento.getMedicamento());
    }

    /** Duplicado de getter de dosis. */
    @Test
    void testGetDosis() {
        assertEquals("500mg", tratamiento.getDosis());
    }

    /** Duplicado de getter de frecuencia. */
    @Test
    void testGetFrecuencia() {
        assertEquals("Cada 8h", tratamiento.getFrecuencia());
    }

    /** Getter de días de duración. */
    @Test
    void getDias() {
        assertEquals(7, tratamiento.getDias());
    }

    /** Getter de indicaciones. */
    @Test
    void getIndicaciones() {
        assertEquals("Tomar con agua", tratamiento.getIndicaciones());
    }

    /** Setter de medicamento. */
    @Test
    void setMedicamento() {
        tratamiento.setMedicamento("Ibuprofeno");
        assertEquals("Ibuprofeno", tratamiento.getMedicamento());
    }

    /** Setter de dosis. */
    @Test
    void setDosis() {
        tratamiento.setDosis("200mg");
        assertEquals("200mg", tratamiento.getDosis());
    }

    /** Setter de frecuencia. */
    @Test
    void setFrecuencia() {
        tratamiento.setFrecuencia("Cada 12h");
        assertEquals("Cada 12h", tratamiento.getFrecuencia());
    }

    /** Setter de días. */
    @Test
    void setDias() {
        tratamiento.setDias(10);
        assertEquals(10, tratamiento.getDias());
    }

    /** Setter de indicaciones. */
    @Test
    void setIndicaciones() {
        tratamiento.setIndicaciones("Tomar después de comer");
        assertEquals("Tomar después de comer", tratamiento.getIndicaciones());
    }
}
