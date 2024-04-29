package cpuBenchmarks;

public class FibonacciBenchmark {

    // Recursive method to calculate Fibonacci number
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {
        int n = 47; // Choose the Fibonacci number to calculate (adjust as needed)
        long startTime = System.nanoTime(); // Record start time

        // Calculate Fibonacci number
        long result = fibonacci(n);

        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = (endTime - startTime)/1000000; // Calculate elapsed time in nanoseconds

        // Output the result and elapsed time
        System.out.println("Fibonacci number " + n + " = " + result);
        System.out.println("Time taken: " + elapsedTime + " miliseconds");
    }
}
