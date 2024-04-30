import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Recocido {

    final int NBITS = 22;
    final int MAXITER = 15000;

    /**
     * Método que ejecuta el algoritmo de recocido simulado
     * 
     * @param numFun    es el número de la función a evaluar
     * @param dimension es la dimensión de la función
     * @return un arreglo con la solución encontrada
     */
    public double[] recocido(int enfriamiento, int numFun, int dimension, int seed, int numArchivo) {
        Binario bin = new Binario();
        Evaluador eval = new Evaluador();
        double[] intervalo = eval.intervalo(numFun);
        double[] actual = new double[MAXITER];

        // solución inicial y temperatura inicial
        int[] solucion = bin.generaSolucionAleatoria(NBITS * dimension, seed);
        double temp = 10000;
        int numIt = 0;

        // condición de término
        while (numIt < MAXITER && temp > 0) {

            // seleccionamos un vecino
            int[] vecino = bin.vecinoRnd(solucion);
            double[] valores = evaluaBin(solucion, vecino, numFun, intervalo);
            // si es mejor lo aceptamos automáticamente
            if (valores[1] < valores[0]) {
                solucion = vecino;
            } else {
                // si no es mejor lo aceptamos con cierta probabilidad
                double proba = Math.exp((valores[0] - valores[1]) / temp);
                double rnd = Math.random();
                if (rnd < proba) {
                    solucion = vecino;
                }
            }

            // guardamos el valor de la solución actual
            actual[numIt] = valores[0];
            numIt++;
            // actualizamos temperatura
            temp = enfriar(temp, enfriamiento, numIt);

        }
        double ultimoValor = actual[numIt - 1];
        for (int i = numIt; i < actual.length; i++) {
            actual[i] = ultimoValor;
        }
        generarReporte(actual, enfriamiento, numArchivo);
        // regresamos la solución decodificada
        return bin.decodifica(solucion, NBITS, intervalo[0], intervalo[1]);
    }

    private void generarReporte(double[] actual, int enfriamiento, int numArchivo) {
        String nombreArchivo = "src/output/solucionesContinuas/aptitud/SA" + enfriamiento + "_" + numArchivo + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("# iteración promedio");
            bufferedWriter.newLine();
            for (int i = 0; i < actual.length; i++) {
                bufferedWriter.write(i + " " + actual[i]);
                bufferedWriter.newLine();
            }

            // Cerrar el BufferedWriter
            bufferedWriter.close();

            System.out.println("Texto guardado en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private double enfriar(double temp, int enfriamiento, int numIt) {
        // Enfriamiento geométrico
        if (enfriamiento == 1) {
            return temp * 0.999;
        }
        // Enfriamiento lineal
        if (numIt % 50 == 0) {
            return temp - 0.005 * (numIt);
        }
        return temp;
    }

    /**
     * Método que evalúa dos soluciones en una de las funciones usando
     * representación binaria por una partición generada uniformemente
     * 
     * @param solucion  es la solución actual a evaluar
     * @param vecino    es la solución vecina a evaluar
     * @param numFun    es el número de la función
     * @param intervalo es el intervalo de la función
     * @return un arreglo con los valores de las soluciones, el primero es el valor
     *         de la solución actual y el segundo es el valor de la solución vecina
     */
    private double[] evaluaBin(int[] solucion, int[] vecino, int numFun, double[] intervalo) {
        Evaluador evaluador = new Evaluador();
        Binario binario = new Binario();

        double[] solucionAct = binario.decodifica(solucion, NBITS, intervalo[0], intervalo[1]);
        double valorAct = evaluador.evaluaEn(numFun, solucionAct);

        double[] solucionVec = binario.decodifica(vecino, NBITS, intervalo[0], intervalo[1]);
        double valorVec = evaluador.evaluaEn(numFun, solucionVec);
        return new double[] { valorAct, valorVec };
    }

}
