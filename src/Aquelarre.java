public enum Aquelarre {
    ABOMINACIONES("Abominaciones", "Puño de Lodo", "Invocar Golem", "Muro Viscoso"),
    PLANTAS("Plantas", "Látigo de Espinas", "Raíces Atrapadoras", "Barrera de Zarzas"),
    ILUSIONES("Ilusiones", "Rayo Ilusorio", "Clones Distractores", "Espejismo Evasivo"),
    BARDO("Bardo", "Onda Sonora", "Melodía Debilitadora", "Barrera Acústica"),
    CURACION("Curación", "Pulso Vital", "Regeneración", "Aura Protectora"),
    BESTIAS("Bestias", "Zarpazo Salvaje", "Invocar Bestia", "Instinto Evasivo"),
    POCIONES("Pociones", "Bomba Explosiva", "Frasco de Veneno", "Cortina de Humo"),
    ORACULO("Oráculo", "Descarga Psíquica", "Invocar Espíritu", "Visión Futura"),
    CONSTRUCCION("Construcción", "Golpe Potenciado", "Pilar de Roca", "Armadura de Piedra"),
    EMPERADOR("Emperador", "Rayo de Bastón", "Sello Restrictivo", "Escudo Imperial");

    private final String nombre;
    private final String ataque;
    private final String especial;
    private final String defensa;

    Aquelarre(String nombre, String ataque, String especial, String defensa) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.especial = especial;
        this.defensa = defensa;
    }

    public String getNombre() { return nombre; }
    public String getAtaque() { return ataque; }
    public String getEspecial() { return especial; }
    public String getDefensa() { return defensa; }

    public Aquelarre getVentaja() {
        switch (this) {
            case ABOMINACIONES: return CONSTRUCCION;
            case PLANTAS:       return ABOMINACIONES;
            case ILUSIONES:     return BESTIAS;
            case BARDO:         return ILUSIONES;
            case CURACION:      return POCIONES;
            case BESTIAS:       return BARDO;
            case POCIONES:      return PLANTAS;
            case ORACULO:       return EMPERADOR;
            case CONSTRUCCION:  return ORACULO;
            case EMPERADOR:     return CURACION;
            default: throw new IllegalStateException("Aquelarre desconocido: " + this);
        }
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
