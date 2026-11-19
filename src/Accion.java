public record Accion(Poder poder, int indiceGlifo) {

    public static Accion soloPoder(Poder p) { return new Accion(p, -1); }
    public static Accion soloGlifo(int indice) { return new Accion(null, indice); }
    public static Accion combo(Poder p, int indice) { return new Accion(p, indice); }

    public boolean tienePoder() { return poder != null; }
    public boolean tieneGlifo() { return indiceGlifo >= 0; }
    public boolean esValida() { return tienePoder() || tieneGlifo(); }
}
