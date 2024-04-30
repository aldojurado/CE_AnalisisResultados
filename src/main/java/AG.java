import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AG {
    final int NBITS = 22;
    final int MAXITER = 1000;

    public double[] algoritmoGenetico(int numFun, int tamPoblacion, int seed, double probCruza, double probMutacion,
            int dimension, int estrategiaSeleccion, int numArchivo) {
        // Inicializar población
        Poblacion p = new Poblacion(tamPoblacion, NBITS, seed, dimension, numFun);
        double[] promedio = new double[MAXITER];
        double[] mejor = new double[MAXITER];
        double[] hamilton = new double[MAXITER];
        double[] hamiltonAlMej = new double[MAXITER];
        double[] euclides = new double[MAXITER];
        double[] euclidesAlMej = new double[MAXITER];

        // evaluar población

        int iteracion = 0;
        while (iteracion < MAXITER) {
            p.evaluarPoblacion();
            mejor[iteracion] = p.mejorAptitud();
            promedio[iteracion] = p.promedioAptitud();
            /*
             * hamilton[iteracion] = p.hamilton();
             * hamiltonAlMej[iteracion] = p.hamiltonAlMejor();
             * euclides[iteracion] = p.euclides();
             * euclidesAlMej[iteracion] = p.euclidesAlMejor();
             */

            iteracion++;
            // 1.-Selección de padres por ruleta
            /*
             * 1.- esquema generacional
             * 2.- elitismo
             * 3.- reemplazo de los peores
             */
            Poblacion hijos = p.clone();
            if (estrategiaSeleccion == 1 || estrategiaSeleccion == 2) {
                // realiza la cruza por ruleta
                hijos.seleccionarPadresRuletaRecombinar(probCruza);

                if (estrategiaSeleccion == 2) {
                    hijos.elite();

                }

            } else if (estrategiaSeleccion == 3) {
                hijos.seleccionarPadresReemplazarPeores(probCruza);

            }

            hijos.mutar(probMutacion);
            // reemplazo de la nueva generación (sin importar qué estrategia se haya usado)
            p = hijos.clone();
        }
        generarReporte(mejor, promedio, numArchivo, numFun);
        // generarDiversidad(hamilton, hamiltonAlMej, numArchivo, numFun, "hamilton");
        // generarDiversidad(euclides, euclidesAlMej, numArchivo, numFun, "euclides");
        p.evaluarPoblacion();
        return p.mejor();
    }

    private void generarDiversidad(double[] hamilton, double[] euclides, int numArchivo, int numFun, String tipo) {
        String nombreArchivo = "src/output/solucionesContinuas/diversidad/" + tipo + "_" + numFun + "_" + numArchivo
                + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("# iteración promedio promedioAlMejor");
            bufferedWriter.newLine();
            for (int i = 0; i < hamilton.length; i++) {
                bufferedWriter.write(i + " " + hamilton[i] + " " + euclides[i]);
                bufferedWriter.newLine();
            }

            // Cerrar el BufferedWriter
            bufferedWriter.close();

            System.out.println("Texto guardado en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    private void generarReporte(double[] mejor, double[] promedio, int numArchivo, int numFun) {
        String nombreArchivo = "src/output/solucionesContinuas/aptitud/" + numFun + "_" + numArchivo + ".txt";
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
