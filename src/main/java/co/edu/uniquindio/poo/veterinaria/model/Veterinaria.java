package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
public class Veterinaria {

    /** Datos básicos de la clínica. */
    private String nombre;
    private String direccion;
    private String telefono;
    private String rut;

    /** Colecciones internas (mutables) no expuestas directamente. */
    private final List<Veterinario> veterinarios = new LinkedList<>();
    private final List<PersonalApoyo> personalesApoyo = new LinkedList<>();
    private final List<Propietario> propietarios = new LinkedList<>();
    private final List<Mascota> mascotas = new LinkedList<>();
    private final List<Cita> citas = new LinkedList<>();
    private final List<Consulta> consultas = new LinkedList<>();

    /**
     * Crea la clínica con sus datos básicos.
     * SOLID: SRP.
     */
    public Veterinaria(String nombre, String direccion, String telefono, String rut) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rut = rut;
    }

    /* =========================
       Getters compatibles UI (inmutables)
       ========================= */

    /** Listas de solo lectura para UI. SOLID: ISP. */
    public List<Cita> getListaCitas() { return Collections.unmodifiableList(citas); }
    public List<Propietario> getListaPropietarios() { return Collections.unmodifiableList(propietarios); }
    public List<Veterinario> getListaVeterinarios() { return Collections.unmodifiableList(veterinarios); }
    public List<PersonalApoyo> getListaPersonalApoyo() { return Collections.unmodifiableList(personalesApoyo); }
    public List<Mascota> getListaMascotas() { return Collections.unmodifiableList(mascotas); }
    public List<Consulta> getListaConsultas() { return Collections.unmodifiableList(consultas); }

    /* =========================
       Búsquedas / verificaciones
       ========================= */

    /** Busca propietario por id. SOLID: SRP. */
    public Optional<Propietario> buscarPropietarioPorId(String id) {
        if (id == null) return Optional.empty();
        return propietarios.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
    }

    /** Existe propietario por id. SOLID: ISP. */
    public boolean existePropietarioPorId(String id) {
        return buscarPropietarioPorId(id).isPresent();
    }

    /** Busca veterinario por licencia. SOLID: SRP. */
    public Optional<Veterinario> buscarVeterinarioPorLicencia(String licencia) {
        if (licencia == null) return Optional.empty();
        return veterinarios.stream().filter(v -> Objects.equals(v.getNumLicencia(), licencia)).findFirst();
    }

    /** Existe veterinario por licencia. SOLID: ISP. */
    public boolean existeVeterinarioPorLicencia(String licencia) {
        return buscarVeterinarioPorLicencia(licencia).isPresent();
    }

    /** Busca mascota por id veterinaria. SOLID: SRP. */
    public Optional<Mascota> buscarMascotaPorIdVet(String idVeterinaria) {
        if (idVeterinaria == null) return Optional.empty();
        return mascotas.stream().filter(m -> Objects.equals(m.getIdVeterinaria(), idVeterinaria)).findFirst();
    }

    /** Existe mascota por id veterinaria. SOLID: ISP. */
    public boolean existeMascotaPorIdVet(String idVeterinaria) {
        return buscarMascotaPorIdVet(idVeterinaria).isPresent();
    }

    /** Busca cita por id. SOLID: SRP. */
    public Optional<Cita> buscarCitaPorId(String id) {
        if (id == null) return Optional.empty();
        return citas.stream().filter(c -> Objects.equals(c.getId(), id)).findFirst();
    }

    /** Existe cita por id. SOLID: ISP. */
    public boolean existeCitaPorId(String id) {
        return buscarCitaPorId(id).isPresent();
    }

    /* =========================
       Propietarios
       ========================= */

    /**
     * Agrega propietario (único por id). Sincroniza mascotas que traiga.
     * SOLID: SRP, OCP.
     */
    public boolean agregarPropietario(Propietario propietario) {
        if (propietario == null || propietario.getId() == null) return false;
        if (existePropietarioPorId(propietario.getId())) return false;

        propietarios.add(propietario);

        // Si ya trae mascotas, sincronizarlas
        if (propietario.getMascotas() != null) {
            for (Mascota m : propietario.getMascotas()) {
                if (m == null) continue;
                m.setPropietario(propietario);
                if (!existeMascotaPorIdVet(m.getIdVeterinaria())) {
                    mascotas.add(m);
                }
            }
        }
        return true;
    }

    /** Alias (compatibilidad UI). SOLID: ISP. */
    public boolean agregarCliente(Propietario propietario) {
        return agregarPropietario(propietario);
    }

    /**
     * Actualiza datos de propietario y sincroniza mascotas si se envían.
     * SOLID: SRP.
     */
    public boolean actualizarPropietario(String id, Propietario datos) {
        if (id == null || datos == null) return false;
        for (Propietario p : propietarios) {
            if (Objects.equals(p.getId(), id)) {
                p.setNombre(datos.getNombre());
                p.setDireccion(datos.getDireccion());
                p.setTelefono(datos.getTelefono());
                p.setGmail(datos.getGmail());
                if (datos.getMascotas() != null) {
                    for (Mascota m : datos.getMascotas()) {
                        if (m == null) continue;
                        m.setPropietario(p);
                        if (!existeMascotaPorIdVet(m.getIdVeterinaria())) {
                            mascotas.add(m);
                        }
                    }
                    p.setMascotas(datos.getMascotas());
                }
                return true;
            }
        }
        return false;
    }

    /** Alias con typo (compatibilidad). SOLID: ISP. */
    public boolean actualiazarPropietario(String id, Propietario datos) {
        return actualizarPropietario(id, datos);
    }

    /** Elimina propietario por id (sin cascada agresiva). SOLID: SRP. */
    public boolean eliminarPropietarioPorId(String id) {
        return propietarios.removeIf(p -> Objects.equals(p.getId(), id));
    }

    /** Alias (compatibilidad). SOLID: ISP. */
    public boolean eliminarPropietario(String id) {
        return eliminarPropietarioPorId(id);
    }

    /** Verifica existencia de propietario. SOLID: ISP. */
    public boolean verificarPropietarios(String id) {
        return existePropietarioPorId(id);
    }

    /* =========================
       Personal de apoyo
       ========================= */

    /** Agrega personal de apoyo (único por id). SOLID: SRP. */
    public boolean agregarPersonalApoyo(PersonalApoyo pa) {
        if (pa == null || pa.getId() == null) return false;
        if (personalesApoyo.stream().anyMatch(x -> Objects.equals(x.getId(), pa.getId()))) return false;
        personalesApoyo.add(pa);
        return true;
    }

    /** Actualiza datos de personal de apoyo. SOLID: SRP. */
    public boolean actualizarPersonalApoyo(String id, PersonalApoyo datos) {
        if (id == null || datos == null) return false;
        for (PersonalApoyo p : personalesApoyo) {
            if (Objects.equals(p.getId(), id)) {
                p.setNombre(datos.getNombre());
                p.setDireccion(datos.getDireccion());
                p.setTelefono(datos.getTelefono());
                p.setGmail(datos.getGmail());
                // Nota: 'cargo' se gestiona en la clase PersonalApoyo; aquí no se fuerza cambio.
                return true;
            }
        }
        return false;
    }

    /** Elimina personal de apoyo por id. SOLID: SRP. */
    public boolean eliminarPersonalApoyoPorId(String id) {
        return personalesApoyo.removeIf(p -> Objects.equals(p.getId(), id));
    }

    /* =========================
       Veterinarios
       ========================= */

    /** Agrega veterinario (único por licencia). SOLID: SRP. */
    public boolean agregarVeterinario(Veterinario v) {
        if (v == null || v.getNumLicencia() == null) return false;
        if (existeVeterinarioPorLicencia(v.getNumLicencia())) return false;
        veterinarios.add(v);
        return true;
    }

    /** Actualiza datos/licencia/especialidad de un veterinario. SOLID: SRP. */
    public boolean actualizarVeterinarioPorLicencia(String licenciaActual, Veterinario datos) {
        if (licenciaActual == null || datos == null) return false;
        for (Veterinario v : veterinarios) {
            if (Objects.equals(v.getNumLicencia(), licenciaActual)) {
                if (datos.getNumLicencia() != null &&
                        !Objects.equals(licenciaActual, datos.getNumLicencia()) &&
                        existeVeterinarioPorLicencia(datos.getNumLicencia())) {
                    return false;
                }
                v.setNombre(datos.getNombre());
                v.setDireccion(datos.getDireccion());
                v.setTelefono(datos.getTelefono());
                v.setGmail(datos.getGmail());
                if (datos.getNumLicencia() != null) v.setNumLicencia(datos.getNumLicencia());
                if (datos.getEspecialidad() != null) v.setEspecialidad(datos.getEspecialidad());
                return true;
            }
        }
        return false;
    }

    /** Elimina veterinario por licencia. SOLID: SRP. */
    public boolean eliminarVeterinarioPorLicencia(String licencia) {
        return veterinarios.removeIf(v -> Objects.equals(v.getNumLicencia(), licencia));
    }

    /* =========================
       Mascotas
       ========================= */

    /** Busca mascotas por id de propietario. SOLID: SRP. */
    public List<Mascota> buscarMascotasPorPropietarioId(String propietarioId) {
        if (propietarioId == null) return List.of();
        return mascotas.stream()
                .filter(m -> m.getPropietario() != null &&
                        Objects.equals(m.getPropietario().getId(), propietarioId))
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * Agrega mascota (única por idVeterinaria). Valida propietario existente y enlaza ambos lados.
     * SOLID: SRP, OCP.
     */
    public boolean agregarMascota(Mascota mascota) {
        if (mascota == null || mascota.getIdVeterinaria() == null) return false;
        if (existeMascotaPorIdVet(mascota.getIdVeterinaria())) return false;

        Propietario p = mascota.getPropietario();
        if (p == null) return false;

        Optional<Propietario> propSys = buscarPropietarioPorId(p.getId());
        if (propSys.isEmpty()) return false;

        mascota.setPropietario(propSys.get());
        if (!propSys.get().getMascotas().contains(mascota)) {
            propSys.get().getMascotas().add(mascota);
        }
        mascotas.add(mascota);
        return true;
    }

    /**
     * Actualiza mascota, cuidando unicidad del id y posible cambio de propietario.
     * SOLID: SRP.
     */
    public boolean actualizarMascota(String idVeterinariaActual, Mascota datos) {
        if (idVeterinariaActual == null || datos == null) return false;

        Optional<Mascota> actualOpt = buscarMascotaPorIdVet(idVeterinariaActual);
        if (actualOpt.isEmpty()) return false;

        Mascota actual = actualOpt.get();

        if (datos.getIdVeterinaria() != null &&
                !Objects.equals(datos.getIdVeterinaria(), actual.getIdVeterinaria()) &&
                existeMascotaPorIdVet(datos.getIdVeterinaria())) {
            return false;
        }

        Propietario nuevoProp = datos.getPropietario();
        if (nuevoProp != null && (actual.getPropietario() == null ||
                !Objects.equals(nuevoProp.getId(), actual.getPropietario().getId()))) {

            Optional<Propietario> propSys = buscarPropietarioPorId(nuevoProp.getId());
            if (propSys.isEmpty()) return false;

            if (actual.getPropietario() != null && actual.getPropietario().getMascotas() != null) {
                actual.getPropietario().getMascotas()
                        .removeIf(m -> Objects.equals(m.getIdVeterinaria(), actual.getIdVeterinaria()));
            }

            actual.setPropietario(propSys.get());
            if (!propSys.get().getMascotas().contains(actual)) {
                propSys.get().getMascotas().add(actual);
            }
        }

        if (datos.getIdVeterinaria() != null) actual.setIdVeterinaria(datos.getIdVeterinaria());
        if (datos.getNombreMascota() != null) actual.setNombreMascota(datos.getNombreMascota());
        if (datos.getEspecie() != null) actual.setEspecie(datos.getEspecie());
        if (datos.getRaza() != null) actual.setRaza(datos.getRaza());
        if (datos.getEdad() >= 0) actual.setEdad(datos.getEdad());

        return true;
    }

    /**
     * Elimina mascota por id veterinaria, quitándola también del propietario.
     * SOLID: SRP.
     */
    public boolean eliminarMascotaPorIdVet(String idVeterinaria) {
        Optional<Mascota> mOpt = buscarMascotaPorIdVet(idVeterinaria);
        if (mOpt.isEmpty()) return false;

        Mascota m = mOpt.get();

        if (m.getPropietario() != null && m.getPropietario().getMascotas() != null) {
            m.getPropietario().getMascotas()
                    .removeIf(mx -> Objects.equals(mx.getIdVeterinaria(), idVeterinaria));
        }
        return mascotas.removeIf(mx -> Objects.equals(mx.getIdVeterinaria(), idVeterinaria));
    }

    /* =========================
       Citas
       ========================= */

    /** Citas de un día (ordenadas por hora). SOLID: SRP. */
    public List<Cita> buscarCitasPorDia(LocalDate fecha) {
        if (fecha == null) return List.of();
        return citas.stream()
                .filter(c -> c.getFecha() != null && c.getFecha().toLocalDate().equals(fecha))
                .sorted(Comparator.comparing(Cita::getFecha))
                .collect(Collectors.toUnmodifiableList());
    }

    /** Chequea choque de agenda para el primer veterinario de la cita. SOLID: SRP. */
    private boolean hayChoque(Cita nueva) {
        Veterinario vNueva = (nueva.getVeterinarios().isEmpty() ? null : nueva.getVeterinarios().get(0));
        if (vNueva == null || nueva.getFecha() == null) return false;

        return citas.stream().anyMatch(c -> {
            Veterinario v = (c.getVeterinarios().isEmpty() ? null : c.getVeterinarios().get(0));
            return v != null && v.equals(vNueva) && Objects.equals(c.getFecha(), nueva.getFecha());
        });
    }

    /**
     * Agenda una cita validando 1:1 en Cita y existencia de entidades.
     * SOLID: SRP, ISP.
     */
    public boolean agendarCita(Cita nueva) {
        if (nueva == null || nueva.getFecha() == null) return false;

        if (nueva.getPropietarios().size() != 1 ||
                nueva.getMascotas().size() != 1 ||
                nueva.getVeterinarios().size() != 1) {
            return false;
        }

        Mascota mascota = nueva.getMascotas().get(0);
        Veterinario vet = nueva.getVeterinarios().get(0);
        Propietario prop = nueva.getPropietarios().get(0);

        if (!mascotas.contains(mascota)) return false;
        if (!veterinarios.contains(vet)) return false;
        if (!propietarios.contains(prop)) return false;

        if (hayChoque(nueva)) return false;

        citas.add(nueva);
        return true;
    }

    /** Elimina cita por id. SOLID: SRP. */
    public boolean eliminarCita(String idCita) {
        if (idCita == null) return false;
        return citas.removeIf(c -> Objects.equals(c.getId(), idCita));
    }

    /* =========================
       Consultas / Historial
       ========================= */

    /** ¿Existe consulta para una cita? (1:1). SOLID: SRP. */
    public boolean existeConsultaPorCitaId(String citaId) {
        if (citaId == null) return false;
        return consultas.stream().anyMatch(con ->
                con.getCita() != null &&
                        con.getCita().getId() != null &&
                        con.getCita().getId().equals(citaId)
        );
    }

    /**
     * Registra consulta si la cita existe y no hay otra para la misma.
     * También intenta agregarla al historial de la primera mascota de la cita.
     * SOLID: SRP, OCP.
     */
    public boolean registrarConsulta(Consulta consulta) {
        if (consulta == null || consulta.getCita() == null || consulta.getCita().getId() == null) return false;

        boolean citaExiste = citas.stream().anyMatch(c -> Objects.equals(c.getId(), consulta.getCita().getId()));
        if (!citaExiste) return false;

        if (existeConsultaPorCitaId(consulta.getCita().getId())) return false;

        consultas.add(consulta);

        if (consulta.getCita().getMascotas() != null && !consulta.getCita().getMascotas().isEmpty()) {
            Mascota m = consulta.getCita().getMascotas().get(0);
            try {
                if (m.getHistorial() != null) {
                    m.getHistorial().agregarConsulta(consulta);
                }
            } catch (Exception ignored) {}
        }

        return true;
    }

    /** Historial (consultas) por mascota. SOLID: SRP. */
    public List<Consulta> historialPorMascota(Mascota mascota) {
        if (mascota == null) return List.of();
        return consultas.stream()
                .filter(con -> con.getCita() != null &&
                        con.getCita().getMascotas() != null &&
                        !con.getCita().getMascotas().isEmpty() &&
                        mascota.equals(con.getCita().getMascotas().get(0)))
                .sorted(Comparator.comparing(c -> c.getCita().getFecha()))
                .collect(Collectors.toUnmodifiableList());
    }

    /** Consultas por día (fecha de la cita). SOLID: SRP. */
    public List<Consulta> consultasPorDia(LocalDate dia) {
        if (dia == null) return List.of();
        return consultas.stream()
                .filter(con -> con.getCita() != null &&
                        con.getCita().getFecha() != null &&
                        con.getCita().getFecha().toLocalDate().equals(dia))
                .sorted(Comparator.comparing(c -> c.getCita().getFecha()))
                .collect(Collectors.toUnmodifiableList());
    }

    /** Elimina la consulta asociada a una cita. SOLID: SRP. */
    public boolean eliminarConsultaPorCitaId(String citaId) {
        if (citaId == null || citaId.isBlank()) return false;
        return consultas.removeIf(con ->
                con.getCita() != null &&
                        con.getCita().getId() != null &&
                        con.getCita().getId().equals(citaId)
        );
    }

    /** Elimina una consulta específica. SOLID: SRP. */
    public boolean eliminarConsulta(Consulta consulta) {
        if (consulta == null) return false;
        return consultas.remove(consulta);
    }
}
