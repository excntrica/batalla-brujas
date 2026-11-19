import java.util.Collections;
import java.util.Stack;

public class MazoGlifos {
    private static final int COPIAS_POR_GLIFO = 8;

    private final Stack<Glifo> mazo = new Stack<>();
    private final Stack<Glifo> descarte = new Stack<>();

    public MazoGlifos() {
        for (Glifo glifo : Glifo.values()) {
            for (int i = 0; i < COPIAS_POR_GLIFO; i++) {
                mazo.push(glifo);
            }
        }
        Collections.shuffle(mazo);
    }

    public Glifo robar() {
        if (mazo.isEmpty()) {
            rebarajarDescarte();
        }
        if (mazo.isEmpty()) {
            throw new IllegalStateException("No quedan glifos en el mazo ni en el descarte.");
        }
        return mazo.pop();
    }

    public void descartar(Glifo glifo) {
        descarte.push(glifo);
    }

    private void rebarajarDescarte() {
        mazo.addAll(descarte);
        descarte.clear();
        Collections.shuffle(mazo);
    }

    public int cartasRestantes() {
        return mazo.size();
    }
}
