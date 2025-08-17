package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Persona}.
 * Se validan los getters, setters y toString.
 */
class PersonaTest {

    private Persona persona;

    @BeforeEach
    void setUp() {
        // 👇 Clase anónima porque Persona es abstracta
        persona = new Persona("Carlos", "123", "Av 10", "3101234567", "carlos@mail.com") {};
    }

    /** El método toString debe incluir nombre e id. */
    @Test
    void testToString() {
        String s = persona.toString();
        assertTrue(s.contains("Carlos"));
        assertTrue(s.contains("123"));
    }

    /** Getter del nombre. */
    @Test
    void getNombre() {
        assertEquals("Carlos", persona.getNombre());
    }

    /** Getter del id. */
    @Test
    void getId() {
        assertEquals("123", persona.getId());
    }

    /** Getter de la dirección. */
    @Test
    void getDireccion() {
        assertEquals("Av 10", persona.getDireccion());
    }

    /** Getter del teléfono. */
    @Test
    void getTelefono() {
        assertEquals("3101234567", persona.getTelefono());
    }

    /** Getter del gmail. */
    @Test
    void getGmail() {
        assertEquals("carlos@mail.com", persona.getGmail());
    }

    /** Setter del nombre. */
    @Test
    void setNombre() {
        persona.setNombre("Ana");
        assertEquals("Ana", persona.getNombre());
    }

    /** Setter del id. */
    @Test
    void setId() {
        persona.setId("999");
        assertEquals("999", persona.getId());
    }

    /** Setter de la dirección. */
    @Test
    void setDireccion() {
        persona.setDireccion("Calle 20");
        assertEquals("Calle 20", persona.getDireccion());
    }

    /** Setter del teléfono. */
    @Test
    void setTelefono() {
        persona.setTelefono("3201111111");
        assertEquals("3201111111", persona.getTelefono());
    }

    /** Setter del gmail. */
    @Test
    void setGmail() {
        persona.setGmail("ana@mail.com");
        assertEquals("ana@mail.com", persona.getGmail());
    }
}
