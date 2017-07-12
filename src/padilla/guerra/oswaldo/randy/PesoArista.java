package padilla.guerra.oswaldo.randy;

import java.util.Stack;


public class PesoArista{
    

    private final int V;
    private int E;
    private Listasimple<Arista>[] adj;
    
    /**
     * Inicializar una vacia arista-peso grafo con {V} vertices y arista 0.
     *
     * @param  V el numero de vertices
     * @throws IllegalArgumentException if {V < 0}
     */
    public PesoArista(int V) {
        if (V < 0) throw new IllegalArgumentException("Numero de vertices que no son no negativo");
        this.V = V;
        this.E = 0;
        adj = (Listasimple<Arista>[]) new Listasimple[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Listasimple<Arista>();
        }
    }

  
    /**
     * Inicializa una nuevo arista-peso grafo que es una copia profunda de {G}.
     *
     * @param  G la arista-peso grfo para copiar
     */
    
    
    public PesoArista(PesoArista G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // revertir la lista en su orden original
            Stack<Arista> reverse = new Stack<Arista>();
            for (Arista e : G.adj[v]) {
                reverse.push(e);
            }
            for (Arista e : reverse) {
                adj[v].add(e);
            }
        }
    }


    /**
     * Retorna el numero de vertices en esto: arista-peso del grafo.
     *
     * @retorna el numero de vertices en esto: arista-peso del grafo
     */
    public int V() {
        return V;
    }

    /**
     * Retorna el numero de arista en esto: arista-peso del grafo.
     *
     * @return el numero de arista en esto: arista-peso del grafo
     */
    public int E() {
        return E;
    }

    // throw IllegalArgumentException a no ser que {0 <= v < V}
    private void validarVertice(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertice " + v + " no esta entre 0 y " + (V-1));
    }

    /**
     * Agregar directo la arista {e} para esto: arista-peso del grafo.
     *
     * @param  e la arista
     * @throws IllegalArgumentException a no ser que ambos puntosfinales estan entre {0} y {V-1}
     */
    public void agregarArista(Arista e) {
        int v = e.cualquiera();
        int w = e.otro(v);
        validarVertice(v);
        validarVertice(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * Retornas la arista incidente en el vertice {v}.
     *
     * @param  v el vertice
     * @return la arista incidente en el vertice {v} cuando es un Iterable
     * @throws IllegalArgumentException a no ser que {0 <= v < V}
     */
    public Iterable<Arista> adj(int v) {
    	validarVertice(v);
        return adj[v];
    }

    /**
     * Retorna el grado del vertice {v}.
     *
     * @param  v el vertice
     * @return el grado del vertice {v}               
     * @throws IllegalArgumentException unless {0 <= v < V}
     */
    public int grado(int v) {
    	validarVertice(v);
        return adj[v].size();
    }

    /**
     * Retorna todas las aristas en esta arista-peso grafo.
     * Para iterar encima de la aristas en esta arista-peso grafo, usando la notacion foreach notacion:
     * {for (Edge e : G.edges())}.
     *
     * @retornar todas las aristas en esta arista-peso grafo, cuando es un iterable
     */
    public Iterable<Arista> aristas() {
    	Listasimple<Arista> list = new Listasimple<Arista>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Arista e : adj(v)) {
                if (e.otro(v) > v) {
                    list.add(e);
                }
                // agregar una unica copia de busqueda para enlazarla (buscar y enlazar consecutivamente)
                else if (e.otro(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    
        
    }
