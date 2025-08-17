package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link PersonalApoyo}.
 * Validan getters, setters, toString y la asignación de cargo.
 */
class PersonalApoyoTest {

    private PersonalApoyo personal;

    @BeforeEach
    void setUp() {
        personal = new PersonalApoyo(
                "Laura",
                "789",
                "Calle 45",
                "3101111111",
                "laura@mail.com",
                "Recepcionista"
        );
    }



    /** Getter del nombre. */
    @Test
    void getNombre() {
        assertEquals("Laura", personal.getNombre());
    }

    /** Getter del id. */
    @Test
    void getId() {
        assertEquals("789", personal.getId());
    }

    /** Getter de la dirección. */
    @Test
    void getDireccion() {
        assertEquals("Calle 45", personal.getDireccion());
    }

    /** Getter del teléfono. */
    @Test
    void getTelefono() {
        assertEquals("3101111111", personal.getTelefono());
    }

    /** Getter del gmail. */
    @Test
    void getGmail() {
        assertEquals("laura@mail.com", personal.getGmail());
    }

    /** Setter del nombre. */
    @Test
    void setNombre() {
        personal.setNombre("Paola");
        assertEquals("Paola", personal.getNombre());
    }

    /** Setter del id. */
    @Test
    void setId() {
        personal.setId("999");
        assertEquals("999", personal.getId());
    }

    /** Setter de la dirección. */
    @Test
    void setDireccion() {
        personal.setDireccion("Carrera 10");
        assertEquals("Carrera 10", personal.getDireccion());
    }

    /** Setter del teléfono. */
    @Test
    void setTelefono() {
        personal.setTelefono("3202222222");
        assertEquals("3202222222", personal.getTelefono());
    }

    /** Setter del gmail. */
    @Test
    void setGmail() {
        personal.setGmail("paola@mail.com");
        assertEquals("paola@mail.com", personal.getGmail());
    }

    /** toString alternativo validando que no devuelva null ni vacío. */
    @Test
    void testToString1() {
        assertNotNull(personal.toString());
        assertFalse(personal.toString().isEmpty());
    }

    /** Getter del cargo. */
    @Test
    void getCargo() {
        assertEquals("Recepcionista", personal.getCargo());
    }

    /** Setter del cargo. */
    @Test
    void setCargo() {
        personal.setCargo("Auxiliar");
        assertEquals("Auxiliar", personal.getCargo());
    }
}
