public abstract class Poder {
    private final String nombre;

    protected Poder(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    /** Daño base que aporta este poder. La Defensa devuelve 0. */
    public abstract int danioBase();

    /** Aplica el efecto propio de este poder. Devuelve el daño total que hará. */
    public abstract int ejecutar(ContextoPoder ctx);

    /** ¿Se puede usar ahora mismo? Por defecto sí. El Especial lo sobreescribe. */
    public boolean disponible(EstadoCombate estado) { return true; }

    @Override
    public String toString() { return nombre; }
}

