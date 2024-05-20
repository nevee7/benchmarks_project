package cpuBenchmarks;

import benchmark.BenchmarkInfo;

public class FibonacciBenchmark {
    double score = 0;

    public void computeScore(double time, long operationNumber){
        this.score = ((double) operationNumber / (time)) * 245000;
    }

    public double getScore(){
        return score;
    }

    // Recursive method to calculate Fibonacci number
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public BenchmarkInfo startBenchmark() {
        int n = 47; // Choose the Fibonacci number to calculate (adjust as needed)
        long startTime = System.nanoTime(); // Record start time

        // Calculate Fibonacci number
        long result = fibonacci(n);

        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = (endTime - startTime)/1000000; // Calculate elapsed time in nanoseconds

        computeScore(result, elapsedTime);
        System.out.println("Score: " + this.getScore());
        return new BenchmarkInfo(this.getClass().getSimpleName(),this.getScore(),0);
    }
}
