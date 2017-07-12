package padilla.guerra.oswaldo.randy;

public class Arista implements Comparable<Arista> {

    private final int v;
    private final int w;
    private final double peso;

    /**
     * Inicializa una arista entre los vertices {v} y {w} dado
     * dado su peso.
     *
     * @param v    un vertice
     * @param w    el otro vertice
     * @param peso el peso de las arista
     * @throws IllegalArgumentException si cualquiera {v} o {w}
     *                                  es un entero negativo
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Arista(int v, int w, double peso) {
        if (v < 0) {
            throw new IllegalArgumentException("indice del vertice no es un entero negativo");
        }
        if (w < 0) {
            throw new IllegalArgumentException("indice del vertice no es un entero negativo");
        }
        if (Double.isNaN(peso)) {
            throw new IllegalArgumentException("peso no esta negativo");
        }
        this.v = v;
        this.w = w;
        this.peso = peso;
    }

    /**
     * Retornar el peso de la arista.
     *
     * @return el peso de la arista
     */
    public double peso() {
        return peso;
    }

    /**
     * Retornar otro puntofinal de la arista.
     *
     * @return otro puntofinal de nuestra arista
     */
    public int cualquiera() {
        return v;
    }

    /**
     * Retornar el puntofinal de esta arista que es diferente vertice de donde esta.
     *
     * @param vertice v&eacute;rtice del puntofinal de esta arista
     * @return el otro puntofinal de esta arista
     * @throws IllegalArgumentException si el vertice no es un unico
     *                                  puntofinal de esta arista
     */
    public int otro(int vertice) {
        if (vertice == v) {
            return w;
        } else if (vertice == w) {
            return v;
        } else throw new IllegalArgumentException("puntofinal Ilegal...");
    }

    /**
     * Compara dos aristas por el peso.
     * Notar que {compareTo()}no es consistente con equals()},
     * que usa la referencia de igualdad de la implementacion herededada de {Object}.
     *
     * @param aquella la otra arista
     * @return un entero negativo, zero, o entero postivo dependiendo  si
     * el peso de esta arista esta menos, igual o, mas grande que
     * el argumento arista
     */
    @Override
    public int compareTo(Arista aquella) {
        return Double.compare(this.peso, aquella.peso);
    }

    /**
     * Retornar a string representado de esta arista.
     *
     * @returnar a string representado de esta arista.
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, peso);
    }
}