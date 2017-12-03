package com.example.azizrafsanjani.numericals.utils;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.util.Precision;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.nio.charset.MalformedInputException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public final class Numericals {
    public enum BinaryOperationType {
        DecimalInteger,
        DecimalFraction,
        Mixed
    }

    /***
     * Converts a whole number from Base 10 to Base 2. Note: Only Integers
     * @param dec A decimal number(n) where n >= 1
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String DecimalIntToBinary(int dec) {
        int Nk = dec;
        StringBuilder binary = new StringBuilder();

        int bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalInteger);

        //magically manipulate and append result to the stringbuilder whilst Nk is greater than 0
        while (Nk != 0 && (Nk - bk != 0)) {
            Nk = (Nk - bk) / 2;
            bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalInteger);
        }

        //placeholder for our final binary result
        String result = binary.reverse().toString();

        return result;
    }

    /***
     * Converts a whole number from Base 10 to Base 2. Note: Only Integers
     * @param dec A decimal number(n) where n >= 1
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String DecimalFractionToBinary(double dec) {
        double Nk = dec;
        StringBuilder binary = new StringBuilder();
        binary.append(".");

        Nk = 2 * Nk;


        int bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalFraction);


        //the magic recipe, (:)
        while (Nk != 0 && ((Nk - bk) != 0)) {
            Nk = (Nk - bk) * 2;
            bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalFraction);
        }

        String result = binary.toString();
        return result;
    }

    /**
     * Converts a decimal integer to a binary numeral but if the decimal has a fractional part,
     * the number is separated into two parts, one being the whole part and the other being the fractional part.
     * the binary equivalent of these parts are individually computed and merged together to form a complete binary
     *
     * @param dec
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String DecimalToBinary(double dec) {

        String decStr = String.valueOf(dec);

        //differentiate decimal numeral into fractional and whole parts
        String wholeStr = decStr.substring(0, decStr.indexOf("."));
        String fractionalStr = "0." + decStr.substring(wholeStr.length() + 1);

        int whole = Integer.parseInt(wholeStr);
        double fractional = Double.parseDouble(fractionalStr);

        //Independently calculate the binary form of the individual parts i.e whole and fractional parts
        String integerResult = DecimalIntToBinary(whole);
        String fractionalResult = DecimalFractionToBinary(fractional);


        //join it all together
        String binary = integerResult + fractionalResult;

        return binary;
    }

    /***
     * Appends the result of the ternary operation on bk to a stringbuilder object supplied as an argument
     * @param N
     * @param sb The stringbuilder object to which the result of the operation will be appended
     * @param op
     * @return int
     */
    private static int AppendToResult(double N, StringBuilder sb, BinaryOperationType op) {
        int bk = 0000; //assign something dummy to prevent compiler issues
        switch (op) {
            case DecimalInteger: //number is exclusively an integer (eg XXX.00000)
                bk = N % 2 == 0 ? 0 : 1;
                break;

            case DecimalFraction: //number is exclusively a fraction (eg 0.XXXXX)
                bk = (N >= 1 ? 1 : 0);
            default:
                //oops
                break;
        }
        //append outcome to our stringbuilder
        sb.append(bk);
        return bk;
    }

    /***
     * Computes the roots of an equation using the bisection method
     * @param expr  an expression of the form f(x) = 0
     * @param x1 The lower boundary of the interval
     * @param  x2 upper boundary of the interval
     * @param iterations The maximum number of times the interval must be bisected
     * @param tol The tolerance level of the answer produced
     * @return double
     */
    public static double Bisect(String expr, double x1, double x2, int iterations, double tol) {

        if (iterations < 1)
            return 0;

        double x3 = (x1 + x2) / 2;

        double tolValue = Math.abs(x1 - x2) / 2;

        //a mathematical function of the form f(x) = 0
        Function fx;

        //is our approximated root less than or equal to the tolerance limit or are we out of moves?
        if (tolValue <= tol || iterations == 1)
            return x3;

        if (expr.contains("f(x)"))
            fx = new Function(expr);
        else
            fx = new Function(String.format("f(x) = %s", expr));

        double fx1 = fx.calculate(x1);
        double fx3 = fx.calculate(x3);

        //the root lies in the left part of the boundary
        if (fx1 * fx3 < 0)
            return Bisect(expr, x1, x3, --iterations, tol);
        else
            //the root lies in the right part of the boundary
            return Bisect(expr, x3, x2, --iterations, tol);
    }

    /***
     * Computes the root of an equation of the form f(x) = 0 using the Newton - Raphson Forumula
     * @param expr a function of the form f(x) = 0
     * @param x1 the initial guess of the root
     * @param maxIterations maximum number of times we are to iterate
     * @return double
     */
    public static Double NewtonRaphson(String expr, double x1, int maxIterations) {
        //TODO: Newton Raphson method goes here
        if (maxIterations < 1) {
            return 0.00;
        }

        if (expr.contains("f(x)")) {
            expr = expr.substring(5);
        }

        Argument x = new Argument(String.format("x = %s", x1));

        Expression ex = new Expression("der(" + expr + ", x)", x);

        Function fx = new Function(String.format("f(x) = %s", expr));

        double fx1 = fx.calculate(x1);
        double derX1 = ex.calculate();

        double approxRoot = x1 - (fx1 / derX1);

        if (maxIterations == 1 || (approxRoot == x1))
            return approxRoot;

        return NewtonRaphson(expr, approxRoot, maxIterations - 1);
    }

    /***
     * Computes the root of an equation of the form f(x) = 0 using the false position (regula falsi) method
     * @param expr an expression of the form f(x) = 0
     * @param x0 The lower boundary of the interval
     * @param x1 The upper boundary of the interval
     * @param maxIterations The maximum number of times the interval must be bisected
     * @param tol
     * @return double
     * @throws IllegalArgumentException When the interval doesn't bracket the root
     */
    public static Double FalsePosition(String expr, double x0, double x1, int maxIterations, double tol) throws IllegalArgumentException {
        if (maxIterations < 1)
            return 0.00;

        //sanitize the equation
        if (expr.contains("="))
            expr = expr.substring(expr.lastIndexOf("=") + 1);


        Function fx = new Function(String.format("f(x) = %s", expr));

        double fx0 = fx.calculate(x0);
        double fx1 = fx.calculate(x1);
        double fx2;

        if (fx0 * fx1 > 0)
            throw new IllegalArgumentException("The function doesn't change sign between the specified intervals");

        double x2 = x1 - ((x1 - x0) / (fx1 - fx0));
        fx2 = fx.calculate(x2);

        if (maxIterations == 1)
            return x2;

        if ((fx0 * fx2) < 0)
            x1 = x2;
        else
            x0 = x2;

        return FalsePosition(expr, x0, x1, maxIterations - 1, 0);
    }

    /***
     * Computes the root of an equation using the secante method
     * @param expr an equation of the form f(x) = 0
     * @param x0
     * @param x1
     * @param maxIterations The maximum number of iterations to be conducted
     * @return double
     */

    public static Double Secante(String expr, double x0, double x1, int maxIterations) {
        if (maxIterations < 1)
            return 0.00;

        if (expr.contains("=")) {
            expr = expr.substring(expr.lastIndexOf("=") + 1);
        }

        Function fx = new Function("f(x) = " + expr);
        double fx0 = fx.calculate(x0);
        double fx1 = fx.calculate(x1);

        double x2 = x1 - ((x1 - x0) / (fx1 - fx0));

        if (maxIterations == 1)
            return x2;

        return Secante(expr, x2, x1, maxIterations - 1);
    }

    public static double[] GaussianWithCompletePivoting(double[][] A, double B[]) {
        int N = B.length;
        for (int k = 0; k < N; k++) {
            //get pivot column
            int maxColumn = getPivotColumn(A, k);
            //swap pivot column
            swapColumns(A, maxColumn, k);

            //get the pivot row
            int maxRow = getPivotRow(A, k);
            //swap the pivot row with the first row in matrix A
            swapRows(A, maxRow, k);

            //swap corresponding values of the pivot row in the solution matrix
            double temp = B[k];
            B[k] = B[maxRow];
            B[maxRow] = temp;

            //reduce all elements beneath the pivot row
            killRowsBeneath(A, B, k);
        }

        //solve by backsubstitution
        double[] solution = getSolutionByBackSubstitution(A, B, N);

        return solution;
    }

    public static double[] GaussianWithPartialPivoting(double[][] A, double B[]) {
        int N = B.length;
        for (int k = 0; k < N; k++) {
            //get the pivot row
            int max = getPivotRow(A, k);

            //swap the pivot row with the first row in matrix A
            swapRows(A, max, k);

            //swap corresponding values of the pivot row in the solution matrix
            double temp = B[k];
            B[k] = B[max];
            B[max] = temp;

            //reduce all elements beneath the pivot row
            killRowsBeneath(A, B, k);
        }

        //solve by backsubstitution
        double[] solution = getSolutionByBackSubstitution(A, B, N);

        return solution;
    }

    private static double[] getSolutionByBackSubstitution(double A[][], double B[], int N) {
        double[] solution = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++)
                sum += A[i][j] * solution[j];
            solution[i] = (B[i] - sum) / A[i][i];
        }
        //round all array contents to 2dp
        roundTo2Dp(A, B);
        roundTo2Dp(solution);
        return solution;
    }

    private static void killRowsBeneath(double A[][], double B[], int k) {
        int N = A.length;
        for (int i = k + 1; i < N; i++) {
            double factor = A[i][k] / A[k][k];
            B[i] -= factor * B[k];
            for (int j = k; j < N; j++)
                A[i][j] -= factor * A[k][j];
        }
    }

    private static void roundTo2Dp(double A[][], double B[]) {
        for (int i = 0; i < A.length; i++) {
            B[i] = Double.parseDouble(String.format("%.2f", B[i]));
            for (int j = 0; j < A.length; j++) {
                A[i][j] = Double.parseDouble(String.format("%.2f", A[i][j]));
            }
        }
    }

    private static void roundTo2Dp(double[] B) {
        for (int i = 0; i < B.length; i++) {
            B[i] = Double.parseDouble(String.format("%.2f", B[i]));
        }
    }

    private static int getPivotRow(double system[][], int k) {
        int maxRowIndex = k;

        for (int i = k + 1; i < system.length; i++) {
            if (Math.abs(system[i][k]) > Math.abs(system[maxRowIndex][k]))
                maxRowIndex = i;
        }

        return maxRowIndex;
    }

    private static void swapRows(double system[][], int maxRow, int rowIndex) {
        double[] temp = system[rowIndex];
        system[rowIndex] = system[maxRow];
        system[maxRow] = temp;
    }

    public static void swapColumns(double system[][], int maxCol, int colIndex) {
        for (int i = 0; i < system.length; i++) {
            ArrayUtils.swap(system[i], maxCol, colIndex);
        }
    }

    public static int getPivotColumn(double[][] system, int k) {
        int N = system.length;
        int maxColIndex = k;
        int maxRowIndex;

        double maxNumber = -1;

        for (int i = k; i < N; i++) {
            for (int j = k; j < N; j++) {
                if (system[i][j] > maxNumber) {
                    maxRowIndex = i;
                    maxColIndex = j;
                    maxNumber = system[maxRowIndex][maxColIndex];
                }
            }
        }

        return maxColIndex;
    }

    private static void printMatrix(double system[][]) {
        for (int rowIndex = 0; rowIndex < system.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < system.length; columnIndex++) {
                System.out.print(system[rowIndex][columnIndex] + " ");
            }
            System.out.println();
        }
    }

    /***
     * Solves a system of linear equations using Jacobi's method
     * @param system
     * @return
     */
    public static double[] Jacobi(String[] system, double[] initGuess, double epsilon) {
        double iSolution[] = new double[3];

        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuess[0]));
            fx.addArguments(new Argument("x2 = " + initGuess[1]));
            fx.addArguments(new Argument("x3 = " + initGuess[2]));

            iSolution[i] = fx.calculate();

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }


        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);

        //stopping criteria
        if (iNorm < epsilon) {
            return iSolution;
        } else {
            return Jacobi(system, iSolution, epsilon);
        }
    }


    private static double getMaxElement(double[] array) {
        double max = 0;
        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i]) > Math.abs(max))
                max = array[i];
        }

        return Math.abs(max);
    }

    /***
     * Solves a system of linear equations using Gauss-Seidel's method
     * @param system
     * @return
     */
    public static double[] GaussSeidel(String[] system, double initGuess[], double epsilon) {
        double iSolution[] = new double[3];
        double[] initGuessTemp = initGuess.clone();


        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuessTemp[0]));
            fx.addArguments(new Argument("x2 = " + initGuessTemp[1]));
            fx.addArguments(new Argument("x3 = " + initGuessTemp[2]));

            iSolution[i] = fx.calculate();
            initGuessTemp[i] = iSolution[i];

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }

        //print out the solution vector
        System.out.println("The solution vector is given as");
        printArray(iSolution);

        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        System.out.println("The difference vector is given as:");
        printArray(difference);

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);
        System.out.println("Infininte norm is given as:  " + iNorm);
        System.out.println("Epsilon is given as: " + epsilon);
        //stopping criteria
        if (iNorm < epsilon) {
            System.out.println("stopping criteria met: terminating");
            return iSolution;
        } else {
            System.out.println("stopping criteria not met, reiteriating with these values");
            System.out.println("Guesses; ");
            printArray(iSolution);
            return GaussSeidel(system, iSolution, epsilon);
        }

    }

    public static double[] GaussSeidelWithSOR(String[] system, double initGuess[], double epsilon, double omega) {
        double iSolution[] = new double[3];
        double[] initGuessTemp = initGuess.clone();


        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuessTemp[0]));
            fx.addArguments(new Argument("x2 = " + initGuessTemp[1]));
            fx.addArguments(new Argument("x3 = " + initGuessTemp[2]));

            iSolution[i] = (fx.calculate() * omega) + (((1 - omega) * initGuess[i]));
            initGuessTemp[i] = iSolution[i];

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }

        //print out the solution vector
        System.out.println("The solution vector is given as");
        printArray(iSolution);

        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        System.out.println("The difference vector is given as:");
        printArray(difference);

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);
        System.out.println("Infininte norm is given as:  " + iNorm);
        System.out.println("Epsilon is given as: " + epsilon);
        //stopping criteria
        if (iNorm < epsilon) {
            System.out.println("stopping criteria met: terminating");
            return iSolution;
        } else {
            System.out.println("stopping criteria not met, reiteriating with these values");
            System.out.println("Guesses; ");
            printArray(iSolution);
            return GaussSeidelWithSOR(system, iSolution, epsilon, omega);
        }

    }


    private static void printArray(double[] array) {
        System.out.print("[ ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("]");
    }


    /***
     * Solves a system of linear equations using the Gauss-Seidel's method with Successive Over Relaxation
     * @param system
     * @return
     */
    public static double[] gaussSeidelWithXOR(String[][] system) {
        return new double[20];
    }
}
