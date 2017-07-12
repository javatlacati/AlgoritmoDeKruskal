package padilla.guerra.oswaldo.randy;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Monticulominimo<Llave> implements Iterable<Llave>{
    private Llave[] pq;                    // almacenar items para indices 1 para n
    private int n;                       // numero de items en la cola con prioridad
    private Comparator<Llave> comparator;  // opcional comparator

    /**
     * Inicializa una cola con prioridad dandole una capacidad inicial.
     *
     * @param  iniciarCapacidad la capacidad iniciar de la cola con prioridad
     */
    public Monticulominimo(int iniciarCapacidad) {
        pq = (Llave[]) new Object[iniciarCapacidad + 1];
        n = 0;
    }

    /**
     * Inicializar una cola con prioridad vacia.
     */
    public Monticulominimo() {
        this(1);
    }

    /**
     * Inicializar la capacidad inicial de la cola con prioridad dandole su capacidad inicial,
     * usando el comparator.
     *
     * @param iniciarCapacidad la capacidad inicial de esta cola con prioridad
     * @param  comparator el orden para usar cuando comparamos llaves
     */
    public Monticulominimo(int iniciarCapacidad, Comparator<Llave> comparator) {
        this.comparator = comparator;
        pq = (Llave[]) new Object[iniciarCapacidad + 1];
        n = 0;
    }

    /**
     * Inicializar una cola con prioridad usando el comparator dado.
     *
     * @param el orden para usar cuando comparamos llaves
     */
    public Monticulominimo(Comparator<Llave> comparator) {
        this(1, comparator);
    }

    /**
     * Inicializar una cola con prioridad desde el arreglo de llaves.
     * <p>
     * Tomar el tiempo dado para el numero de llaves, usando la construccion del monticulo descendiendo-desde la base.
     *
     * @param  llaves el arreglo de las llaves
     */
    public Monticulominimo(Llave[] llaves) {
        n = llaves.length;
        pq = (Llave[]) new Object[llaves.length + 1];
        for (int i = 0; i < n; i++)
            pq[i+1] = llaves[i];
        for (int k = n/2; k >= 1; k--)
            descender(k);
        assert esMinMont();
    }

    /**
     * Retornar true si esta cola con prioridad esta vacia.
     *
     * @return {true} si esta cola con prioridad esta vacia;
     *         {false} si es distinto
     */
    public boolean estaVacia() {
        return n == 0;
    }

    /**
     * Retorna the numero de llaves en esta cola con prioridad.
     *
     * @return el numero de llaves en esta cola con prioridad
     */
    public int tamanio() {
        return n;
    }

    /**
     * Returns a smallest key on this priority queue.
     *
     * @return a smallest key on this priority queue
     * @throws NoSuchElementException si esta cola con prioridad esta vacia
     */
    public Llave min() {
        if (estaVacia()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    // ayudar para la doble funcion del tama�o del arreglo de monticulo
    private void resize(int capacity) {
        assert capacity > n;
        Llave[] temp = (Llave[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * Agregamos una nueva llave para esta cola con prioridad.
     *
     * @param  x la llave para agregar para esta cola con prioridad
     */
    public void insertar(Llave x) {
        // doble tama�o del arreglo si es necesario
        if (n == pq.length - 1) resize(2 * pq.length);

        // agregar x, and percolate it up to maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert esMinMont();
    }

    /**
     * Remueve y retorna una llave peque�a en esta cola con prioridad
     *
     * @return una llave peque�as en esta cola con prioridad
     * @throws NoSuchElementException si esta cola con prioridad esta vacia
     */
    public Llave delMin() {
        if (estaVacia()) throw new NoSuchElementException("Cola prioridad underflow");
        exch(1, n);
        Llave min = pq[n--];
        descender(1);
        pq[n+1] = null;         // evita perder el tiempo y ayuda con la recolector de basura
        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length  / 2);
        assert esMinMont();
        return min;
    }


   /***************************************************************************
    * Funcion: ayuda para restaurar el monticulo .
    ***************************************************************************/

    private void swim(int k) {
        while (k > 1 && grande(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void descender(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && grande(j, j+1)) j++;
            if (!grande(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

   /***************************************************************************
    * Funcion de ayuda para comprar y cambiar.
    ***************************************************************************/
    private boolean grande(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Llave>) pq[i]).compareTo(pq[j]) > 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) > 0;
        }
    }

    private void exch(int i, int j) {
    	Llave swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // es pq[1..N] un monticulo minimo?
    private boolean esMinMont() {
        return esMinMont(1);
    }

    // es subarbol de pq[1..n] k ruta para un monticulo minimo?
    private boolean esMinMont(int k) {
        if (k > n) return true;
        int izquierdo = 2*k;
        int derecho = 2*k + 1;
        if (izquierdo  <= n && grande(k, izquierdo))  return false;
        if (derecho <= n && grande(k, derecho)) return false;
        return esMinMont(izquierdo) && esMinMont(derecho);
    }


    /**
     * Retorna un iterator que itera encima de las llaves en esta cola con prioridad
     * en orden ascendiendo
     * <p>
     * The iterator no esta implementado {remove()} esto ya es es opcional.
     *
     * @return un iterador que itera encima de las llaves en orden ascendiendo
     */
    public Iterator<Llave> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Llave> {
        // crear un nuevo pq
        private Monticulominimo<Llave> copy;

        // agregar todos los items para copiar del monticulo
        // tomar tiempo lineal desde el orden del monticulo para no mover las llaves
        public HeapIterator() {
            if (comparator == null) copy = new Monticulominimo<Llave>(tamanio());
            else                    copy = new Monticulominimo<Llave>(tamanio(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insertar(pq[i]);
        }

        public boolean hasNext()  { return !copy.estaVacia();                     }
        public void quitar()      { throw new UnsupportedOperationException();  }

        public Llave next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }
}
