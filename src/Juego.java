import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private static final Scanner IN = new Scanner(System.in);
    private static final Random RND = new Random();

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   BATALLA DE BRUJAS - Las Islas Hirvientes");
        System.out.println("==================================================");

        int modo = leerEntero("Modo de juego:\n  1. Jugador vs CPU\n  2. Dos jugadores (mismo teclado)", 1, 2);

        MazoGlifos mazo = new MazoGlifos();
        boolean[] esCpu = new boolean[2];

        Jugador j1 = crearJugador("Jugador 1", mazo);
        Jugador j2;
        if (modo == 1) {
            Aquelarre[] todos = Aquelarre.values();
            Aquelarre elegido = todos[RND.nextInt(todos.length)];
            j2 = new Jugador(elegido.getBruja() + " (CPU)", elegido, mazo);
            esCpu[1] = true;
            System.out.println("\nLa CPU ha elegido el aquelarre: " + elegido.getNombre());
        } else {
            j2 = crearJugador("Jugador 2", mazo);
        }

        Jugador[] jugadores = {j1, j2};
        MotorCombate motor = new MotorCombate(j1, j2);

        int turno = 0;
        boolean fin = false;
        while (!fin) {
            int i = turno % 2;
            fin = jugarTurno(jugadores[i], jugadores[1 - i], motor, esCpu[i], modo == 2, i);
            turno++;
        }
        System.out.println("\nFin de la partida.");
    }


    private static Jugador crearJugador(String porDefecto, MazoGlifos mazo) {
        Aquelarre[] todos = Aquelarre.values();
        System.out.println("\n" + porDefecto + ", elige tu aquelarre:");
        for (int i = 0; i < todos.length; i++) {
            Aquelarre a = todos[i];
            System.out.printf("  %2d. %-13s -> juegas como %s (fuerte vs %s)%n",
                    i + 1, a.getNombre(), a.getBruja(), a.getVentaja().getNombre());
            System.out.printf("      Ataque: %s | Especial: %s | Defensa: %s%n",
                    a.getAtaque(), a.getEspecial(), a.getDefensa());
        }
        int op = leerEntero("Tu elección", 1, todos.length);
        Aquelarre elegido = todos[op - 1];
        System.out.println("¡Ahora eres " + elegido.getBruja() + "!");
        return new Jugador(elegido.getBruja(), elegido, mazo);
    }


    /** @return true si la partida terminó. */
    private static boolean jugarTurno(Jugador activo, Jugador rival, MotorCombate motor,
                                      boolean cpu, boolean pasarTeclado, int indice) {
        Consola.encabezadoTurno(activo.getNombre(), indice);

        if (pasarTeclado) {
            System.out.print("Pasa el teclado a " + activo.getNombre() + " y pulsa Enter...");
            IN.nextLine();
        }

        // Efectos al empezar el turno (energía, quemadura)
        List<String> inicio = motor.iniciarTurno(activo);
        Consola.imprimirLog(inicio);
        if (!activo.estaVivo()) {
            anunciarGanador(rival);
            return true;
        }

        Consola.mostrarPanel(activo, rival, motor);
        if (!cpu) Consola.mostrarMano(activo);

        if (cpu) System.out.println("La CPU piensa...");
        else faseRotacion(activo);

        Accion accion;
        while (true) {
            accion = cpu ? decidirCpu(activo, motor) : decidirHumano(activo, motor);
            String motivo = motor.motivoInvalido(activo, accion);
            if (motivo == null) break;
            System.out.println(motivo);
        }

        Glifo glifoUsado = accion.tieneGlifo() ? activo.getManoGlifos().get(accion.indiceGlifo()) : null;
        List<String> log = motor.ejecutarTurno(activo, rival, accion);
        Consola.imprimirLog(log);
        activo.registrarUso(accion.poder(), glifoUsado);

        if (!rival.estaVivo()) {
            anunciarGanador(activo);
            return true;
        }
        return false;
    }

    private static void faseRotacion(Jugador activo) {
        Consola.fase("FASE 1 · Rotación de glifos");
        int rot = leerEntero("  0 = no rotar | 4 = cambiar glifo 4 | 5 = cambiar glifo 5", 0, 5);
        while (rot == 1 || rot == 2 || rot == 3) {
            System.out.println("  Opción no válida: solo puedes rotar glifos (4 o 5) o 0 para saltar.");
            rot = leerEntero("  0 / 4 / 5", 0, 5);
        }
        if (rot != 0) {
            Glifo nuevo = activo.rotarGlifo(rot - 4);
            System.out.println("  Has robado: " + nuevo);
            Consola.mostrarMano(activo);
        }
    }

    private static Accion decidirHumano(Jugador activo, MotorCombate motor) {
        Consola.fase("FASE 2 · Acción de combate (energía: " + motor.estado(activo).energia + ")");
        int tipo = leerEntero("  1. Solo un poder\n  2. Solo un glifo\n  3. Combo Salvaje (cuesta 2 de energía)", 1, 3);

        Poder poder = null;
        int glifo = -1;

        if (tipo == 1 || tipo == 3) {
            while (true) {
                int p = leerEntero("  Elige poder (1 Ataque, 2 Especial, 3 Defensa)", 1, 3);
                poder = Poder.values()[p - 1];
                if (activo.puedeUsarPoder(poder)) break;
                System.out.println("  Ese poder está bloqueado este turno. Elige otro.");
            }
        }
        if (tipo == 2 || tipo == 3) {
            while (true) {
                int g = leerEntero("  Elige glifo (4 o 5)", 4, 5);
                glifo = g - 4;
                if (activo.puedeUsarGlifo(glifo)) break;
                System.out.println("  Ese glifo está bloqueado este turno. Elige otro.");
                if (!activo.puedeUsarGlifo(0) && !activo.puedeUsarGlifo(1)) {
                    System.out.println("  Ambos glifos están bloqueados: juega solo un poder.");
                    return new Accion(poder != null ? poder : pedirPoderLibre(activo), -1);
                }
            }
        }
        return new Accion(poder, glifo);
    }

    private static Poder pedirPoderLibre(Jugador activo) {
        while (true) {
            int p = leerEntero("  Elige poder (1 Ataque, 2 Especial, 3 Defensa)", 1, 3);
            Poder poder = Poder.values()[p - 1];
            if (activo.puedeUsarPoder(poder)) return poder;
            System.out.println("  Ese poder está bloqueado este turno. Elige otro.");
        }
    }

    private static Accion decidirCpu(Jugador activo, MotorCombate motor) {
        while (true) {
            Poder poder = Poder.values()[RND.nextInt(3)];
            int glifo = RND.nextInt(Jugador.GLIFOS_EN_MANO);
            Accion a = switch (RND.nextInt(3)) {
                case 0 -> Accion.soloPoder(poder);
                case 1 -> Accion.soloGlifo(glifo);
                default -> Accion.combo(poder, glifo);
            };
            boolean poderOk = !a.tienePoder() || activo.puedeUsarPoder(a.poder());
            boolean glifoOk = !a.tieneGlifo() || activo.puedeUsarGlifo(a.indiceGlifo());
            if (poderOk && glifoOk && motor.motivoInvalido(activo, a) == null) return a;
        }
    }

    private static void anunciarGanador(Jugador g) {
        Consola.ganador(g);
    }


    private static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.println(mensaje);
            System.out.print("> ");
            String linea = IN.nextLine().trim();
            try {
                int n = Integer.parseInt(linea);
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignorado) {
                // cae al mensaje de error
            }
            System.out.println("  Entrada no válida. Escribe un número entre " + min + " y " + max + ".");
        }
    }
}
