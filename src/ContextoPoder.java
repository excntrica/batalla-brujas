import java.util.List;

public class ContextoPoder {
    final Jugador activo;
    final Jugador rival;
    final EstadoCombate estadoActivo;
    final EstadoCombate estadoRival;
    final List<String> log;

    public ContextoPoder(Jugador activo, Jugador rival, EstadoCombate estadoActivo,
                         EstadoCombate estadoRival, List<String> log) {
        this.activo = activo;
        this.rival = rival;
        this.estadoActivo = estadoActivo;
        this.estadoRival = estadoRival;
        this.log = log;
    }
}
