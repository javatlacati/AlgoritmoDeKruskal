
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ColaPri<Item> implements Iterable<Item> {
    private Node<Item> primero;    // principio de la cola
    private Node<Item> ultimo;     // final de la cola
    private int n;               // numero de elementos en la cola 

    // Clase de la lista conectada
    private static class Node<Item> {
        private Item item;
        private Node<Item> siguiente;
    }

    /**
     * Inicializa una cola vacia.
     */
    public ColaPri() {
        primero = null;
        ultimo  = null;
        n = 0;
    }

    /**
     * Retornas true si esta cola es vacia.
     *
     * @retorna {true} si esta cola es vacia; {false} si es distinto
     */
    public boolean estaVacia() {
        return primero == null;
    }

    /**
     * Retorna el numero de items en esta cola.
     *
     * @retorna el numero d items en esta cola
     */
    public int tamanio() {
        return n;
    }

    /**
     * Retorna el item reciente agregado para esta cola.
     *
     * @retorna el item reciente agregado para esta cola
     * @throws NoSuchElementException si esta cola esta vacia
     */
    public Item mirar() {
        if (estaVacia()) throw new NoSuchElementException("Cola vacia....");
        return primero.item;
    }

    /**
     * Agregar el item para esta cola.
     *
     * @param  item the item para agregar
     */
    public void encolar(Item item) {
        Node<Item> anterior = ultimo;
        ultimo = new Node<Item>();
        ultimo.item = item;
        ultimo.siguiente = null;
        if (estaVacia()) primero = ultimo;
        else           anterior.siguiente = primero;
        n++;
    }

    /**
     * Remueve y retorna el item que esta recientemente agregado en la cola .
     *
     * @retorna el item que esta recientemente agregado en la cola
     * @throws NoSuchElementException si esta cola esta vacia
     */
    public Item colavacia() {
        if (estaVacia()) throw new NoSuchElementException("Cola vacia...");
        Item item = primero.item;
        primero = primero.siguiente;
        n--;
        if (estaVacia()) ultimo = null;   // para evitar que la cola este vacia
        return item;
    }

    
    /**
     * Retorna un iterator que itera encima del item en esta cola-prioridad en orden FIFO.
     *
     * @retorna Retorna un iterator que itera encima del item en esta cola-prioridad en orden FIFO.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(primero);  
    }

    // un iterator, no esta implementando remove()  esto es opcional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> actual;

        public ListIterator(Node<Item> primero) {
            actual = primero;
        }

        public boolean hasNext()  { return actual != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = actual.item;
            actual = actual.siguiente; 
            return item;
        }
    }
}