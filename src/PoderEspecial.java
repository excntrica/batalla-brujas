public class PoderEspecial extends Poder {
    private static final int DANIO = 30;
    private static final int CURA = 40;
    private static final int RECARGA = 2;

    private final boolean cura;
    private final boolean bloqueaDefensa;

    /**
     * @param cura true si este especial cura en vez de dañar (Aquelarre de Curación).
     * @param bloqueaDefensa true si el objetivo no podrá defenderse (Sello Restrictivo).
     */
    public PoderEspecial(String nombre, boolean cura, boolean bloqueaDefensa) {
        super(nombre);
        this.cura = cura;
        this.bloqueaDefensa = bloqueaDefensa;
    }

    @Override
    public int danioBase() { return cura ? 0 : DANIO; }

    @Override
    public boolean disponible(EstadoCombate estado) {
        return estado.recargaEspecial == 0;
    }

    @Override
    public int ejecutar(ContextoPoder ctx) {
        ctx.log.add(ctx.activo.getNombre() + " usa " + getNombre() + ".");
        ctx.estadoActivo.recargaEspecial = RECARGA + 1; // se descuenta al final del turno

        if (cura) {
            ctx.activo.curar(CURA);
            ctx.log.add("  Recupera " + CURA + " PV.");
            return 0;
        }
        if (bloqueaDefensa) {
            ctx.estadoRival.defensaBloqueada = true;
            ctx.log.add("  " + ctx.rival.getNombre() + " queda sellado: no podrá defenderse.");
        } else {
            ctx.estadoRival.turnosDebilitado = 2;
            ctx.log.add("  " + ctx.rival.getNombre() + " queda debilitado 2 turnos.");
        }
        return DANIO;
    }
}
