package com.example.core;

/*
 * Created by Aziz Rafsanjani on 10/21/2017.
 */


import com.foreverrafs.core.LocationOfRootResult;
import com.foreverrafs.core.Numericals;
import com.foreverrafs.core.OdeResult;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class NumericalsTest {
    private static void printMatrix(double[][] matrix) {
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(aMatrix[j] + " ");
            }
            System.out.println();
        }
    }

    private static void printMatrix(double[][] A, double[] B) {
        for (int rowIndex = 0; rowIndex < A.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < A.length; columnIndex++) {
                System.out.print(A[rowIndex][columnIndex] + " ");
            }
            System.out.print(B[rowIndex]);
            System.out.println();
        }
    }

    private static void printMatrix(double[] system) {
        for (double aSystem : system) {
            System.out.print(aSystem + " ");
        }
    }

    @Test
    public void getIterationsWithTolerance() {
        double x1 = -2;
        double x2 = -1;
        double tol = 0.0039;

        int iterations = Numericals.getBisectionIterations(tol, x1, x2);
        System.out.println(iterations);

        assertEquals(iterations, 8);
    }

    @Test
    public void getToleranceWithIterations() {
        double x1 = -2;
        double x2 = -1;
        int iterations = 8;

        double tolerance = Numericals.getBisectionTolerance(iterations, x1, x2);

        assertEquals(tolerance, 0.00390625);
    }

    @Test
    public void binaryToDecimal() {
        assertEquals("1010", Numericals.decimalIntToBinary(10));
    }

    @Test
    public void generateTexEquation() {
        String tex = Numericals.generateTexEquation("f(x) = 3*x^2 - 2");
        String texFormatted = "$$f(x) = 3x^2 - 2$$";
        assertEquals(texFormatted, tex);
    }

    @Test
    public void Bisection() {
        String expr = "x^5 + x^3 + 3x";
        double x1 = -2;
        double x2 = -1;
        int iterations = 1;
        double tolerance = 0.005;

        List<LocationOfRootResult> results = Numericals.bisect(expr, x1, x2, iterations, tolerance);
        double root = results.get(results.size() - 1).getX3();
        //assertEquals(y, root);
    }

    @Test
    public void testBisectionAllShouldPass() {
        List<LocationOfRootResult> longBisection = Numericals.bisect("x^5 + x^3 + 3*x", -2, -1, 4, 0.005);
        assertEquals(-1.0625, longBisection.get(3).getX3());
    }

