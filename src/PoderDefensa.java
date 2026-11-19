public class PoderDefensa extends Poder {

    public PoderDefensa(String nombre) {
        super(nombre);
    }

    @Override
    public int danioBase() { return 0; }

    @Override
    public int ejecutar(ContextoPoder ctx) {
        if (ctx.estadoActivo.defensaBloqueada) {
            ctx.log.add(ctx.activo.getNombre() + " intenta " + getNombre()
                    + " pero las raíces la bloquean.");
            return 0;
        }
        ctx.estadoActivo.escudoActivo = true;
        ctx.estadoActivo.reduccionEscudo = 0.5;
        ctx.estadoActivo.escudoInflamable =
                CalculadoraDanio.esDefensaOrganica(ctx.activo.getAquelarre());
        ctx.log.add(ctx.activo.getNombre() + " usa " + getNombre() + ".");
        return 0;
    }
}
