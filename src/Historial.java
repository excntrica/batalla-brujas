import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class Historial {
    private static final Path ARCHIVO = Path.of("historial.txt");
    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Historial() {}

    public static void registrar(String ganador, String perdedor, int turnos) {
        String linea = String.format("%s | GANADOR: %s | PERDEDOR: %s | TURNOS: %d",
                LocalDateTime.now().format(FORMATO), ganador, perdedor, turnos);
        try {
            Files.writeString(ARCHIVO, linea + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("No se pudo guardar el historial: " + e.getMessage());
        }
    }

    public static List<String> leer() {
        try {
            if (!Files.exists(ARCHIVO)) return new ArrayList<>();
            return Files.readAllLines(ARCHIVO, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("No se pudo leer el historial: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean hayPartidas() {
        return Files.exists(ARCHIVO) && !leer().isEmpty();
    }
}
