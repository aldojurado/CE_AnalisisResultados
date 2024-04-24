import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticColoration {

    // Representación del problema con matriz de adyacencia
    private int[][] matrizAdyacencia;

    // Almacena el número de vértices del archivo ingresado para crear las matrices
    private int numVertices;

    // Almacena la ruta del archivo ingresada para devolver la solución
    String rutaArchivo = "";

    // Almacena la población de soluciones
    private List<int[]> poblacion;

    // Listas para almacenar los datos de las evaluaciones
    List<Integer> mejorEvaluacionPorGeneracion = new ArrayList<>();
    List<Double> promedioEvaluacionPorGeneracion = new ArrayList<>();

    /**
     * Constructor de la clase GeneticColoration
     * Guarda la ruta ingresada en rutaArchivo, lee el archivo e inicializa la
     * población
     */
    public GeneticColoration(String rutaIngresada, int nPoblacion) {
        rutaArchivo = rutaIngresada;
        leeArchivo(rutaIngresada);

        poblacion = new ArrayList<>();
        poblacionInicial(nPoblacion);
    }

    /**
     * Getter de la clase Coloracion
     * Devuelve la ruta del archivo ingresada
     */
    public String getRuta() {
        return rutaArchivo;
    }

    /**
     * Lee el archivo .col ingresado y almacena la información en la matriz de
     * adyacencia.
     * 
     * @param rutaIngresada Ruta del archivo .col
     */
    private void leeArchivo(String rutaIngresada) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/output/graficas/" + rutaIngresada + ".col"))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                // Comentarios
                if (linea.startsWith("c")) {
                    continue;
                    // Número de vértices y aristas
                } else if (linea.startsWith("p edge")) {
                    String[] partes = linea.split(" ");
                    numVertices = Integer.parseInt(partes[2]);
                    // Creamos la matriz de adyacencia con el número de vértices
                    matrizAdyacencia = new int[numVertices][numVertices];
                    // Las adyacencias de los vértices
                } else if (linea.startsWith("e")) {
                    String[] partes = linea.split(" ");
                    // Le restamos uno a los vértices para que se representen correctamente en la
                    // matriz que comienza en 0
                    int v1 = Integer.parseInt(partes[1]) - 1;
                    int v2 = Integer.parseInt(partes[2]) - 1;
                    // Nos informa que son adyacentes, por lo que asignamos 1 en la matriz.
                    matrizAdyacencia[v1][v2] = 1;
                    matrizAdyacencia[v2][v1] = 1;
                } else {
                    throw new RuntimeException("Esquema de codificación incorrecto en el archivo .col ingresado");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido leer el archivo ingresado.", e);
        }
    }

    /**
     * Escribe la solución en un archivo de salida.
     * 
     * @param solucion    Un arreglo de enteros que indican los colores de la
     *                    solución
     * @param iteraciones El número de iteraciones con mejora
     * @param tolerancia  El número de iteraciones sin mejora
     */
    private void escribeSolucion(int[] solucion, int nPoblacion, double mutacion) {
        // El nombre del archivo de salida es la ruta del archivo de entrada con un
        // identificador "_solucion" al final.
        String nombreArchivoSalida = "src/output/graficas/" + getRuta() + "_solucion.col";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivoSalida))) {

            writer.println("Solución: " + java.util.Arrays.toString(solucion) + " con " + evaluarSolucion(solucion)
                    + " colores, " + nPoblacion + " individuos en la población y una tasa de mutación de " + mutacion);

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Encuentra un color válido para un vértice, es decir, un color que no esté
     * siendo utilizado por sus vértices adyacentes
     * 
     * @param vertice  El vértice para el cual se busca un color válido
     * @param solucion La solución actual
     * @return
     */
    private int colorea(int vertice, int[] solucion) {
        Random rand = new Random();
        // Creamos un arreglo de valores booleanos para registrar los colores utilizados
        boolean[] coloresInvalidos = new boolean[numVertices + 1];

        // Marcar los colores de los vértices adyacentes como no válidos
        for (int i = 0; i < numVertices; i++) {
            // Si son adyacentes, el color no es válido
            if (matrizAdyacencia[vertice][i] == 1) {
                coloresInvalidos[solucion[i]] = true;
            }
        }

        // Encontrar un color válido (no usado por ningún adyacente)
        int color;
        do {
            color = 1 + rand.nextInt(numVertices);
        } while (coloresInvalidos[color]);

        return color;
    }

    /**
     * Genera una solución aleatoria válida, asigna un color distinto a cada vértice
     * adyacente.
     * 
     * @return Un arreglo de enteros que representan colores con la solución
     *         aleatoria.
     */
    private int[] solucionAleatoria() {

        int[] solucion = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            solucion[i] = colorea(i, solucion);
        }
        return solucion;
    }

    /**
     * Evalúa la solución ingresada, cuenta cuántos colores son utilizados para
     * colorear la gráfica
     * Mientras menos colores se utilicen la solución es mejor
     * 
     * @param solucion Un arreglo de enteros que representan colores con la solución
     * @return El número de colores utilizados
     */
    private int evaluarSolucion(int[] solucion) {
        boolean[] coloresUsados = new boolean[numVertices + 1];
        int totalColores = 0;
        // Itera sobre la solución, marca los colores ya utilizados y cuenta el número
        // de apariciones
        for (int color : solucion) {
            if (!coloresUsados[color]) {
                coloresUsados[color] = true;
                totalColores++;
            }
        }

        return totalColores;
    }

    /**
     * Genera una población inicial de soluciones aleatorias
     * 
     * @param nPoblacion Tamaño de la población
     */
    private void poblacionInicial(int nPoblacion) {
        for (int i = 0; i < nPoblacion; i++) {
            poblacion.add(solucionAleatoria());
        }
    }

    /**
     * Método de selección de torneo para encontrar los individuos más aptos para
     * reproducirse
     * 
     * @param poblacion Población actual
     * @return El mejor individuo del torneo
     */
    private int[] torneo(List<int[]> poblacion) {
        Random rand = new Random();
        // Seleccionamos un 30% de la población para el torneo
        int tamanoTorneo = (int) (poblacion.size() * 0.3);
        // Inicializamos el mejor índice y evaluación
        int mejorIndice = -1;
        int mejorEvaluacion = Integer.MAX_VALUE;

        // Realizamos el torneo
        for (int i = 0; i < tamanoTorneo; i++) {

            // Seleccionamos un individuo aleatorio
            int indiceAleatorio = rand.nextInt(poblacion.size());
            // Evaluamos la solución candidata
            int[] solucionCandidata = poblacion.get(indiceAleatorio);
            int evaluacionCandidata = evaluarSolucion(solucionCandidata);

            // Si la evaluación de la solución candidata es mejor que la mejor evaluación,
            // actualizamos el mejor índice y evaluación
            if (evaluacionCandidata < mejorEvaluacion) {
                mejorEvaluacion = evaluacionCandidata;
                mejorIndice = indiceAleatorio;
            }
        }

        // Devolvemos el ganador del torneo
        return poblacion.get(mejorIndice).clone();
    }

    /**
     * Cruza de dos puntos para generar dos hijos a partir de dos padres ingresados.
     * Tenemos en cuenta que no siempre se generan soluciones válidas al
     * intercambiar los genes.
     * 
     * @param padre1 Solución padre 1
     * @param padre2 Solución padre 2
     * @return Una arreglo que contiene los dos hijos generados
     */
    private int[][] cruzar(int[] padre1, int[] padre2) {
        Random rand = new Random();

        // Inicializamos los dos hijos
        int[][] hijos = new int[2][numVertices];

        // Seleccionar dos puntos de cruza al azar, asegurándonos que sean diferentes
        int puntoCruce1 = rand.nextInt(numVertices - 1);
        int puntoCruce2 = rand.nextInt(numVertices - puntoCruce1) + puntoCruce1 + 1;

        // Ordenar los puntos para asegurar que puntoCruce1 < puntoCruce2
        if (puntoCruce1 > puntoCruce2) {
            int temp = puntoCruce1;
            puntoCruce1 = puntoCruce2;
            puntoCruce2 = temp;
        }

        // Rellenar los hijos basado en los puntos de cruza
        for (int i = 0; i < numVertices; i++) {

            if (i >= puntoCruce1 && i <= puntoCruce2) {
                // Entre los puntos de cruce, intercambiar los genes de los padres
                hijos[0][i] = padre2[i];
                hijos[1][i] = padre1[i];
            } else {
                // Fuera de los puntos de cruce, mantener los genes de los padres
                hijos[0][i] = padre1[i];
                hijos[1][i] = padre2[i];
            }
        }

        return hijos;
    }

    /**
     * Verifica si una solución es válida, es decir, si dos vértices adyacentes no
     * tienen el mismo color
     * 
     * @param solucion La solución a verificar
     * @return true si la solución es válida, false en caso contrario
     */
    private boolean esSolucionValida(int[] solucion) {
        // Iteramos a través de cada vértice en la matriz de adyacencia
        for (int i = 0; i < numVertices; i++) {

            for (int j = 0; j < numVertices; j++) {
                // Verificamos si los vértices i y j son adyacentes
                if (matrizAdyacencia[i][j] == 1) {

                    // Si dos vértices adyacentes tienen el mismo color, la solución no es válida
                    if (solucion[i] == solucion[j]) {
                        return false;
                    }
                }
            }
        }
        // Si no se encontraron conflictos, la solución es válida
        return true;
    }

    /**
     * Mutación de una solución, cambia el color de un vértice aleatorio
     * 
     * @param solucion La solución a mutar
     * @return La solución mutada
     */
    private int[] mutar(int[] solucion) {
        Random rand = new Random();

        // Clonamos la solución para no modificar la original
        int[] mutada = solucion.clone();

        // Seleccionamos un vértice aleatorio
        int vertice = rand.nextInt(numVertices);

        // Recopilamos los colores ya usados por los vértices adyacentes
        boolean[] coloresInvalidos = new boolean[numVertices + 1];
        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[vertice][i] == 1) {
                coloresInvalidos[solucion[i]] = true;
            }
        }

        // Intentamos encontrar un nuevo color que no esté usado por los adyacentes.
        List<Integer> coloresValidos = new ArrayList<>();
        for (int color = 1; color <= numVertices; color++) {
            if (!coloresInvalidos[color]) {
                coloresValidos.add(color);
            }
        }

        // Asignamos un color aleatorio de los colores válidos
        if (!coloresValidos.isEmpty()) {
            int colorNuevo = coloresValidos.get(rand.nextInt(coloresValidos.size()));
            mutada[vertice] = colorNuevo;
        }

        return mutada;
    }

    /**
     * Algoritmo genético elitista para encontrar la mejor solución al problema de
     * coloración de grafos
     * 
     * @param numGeneraciones Número de generaciones
     * @param tasaMutacion    Tasa de mutación
     * @return La mejor solución encontrada
     */
    public int[] algoritmoGeneticoElitista(int numGeneraciones, double tasaMutacion) throws IOException {
        try {
            Random rand = new Random();

            // Listas para almacenar los datos de las evaluaciones
            List<Integer> mejorEvaluacionPorGeneracion = new ArrayList<>();
            List<Double> promedioEvaluacionPorGeneracion = new ArrayList<>();

            int[] mejorIndividuo = new int[numVertices];
            for (int generacion = 0; generacion < numGeneraciones; generacion++) {
                List<int[]> nuevaPoblacion = new ArrayList<>(poblacion.size());

                // Guardamos el individuo más apto para elitismo
                mejorIndividuo = seleccionarMejorSolucion(poblacion);

                // Generamos una nueva población a partir hijos de las soluciones más aptas de
                // la solución actual
                while (nuevaPoblacion.size() < poblacion.size() - 1) {
                    int[] padre1 = torneo(poblacion);
                    int[] padre2 = torneo(poblacion);

                    int[][] hijos = cruzar(padre1, padre2);

                    for (int i = 0; i < hijos.length; i++) {

                        // Mutamos al hijo si es que se cumple la tasa de mutación
                        if (rand.nextDouble() < tasaMutacion) {
                            hijos[i] = mutar(hijos[i]);
                        }
                        // Verificamos que la solución sea válida para agregarla a la población
                        if (esSolucionValida(hijos[i])) {
                            nuevaPoblacion.add(hijos[i]);
                            if (nuevaPoblacion.size() == poblacion.size() - 1)
                                break;
                        }
                    }
                }

                // Preservamos al mejor individuo de la generación anterior
                nuevaPoblacion.add(mejorIndividuo);
                // Actualizamos la población
                poblacion = nuevaPoblacion;

                // Recolectar datos para la gráfica
                int mejorEvaluacion = evaluarSolucion(mejorIndividuo);
                mejorEvaluacionPorGeneracion.add(mejorEvaluacion);

                double promedioEvaluacion = poblacion.stream()
                        .mapToInt(this::evaluarSolucion)
                        .average()
                        .orElse(Double.NaN);
                promedioEvaluacionPorGeneracion.add(promedioEvaluacion);
            }

            // Escribimos los datos recolectados en un archivo para Gnuplot
            escribirDatosParaGnuplot(mejorEvaluacionPorGeneracion, promedioEvaluacionPorGeneracion);

            escribeSolucion(mejorIndividuo, numGeneraciones, tasaMutacion);

            return seleccionarMejorSolucion(poblacion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Selecciona la mejor solución de la población actual
     * 
     * @param poblacion La población actual
     * @return La mejor solución
     */
    private int[] seleccionarMejorSolucion(List<int[]> poblacion) {
        int mejorIndice = 0;
        int mejorEvaluacion = Integer.MAX_VALUE;

        for (int i = 0; i < poblacion.size(); i++) {
            int evaluacionActual = evaluarSolucion(poblacion.get(i));
            if (evaluacionActual < mejorEvaluacion) {
                mejorEvaluacion = evaluacionActual;
                mejorIndice = i;
            }
        }

        return poblacion.get(mejorIndice);
    }

    /**
     * Escribe los datos recolectados en un archivo para Gnuplot
     * 
     * @param mejorEvaluacion    Lista de las mejores evaluaciones por generación
     * @param promedioEvaluacion Lista de los promedios de evaluación por generación
     */
    private void escribirDatosParaGnuplot(List<Integer> mejorEvaluacion, List<Double> promedioEvaluacion)
            throws IOException {
        // Agregar ruta de salida
        try (PrintWriter writer = new PrintWriter(new FileWriter("resultados2.dat"))) {
            for (int i = 0; i < mejorEvaluacion.size(); i++) {
                writer.printf("%d %d %.2f%n", i, mejorEvaluacion.get(i), promedioEvaluacion.get(i));
            }
        }
    }

}
