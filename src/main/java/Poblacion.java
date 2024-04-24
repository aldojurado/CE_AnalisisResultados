import java.util.Random;

public class Poblacion implements Cloneable {
    private int tam;
    private int numBits;
    private int seed;
    private double[] evaluaciones;
    private int[][] individuos;
    private int dimension;
    private int[] mejorIndividuo;
    private int numFun;
    private double[] intervalo;

    /**
     * Constructor de la población
     * 
     * @param tamPoblacion
     * @param numBits
     * @param seed
     * @param dimension
     */
    public Poblacion(int tamPoblacion, int numBits, int seed, int dimension, int numFun) {
        this.tam = tamPoblacion;
        this.numBits = numBits;
        this.seed = seed;
        this.individuos = generaIndividuos(seed, tamPoblacion, numBits, dimension);
        this.evaluaciones = new double[tamPoblacion];
        this.dimension = dimension;
        this.numFun = numFun;
        Evaluador evaluador = new Evaluador();
        this.intervalo = evaluador.intervalo(numFun);
    }

    /**
     * Genera una población de individuos aleatorios con una semilla
     * 
     * @param semilla
     * @param tamPoblacion
     * @param numBits2
     * @param dimension
     * @return
     */
    private int[][] generaIndividuos(int semilla, int tamPoblacion, int numBits2, int dimension) {
        int[][] individuos = new int[tamPoblacion][numBits2 * dimension];
        Random random = new Random(semilla);
        for (int i = 0; i < tamPoblacion; i++) {
            for (int j = 0; j < numBits2 * dimension; j++) {
                individuos[i][j] = random.nextInt(2);
            }
        }
        return individuos;
    }

    public void evaluarPoblacion() {
        Binario binario = new Binario();
        Evaluador evaluador = new Evaluador();
        for (int i = 0; i < tam; i++) {
            int[] individuo = individuos[i];
            double[] solucion = binario.decodifica(individuo, numBits, intervalo[0], intervalo[1]);
            evaluaciones[i] = evaluador.evaluaEn(numFun, solucion);
        }
        mejorIndividuo = mejorBin();

    }

    /**
     * @return Devuelve el mejor individuo de la población en su representación
     *         binaria
     */
    private int[] mejorBin() {
        int mejor = 0;
        for (int i = 0; i < tam; i++) {
            if (evaluaciones[i] < evaluaciones[mejor]) {
                mejor = i;
            }
        }
        return individuos[mejor];
    }

    /**
     * @return Devuelve el mejor individuo de la población en su representación
     *         decimal
     */
    public double[] mejor() {
        Binario binario = new Binario();
        int[] mejorIndividuo = mejorBin();
        double[] mejorSol = binario.decodifica(mejorIndividuo, numBits, intervalo[0], intervalo[1]);
        return mejorSol;
    }

    /**
     * Selecciona padres por ruleta y los cruza para generar una nueva generación
     * 
     * @param probCruza
     */
    public void seleccionarPadresRecombinar(double probCruza) {
        int[][] nuevaGeneracion = new int[tam][numBits * dimension];
        int numHijos = 0;

        while (numHijos < tam) {
            int[] padre1 = seleccionarPadre();
            int[] padre2 = seleccionarPadre();
            int[][] hijos = cruzarN(padre1, padre2, probCruza);

            // Agregar hijos a la nueva generación
            for (int i = 0; i < hijos.length && numHijos < tam; i++) {
                nuevaGeneracion[numHijos++] = hijos[i];
            }
        }
        individuos = nuevaGeneracion;
    }

    private int[] seleccionarPadre() {
        return individuos[ruleta()];
    }

    private int[][] cruzar(int[] padre1, int[] padre2, double probCruza) {
        int[][] hijos = new int[2][padre1.length];

        // vamos a ir haciendo saltos random entre el índice actual y el final, o sea
        // que el tamñao se va a ir reduciende
        Random random = new Random();
        int tamPadre = padre1.length;
        int salto = random.nextInt(tamPadre);
        for (int i = 0; i < salto; i++) {
            hijos[0][i] = padre1[i];
            hijos[1][i] = padre2[i];
        }
        for (int i = salto; i < tamPadre; i++) {
            hijos[0][i] = padre2[i];
            hijos[1][i] = padre1[i];
        }

        return hijos;
    }

