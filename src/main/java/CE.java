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

        System.out.println("Ingrese la dimensión:");
        int esquemaReemplazo = escaneaNum(3);

        AG ag = new AG();
        double[] solucion = ag.algoritmoGenetico(numFun, tamPoblacion, seed,
                probCruza, probMutacion, dimension, esquemaReemplazo);
        Evaluador evaluador = new Evaluador();
        System.out.println("El valor de la función es: " + evaluador.evaluaEn(numFun,
                solucion));
    }

    private static void ejecutarRecocido() {
        imprimeMenu(2);
        int numFun = escaneaNum(8);

        imprimeMenu(3);
        int enfriamiento = escaneaNum(8);

        System.out.println("Ingrese la dimensión:");
        int dimension = escaneaNum(50);

        OptimizacionCombinatoria metodoOptimizacion = new OptimizacionCombinatoria();
        metodoOptimizacion.ejecutarAlgoritmo(enfriamiento, numFun, dimension);
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
            System.out.println(GREEN + "Seleccione la función a evaluar:" + BLANCO);
            System.out.println("1. Enfriamiento 1");
            System.out.println("2. Enfriamiento 2");
        }
    }

}
