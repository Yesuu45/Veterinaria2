package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Mascota}.
 * Se validan constructores, getters/setters,
 * igualdad, historial de consultas y representación textual.
 */
class MascotaTest {

    private Propietario propietario;
    private Mascota mascota;

    @BeforeEach
    void setUp() {
        propietario = new Propietario("Ana", "123", "Calle 1", "3100000000", "ana@mail.com");
        mascota = new Mascota("M1", "Firulais", Especie.PERRO, "Labrador", propietario, 4);
    }


    /** Equals y hashCode deben basarse en el id. */
    @Test
    void testEquals() {
        Mascota otra = new Mascota("M1", "Max", Especie.GATO, "Siames", propietario, 2);
        assertEquals(mascota, otra);
        assertEquals(mascota.hashCode(), otra.hashCode());
    }

    /** hashCode coherente con equals. */
    @Test
    void testHashCode() {
        Mascota otra = new Mascota("M1", "Rocky", Especie.PERRO, "Beagle", propietario, 3);
        assertEquals(mascota.hashCode(), otra.hashCode());
    }

    /** Getter del id. */
    @Test
    void getId() {
        assertEquals("M1", mascota.getId());
    }



    @Test
    void getIdVeterinaria() {
        mascota.setIdVeterinaria("VET-001");
        assertEquals("VET-001", mascota.getIdVeterinaria());
    }

    @Test
    void getNombreMascota() {
        assertEquals("Firulais", mascota.getNombreMascota());
    }

    @Test
    void getEspecie() {
        assertEquals(Especie.PERRO, mascota.getEspecie());
    }

    @Test
    void getRaza() {
        assertEquals("Labrador", mascota.getRaza());
    }

    @Test
    void getPropietario() {
        assertEquals(propietario, mascota.getPropietario());
    }

    @Test
    void getEdad() {
        assertEquals(4, mascota.getEdad());
    }

    @Test
    void getHistorial() {
        assertNotNull(mascota.getHistorial());
        assertEquals(0, mascota.getHistorial().getTotalConsultas());
    }

    @Test
    void setIdVeterinaria() {
        mascota.setIdVeterinaria("VET-999");
        assertEquals("VET-999", mascota.getIdVeterinaria());
    }

    @Test
    void setNombreMascota() {
        mascota.setNombreMascota("Luna");
        assertEquals("Luna", mascota.getNombreMascota());
    }

    @Test
    void setEspecie() {
        mascota.setEspecie(Especie.GATO);
        assertEquals(Especie.GATO, mascota.getEspecie());
    }

    @Test
    void setRaza() {
        mascota.setRaza("Siames");
        assertEquals("Siames", mascota.getRaza());
    }

    @Test
    void setPropietario() {
        Propietario nuevo = new Propietario("Luis", "456", "Calle 2", "3200000000", "luis@mail.com");
        mascota.setPropietario(nuevo);
        assertEquals(nuevo, mascota.getPropietario());
    }

    @Test
    void setEdad() {
        mascota.setEdad(7);
        assertEquals(7, mascota.getEdad());
    }

    @Test
    void setHistorial() {
        Historial h = new Historial();
        mascota.setHistorial(h);
        assertEquals(h, mascota.getHistorial());
    }
}
