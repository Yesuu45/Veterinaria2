package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.Mascota;
import co.edu.uniquindio.poo.veterinaria.model.Propietario;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


public class ClienteController {

    /** Agregado de dominio contra el que opera el controlador. */
    private final Veterinaria veterinaria;

    /**
     * Crea el controlador.
     * @param veterinaria agregado de dominio (no null)
     * SOLID: DIP.
     */
    public ClienteController(Veterinaria veterinaria) {
        this.veterinaria = Objects.requireNonNull(veterinaria, "Veterinaria no puede ser null");
    }

    /* =========================
       Creación
       ========================= */

    /**
     * Crea un propietario sin mascotas (pueden agregarse luego).
     * @param propietario nuevo propietario (no null)
     * @return true si se creó
     * @throws NullPointerException si propietario es null
     * SOLID: SRP (orquesta), DIP (delegación al dominio).
     */
    public boolean crearPropietario(Propietario propietario) {
        Objects.requireNonNull(propietario, "Propietario no puede ser null");
        return veterinaria.agregarCliente(propietario);
    }

    /**
     * Crea un propietario y asocia una mascota (si se envía).
     * @param propietario propietario (no null)
     * @param mascota mascota opcional (se asigna al propietario)
     * @return true si se creó
     * @throws NullPointerException si propietario es null
     * SOLID: SRP, DIP.
     */
    public boolean crearPropietarioConMascota(Propietario propietario, Mascota mascota) {
        Objects.requireNonNull(propietario, "Propietario no puede ser null");
        if (mascota != null) {
            mascota.setPropietario(propietario);
            propietario.agregarMascota(mascota);
        }
        return veterinaria.agregarCliente(propietario);
    }

    /**
     * Agrega una mascota a un propietario existente y la registra globalmente si falta.
     * @param idPropietario id del propietario
     * @param mascota mascota a agregar (no null)
     * @return true si se agregó
     * @throws NullPointerException si la mascota es null
     * SOLID: SRP, DIP.
     */
    public boolean agregarMascotaAPropietario(String idPropietario, Mascota mascota) {
        Objects.requireNonNull(mascota, "La mascota no puede ser null");
        Optional<Propietario> propOpt = buscarPropietarioPorId(idPropietario);
        if (propOpt.isEmpty()) return false;

        Propietario p = propOpt.get();
        mascota.setPropietario(p);
        if (!p.getMascotas().contains(mascota)) {
            p.getMascotas().add(mascota);
        }
        if (!veterinaria.getListaMascotas().contains(mascota)) {
            veterinaria.getListaMascotas().add(mascota);
        }
        return true;
    }

    /* =========================
       Eliminación / Actualización
       ========================= */

    /**
     * Elimina un propietario por id (delegado al dominio).
     * @param idPropietario id del propietario
     * @return true si se eliminó
     * SOLID: SRP, DIP.
     */
    public boolean eliminarPropietario(String idPropietario){
        return veterinaria.eliminarPropietario(idPropietario);
    }

    /**
     * Actualiza datos del propietario (usa el método del dominio).
     * @param idPropietario id del propietario a actualizar
     * @param propietarioActualizado datos nuevos
     * @return true si se actualizó
     * SOLID: SRP, DIP.
     */
    public boolean actualizarPropietario(String idPropietario, Propietario propietarioActualizado){
        return veterinaria.actualiazarPropietario(idPropietario, propietarioActualizado);
        // Nota: en el modelo el método se llama "actualiazarPropietario" (con i extra).
    }

    /* =========================
       Lecturas
       ========================= */

    /** Propietarios de solo lectura para UI. SOLID: ISP. */
    public Collection<Propietario> obtenerPropietarios() {
        return veterinaria.getListaPropietarios();
    }

    /** Mascotas de solo lectura para UI. SOLID: ISP. */
    public Collection<Mascota> obtenerMascotas() {
        return veterinaria.getListaMascotas();
    }

    /**
     * Mascotas del propietario indicado (o lista vacía).
     * @param idPropietario id del propietario
     * @return colección de mascotas (posible vacía)
     * SOLID: ISP.
     */
    public Collection<Mascota> obtenerMascotasDePropietario(String idPropietario) {
        return buscarPropietarioPorId(idPropietario)
                .map(Propietario::getMascotas)
                .orElseGet(java.util.LinkedList::new);
    }

    /** Verifica existencia de propietario por id. SOLID: ISP. */
    public boolean propietarioExiste(String idPropietario) {
        return veterinaria.verificarPropietarios(idPropietario);
    }

    /**
     * Busca propietario por id en la lista de solo lectura.
     * @param idPropietario id a buscar
     * @return propietario o vacío
     * SOLID: SRP.
     */
    public Optional<Propietario> buscarPropietarioPorId(String idPropietario) {
        if (idPropietario == null) return Optional.empty();
        return veterinaria.getListaPropietarios().stream()
                .filter(p -> idPropietario.equals(p.getId()))
                .findFirst();
    }
}
