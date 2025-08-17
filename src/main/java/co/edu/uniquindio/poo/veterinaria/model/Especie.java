package co.edu.uniquindio.poo.veterinaria.model;

public enum Especie {
    PERRO("Perro"),
    GATO("Gato"),
    AVE("Ave"),
    PEZ("Pez"),
    REPTIL("Reptil"),
    ROEDOR("Roedor"),
    OTRO("Otro");

    private final String nombre;

    Especie(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
