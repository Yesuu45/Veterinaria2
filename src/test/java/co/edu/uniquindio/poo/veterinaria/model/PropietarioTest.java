package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Propietario}.
 * Se validan getters, setters, manejo de mascotas y toString.
 */
class PropietarioTest {

    private Propietario propietario;
    private Mascota mascota;

    @BeforeEach
    void setUp() {
        propietario = new Propietario("Laura", "111", "Calle 5", "3101111111", "laura@mail.com");
        mascota = new Mascota("M1", "Firulais", Especie.PERRO, "Labrador", propietario, 3);
    }

    /** El método toString debe incluir el nombre y el id. */
    @Test
    void testToString() {
        String s = propietario.toString();
        assertTrue(s.contains("Laura"));
        assertTrue(s.contains("111"));
    }

    /** Getter del nombre. */
    @Test
    void getNombre() {
        assertEquals("Laura", propietario.getNombre());
    }

    /** Getter del id. */
    @Test
    void getId() {
        assertEquals("111", propietario.getId());
    }

    /** Getter de la dirección. */
    @Test
    void getDireccion() {
        assertEquals("Calle 5", propietario.getDireccion());
    }

    /** Getter del teléfono. */
    @Test
    void getTelefono() {
        assertEquals("3101111111", propietario.getTelefono());
    }

    /** Getter del gmail. */
    @Test
    void getGmail() {
        assertEquals("laura@mail.com", propietario.getGmail());
    }

    /** Setter del nombre. */
    @Test
    void setNombre() {
        propietario.setNombre("Ana");
        assertEquals("Ana", propietario.getNombre());
    }

    /** Setter del id. */
    @Test
    void setId() {
        propietario.setId("222");
        assertEquals("222", propietario.getId());
    }

    /** Setter de la dirección. */
    @Test
    void setDireccion() {
        propietario.setDireccion("Av 10");
        assertEquals("Av 10", propietario.getDireccion());
    }

    /** Setter del teléfono. */
    @Test
    void setTelefono() {
        propietario.setTelefono("3000000000");
        assertEquals("3000000000", propietario.getTelefono());
    }

    /** Setter del gmail. */
    @Test
    void setGmail() {
        propietario.setGmail("ana@mail.com");
        assertEquals("ana@mail.com", propietario.getGmail());
    }

    /** Agregar mascota al propietario. */
    @Test
    void agregarMascota() {
        propietario.agregarMascota(mascota);
        assertTrue(propietario.getMascotas().contains(mascota));
    }



    /** Validar que toString muestre también cantidad de mascotas. */
    @Test
    void testToString1() {
        propietario.agregarMascota(mascota);
        String s = propietario.toString();
        assertTrue(s.contains("Laura"));
        assertTrue(s.contains("1")); // cantidad de mascotas
    }

    /** Getter de mascotas. */
    @Test
    void getMascotas() {
        propietario.agregarMascota(mascota);
        assertEquals(1, propietario.getMascotas().size());
        assertEquals(mascota, propietario.getMascotas().get(0));
    }

    /** Setter de mascotas. */
    @Test
    void setMascotas() {
        LinkedList<Mascota> lista = new LinkedList<>();
        lista.add(mascota);
        propietario.setMascotas(lista);
        assertEquals(1, propietario.getMascotas().size());
    }
}
