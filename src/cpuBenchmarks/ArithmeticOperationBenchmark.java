public class ArithmeticOperationBenchmark {

    public static void main(String[] args) {
        long numOperations = 1000000000; // Number of arithmetic operations to perform (adjust as needed)

        // Benchmark addition
        long startTimeAddition = System.nanoTime();
        performAddition(numOperations);
        long endTimeAddition = System.nanoTime();
        long elapsedTimeAddition = (endTimeAddition - startTimeAddition) / 1_000_000; // Convert nanoseconds to milliseconds

        // Benchmark multiplication
        long startTimeMultiplication = System.nanoTime();
        performMultiplication(numOperations);
        long endTimeMultiplication = System.nanoTime();
        long elapsedTimeMultiplication = (endTimeMultiplication - startTimeMultiplication) / 1_000_000; // Convert nanoseconds to milliseconds

        // Output benchmark results
        System.out.println("Arithmetic Operation Benchmark:");
        System.out.println("Number of operations: " + numOperations);
        System.out.println("Time taken for addition: " + elapsedTimeAddition + " milliseconds");
        System.out.println("Time taken for multiplication: " + elapsedTimeMultiplication + " milliseconds");
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
