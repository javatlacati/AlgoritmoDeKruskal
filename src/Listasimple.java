
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Listasimple<Item> implements Iterable<Item> {
    private Node<Item> primero;    // principio de la Listasimple
    private int n;               // numero de elementos en Listasimple

    // ayuda de la clase de la lista
    private static class Node<Item> {
        private Item item;
        private Node<Item> siguiente;
    }

    /**
     * Inicializa una Listasimple.
     */
    public Listasimple() {
       primero = null;
        n = 0;
    }

    /**
     * Retorna true si Listasimple es vacia.
     *
     * @return {true} si esta Listasimple es vacia;
     *         {false} si es distinto.Se entiende
     */
    public boolean estaVacia() {
        return primero == null;
    }

    /**
     * Retorna el numero de items en esta Listasimple.
     *
     * @retorna el numero de items en esta Listasimple
     */
    public int size() {
        return n;
    }

    /**
     * Agregar el item para esta Listasimple.
     *
     * @param  item Agregar el item para esta Listasimple.
     */
    public void add(Item item) {
        Node<Item> anterior = primero;
        primero = new Node<Item>();
        primero.item = item;
        primero.siguiente = anterior;
        n++;
    }


    /**
     * Retorna un iterator que itere encima del items in esta Agregar el item para esta Listasimple. en orden arbitrario.
     *
     * @retornar  un iterator que itere encima del items in esta Agregar el item para esta Listasimple. en orden arbitrario.
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(primero);  
    }

    //  si un terador, no esta implementando remove() esto es opcional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> actual;

        public ListIterator(Node<Item> primero) {
            actual = primero;
        }

        public boolean hasNext()  { return actual != null;                     }
        public void quitar()      { throw new UnsupportedOperationException();  }
    
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = actual.item;
            actual = actual.siguiente; 
            return item;
            
           }
    }
    
}

