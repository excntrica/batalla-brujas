public class EstadoCombate {
    int turnosQuemadura = 0;          // daño al inicio de su turno
    int turnosDebilitado = 0;         // su daño se reduce
    boolean congelado = false;        // su próximo ataque hace la mitad
    boolean defensaBloqueada = false; // no puede usar Defensa en su próximo turno
    boolean esquivaProximo = false;   // esquiva el próximo ataque (Luz)
    boolean escudoActivo = false;     // absorbe parte del próximo golpe
    double reduccionEscudo = 0.5;
    boolean escudoInflamable = false; // el Fuego lo destruye
    int energia = 0;                  // energía para Combos
    int recargaEspecial = 0;          // turnos en que el Especial no está disponible

    public String resumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Energía ").append(energia).append("] ");
        if (recargaEspecial > 0) sb.append("[Especial en recarga ").append(recargaEspecial).append("] ");
        if (turnosQuemadura > 0) sb.append("[Quemado ").append(turnosQuemadura).append("] ");
        if (turnosDebilitado > 0) sb.append("[Debilitado ").append(turnosDebilitado).append("] ");
        if (congelado) sb.append("[Congelado] ");
        if (defensaBloqueada) sb.append("[Defensa bloqueada] ");
        if (esquivaProximo) sb.append("[Cegará al próximo ataque] ");
        if (escudoActivo) sb.append("[Escudo] ");
        return sb.toString().trim();
    }
}


