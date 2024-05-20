package cpuBenchmarks;

import java.util.Random;
import benchmark.BenchmarkInfo;

public class MatrixMultiplicationBenchmark {
    double score = 0;

    public void computeScore(double time, long operationNumber) {
        this.score = (double) operationNumber / (time) * 7900;
    }

    public double getScore() {
        return score;
    }

    // Function to multiply two matrices
    public static int[][] multiply(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    public BenchmarkInfo runBenchmark() {
        int size = 1200; // Size of the square matrices (adjust as needed)
        int[][] matrixA = generateRandomMatrix(size, size);
        int[][] matrixB = generateRandomMatrix(size, size);

        long startTime = System.nanoTime(); // Record start time

        // Perform matrix multiplication
        int[][] result = multiply(matrixA, matrixB);

        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = (endTime - startTime) / 1000000; // Calculate elapsed time in milliseconds

        // Output the size of matrices and elapsed time
        System.out.println("Matrix multiplication of size " + size + "x" + size + " took " + elapsedTime + " milliseconds");

        computeScore(elapsedTime, size);
        System.out.println("Score: " + getScore());

        return new BenchmarkInfo(this.getClass().getSimpleName(), getScore(), elapsedTime);
    }

    // Helper function to generate a random matrix of given dimensions
    public static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(100); // Fill matrix with random integers (0-99)
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        MatrixMultiplicationBenchmark benchmark = new MatrixMultiplicationBenchmark();
        BenchmarkInfo info = benchmark.runBenchmark();
        //System.out.println("Benchmark Info: " + info);
    }
}