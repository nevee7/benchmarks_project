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

    public BenchmarkInfo runBenchmark(int n) {
        long startTime = System.nanoTime(); // Record start time

        // Calculate Fibonacci number
        long result = fibonacci(n);

        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = (endTime - startTime)/1000000; // Calculate elapsed time in nanoseconds

        // Output the result and elapsed time
        System.out.println("Fibonacci number " + n + " = " + result);
        System.out.println("Time taken: " + elapsedTime + " milliseconds");

        // Compute and print score
        computeScore(elapsedTime, n);
        System.out.println("Score: " + getScore());

        // Return BenchmarkInfo
        return new BenchmarkInfo(this.getClass().getSimpleName(), getScore(), elapsedTime);
    }

    public static void main(String[] args) {
        FibonacciBenchmark benchmark = new FibonacciBenchmark();
        BenchmarkInfo benchmarkInfo = benchmark.runBenchmark(47);
        // Use benchmarkInfo object as needed
    }
}
