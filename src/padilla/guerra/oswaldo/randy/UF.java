package padilla.guerra.oswaldo.randy;

public class UF {

    private int[] padre;  // padre[i] = padre de i
    private byte[] rango;   // rango[i] = rango del subarbol ruta a i (nunca  mayor a 31)
    private int cont;     // numero del  conjunto

    /**
     * Inicializar un conjunto-disjunto vacio para la estructura de dato con {n} situamos
     * {0} y {n-1}.
     *
     * @param  n el numero de sitios
     * @throws IllegalArgumentException if {n < 0}
     */
    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        cont = n;
        padre = new int[n];
        rango = new byte[n];
        for (int i = 0; i < n; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
    }

    /**
     * Retorna el conjunto identificador de el conjunto situado en {p}.
     *
     * @param  el entero p representado en un solo sitio
     * @retorna el conjunto identificador de el conjunto situado en {p}.
     */
    public int find(int p) {
        validar(p);
        while (p != padre[p]) {
            padre[p] = padre[padre[p]];    
            p = padre[p];
        }
        return p;
    }

    /**
     * Retorna el numero de los componentes.
     *
     * @Retorna el numero de los componentes (entre {1} y {n})
     */
    public int cont() {
        return cont;
    }
  
    /**
     * Retorna verdadero si los dos  sitios estan en el mismo componente.
     *
     * @param  el entero p representado en un solo sitio
     * @param  
     * @return {verdadero} si los dos conjuntos{p} y {q} es5tan en el mismo conjunto;
              si no {falso} */
    
    public boolean connectado(int p, int q) {
        return find(p) == find(q);
    }
  
    /**
     *  Fusionar el conjunto situado en (p) con el 
     *  conjunto situado en (q).
     *
     * @param  el entero p representado en un sitio
     * @param  el entero q representado en otro sitio
     */
    
    public void union(int p, int q) {
        int rutaP = find(p);
        int rutaQ = find(q);
        if (rutaP == rutaQ) return;

        // marcar la raiz del rango pequeño a un  punto de una raiz de rango largo
        if      (rango[rutaP] < rango[rutaQ]) padre[rutaP] = rutaQ;
        else if (rango[rutaP] > rango[rutaQ]) padre[rutaQ] = rutaP;
        else {
            padre[rutaQ] = rutaP;
            rango[rutaP]++;
        }
        cont--;
    }

    // validar que p es un indice valido
    private void validar(int p) {
        int n = padre.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("indice " + p + "  no esta entre 0 y " + (n-1));  
        }
    }
}
