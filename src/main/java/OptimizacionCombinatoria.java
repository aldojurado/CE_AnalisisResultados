import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OptimizacionCombinatoria {

    public void ejecutarAlgoritmo(int enfriamiento, int numFun, int dimension, int seed) {
        Evaluador evaluador = new Evaluador();
        // Recocido simulado
        // solsIteradasR(enfriamiento, numFun, dimension);
        for (int i = 1; i < 3; i++) {
            vsEnfriamientos(i, numFun, dimension, seed);
        }

    }

    /**
     * Imprime el contenido de un arreglo y un valor
     * El contenido representa la solución y el valor la evaluación de la solución
     * 
     * @param res
     * @param valor
     */
    private void imprimeSol(double[] res, double valor) {
        System.out.println("Solución: ");
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + " ");
        }
        System.out.println("\n\nEvaluación de la solución: " + valor);
    }

    /**
     * Genera 10 soluciones aleatorias para una función y lo guarda
     * en un archivo de texto junto con la evaluación de la solución
     * 
     * @param numFun
     * @param dimension
     */
    private void solsIteradasR(int enfriamiento, int numFun, int dimension) {
        Evaluador evaluador = new Evaluador();
        Recocido recocido = new Recocido();
        for (int i = 0; i < 30; i++) {
            System.out.println("\u001B[32m\nBúsqueda # " + i + ":\u001B[0m");
            double[] res = recocido.recocido(enfriamiento, numFun, dimension, 1456, i);
            double valor = evaluador.evaluaEn(numFun, res);

            imprimeSol(res, valor);
            guardarSolucion(res, valor, numFun, i, dimension, "SolucionesRecocido/solRecocido");
        }
    }

    private void vsEnfriamientos(int enfriamiento, int numFun, int dimension, int seed) {

        for (int i = 0; i < 30; i++) {
            Evaluador evaluador = new Evaluador();
            Recocido recocido = new Recocido();
            double[] res = recocido.recocido(enfriamiento, numFun, dimension, seed++, i);
            double valor = evaluador.evaluaEn(numFun, res);
            System.out.println("\u001B[32m\nBúsqueda # " + i + " valor: " + valor + " \u001B[0m");
        }
    }

    /**
     * Guarda la solución y su evaluación en un archivo de texto
     * Este método es el encargado de ir generando los archivos de texto
     * Guarda los archivos con un nombre que indica la forma de optimización y
     * la función que se optimiza
     * Dentro del archivo guarda la solución, su evaluación y la dimensión
     * 
     * @param res
     * @param valor
     * @param numFun
     * @param index
     * @param dimension
     * @param optimizacion
     */
    private void guardarSolucion(double[] res, double valor, int numFun, int index, int dimension,
            String optimizacion) {
        String solucion = "Solución: [";

        // Nombre del archivo donde se guardará el texto
        String nombFun = nombreFuncion(numFun);
        String nombreArchivo = "src/output/solucionesContinuas/" + optimizacion + "_" + nombFun + "_D" + index + ".txt";
        for (int i = 0; i < res.length; i++) {
            solucion += res[i];
            if (i < res.length - 1) {
                solucion += ", ";
            }
        }
        solucion += "]";

        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Escribir la cadena en el archivo
            bufferedWriter.write("Dimensión: " + dimension);
            bufferedWriter.newLine();
            bufferedWriter.write(solucion);
            bufferedWriter.newLine();
            bufferedWriter.write("Evaluación de la solución: " + valor);

            // Cerrar el BufferedWriter
            bufferedWriter.close();

            System.out.println("Texto guardado en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Devuelve el nombre de la función que se está optimizando respecto al índice
     * que se le asigna
     * 
     * @param numFun
     * @return string con nombre de la función
     */
    private String nombreFuncion(int numFun) {
        String nombre = "";
        switch (numFun) {
            case 1:
                nombre = "Sphere";
                break;
            case 2:
                nombre = "Ackley";
                break;
            case 3:
                nombre = "Griewank";
                break;
            case 4:
                nombre = "Rastrigin";
                break;
            case 5:
                nombre = "Rosenbrock";
                break;
            case 6:
                nombre = "SumSquare";
                break;
            case 7:
                nombre = "StyblinskiTang";
                break;
            case 8:
                nombre = "DixonPrice";
                break;
            default:
                nombre = "desconocida";
                break;
        }
        return nombre;
    }

}
