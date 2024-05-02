import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.GrayFilter;

public class CE {
    final static String GREEN = "\u001B[32m";
    final static String BLANCO = "\u001B[0m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        imprimeMenu(1);
        int opcion = escaneaNum(2);
        if (opcion == 1) {
            ejecutarRecocido();
        } else if (opcion == 2) {
            ejecutarAlgoritmoGenetico();

        } else {
            System.out.println("Opción inválida");
        }
    }

    private static void ejecutarAlgoritmoGenetico() {
        imprimeMenu(2);
        int numFun = escaneaNum(8);

        System.out.println("Ingrese el tamaño de la población:");
        int tamPoblacion = escaneaNum(200);

        System.out.println("Ingrese la semilla para generar la población inicial:");
        int seed = escaneaNum(Integer.MAX_VALUE);

        double probCruza = escaneaProba(1);

        // double probMutacion = escaneaProba(2);
        double probMutacion = 2 / (double) tamPoblacion;

        System.out.println("Ingrese la dimensión:");
        int dimension = escaneaNum(50);

        imprimeMenu(4);
        int esquemaReemplazo = escaneaNum(3);

        for (int k = 1; k < 4; k++) {
            for (int j = 1; j < 6; j++) {
                int seed2 = seed;
                for (int i = 0; i < 30; i++) {
                    AG ag = new AG();
                    ag.algoritmoGenetico(j, tamPoblacion, seed2++,
                            probCruza, probMutacion, dimension, k, i);

                }
            }
        }

    }

    private static void ejecutarRecocido() {
        imprimeMenu(2);
        int numFun = escaneaNum(8);

        imprimeMenu(3);
        int enfriamiento = escaneaNum(8);

        System.out.println("Ingrese la dimensión:");
        int dimension = escaneaNum(50);

        System.out.println("Ingrese la semilla:");
        int semilla = escaneaNum(Integer.MAX_VALUE);

        OptimizacionCombinatoria metodoOptimizacion = new OptimizacionCombinatoria();
        metodoOptimizacion.ejecutarAlgoritmo(enfriamiento, numFun, dimension, semilla);
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
            System.out.println("Seleccione uno de los siguientes algoritmos:");

            System.out.println("1. Recocido Simulado");
            System.out.println("2. Algoritmo Genético");
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
        } else if (opcion == 3) {
            System.out.println(GREEN + "Seleccione el esquema de enfriamiento:" + BLANCO);
            System.out.println("1. Enfriamiento 1");
            System.out.println("2. Enfriamiento 2");
        } else if (opcion == 4) {
            System.out.println(GREEN + "Seleccione el esquema de reemplazo:" + BLANCO);
            System.out.println("1. Reemplazo generacional");
            System.out.println("2. Reemplazo elitista");
            System.out.println("3. Reemplazo de los peores");
        }
    }

}
