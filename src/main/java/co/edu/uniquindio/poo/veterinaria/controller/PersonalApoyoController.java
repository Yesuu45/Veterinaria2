package co.edu.uniquindio.poo.veterinaria.controller;

import co.edu.uniquindio.poo.veterinaria.model.PersonalApoyo;
import co.edu.uniquindio.poo.veterinaria.model.Veterinaria;

import java.util.Collection;

public class PersonalApoyoController {

    Veterinaria veterinaria;
    public PersonalApoyoController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }
    public boolean crearPersonalApoyo(PersonalApoyo personalApoyo) {
        return veterinaria.agregarPersonalApoyo(personalApoyo);
    }

    public boolean eliminarPersonalApoyo(String idPersonalApoyo){
        return veterinaria.eliminarPersonalApoyo(idPersonalApoyo);
    }

    public boolean actualizarPersonalApoyo(String idPersonalApoyo, PersonalApoyo personalApoyo){
        return veterinaria.actualiazarPersonalApoyo(idPersonalApoyo, personalApoyo);
    }



    public Collection<PersonalApoyo> obtenerPersonalApoyo() {
        return veterinaria.getPersonalesApoyo();
    }


}
