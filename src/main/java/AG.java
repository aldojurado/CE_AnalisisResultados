import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AG {
    final int NBITS = 22;
    final int MAXITER = 1000;

    public double[] algoritmoGenetico(int numFun, int tamPoblacion, int seed, double probCruza, double probMutacion,
            int dimension) {
        // Inicializar población
        Poblacion p = new Poblacion(tamPoblacion, NBITS, seed, dimension, numFun);
        double[] promedio = new double[MAXITER];
        double[] mejor = new double[MAXITER];

        // evaluar población

        int iteracion = 0;
        while (iteracion < MAXITER) {
            p.evaluarPoblacion();
            mejor[iteracion] = p.mejorAptitud();
            promedio[iteracion] = p.promedioAptitud();

            iteracion++;
            // 1.-Selección de padres por ruleta
            Poblacion hijos = p.clone();
            // 2.-Recombinación, cruz a de un punto con probabilidad probCruza 2 sugerido
            hijos.seleccionarPadresRecombinar(probCruza);

            /*
             * 3.-Mutación, intercambio 1 bit con probabilidad probMutacion
             * 2 / (tamPoblacion) sugerido para que se muten dos individuos
             */
            hijos.mutar(probMutacion);

            // Elitismo guardamos al mejor individuo de la población actual antes de
            // evaluarla de nuevo en la siguiente iteración
            hijos.elite();

            // 4.-Reemplazo generacional
            p = hijos.clone();
        }
        generarReporte(mejor, promedio);
        p.evaluarPoblacion();
        return p.mejor();
    }

    private void generarReporte(double[] mejor, double[] promedio) {
        String nombreArchivo = "src/output/solucionesContinuas/reporte.txt";
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("# iteración mejor promedio");
            bufferedWriter.newLine();
            for (int i = 0; i < promedio.length; i++) {
                bufferedWriter.write(i + " " + mejor[i] + " " + promedio[i]);
                bufferedWriter.newLine();
            }

            // Cerrar el BufferedWriter
            bufferedWriter.close();

            System.out.println("Texto guardado en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

}
