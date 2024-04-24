import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CE {
    final static String GREEN = "\u001B[32m";
    final static String BLANCO = "\u001B[0m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        imprimeMenu(1);
        int opcion = escaneaNum(2);
        if (opcion == 1) {
            System.out.println("Ingrese el nombre del archivo: ");
            String rutaIngresada = scanner.next();
            // Tamaño de la población modificable, en este caso 100
            GeneticColoration coloracion = new GeneticColoration(rutaIngresada, 100);
            // Número de generaciones y tasa de mutación modificables, en este caso 100
            // generaciones y 0.1 de tasa
            try {
                coloracion.algoritmoGeneticoElitista(100, 0.1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (opcion == 2) {
            imprimeMenu(2);
            int numFun = escaneaNum(8);

            /*
             * Parámetros:
             * 1.- Tamaño de la población
             * 2.- Semilla para generar población inicial
             * 3.- Probabilidad de cruza (0,1)
             * 4.- Probabilidad de mutación (0,1)
             */
            System.out.println("Ingrese el tamaño de la población:");
            int tamPoblacion = escaneaNum(200);

            System.out.println("Ingrese la semilla para generar la población inicial:");
            int seed = escaneaNum(Integer.MAX_VALUE);

            double probCruza = escaneaProba(1);

            // double probMutacion = escaneaProba(2);
            double probMutacion = 2 / (double) tamPoblacion;

            System.out.println("Ingrese la dimensión:");
            int dimension = escaneaNum(50);

            AG ag = new AG();
            double[] solucion = ag.algoritmoGenetico(numFun, tamPoblacion, seed,
                    probCruza, probMutacion, dimension);
            Evaluador evaluador = new Evaluador();
            System.out.println("El valor de la función es: " + evaluador.evaluaEn(numFun,
                    solucion));

        } else {
            System.out.println("Opción inválida");
        }
    }

    /**
     * Si se le pasa como argumento el 1, escanea la probabilidad de cruza
     * Si se le pasa como argumento el 2, escanea la probabilidad de mutación
     * 
     * @param i
     * @return probabilidad de cruza o mutación
     */
    private static double escaneaProba(int i) {
        double res = Double.MAX_VALUE;
        if (i == 1) {
            System.out.println("Ingrese la probabilidad de cruza:");
        } else if (i == 2) {
            System.out.println("Ingrese la probabilidad de mutación:");

        }
        while (res == Double.MAX_VALUE) {
            Scanner scanner = new Scanner(System.in);
            try {
                double num = scanner.nextDouble();
                if (num < 0 || num > 1) {
                    res = Double.MAX_VALUE;
                    System.out.println("Ingrese un número entre 0 y 1:");
                } else {
                    res = num;
                }
            } catch (Exception e) {
                System.out.println("Se debe ingresar un número");
                res = Double.MAX_VALUE;
                System.out.println("Ingrese un número:");
            }
        }
        return res;
    }

    public static int escaneaNum(int max) {
        int res = Integer.MAX_VALUE;

        while (res == Integer.MAX_VALUE) {
            Scanner scanner = new Scanner(System.in);
            try {
                int num = scanner.nextInt();
                if (num < 1 || num > max) {
                    res = Integer.MAX_VALUE;
                    System.out.println("Ingrese un índice válido:");
                } else {
                    res = num;
                }
            } catch (Exception e) {
                System.out.println("Se debe ingresar un número entero");
                res = Integer.MAX_VALUE;
                System.out.println("Ingrese un entero:");
            }

        }
        return res;
    }

    private static void imprimeMenu(int opcion) {
        if (opcion == 1) {
            System.out.println("Algoritmo genético");
            System.out.println(GREEN + "Seleccione una opción:" + BLANCO);

            System.out.println("1. Problema de coloración BLI");
            System.out.println("2. Problema de optimización continua");
        } else if (opcion == 2) {
            System.out.println(GREEN + "Seleccione la función a evaluar:" + BLANCO);
            System.out.println("1. Sphere Function");
            System.out.println("2. Ackley Function");
            System.out.println("3. Griewank Function");
            System.out.println("4. Rastrigin Function");
            System.out.println("5. Rosenbrock Function");
            System.out.println("6. Sum Square Function");
            System.out.println("7. Styblinski-Tang Function");
            System.out.println("8. Dixon-Price Function");
        }
    }

}
