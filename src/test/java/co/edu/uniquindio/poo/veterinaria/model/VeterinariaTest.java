package co.edu.uniquindio.poo.veterinaria.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para {@link Veterinaria}.
 * Valida CRUD y verificaciones básicas sobre propietarios, veterinarios y personal de apoyo.
 */
class VeterinariaTest {

    private Veterinaria vet;

    @BeforeEach
    void setUp() {
        vet = new Veterinaria("Canino", "cr40-42-16", "21212121", "232323");
    }

    /** Agregar propietario (alias agregarCliente) y evitar duplicados. */
    @Test
    void agregarCliente() {
        Propietario p = new Propietario("Laura", "111", "Calle 5", "3101111111", "laura@mail.com");
        assertTrue(vet.agregarCliente(p));
        // Duplicado por id
        assertFalse(vet.agregarCliente(new Propietario("Laura2", "111", "X", "Y", "Z")));
        assertTrue(vet.verificarPropietarios("111"));
    }

    /** Agregar veterinario por licencia y evitar duplicados. */
    @Test
    void agregarVeterinario() {
        Veterinario v = new Veterinario("Ana", "999", "Cra 1", "300", "a@mail.com", "V-001", Especialidad.HERBIVOROS);
        assertTrue(vet.agregarVeterinario(v));
        assertFalse(vet.agregarVeterinario(
                new Veterinario("Ana2", "998", "Cra 2", "301", "b@mail.com", "V-001", Especialidad.ANIMALES_PEQUENOS)));
        assertTrue(vet.existeVeterinarioPorLicencia("V-001"));
    }

    /** Agregar personal de apoyo y evitar duplicados por id. */
    @Test
    void agregarPersonalApoyo() {
        PersonalApoyo pa = new PersonalApoyo("Leo", "PA-1", "Dir", "Tel", "mail@mail.com", "Recep");
        assertTrue(vet.agregarPersonalApoyo(pa));
        assertFalse(vet.agregarPersonalApoyo(new PersonalApoyo("Otro", "PA-1", "X", "Y", "Z")));
        assertEquals(1, vet.getListaPersonalApoyo().size());
    }

    /** Verificar existencia de veterinario por licencia. */
    @Test
    void verificarVeterinario() {
        assertFalse(vet.existeVeterinarioPorLicencia("V-404"));
        vet.agregarVeterinario(new Veterinario("Ana", "999", "Cra 1", "300", "a@mail.com", "V-001", Especialidad.HERBIVOROS));
        assertTrue(vet.existeVeterinarioPorLicencia("V-001"));
    }

    /** Verificar existencia de personal de apoyo (por id) a través del listado. */
    @Test
    void verificarPersonalApoyo() {
        assertTrue(vet.getListaPersonalApoyo().isEmpty());
        vet.agregarPersonalApoyo(new PersonalApoyo("Leo", "PA-1", "Dir", "Tel", "mail@mail.com", "Recep"));
        boolean existe = vet.getListaPersonalApoyo().stream().anyMatch(pa -> "PA-1".equals(pa.getId()));
        assertTrue(existe);
    }

    /** Verificar propietarios (alias verificarPropietarios). */
    @Test
    void verificarPropietarios() {
        assertFalse(vet.verificarPropietarios("111"));
        vet.agregarCliente(new Propietario("Laura", "111", "Calle 5", "3101111111", "laura@mail.com"));
        assertTrue(vet.verificarPropietarios("111"));
    }

    /** Eliminar propietario por id (alias eliminarPropietario). */
    @Test
    void eliminarPropietario() {
        vet.agregarCliente(new Propietario("Laura", "111", "Calle 5", "3101111111", "laura@mail.com"));
        assertTrue(vet.eliminarPropietario("111"));
        assertFalse(vet.verificarPropietarios("111"));
    }

    /** Eliminar veterinario por licencia. */
    @Test
    void eliminarVeterinario() {
        vet.agregarVeterinario(new Veterinario("Ana", "999", "Cra 1", "300", "a@mail.com", "V-001", Especialidad.ANIMALES_PEQUENOS));
        assertTrue(vet.eliminarVeterinarioPorLicencia("V-001"));
        assertFalse(vet.existeVeterinarioPorLicencia("V-001"));
    }

    /** Eliminar personal de apoyo por id. */
    @Test
    void eliminarPersonalApoyo() {
        vet.agregarPersonalApoyo(new PersonalApoyo("Leo", "PA-1", "Dir", "Tel", "mail@mail.com", "Recep"));
        assertTrue(vet.eliminarPersonalApoyoPorId("PA-1"));
        assertTrue(vet.getListaPersonalApoyo().isEmpty());
    }

    /** Actualizar datos de personal de apoyo por id. */
    @Test
    void actualiazarPersonalApoyo() {
        vet.agregarPersonalApoyo(new PersonalApoyo("Leo", "PA-1", "Dir", "Tel", "mail@mail.com", "Recep"));
        PersonalApoyo cambios = new PersonalApoyo("Leonardo", "PA-1", "Nueva Dir", "999", "leo@mail.com", "Recep");
        assertTrue(vet.actualizarPersonalApoyo("PA-1", cambios));
        var pa = vet.getListaPersonalApoyo().stream().filter(x -> "PA-1".equals(x.getId())).findFirst().orElseThrow();
        assertEquals("Leonardo", pa.getNombre());
        assertEquals("Nueva Dir", pa.getDireccion());
        assertEquals("999", pa.getTelefono());
        assertEquals("leo@mail.com", pa.getGmail());
    }

    /** Actualizar veterinario por licencia (incluye cambio de licencia si no hay duplicados). */
    @Test
    void actualiazarVeterinario() {
        vet.agregarVeterinario(new Veterinario("Ana", "999", "Cra 1", "300", "a@mail.com", "V-001", Especialidad.ANIMALES_PEQUENOS));
        Veterinario cambios = new Veterinario("Ana María", "999", "Av 2", "301", "ana@mail.com", "V-002", Especialidad.HERBIVOROS);
        assertTrue(vet.actualizarVeterinarioPorLicencia("V-001", cambios));
        assertFalse(vet.existeVeterinarioPorLicencia("V-001"));
        assertTrue(vet.existeVeterinarioPorLicencia("V-002"));
        var v = vet.buscarVeterinarioPorLicencia("V-002").orElseThrow();
        assertEquals("Ana María", v.getNombre());
        assertEquals("Av 2", v.getDireccion());
        assertEquals("301", v.getTelefono());
        assertEquals(Especialidad.HERBIVOROS, v.getEspecialidad());
    }

    /** Actualizar propietario por id (alias actualiazarPropietario). */
    @Test
    void actualiazarPropietario() {
        vet.agregarCliente(new Propietario("Laura", "111", "Calle 5", "3101111111", "laura@mail.com"));
        Propietario cambios = new Propietario("Laura Gómez", "111", "Av 10", "3200000000", "lg@mail.com");
        assertTrue(vet.actualiazarPropietario("111", cambios));
        var p = vet.buscarPropietarioPorId("111").orElseThrow();
        assertEquals("Laura Gómez", p.getNombre());
        assertEquals("Av 10", p.getDireccion());
        assertEquals("3200000000", p.getTelefono());
        assertEquals("lg@mail.com", p.getGmail());
    }
}
