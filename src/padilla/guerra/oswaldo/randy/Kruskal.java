package padilla.guerra.oswaldo.randy;

import java.util.Scanner;

public class Kruskal {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double peso;                        // peso
    private ColaPri<Arista> mst = new ColaPri<Arista>();  // aristas del algoritmo

    /**
     * Ordenar el arbol de recubrimiento minimo de una: arista-peso del grafo.
     * @param G: la arista-peso grafo.
     */
    public Kruskal(PesoArista G) {
        // mejor eficiencia del monticulo pasando por el arreglo de aristas
        Monticulominimo<Arista> pq = new Monticulominimo<Arista>();
        for (Arista e : G.aristas()) {
            pq.insertar(e);
        }

        // recorrido del algoritmo
        UF uf = new UF(G.V());
        while (!pq.estaVacia() && mst.tamanio() < G.V() - 1) {
        	Arista e = pq.delMin();
            int v = e.cualquiera();
            int w = e.otro(v);
            if (!uf.connectado(v, w)) { // v-w no estan creando un ciclo
                uf.union(v, w);  // fusionar componentes de v and w 
                mst.encolar(e);  // agregar arista e para el arbol de recubrimiento minimo 
                peso += e.peso();
            }
        }

        // chequeo de las condiciones de optimacion
        assert chequear(G);
    }

    /**
     * Retorna las arista en un arbol de recubrimiento minimo.
     * @retorna las arista en un arbol de recubrimiento minimo o como
     *    un iterable de aristas
     */
    public Iterable<Arista> aristas() {
        return mst;
    }

    /**
     * Retorna la suma de los pesos de las aristas en un arbol de recubrimieno minimo.
     * @retorna la suma de los pesos de las aristas en un arbol de recubrimieno minimo.
     */
    public double peso() {
        return peso;
    }
    
    // chequear las condiciones de optimacion (tomandolo en tiempo dado para E V lg* V)
    private boolean chequear(PesoArista G) {

        // chequeando el peso total
        double total = 0.0;
        for (Arista e : aristas()) {
            total += e.peso();
        }
        if (Math.abs(total - peso()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Peso de las aristas no son iguales(): %f vs. %f\n", total, peso());
            return false;
        }

        // chequear que esto nosea ciclo
        UF uf = new UF(G.V());
        for (Arista e : aristas()) {
            int v = e.cualquiera(), w = e.otro(v);
            if (uf.connectado(v, w)) {
                System.err.println("No es un a arbol");
                return false;
            }
            uf.union(v, w);
        }

        // chequear que sea un arbol minimo
        for (Arista e : G.aristas()) {
            int v = e.cualquiera(), w = e.otro(v);
            if (!uf.connectado(v, w)) {
                System.err.println("No es un arbol minimo");
                return false;
            }
        }

        // chequear que este sea un arbol de recubriniento minimo (cortando las condiciones de optimacion)
        for (Arista e : aristas()) {

            // todas las aristas del arbol de recubrimiento minimo excepto e
            uf = new UF(G.V());
            for (Arista f : mst) {
                int x = f.cualquiera(), y = f.otro(x);
                if (f != e) uf.union(x, y);
            }
            
            // chequear que e es una arista con peso minimo en el corte de cruce
            for (Arista f : G.aristas()) {
                int x = f.cualquiera(), y = f.otro(x);
                if (!uf.connectado(x, y)) {
                    if (f.peso() < e.peso()) {
                        System.err.println("padilla.guerra.oswaldo.randy.Arista " + f + " violar las condiciones de optimacion en el corte");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    public static void main(String[] args) {
    	System.out.println("Ingrese los nodos: ");
    	Scanner n=new Scanner(System.in);
    	int n1=n.nextInt();
        PesoArista G = new PesoArista(n1);
        Kruskal mst = new Kruskal(G);
        for (Arista e : mst.aristas()) {
            System.out.println(e);
        }
        System.out.printf("%.5f\n", mst.peso());
    }

}

