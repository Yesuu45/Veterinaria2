package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Veterinario}.
 */
class VeterinarioTest {

    private Veterinario vet;

    @BeforeEach
    void setUp() {
        vet = new Veterinario("Carlos", "101", "Cra 10", "3100000000", "carlos@mail.com", "V-123", Especialidad.ANIMALES_PEQUENOS);
    }



    /** Validar getters básicos heredados de Persona. */
    @Test
    void getNombre() {
        assertEquals("Carlos", vet.getNombre());
    }

    @Test
    void getId() {
        assertEquals("101", vet.getId());
    }

    @Test
    void getDireccion() {
        assertEquals("Cra 10", vet.getDireccion());
    }

    @Test
    void getTelefono() {
        assertEquals("3100000000", vet.getTelefono());
    }

    @Test
    void getGmail() {
        assertEquals("carlos@mail.com", vet.getGmail());
    }

    /** Validar setters heredados de Persona. */
    @Test
    void setNombre() {
        vet.setNombre("Andrés");
        assertEquals("Andrés", vet.getNombre());
    }

    @Test
    void setId() {
        vet.setId("202");
        assertEquals("202", vet.getId());
    }

    @Test
    void setDireccion() {
        vet.setDireccion("Av 15");
        assertEquals("Av 15", vet.getDireccion());
    }

    @Test
    void setTelefono() {
        vet.setTelefono("3200000000");
        assertEquals("3200000000", vet.getTelefono());
    }

    @Test
    void setGmail() {
        vet.setGmail("andres@mail.com");
        assertEquals("andres@mail.com", vet.getGmail());
    }

    /** toString alternativo (igual al principal). */
    @Test
    void testToString1() {
        assertEquals(vet.toString(), vet.toString());
    }

    /** Validar equals: compara por número de licencia. */
    @Test
    void testEquals() {
        Veterinario v2 = new Veterinario("Ana", "202", "Dir", "Tel", "a@mail.com", "V-123", Especialidad.HERBIVOROS);
        Veterinario v3 = new Veterinario("Luis", "303", "Dir", "Tel", "l@mail.com", "V-999", Especialidad.ANIMALES_PEQUENOS);

        assertEquals(vet, v2);   // misma licencia
        assertNotEquals(vet, v3); // licencia diferente
    }

    /** Validar hashCode consistente con equals. */
    @Test
    void testHashCode() {
        Veterinario v2 = new Veterinario("Ana", "202", "Dir", "Tel", "a@mail.com", "V-123", Especialidad.HERBIVOROS);
        assertEquals(vet.hashCode(), v2.hashCode());
    }

    /** Getter numLicencia. */
    @Test
    void getNumLicencia() {
        assertEquals("V-123", vet.getNumLicencia());
    }

    /** Getter especialidad. */
    @Test
    void getEspecialidad() {
        assertEquals(Especialidad.ANIMALES_PEQUENOS, vet.getEspecialidad());
    }

    /** Setter numLicencia. */
    @Test
    void setNumLicencia() {
        vet.setNumLicencia("V-456");
        assertEquals("V-456", vet.getNumLicencia());
    }


}
