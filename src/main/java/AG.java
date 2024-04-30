import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AG {
    final int NBITS = 22;
    final int MAXITER = 1000;

    public double[] algoritmoGenetico(int numFun, int tamPoblacion, int seed, double probCruza, double probMutacion,
            int dimension, int estrategiaSeleccion) {
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