    private int[][] cruzarN(int[] padre1, int[] padre2, double probCruza) {
        int[][] hijos = new int[2][padre1.length];

        // vamos a ir haciendo saltos random entre el índice actual y el final, o sea
        // que el tamñao se va a ir reduciende
        Random random = new Random();
        if (random.nextDouble() < probCruza) {
            int indActual = 0;
            int intercambiador = 0;
            int tamPadre = padre1.length;
            while (indActual < tamPadre) {
                int rango = tamPadre - indActual;
                int salto = random.nextInt(rango) + indActual;
                if (salto == indActual) {
                    salto++;
                }
                for (int i = indActual; i < salto; i++) {
                    hijos[intercambiador][i] = padre1[i];
                    hijos[1 - intercambiador][i] = padre2[i];
                }
                intercambiador = 1 - intercambiador;
                indActual = salto;
            }
        } else {
            hijos[0] = padre1;
            hijos[1] = padre2;

        }

        return hijos;
    }

    private int ruleta() {
        // Calculamos la aptitud total
        double f = 0;
        for (int i = 0; i < tam; i++) {
            f += 1 / evaluaciones[i];
        }

        // Calculamos la probabilidad de selección de cada individuo
        double[] proba = new double[tam];
        for (int i = 0; i < tam; i++) {
            proba[i] = (1 / evaluaciones[i]) / f;
        }

        // Generamos un número aleatorio entre 0 y 1
        Random random = new Random();
        double r = random.nextDouble();

        // Realizamos la selección basada en la ruleta
        double probaAcumulada = 0;
        for (int i = 0; i < tam; i++) {
            probaAcumulada += proba[i];
            if (r < probaAcumulada) {
                return i;
            }
        }

        // El ciclo debe funcionar bn, pero igual java nos pide que devolvamos algunos,
        // por cualquier cosa dejamos el último
        return tam - 1;
    }

    @Override
    public Poblacion clone() {
        try {
            Poblacion clonedPoblacion = (Poblacion) super.clone();
            // Copiar los atributos primitivos
            clonedPoblacion.tam = this.tam;
            clonedPoblacion.numBits = this.numBits;
            clonedPoblacion.seed = this.seed;
            clonedPoblacion.dimension = this.dimension;
            clonedPoblacion.numFun = this.numFun;

            // Copiar los arrays
            clonedPoblacion.evaluaciones = this.evaluaciones.clone();
            clonedPoblacion.mejorIndividuo = this.mejorIndividuo.clone();
            clonedPoblacion.intervalo = this.intervalo.clone();

            // Copiar la matriz
            clonedPoblacion.individuos = new int[this.individuos.length][];
            for (int i = 0; i < this.individuos.length; i++) {
                clonedPoblacion.individuos[i] = this.individuos[i].clone();
            }

            return clonedPoblacion;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public void mutar(double probMutacion) {
        Random random = new Random();
        for (int j = 0; j < tam; j++) {
            if (random.nextDouble() < probMutacion) {
                mutarflip(j);
            }
        }

    }

    /**
     * Hace una mutación flip al individuo en la posición j
     * Por como lo mandamos a llamar previamente ya nos aseguramos de que j
     * esté entre 0 y numBits*dimension, es decir, que sea un índice válido
     * 
     * @param j
     */
    private void mutarflip(int j) {
        for (int i = 0; i < individuos[j].length; i++) {
            individuos[j][i] = 1 - individuos[j][i];
        }
    }

    /**
     * @return Devuelve la aptitud del mejor individuo de la población
     */
    public double mejorAptitud() {
        double mejor = evaluaciones[0];
        for (int i = 1; i < tam; i++) {
            if (evaluaciones[i] < mejor) {
                mejor = evaluaciones[i];
            }
        }
        return mejor;
    }

    public double promedioAptitud() {
        double promedio = 0;
        for (int i = 0; i < tam; i++) {
            promedio += evaluaciones[i];
        }
        return promedio / tam;
    }

    public void elite() {
        Random random = new Random();
        int elite = random.nextInt(tam);

        individuos[elite] = mejorIndividuo;
    }
}
