public abstract class Poder {
    private final String nombre;

    protected Poder(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public abstract int danioBase();

    public abstract int ejecutar(ContextoPoder ctx);

    public boolean disponible(EstadoCombate estado) { return true; }

    @Override
    public String toString() { return nombre; }
}

