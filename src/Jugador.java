import java.util.ArrayList;
import java.util.List;

public class Jugador {
    public static final int PV_INICIALES = 100;
    public static final int GLIFOS_EN_MANO = 2;

    private final String nombre;
    private final Aquelarre aquelarre;
    private final MazoGlifos mazo;
    private final List<Glifo> manoGlifos = new ArrayList<>();
    private int pv = PV_INICIALES;

    private Poder poderBloqueado = null;
    private Glifo glifoBloqueado = null;

    public Jugador(String nombre, Aquelarre aquelarre, MazoGlifos mazo) {
        this.nombre = nombre;
        this.aquelarre = aquelarre;
        this.mazo = mazo;
        rellenarMano();
    }

    public void rellenarMano() {
        while (manoGlifos.size() < GLIFOS_EN_MANO) {
            manoGlifos.add(mazo.robar());
        }
    }

    public Glifo rotarGlifo(int indice) {
        if (indice < 0 || indice >= manoGlifos.size()) return null;
        Glifo descartado = manoGlifos.remove(indice);
        mazo.descartar(descartado);
        Glifo nuevo = mazo.robar();
        manoGlifos.add(indice, nuevo);
        return nuevo;
    }

    public Glifo usarGlifo(int indice) {
        if (indice < 0 || indice >= manoGlifos.size()) return null;
        Glifo usado = manoGlifos.remove(indice);
        mazo.descartar(usado);
        return usado;
    }

    /** Ahora se compara el objeto poder, no una constante de enum. */
    public boolean puedeUsarPoder(Poder p) {
        return p != poderBloqueado;
    }

    public boolean puedeUsarGlifo(int indice) {
        return indice >= 0 && indice < manoGlifos.size()
                && manoGlifos.get(indice) != glifoBloqueado;
    }

    /** Al terminar el turno: lo usado hoy queda bloqueado mañana. */
    public void registrarUso(Poder poder, Glifo glifo) {
        poderBloqueado = poder;
        glifoBloqueado = glifo;
    }

    public void recibirDanio(int danio) {
        pv = Math.max(0, pv - Math.max(0, danio));
    }

    public void curar(int cantidad) {
        pv = Math.min(PV_INICIALES, pv + Math.max(0, cantidad));
    }

    public boolean estaVivo() {
        return pv > 0;
    }

    public String getNombre() { return nombre; }
    public Aquelarre getAquelarre() { return aquelarre; }
    public int getPv() { return pv; }
    public Poder getPoderBloqueado() { return poderBloqueado; }
    public Glifo getGlifoBloqueado() { return glifoBloqueado; }
    public List<Glifo> getManoGlifos() { return new ArrayList<>(manoGlifos); }
}