//    @Test
//    public void testNewtonRaphson() {
//        String eqn = "x^5 + x^3+3";
//        double y = Numericals.newtonRaphsonAll(eqn, -1, 20);
//        assertEquals(-1.1052985460061695, y);
//    }

    @Test
    public void testNewtonRaphsonAll() {
        String eqn = "x^5 + x^3+3";
        List<LocationOfRootResult> results = Numericals.newtonRaphsonAll(eqn, -1, 20);
        double firstRoot = -1.1052985460061695;

        assertTrue(results.size() > 0);
        assertEquals(firstRoot, results.get(results.size() - 1).getX1());
    }

    @Test
    public void GaussianComplete() {
        //Note: final answer to this system is {-0.5, 0.5, 0.5}
        double[][] A = {
                {1, -2, -3},
                {3, 5, 2},
                {2, 3, -1}
        };

        double[] B = {0, 0, -1};

        double[] expected = {-0.5, 0.5, 0.5};

        double[] sol = Numericals.gaussianWithCompletePivoting(A, B);

        assertArrayEquals(expected, sol, 0.1);
    }

    @Test
    public void testGaussianWithPartialPivoting() {
        //Note: final answer to this system is {0.5, -0.5, 0.5}
        double[][] A = {
                {1, -2, -3},
                {3, 5, 2},
                {2, 3, -1}
        };

        double[] B = {0, 0, -1};

        double[] expected = {0.5, -0.5, 0.5};
        double[] sol = Numericals.gaussianWithPartialPivoting(A, B);

        assertArrayEquals(expected, sol, 0.1);
    }

    @Test
    public void testGaussianPartial4x4Matrix() {
        double[][] A = {
                {8, 3, 4, 5},
                {14, 4, 33, 23},
                {15, 4, 23, 7},
                {4, 11, 17, 16}
        };

        // double[] solution = {-14.48, 19.56, 34.12, -5.68};

        double[] B = {31, 17, 22, 51};

        double[] iSolution = Numericals.gaussianWithCompletePivoting(A, B);
        //note solution to the above big matrix is {59.5, -67.5, 87,-55, -20.5}
        printMatrix(A);
    }

    @Test
    public void gaussianComplete4x4Matrix() {
        double[][] A = {
                {1, -4, 4, 7},
                {0, 2, -1, 0},
                {2, 1, 1, 4},
                {2, -3, 2, -5}
        };

        double[] expected = {-14.48, 19.56, 34.12, -5.68};

        double[] B = {4, 5, 2, 9};

        double[] sol = Numericals.gaussianWithCompletePivoting(A, B);

        assertArrayEquals(expected, sol, 0.001);


        //ANOTHER EXAMPLE
        double[][] A2 = {
                {1, 2, 4, 3, 5},
                {3, 5, 3, 1, 2},
                {1, 4, 4, 2, 1},
                {4, 1, 2, 5, 3},
                {5, 2, 1, 4, 1}
        };

        double[] B2 = {5, 6, 7, 8, 9};
        double[] expected2 = {59.5, -67.5, 87, -55, -20.5};


        double[] sol2 = Numericals.gaussianWithCompletePivoting(A2, B2);
        //note solution to the above big matrix is {59.5, -67.5, 87,-55, -20.5}
        assertArrayEquals(expected2, sol2, 0.001);

    }

    @Test
    //This test should pass
    public void multiplyMatrices() {
        double[][] A = {
                {4, 3, 5},
                {4, 1, 2},
                {5, 6, 8}
        };
        double[] B = {2, 3, 5};

        double[] solution = Numericals.multiplyMatrix(A, B);
        double[] expected = {42, 21, 68};

        assertArrayEquals(expected, solution, 0.001);
    }

    @Test
    public void multiply() {
        double[][] A = {
                {0, 1, 0},
                {1, 0, 0},
                {0, 0, 1}
        };

        double[][] B = {
                {-0.5, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        double[] C = {-0.5, 0.5, 0.5};

        double[] D = Numericals.multiplyMatrix(A, C);
        printMatrix(D);

    }

    @Test
    public void jacobi() {
        String[] system = {
                "(1/2)*(x2+1)",
                "(1/3)*(x1+x3+8)",
                "(1/2)*(x2-5)"};
        double[] initGuess = {0, 0, 0};
        double[] sol = Numericals.jacobi(system, initGuess, 0.15);

    }


    @Test
    public void testRegulaFalsi() {
        String eqn = "f(x)=x^5 + x^3+3";
        double y = Numericals.falsePosition(eqn, -2, -1, 100, 0);
    }

    @Test
    public void testRegulaFalsiAll() {
        String eqn = "x^5 + x^3 +3";
        List<LocationOfRootResult> here = Numericals.falsePositionAll(eqn, -2, -1, 100, 0);
        System.out.println("  x1     x2      x3     fx1    fx2     fx3   diff");
        for (LocationOfRootResult x : here) {
            System.out.printf("%f %f %f %f %f %f %f", x.getX1(), x.getX2(), x.getX3(), x.getFx1(), x.getFx2(), x.getFx3(), x.getDifference());
            System.out.println();
        }
    }

    @Test
    public void secante() {
        String eqn = "x^3 + x^3 + 3";
        List<LocationOfRootResult> result = Numericals.secanteAll(eqn, 1.0, -1.0, 800);
        System.out.println("X1      X2        X3      DIFFERENCE");

        for (LocationOfRootResult y : result) {
            System.out.println(y.getIteration() + ":::::" + y.getX1() + " " + y.getX2() + " " + y.getX3() + " " + y.getDifference());
        }
        assertEquals(-1.5, result.get(0).getX3());
    }


    @Test
    public void testDecimalToHexadecimal() {
        String decimal = "10";
        String hex = Numericals.decimalToHexadecimal(decimal);

        assertEquals(hex, "A");
    }

    @Test
    public void testDecimalToHexadecimalFraction() {
        String decimal = String.valueOf(Math.PI);
        String hex = Numericals.decimalToHexadecimal(decimal);

        assertEquals(hex, "3.243F6A8885A3");
    }

    @Test
    public void lagrangeForwardInterpolation() {
        String eqn = "x-y^2";
        double yo = 0;
        double height = 0.2;
        double[] interval = {0, 1};

        List<OdeResult> results = Numericals.solveOdeByEulersMethod(eqn, height, interval, yo);

        assertEquals(0.23681533952, results.get(4).getY());
    }

    @Test
    public void EulerForwardMethodTest2() {
        String eqn = "-2*x*y^2";
        double yo = 1;
        double height = 0.2;
        double[] interval = {0, 1};

        List<OdeResult> results = Numericals.solveOdeByEulersMethod(eqn, height, interval, yo);

        for (OdeResult result : results) {
            System.out.println(result.getN() + " " + result.getX() + "  " + result.getY());
        }
    }

    @Test
    public void interpolateLagrangically() {
        double[] x = {-2, -1, 0, 4};
        double[] y = {-2, 4, 1, 8};
        int xx = 2;
        double yy = Numericals.interpolate(x, y, xx);
        assertEquals(-10.20, Double.parseDouble(String.format("%.2f", yy)));
    }

    @Test
    public void testIsBinary() {
        assertTrue(Numericals.isBinary("101010.1010.10101"));
    }
}
