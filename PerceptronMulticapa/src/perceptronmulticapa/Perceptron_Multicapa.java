/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptronmulticapa;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author octag
 */
public class Perceptron_Multicapa {

    public static void main(String[] args) {
        double[][] arr = {
            {1, 1, 0},
            {1, 0, 1},
            {0, 1, 1},
            {0, 0, 0}
        };
        double w1X1;
        double w2X1;
        double w1X2;
        double w2X2;
        double w1Y1;
        double w1Y2;
        double wBias1;
        double wBias2;
        double wBias3;

        double FA = 0.5;

        double ED1 = 0;
        double ED2 = 0;
        double ED3 = 0;
        double y1 = 0;
        double y2 = 0;
        double y3 = 0;

        int f = 0;
        int ite = 1;

        System.out.println("       Introduce la Cantidad de Iteracciones"+"\n"+"Entre mas Iteraciones, mas se acercara al valor esperado");
        Scanner leerX1 = new Scanner(System.in);        
        int x = Integer.parseInt(leerX1.next());
        
        while (f <= 3) {
            y1 = 0;
            y2 = 0;
            y3 = 0;
            ED1 = 0;
            ED2 = 0;
            ED3 = 0;
            ite = 0;

            w1X1 = new Random().nextDouble();
            w2X1 = new Random().nextDouble();
            w1X2 = new Random().nextDouble();
            w2X2 = new Random().nextDouble();
            w1Y1 = new Random().nextDouble();
            w1Y2 = new Random().nextDouble();
            wBias1 = new Random().nextDouble();
            wBias2 = new Random().nextDouble();
            wBias3 = new Random().nextDouble();

            while (ite <= x) {
                y1 = (arr[f][0] * w1X1) + (arr[f][1] * w1X2) + (1 * wBias1);
                y2 = (arr[f][0] * w2X1) + (arr[f][1] * w2X2) + (1 * wBias2);

                y1 = 1.0 / (1 + Math.pow(Math.E, (-1) * y1));
                y2 = 1.0 / (1 + Math.pow(Math.E, (-1) * y2));

                y3 = (y1 * w1Y1) + (y2 * w1Y2) + (1 * wBias3);

                y3 = 1.0 / (1 + Math.pow(Math.E, (-1) * y3));

                ED3 = (y3 * (1 - y3)) * (arr[f][2] - y3);

                w1Y1 = w1Y1 + (FA * ED3 * y1);
                w1Y2 = w1Y2 + (FA * ED3 * y2);
                wBias3 = wBias3 + (ED3);

                ED1 = (y1 * (1 - y1)) * ED3 - w1Y1;
                ED2 = (y2 * (1 - y2)) * ED3 - w1Y2;

                w1X1 = w1X1 + (FA * ED1 * arr[f][0]);
                w1X2 = w1X2 + (FA * ED1 * arr[f][1]);
                wBias1 = wBias1 + ED1;

                w2X1 = w2X1 + (FA * ED2 * arr[f][0]);
                w2X2 = w2X2 + (FA * ED2 * arr[f][1]);
                wBias2 = wBias2 + ED2;
                ite++;
            }
            System.out.println("" + (int) arr[f][0] + "\tXOR\t" + (int) arr[f][1] + "\tXOR\t" + (int) arr[f][2] + "\tXOR\t" + "Calculado: " + y3);
            f++;
        }
    }
}
