import java.util.List;

public class PruebaMotor {
    public static void main(String[] args) {
        MazoGlifos mazo = new MazoGlifos();
        Jugador a = new Jugador("Luz", Aquelarre.PLANTAS, mazo);
        Jugador b = new Jugador("Amity", Aquelarre.ABOMINACIONES, mazo);
        MotorCombate motor = new MotorCombate(a, b);

        Poder ataque = a.getAquelarre().getPoderes().get(0);

        motor.iniciarTurno(a).forEach(System.out::println);
        List<String> log = motor.ejecutarTurno(a, b, Accion.combo(ataque, 0));
        log.forEach(System.out::println);
        System.out.println(motor.estado(b).resumen());
    }
}

