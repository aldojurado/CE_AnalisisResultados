public class Recocido {

    final int NBITS = 22;
    final int MAXITER = 100000;

    /**
     * Método que ejecuta el algoritmo de recocido simulado
     * 
     * @param numFun    es el número de la función a evaluar
     * @param dimension es la dimensión de la función
     * @return un arreglo con la solución encontrada
     */
    public double[] recocido(int numFun, int dimension) {
        Binario bin = new Binario();
        Evaluador eval = new Evaluador();
        double[] intervalo = eval.intervalo(numFun);

        // solución inicial y temperatura inicial
        int[] solucion = bin.generaSolucionAleatoria(NBITS * dimension);
        double temp = 10000;
        int numIt = 0;

        // condición de término
        while (numIt < MAXITER && temp > 0) {
            numIt++;

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

            // actualizamos temperatura
            temp = temp * 0.999;

        }
        // regresamos la solución decodificada
        return bin.decodifica(solucion, NBITS, intervalo[0], intervalo[1]);
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
