package co.edu.uniquindio.poo.veterinaria.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

@Getter
@Setter
public class Consulta {

    @NonNull
    private Cita cita;


    private String diagnostico;


    private Tratamiento tratamiento;


    private String notasAdicionales;


    //  Constructor
    public Consulta(@NonNull Cita cita,
                    String diagnostico,
                    Tratamiento tratamiento,
                    String notasAdicionales) {
        this.cita = cita;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.notasAdicionales = notasAdicionales;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "cita=" + (cita != null ? cita.getId() : "Sin cita") +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento=" + (tratamiento != null ? tratamiento.toString() : "Sin tratamiento") +
                ", notasAdicionales='" + notasAdicionales + '\'' +
                '}';
    }
}
