package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la entidad {@link Cita}.
 * Se valida el correcto funcionamiento de getters, setters,
 * métodos auxiliares y asociaciones (Propietario, Mascota, Veterinario, PersonalApoyo).
 */
class CitaTest {

    private Cita cita;
    private Propietario propietario;
    private Mascota mascota;
    private Veterinario veterinario;
    private PersonalApoyo personal;

    @BeforeEach
    void setUp() {
        propietario = new Propietario("Juan", "123", "Calle 1", "12345", "juan@mail.com");
        mascota = new Mascota("M1", "Firulais", Especie.PERRO, "Labrador", propietario, 5);
        veterinario = new Veterinario("Ana", "321", "Cra 9", "54321", "ana@mail.com", "V-001", Especialidad.ACUATICOS);
        personal = new PersonalApoyo("Pedro", "456", "Av 10", "98765", "pedro@mail.com", "Recepcionista");

        cita = new Cita("C1", LocalDateTime.of(2025, 8, 20, 10, 30), "Chequeo general");
    }

    /** Verifica que se pueda asignar un único propietario. */
    @Test
    void setPropietarioUnico() {
        cita.setPropietarios(List.of(propietario));
        assertEquals(1, cita.getPropietarios().size());
    }

    /** Verifica que se pueda asignar una única mascota. */
    @Test
    void setMascotaUnica() {
        cita.setMascotas(List.of(mascota));
        assertEquals(1, cita.getMascotas().size());
    }

    /** Verifica que se pueda asignar un único veterinario. */
    @Test
    void setVeterinarioUnico() {
        cita.setVeterinarios(List.of(veterinario));
        assertEquals(1, cita.getVeterinarios().size());
    }

    /** Prueba obtener propietario desde la lista. */
    @Test
    void getPropietario() {
        cita.agregarPropietario(propietario);
        assertTrue(cita.getPropietarios().contains(propietario));
    }

    /** Prueba obtener mascota desde la lista. */
    @Test
    void getMascota() {
        cita.agregarMascota(mascota);
        assertTrue(cita.getMascotas().contains(mascota));
    }

    /** Prueba obtener veterinario desde la lista. */
    @Test
    void getVeterinario() {
        cita.agregarVeterinario(veterinario);
        assertTrue(cita.getVeterinarios().contains(veterinario));
    }

    /** Verifica estructura uno a uno: una mascota, un propietario, un veterinario. */
    @Test
    void validarEstructuraUnoAUno() {
        cita.agregarPropietario(propietario);
        cita.agregarMascota(mascota);
        cita.agregarVeterinario(veterinario);
        assertEquals(1, cita.getPropietarios().size());
        assertEquals(1, cita.getMascotas().size());
        assertEquals(1, cita.getVeterinarios().size());
    }

    /** Valida que se obtenga la fecha en formato LocalDate. */
    @Test
    void getFechaLocalDate() {
        assertEquals(LocalDate.of(2025, 8, 20), cita.getFecha().toLocalDate());
    }

    /** Valida que se obtenga la hora en formato LocalTime. */
    @Test
    void getHoraLocalTime() {
        assertEquals(LocalTime.of(10, 30), cita.getFecha().toLocalTime());
    }

    /** Prueba actualizar fecha y hora. */
    @Test
    void setFechaHora() {
        LocalDateTime nueva = LocalDateTime.of(2025, 9, 1, 14, 0);
        cita.setFecha(nueva);
        assertEquals(nueva, cita.getFecha());
    }

    /** Verifica agregar propietario. */
    @Test
    void agregarPropietario() {
        cita.agregarPropietario(propietario);
        assertTrue(cita.getPropietarios().contains(propietario));
    }

    /** Verifica agregar mascota. */
    @Test
    void agregarMascota() {
        cita.agregarMascota(mascota);
        assertTrue(cita.getMascotas().contains(mascota));
    }

    /** Verifica agregar veterinario. */
    @Test
    void agregarVeterinario() {
        cita.agregarVeterinario(veterinario);
        assertTrue(cita.getVeterinarios().contains(veterinario));
    }

    /** Verifica agregar personal de apoyo. */
    @Test
    void agregarPersonalApoyo() {
        cita.agregarPersonalApoyo(personal);
        assertTrue(cita.getPersonalApoyo().contains(personal));
    }

    /** Prueba equals basado en ID. */
    @Test
    void testEquals() {
        Cita otra = new Cita("C1", cita.getFecha(), cita.getDescripcion());
        assertEquals(cita, otra);
    }

    /** Prueba hashCode consistente con equals. */
    @Test
    void testHashCode() {
        Cita otra = new Cita("C1", cita.getFecha(), cita.getDescripcion());
        assertEquals(cita.hashCode(), otra.hashCode());
    }



    @Test
    void getId() {
        assertEquals("C1", cita.getId());
    }

    @Test
    void getFecha() {
        assertEquals(LocalDateTime.of(2025, 8, 20, 10, 30), cita.getFecha());
    }

    @Test
    void getDescripcion() {
        assertEquals("Chequeo general", cita.getDescripcion());
    }

    @Test
    void getEstado() {
        assertEquals(EstadoCita.PROGRAMADA, cita.getEstado());
    }

    @Test
    void getPropietarios() {
        cita.agregarPropietario(propietario);
        assertNotNull(cita.getPropietarios());
    }

    @Test
    void getMascotas() {
        cita.agregarMascota(mascota);
        assertNotNull(cita.getMascotas());
    }

    @Test
    void getVeterinarios() {
        cita.agregarVeterinario(veterinario);
        assertNotNull(cita.getVeterinarios());
    }

    @Test
    void getPersonalApoyo() {
        cita.agregarPersonalApoyo(personal);
        assertNotNull(cita.getPersonalApoyo());
    }

    @Test
    void setId() {
        cita.setId("NEW-ID");
        assertEquals("NEW-ID", cita.getId());
    }

    @Test
    void setFecha() {
        LocalDateTime nueva = LocalDateTime.of(2025, 9, 10, 9, 0);
        cita.setFecha(nueva);
        assertEquals(nueva, cita.getFecha());
    }

    @Test
    void setDescripcion() {
        cita.setDescripcion("Nueva desc");
        assertEquals("Nueva desc", cita.getDescripcion());
    }

    @Test
    void setEstado() {
        cita.setEstado(EstadoCita.TERMINADA);
        assertEquals(EstadoCita.TERMINADA, cita.getEstado());
    }

    @Test
    void setPropietarios() {
        cita.setPropietarios(List.of(propietario));
        assertEquals(1, cita.getPropietarios().size());
    }

    @Test
    void setMascotas() {
        cita.setMascotas(List.of(mascota));
        assertEquals(1, cita.getMascotas().size());
    }

    @Test
    void setVeterinarios() {
        cita.setVeterinarios(List.of(veterinario));
        assertEquals(1, cita.getVeterinarios().size());
    }

    @Test
    void setPersonalApoyo() {
        cita.setPersonalApoyo(List.of(personal));
        assertEquals(1, cita.getPersonalApoyo().size());
    }
}
