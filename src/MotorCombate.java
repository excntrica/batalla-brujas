import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorCombate {
    private static final int DANIO_QUEMADURA = 8;
    private static final int COSTE_COMBO = 2;
    private static final int ENERGIA_MAX = 5;

    private final Map<Jugador, EstadoCombate> estados = new HashMap<>();

    public MotorCombate(Jugador j1, Jugador j2) {
        estados.put(j1, new EstadoCombate());
        estados.put(j2, new EstadoCombate());
    }

    public EstadoCombate estado(Jugador j) { return estados.get(j); }

    public List<String> iniciarTurno(Jugador j) {
        List<String> log = new ArrayList<>();
        EstadoCombate e = estado(j);
        if (e.energia < ENERGIA_MAX) e.energia++;
        if (e.turnosQuemadura > 0) {
            j.recibirDanio(DANIO_QUEMADURA);
            e.turnosQuemadura--;
            log.add(j.getNombre() + " sufre " + DANIO_QUEMADURA + " de daño por quemadura.");
        }
        return log;
    }

    public String motivoInvalido(Jugador j, Accion a) {
        EstadoCombate e = estado(j);
        if (a.tienePoder() && a.tieneGlifo() && e.energia < COSTE_COMBO) {
            return "  Necesitas " + COSTE_COMBO + " de energía para un Combo (tienes " + e.energia + ").";
        }
        if (a.tienePoder() && !a.poder().disponible(e)) {
            return "  " + a.poder().getNombre() + " está en recarga " + e.recargaEspecial + " turno(s) más.";
        }
        return null;
    }

    public List<String> ejecutarTurno(Jugador activo, Jugador rival, Accion accion) {
        if (!accion.esValida()) throw new IllegalArgumentException("Acción vacía.");
        List<String> log = new ArrayList<>();
        EstadoCombate ea = estado(activo);
        EstadoCombate er = estado(rival);

        if (accion.tienePoder() && accion.tieneGlifo()) ea.energia -= COSTE_COMBO;

        Glifo glifo = null;
        if (accion.tieneGlifo()) {
            glifo = activo.usarGlifo(accion.indiceGlifo());
            if (glifo == null) throw new IllegalArgumentException("Índice de glifo inválido.");
        }

        int danioBase = 0;
        boolean ataqueFallido = false;
        boolean defensaJugada = false;

        // --- Poder del aquelarre (polimorfismo: el poder sabe ejecutarse) ---
        if (accion.tienePoder()) {
            ContextoPoder ctx = new ContextoPoder(activo, rival, ea, er, log);
            int resultado = accion.poder().ejecutar(ctx);
            if (resultado == -1) {
                ataqueFallido = true;
            } else {
                danioBase += resultado;
            }
            defensaJugada = ea.escudoActivo;
        }

        boolean usoFuego = false;
        if (glifo != null) {
            log.add(activo.getNombre() + " activa el " + glifo + ".");
            switch (glifo) {
                case FUEGO -> {
                    usoFuego = true;
                    danioBase += 20;
                    er.turnosQuemadura = 3;
                    log.add("  " + rival.getNombre() + " arderá 3 turnos.");
                }
                case HIELO -> {
                    danioBase += 15;
                    er.congelado = true;
                    log.add("  " + rival.getNombre() + " se ralentiza: su próximo ataque hará la mitad.");
                    if (defensaJugada) {
                        ea.reduccionEscudo = 0.7;
                        ea.escudoInflamable = false;
                        log.add("  Se forma una barricada de hielo (escudo reforzado).");
                    }
                }
                case PLANTA -> {
                    danioBase += 12;
                    er.defensaBloqueada = true;
                    log.add("  " + rival.getNombre() + " no podrá usar Defensa el próximo turno.");
                }
                case LUZ -> {
                    ea.esquivaProximo = true;
                    log.add("  " + rival.getNombre() + " quedará cegado: " + activo.getNombre()
                            + " esquivará el próximo ataque.");
                }
            }
        }

        if (ataqueFallido && danioBase == 0) {
            er.escudoActivo = false;
            er.escudoInflamable = false;
        }

        if (danioBase > 0) {
            boolean critico = CalculadoraDanio.esCritico(activo.getAquelarre(), rival.getAquelarre());
            int danio = CalculadoraDanio.calcular(danioBase, activo.getAquelarre(),
                    rival.getAquelarre(), ea);
            if (critico) log.add("  ¡DAÑO CRÍTICO por ventaja de aquelarre!");
            ea.congelado = false; // la ralentización se consume al atacar

            if (er.esquivaProximo) {
                er.esquivaProximo = false;
                danio = 0;
                log.add("  " + rival.getNombre() + " esquiva el golpe cegando al atacante.");
            } else if (er.escudoActivo) {
                if (usoFuego && er.escudoInflamable) {
                    log.add("  ¡El fuego quema la defensa de " + rival.getNombre() + "!");
                } else {
                    danio = (int) Math.round(danio * (1 - er.reduccionEscudo));
                    log.add("  El escudo absorbe parte del daño.");
                }
                er.escudoActivo = false;
                er.escudoInflamable = false;
            }

            rival.recibirDanio(danio);
            log.add("  " + rival.getNombre() + " recibe " + danio + " de daño (PV: " + rival.getPv() + ").");
        }

        if (ea.turnosDebilitado > 0) ea.turnosDebilitado--;
        if (ea.recargaEspecial > 0) ea.recargaEspecial--;
        ea.defensaBloqueada = false; // el bloqueo solo dura su turno
        if (glifo != null) activo.rellenarMano();
        return log;
    }
}
