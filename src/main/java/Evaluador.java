import java.util.Random;

public class Evaluador {
    public double evaluaEn(int k, double[] valores) {
        Funciones funciones = new Funciones();
        switch (k) {
            case 1:
                return funciones.sphere(valores);
            case 2:
                return funciones.ackley(valores);
            case 3:
                return funciones.griewank(valores);
            case 4:
                return funciones.rastrigin(valores);
            case 5:
                return funciones.rosenbrock(valores);
            case 6:
                return funciones.sumSquare(valores);
            case 7:
                return funciones.StyblinskiTang(valores);
            case 8:
                return funciones.dixonPrice(valores);
        }
        return 11111111111.0;
    }

    public double[] intervalo(int numFun) {
        double[] res = new double[2];
        if (numFun == 1 || numFun == 4 || numFun == 6) {
            res[0] = -5.12;
            res[1] = 5.12;
        } else if (numFun == 2) {
            res[0] = -30;
            res[1] = 30;
        } else if (numFun == 3) {
            res[0] = -600;
            res[1] = 600;
        } else if (numFun == 5) {
            res[0] = -2.048;
            res[1] = 2.048;

        } else if (numFun == 7) {
            res[0] = -5;
            res[1] = 5;
        } else if (numFun == 8) {
            res[0] = -10;
            res[1] = 10;
        }
        return res;
    }
}
