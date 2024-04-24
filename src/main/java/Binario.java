import java.util.Random;

public class Binario {
    /**
     * Codifica un número entero a un número binario con nBits
     * 
     * @param n    es el número entero a codificar
     * @param nBit es el número de bits que debe tener el número binario codificado
     * @return el número n codificado en binario
     */
    public int[] codifica_aux(int n, int nBit) {
        int[] res = new int[nBit];
        if (Math.pow(2, nBit) > n) {
            String bin = Integer.toBinaryString(n);
            for (int i = 0; i < nBit; i++) {
                if (i < nBit - bin.length()) {
                    res[nBit - 1 - i] = 0;
                } else {
                    res[nBit - 1 - i] = Integer.parseInt(String.valueOf(bin.charAt(i - (nBit - bin.length()))));
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "El número " + n + " no se puede codificar con " + nBit + " bits.");
        }
        return res;
    }

    /**
     * Codifica un número real a un número binario con nBits respecto a un intervalo
     * [a, b]
     * 
     * @param x     es el número real a codificar
     * @param nBits es el número de bits que debe tener el número binario codificado
     * @param a     es el límite inferior del intervalo
     * @param b     es el límite superior del intervalo
     * @return el número x codificado en binario respecto al intervalo proprcionado
     */
    public int[] codifica(double x, int nBits, double a, double b) {
        int[] res = new int[nBits];
        double valoresInte = b - a;
        x = x - a;
        double pres = valoresInte / (Math.pow(2, nBits) - 1);
        double valor = x / pres;
        res = codifica_aux((int) valor, nBits);
        return res;
    }

    /**
     * Decodifica un número binario a un número real respecto a un intervalo [a, b]
     * 
     * @param x_cod es el número binario que representa un número real en cierto
     *              intervalo
     * @param a     es el límite inferior del intervalo
     * @param b     es el límite superior del intervalo
     * @return el número real decodificado
     */
    public double decodifica(int x_cod[], double a, double b) {
        double decimal = decdifica_aux(x_cod);
        double pres = (b - a) / (Math.pow(2, x_cod.length) - 1);
        decimal = decimal * pres + a;
        return decimal;
    }

    /**
     * Decodifica un número binario a un número entero
     * 
     * @param x_cod es el número binario que representa un número entero
     * @return el número entero decodificado
     */
    public double decdifica_aux(int x_cod[]) {
        double decimal = 0;
        for (int i = 0; i < x_cod.length; i++) {
            decimal += x_cod[i] * Math.pow(2, i);
        }
        return decimal;

    }

    /**
     * Codifica un vector de número reales a un vector de números binarios con nBits
     * respecto a un intervalo [a, b]
     * 
     * @param x     es el vector de números reales a codificar
     * @param nBits es el número de bits que debe tener cada uno de los números
     *              binarios codificados
     * @param a     es el límite inferior del intervalo
     * @param b     es el límite superior del intervalo
     * @return un vector con los números reales codificados en binario respecto al
     *         intervalo proprcionado
     */
    public int[] codifica(double x[], int nBits, double a, double b) {
        int res[] = new int[x.length * nBits];
        for (int i = 0; i < x.length; i++) {
            int[] aux = codifica(x[i], nBits, a, b);
            for (int j = 0; j < nBits; j++) {
                res[i * nBits + j] = aux[j];
            }
        }
        return res;
    }

    /**
     * Decodifica un vector de números binarios a un vector de números reales
     * 
     * @param x_cod es el vector de números binarios de nBits que representa un
     *              vector de números reales en cierto intervalo
     * @param nBits el tamaño de cada número binario
     * @param a     es el límite inferior del intervalo
     * @param b     es el límite superior del intervalo
     * @return un vector con los números reales decodificados
     */
    public double[] decodifica(int x_cod[], int nBits, double a, double b) {
        double[] res = new double[x_cod.length / nBits];
        for (int i = 0; i < res.length; i++) {
            int[] aux = new int[nBits];
            for (int j = 0; j < nBits; j++) {
                aux[j] = x_cod[i * nBits + j];
            }
            res[i] = decodifica(aux, a, b);
        }
        return res;
    }

    public int[] vecinoRnd(int[] x) {
        Random random = new Random();
        int[] vecino = x.clone();
        int indiceAleatorio = random.nextInt(x.length);
        vecino[indiceAleatorio] = 1 - x[indiceAleatorio];
        return vecino;
    }

    public int[] generaSolucionAleatoria(int nBITS) {
        int[] res = new int[nBITS];
        Random random = new Random();
        for (int i = 0; i < nBITS; i++) {
            res[i] = random.nextInt(2);
        }
        return res;
    }
}
