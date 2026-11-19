public class PoderAtaque extends Poder {
    private static final int DANIO = 35;

    public PoderAtaque(String nombre) {
        super(nombre);
    }

    @Override
    public int danioBase() { return DANIO; }

    @Override
    public int ejecutar(ContextoPoder ctx) {
        ctx.log.add(ctx.activo.getNombre() + " usa " + getNombre() + ".");
        if (ctx.estadoRival.escudoActivo) {
            ctx.log.add("  ¡El ataque falla! " + ctx.rival.getNombre() + " estaba a la defensa.");
            return -1; // código especial: el ataque falló
        }
        return DANIO;
    }
}
