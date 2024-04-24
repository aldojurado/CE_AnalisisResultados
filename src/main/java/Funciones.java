import java.lang.Math;

public class Funciones {
    public double sumSquare(double[] valores) {
        double res = 0;
        for (int i = 0; i < valores.length; i++) {
            res += (i + 1) * Math.pow(valores[i], 2);
        }
        return res;
    }

    public double StyblinskiTang(double[] x) {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += Math.pow(x[i], 4) - 16 * Math.pow(x[i], 2) + 5 * x[i];
        }
        return res / 2;
    }

    public double dixonPrice(double[] x) {
        double res = Math.pow(x[0] - 1, 2);
        for (int i = 1; i < x.length; i++) {
            res += (i + 1) * Math.pow(2 * Math.pow(x[i], 2) - x[i - 1], 2);
        }
        return res;
    }

    public double sphere(double[] x) {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += x[i] * x[i];
        }
        return res;
    }

    public double ackley(double[] x) {
        double res = 0;
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < x.length; i++) {
            sum1 += x[i] * x[i];
            sum2 += Math.cos(2 * Math.PI * x[i]);
        }
        res = 20 + Math.E - 20 * Math.exp(-0.2 * Math.sqrt(sum1 / x.length)) - Math.exp(sum2 / x.length);
        return res;
    }

    public double griewank(double[] x) {
        double res = 0;
        double aux1 = 0;
        double aux2 = 1;
        for (int i = 0; i < x.length; i++) {
            aux1 += (x[i] * x[i]) / 4000;
            aux2 *= Math.cos(x[i] / Math.sqrt(i + 1));
        }
        res = 1 + aux1 - aux2;
        return res;
    }

    public double rastrigin(double[] x) {
        double res = 10 * x.length;
        for (int i = 0; i < x.length; i++) {
            res += x[i] * x[i] - 10 * Math.cos(2 * Math.PI * x[i]);
        }
        return res;
    }

    public double rosenbrock(double[] x) {
        double res = 0;
        for (int i = 0; i < x.length - 1; i++) {
            res += (1 - x[i]) * (1 - x[i]);
            res += 100 * (x[i + 1] - x[i] * x[i]) * (x[i + 1] - x[i] * x[i]);
        }
        return res;

    }
}
