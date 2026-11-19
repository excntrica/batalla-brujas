import java.util.List;

/** Todo lo visual: colores, barras de vida, paneles y pausas. */
public final class Consola {
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CIAN = "\u001B[36m";
    private static final String GRIS = "\u001B[90m";
    private static final String NEGRITA = "\u001B[1m";

    /** Color de cada jugador: posición 0 = primero en jugar, 1 = segundo. */
    private static final String[] COLORES_JUGADOR = {AZUL, MAGENTA};

    private static final String LINEA = "═".repeat(56);
    private static final String LINEA_FINA = "─".repeat(56);
    private static final int PAUSA_MS = 450;

    private Consola() {}

    /** @param indice 0 para el jugador 1, 1 para el jugador 2 o la CPU. */
    public static void encabezadoTurno(String nombre, int indice) {
        String c = COLORES_JUGADOR[indice % 2];
        System.out.println("\n\n" + c + NEGRITA + "╔" + LINEA + "╗" + RESET);
        System.out.println(c + NEGRITA + "   >> TURNO DE " + nombre.toUpperCase() + " <<" + RESET);
        System.out.println(c + NEGRITA + "╚" + LINEA + "╝" + RESET);
    }

    /** Barra de vida de 20 casillas, verde / amarilla / roja según los PV. */
    public static String barra(int pv, int max) {
        int llenos = Math.round(pv * 20f / max);
        String color = pv > max * 0.5 ? VERDE : (pv > max * 0.25 ? AMARILLO : ROJO);
        return color + "█".repeat(llenos) + GRIS + "░".repeat(20 - llenos) + RESET
                + " " + pv + "/" + max;
    }

    private static void filaJugador(String etiqueta, Jugador j, MotorCombate motor) {
        System.out.println(NEGRITA + etiqueta + j.getNombre() + RESET
                + GRIS + " (" + j.getAquelarre().getNombre() + ")" + RESET);
        System.out.println("   PV " + barra(j.getPv(), Jugador.PV_INICIALES));
        System.out.println("   " + CIAN + motor.estado(j).resumen() + RESET);
    }

    /** Marcador de combate: tú arriba, el rival debajo. */
    public static void mostrarPanel(Jugador activo, Jugador rival, MotorCombate motor) {
        System.out.println();
        filaJugador("TÚ    > ", activo, motor);
        System.out.println(GRIS + LINEA_FINA + RESET);
        filaJugador("RIVAL > ", rival, motor);
        System.out.println(GRIS + LINEA_FINA + RESET);
    }

    /** Las 5 cartas del turno, con lo bloqueado en gris. */
    public static void mostrarMano(Jugador j) {
        Aquelarre a = j.getAquelarre();
        System.out.println(NEGRITA + "\n  PODERES DE " + a.getBruja().toUpperCase() + RESET);
        carta(1, "Ataque  ", a.getAtaque(), j.getPoderBloqueado() == Poder.ATAQUE);
        carta(2, "Especial", a.getEspecial(), j.getPoderBloqueado() == Poder.ESPECIAL);
        carta(3, "Defensa ", a.getDefensa(), j.getPoderBloqueado() == Poder.DEFENSA);
        System.out.println(NEGRITA + "\n  GLIFOS SALVAJES" + RESET);
        List<Glifo> mano = j.getManoGlifos();
        for (int i = 0; i < mano.size(); i++) {
            Glifo g = mano.get(i);
            carta(4 + i, g.getNombre(), g.getDescripcion(), g == j.getGlifoBloqueado());
        }
        System.out.println(GRIS + LINEA_FINA + RESET);
    }

    private static void carta(int n, String tipo, String texto, boolean bloqueada) {
        if (bloqueada) {
            System.out.println(GRIS + "   [" + n + "] " + tipo + " │ " + texto + "  X BLOQUEADO" + RESET);
        } else {
            System.out.println("   " + AMARILLO + "[" + n + "]" + RESET + " " + tipo + " │ " + texto);
        }
    }

    public static void fase(String titulo) {
        System.out.println(CIAN + NEGRITA + "\n> " + titulo + RESET);
    }

    /** Imprime el resultado del turno con colores y una pequeña pausa entre líneas. */
    public static void imprimirLog(List<String> lineas) {
        System.out.println();
        for (String l : lineas) {
            String color = RESET;
            if (l.contains("CRÍTICO")) color = AMARILLO + NEGRITA;
            else if (l.contains("recibe") || l.contains("quemadura") || l.contains("quema")) color = ROJO;
            else if (l.contains("Recupera")) color = VERDE;
            else if (l.contains("esquiva") || l.contains("escudo") || l.contains("Escudo")) color = CIAN;
            else if (!l.startsWith(" ")) color = NEGRITA;
            System.out.println(color + l + RESET);
            pausa();
        }
    }

    public static void ganador(Jugador g) {
        System.out.println("\n" + AMARILLO + NEGRITA + "╔" + LINEA + "╗");
        System.out.println("   ¡" + g.getNombre().toUpperCase() + " GANA EL CINTURÓN DE CAMPEÓN!");
        System.out.println("   Termina la batalla con " + g.getPv() + " PV");
        System.out.println("╚" + LINEA + "╝" + RESET);
    }

    private static void pausa() {
        try { Thread.sleep(PAUSA_MS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

