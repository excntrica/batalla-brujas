public enum Glifo {
    FUEGO("Fuego", "Añade daño extra y quemadura. Quema defensas físicas orgánicas y de hielo."),
    HIELO("Hielo", "Congela al rival (lo ralentiza) o crea barricadas."),
    PLANTA("Planta", "Atrapa al rival y bloquea su carta de defensa el próximo turno."),
    LUZ("Luz", "Ciega o aturde al rival para esquivar el próximo ataque.");

    private final String nombre;
    private final String descripcion;

    Glifo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return "Glifo de " + nombre;
    }
}
