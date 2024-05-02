import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AG {
    final int NBITS = 22;
    final int MAXITER = 2500;

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
        double[] entropia = new double[MAXITER];
        int[][] frecuencias = new int[2][MAXITER];

        // evaluar población

        int iteracion = 0;
        while (iteracion < MAXITER) {
            p.evaluarPoblacion();
            // mejor[iteracion] = p.mejorAptitud();
            // promedio[iteracion] = p.promedioAptitud();
            /*
             * mejor[iteracion] = p.mejorAptitud();
             * promedio[iteracion] = p.promedioAptitud();
             * hamilton[iteracion] = p.hamilton();
             * hamiltonAlMej[iteracion] = p.hamiltonAlMejor();
             * euclides[iteracion] = p.euclides();
             * euclidesAlMej[iteracion] = p.euclidesAlMejor();
             * entropia[iteracion] = p.entropia();
             */
            if (iteracion == 0) {
                frecuencias[0] = p.frecuencias();
            }
            if (iteracion == 14000) {
                frecuencias[1] = p.frecuencias();

            }

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
        generarReporteFrecuencias(frecuencias, numArchivo, numFun);
        // generarReportePromedio(promedio, numArchivo, estrategiaSeleccion);
        // generarReportePromedio(mejor, numArchivo, estrategiaSeleccion);
        // generarReporte(mejor, promedio, numArchivo, estrategiaSeleccion, numFun);
        // generarReporte(mejor, promedio, numArchivo, numFun);
        // generarDiversidad(hamilton, hamiltonAlMej, numArchivo, numFun, "hamilton");
        // generarDiversidad(euclides, euclidesAlMej, numArchivo, numFun, "euclides");
        // generarReporteEntropia(entropia);
        p.evaluarPoblacion();
        return p.mejor();
    }

    private void generarReporteFrecuencias(int[][] frecuencias, int numArchivo, int numFun) {
        for (int k = 0; k < 2; k++) {
            String nombreArchivo = "src/output/solucionesContinuas/entropia/AG_entropiaFrec" + k
                    + "_Fun_Rosenbrock.txt";
            int[] frec = frecuencias[k];
            try {
                FileWriter fileWriter = new FileWriter(nombreArchivo);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("# iteración frecuencias");
                bufferedWriter.newLine();
                for (int i = 0; i < frec.length; i++) {
                    bufferedWriter.write(i + " " + frec[i]);
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

    private void generarReporteEntropia(double[] entropia) {
        String nombreArchivo = "src/output/solucionesContinuas/entropia/AG_entropia_Fun_Rosenbrock.txt";
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("# iteración entropia");
            bufferedWriter.newLine();
            for (int i = 0; i < entropia.length; i++) {
                bufferedWriter.write(i + " " + entropia[i]);
                bufferedWriter.newLine();
            }

            // Cerrar el BufferedWriter
            bufferedWriter.close();

            System.out.println("Texto guardado en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
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

    private void generarReporte(double[] mejor, double[] promedio, int numArchivo, int esquemaReemplazo, int fun) {
        String nombreArchivo = "src/output/solucionesContinuas/boxplot/AG" + esquemaReemplazo + "_" + fun + "_"
                + numArchivo
                + ".txt";
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

    private void generarReportePromedio(double[] mejor, double[] promedio, int numArchivo, int numFun) {
        String nombreArchivo = "src/output/solucionesContinuas/aptitud/AG" + numFun + "_" + numArchivo + ".txt";
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
