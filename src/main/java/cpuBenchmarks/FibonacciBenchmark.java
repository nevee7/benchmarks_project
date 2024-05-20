package cpuBenchmarks;

import benchmark.BenchmarkInfo;


public class FibonacciBenchmark {
    double score = 0;

    public static void main(String[] args) {
        FibonacciBenchmark benchmark = new FibonacciBenchmark();
        benchmark.startBenchmark();
        //System.out.println(benchmark.getScore());
    }

    public void computeScore(long operationNumber,double time){
        this.score = ((double) operationNumber / (time)) * 4400;
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
        double benchmarkInfo1;
        double benchmarkInfo2;
        double benchmarkInfo3;
        FibonacciBenchmark B1 = new FibonacciBenchmark();
        for (int k = 0; k < 5; k++) {
            B1.runBenchmark();
        }
        benchmarkInfo1 = B1.runBenchmark();
        benchmarkInfo2 = B1.runBenchmark();
        benchmarkInfo3 = B1.runBenchmark();
        return new BenchmarkInfo(this.getClass().getSimpleName(),(benchmarkInfo1+benchmarkInfo2+benchmarkInfo3)/3,0);
    }
    public double runBenchmark() {
        int n = 47; // Choose the Fibonacci number to calculate (adjust as needed)
        long startTime = System.nanoTime(); // Record start time

        // Calculate Fibonacci number
        long result = fibonacci(n);

        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = (endTime - startTime); // Calculate elapsed time in nanoseconds

        computeScore(result, elapsedTime);
        System.out.println("Score: " + this.getScore());
        return this.getScore();
    }
}
