package cpuBenchmarks;

import benchmark.BenchmarkInfo;

public class ArithmeticOperationBenchmark {
    double score = 0;

    public void computeScore(long time1, long time2, long operationNumber){
        this.score = (double) operationNumber / (time1 + time2) ;
    }

    public double getScore(){
        return score;
    }

    public BenchmarkInfo startBenchmark() {
        double benchmarkInfo1;
        double benchmarkInfo2;
        double benchmarkInfo3;
        ArithmeticOperationBenchmark B1 = new ArithmeticOperationBenchmark();
        for (int k = 0; k < 5; k++) {
            B1.runBenchmark();
        }
        benchmarkInfo1 = B1.runBenchmark();
        benchmarkInfo2 = B1.runBenchmark();
        benchmarkInfo3 = B1.runBenchmark();
        return new BenchmarkInfo(this.getClass().getSimpleName(),(benchmarkInfo1+benchmarkInfo2+benchmarkInfo3)/3,0);
    }

    public double runBenchmark() {
        long operationNumber = 1_000_000_000; // Number of arithmetic operations to perform (adjust as needed)

        // Benchmark addition
        long startTimeAddition = System.nanoTime();
        performAddition(operationNumber);
        long endTimeAddition = System.nanoTime();
        long elapsedTimeAddition = (endTimeAddition - startTimeAddition) / 1_000_000; // Convert nanoseconds to milliseconds

        // Benchmark multiplication
        long startTimeMultiplication = System.nanoTime();
        performMultiplication(operationNumber);
        long endTimeMultiplication = System.nanoTime();
        long elapsedTimeMultiplication = (endTimeMultiplication - startTimeMultiplication) / 1_000_000; // Convert nanoseconds to milliseconds

        // Output benchmark results
        computeScore(elapsedTimeAddition,elapsedTimeMultiplication,operationNumber);
        score = score / 500;
        System.out.println("Score: " + score);
        return score;
    }

    // Perform addition operations
    public static void performAddition(long numOperations) {
        double sum = 0.0;
        for (int i = 0; i < numOperations; i++) {
            sum += 1.0; // Increment by 1.0 (simulating addition)
        }
    }

    // Perform multiplication operations
    public static void performMultiplication(long numOperations) {
        double product = 1.0;
        for (int i = 0; i < numOperations; i++) {
            product *= 2.0; // Multiply by 2.0 (simulating multiplication)
        }
    }
}

