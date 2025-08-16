package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedList;

/**
 * La clase Historial representa el historial clínico de una mascota.
 * Contiene una lista de consultas médicas realizadas a lo largo del tiempo.
 */
@Getter
@Setter
public class Historial {

    private LinkedList<Consulta> consultas;

    // Constructor
    public Historial() {
        this.consultas = new LinkedList<>();
    }

    // Método para agregar una consulta al historial
    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            consultas.add(consulta);
        }
    }

    @Override
    public String toString() {
        return "Historial{" +
                "totalConsultas=" + consultas.size() +
                '}';
    }
}
