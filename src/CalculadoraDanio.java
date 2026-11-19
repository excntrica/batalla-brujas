public final class CalculadoraDanio {
    public static final double MULT_CRITICO = 1.5;
    public static final double MULT_CONGELADO = 0.5;
    public static final double MULT_DEBILITADO = 0.7;

    private CalculadoraDanio() {}

    public static boolean esCritico(Aquelarre atacante, Aquelarre defensor) {
        return atacante.tieneVentajaSobre(defensor);
    }

    public static int calcular(int base, Aquelarre atacante, Aquelarre defensor,
                               EstadoCombate estadoAtacante) {
        double d = base;
        if (esCritico(atacante, defensor)) d *= MULT_CRITICO;
        if (estadoAtacante.congelado) d *= MULT_CONGELADO;
        if (estadoAtacante.turnosDebilitado > 0) d *= MULT_DEBILITADO;
        return (int) Math.round(d);
    }

    /** Aquelarres cuyas barreras son orgánicas y arden con Fuego. */
    public static boolean esDefensaOrganica(Aquelarre a) {
        return a == Aquelarre.PLANTAS || a == Aquelarre.BESTIAS || a == Aquelarre.ABOMINACIONES;
    }
}

