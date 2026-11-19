import java.util.List;

public enum Aquelarre {
    ABOMINACIONES("Abominaciones", "Puño de Lodo", "Invocar Golem", "Muro Viscoso", false, false),
    PLANTAS("Plantas", "Látigo de Espinas", "Raíces Atrapadoras", "Barrera de Zarzas", false, false),
    ILUSIONES("Ilusiones", "Rayo Ilusorio", "Clones Distractores", "Espejismo Evasivo", false, false),
    BARDO("Bardo", "Onda Sonora", "Melodía Debilitadora", "Barrera Acústica", false, false),
    CURACION("Curación", "Pulso Vital", "Regeneración", "Aura Protectora", true, false),
    BESTIAS("Bestias", "Zarpazo Salvaje", "Invocar Bestia", "Instinto Evasivo", false, false),
    POCIONES("Pociones", "Bomba Explosiva", "Frasco de Veneno", "Cortina de Humo", false, false),
    ORACULO("Oráculo", "Descarga Psíquica", "Invocar Espíritu", "Visión Futura", false, false),
    CONSTRUCCION("Construcción", "Golpe Potenciado", "Pilar de Roca", "Armadura de Piedra", false, false),
    EMPERADOR("Emperador", "Rayo de Bastón", "Sello Restrictivo", "Escudo Imperial", false, true);

    private final String nombre;
    private final Poder ataque;
    private final Poder especial;
    private final Poder defensa;

    Aquelarre(String nombre, String ataque, String especial, String defensa,
              boolean especialCura, boolean especialSella) {
        this.nombre = nombre;
        this.ataque = new PoderAtaque(ataque);
        this.especial = new PoderEspecial(especial, especialCura, especialSella);
        this.defensa = new PoderDefensa(defensa);
    }

    public String getNombre() { return nombre; }
    public Poder getAtaque() { return ataque; }
    public Poder getEspecial() { return especial; }
    public Poder getDefensa() { return defensa; }

    public List<Poder> getPoderes() {
        return List.of(ataque, especial, defensa);
    }

    public Aquelarre getVentaja() {
        return switch (this) {
            case ABOMINACIONES -> CONSTRUCCION;
            case PLANTAS       -> ABOMINACIONES;
            case ILUSIONES     -> BESTIAS;
            case BARDO         -> ILUSIONES;
            case CURACION      -> POCIONES;
            case BESTIAS       -> BARDO;
            case POCIONES      -> PLANTAS;
            case ORACULO       -> EMPERADOR;
            case CONSTRUCCION  -> ORACULO;
            case EMPERADOR     -> CURACION;
        };
    }

    public String getBruja() {
        return switch (this) {
            case ABOMINACIONES -> "Amity";
            case PLANTAS       -> "Willow";
            case ILUSIONES     -> "Gus";
            case BARDO         -> "Raine";
            case CURACION      -> "Viney";
            case BESTIAS       -> "Eda";
            case POCIONES      -> "Lilith";
            case ORACULO       -> "Lucia";
            case CONSTRUCCION  -> "Boscha";
            case EMPERADOR     -> "Hunter";
        };
    }

    public boolean tieneVentajaSobre(Aquelarre otro) {
        return getVentaja() == otro;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

