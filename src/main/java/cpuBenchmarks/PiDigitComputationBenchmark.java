package cpuBenchmarks;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class PiDigitComputationBenchmark {

    // Computes pi using the Bailey–Borwein–Plouffe (BBP) formula
    public static BigDecimal computePi(int numDigits) {
        int scale = numDigits + 10; // Extra digits for precision

        // Chudnovsky algorithm constants
        BigDecimal sqrtC = BigDecimal.valueOf(10005).sqrt(new MathContext(scale));
        BigDecimal a = BigDecimal.valueOf(13591409);
        BigDecimal b = BigDecimal.valueOf(545140134);
        BigDecimal c = BigDecimal.valueOf(-262537412640768000L);
        BigDecimal d = BigDecimal.valueOf(1);

        BigDecimal sum = BigDecimal.ZERO;
        for (int k = 0; k <= numDigits / 14; k++) {
            BigInteger factorial6k = factorial(6 * k);
            BigInteger factorial3k = factorial(3 * k);
            BigInteger factork = factorial(k);

            BigDecimal term1 = new BigDecimal(factorial6k).multiply(a.add(b.multiply(BigDecimal.valueOf(k))));
            BigDecimal term2 = new BigDecimal(factorial3k).multiply(c.pow(k));
            BigDecimal term3 = new BigDecimal(factork).multiply(d.pow(3 * k));
            BigDecimal term = term1.multiply(term2).divide(new BigDecimal(String.valueOf(term3)), scale, RoundingMode.DOWN);
            sum = sum.add(term);
        }

        BigDecimal pi = BigDecimal.ONE.divide(sqrtC.multiply(sum), scale, RoundingMode.DOWN);
        return pi.setScale(numDigits, RoundingMode.DOWN);
    }

    // Computes factorial of n using BigInteger
    private static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) {
        int numDigits = 4000; // Number of digits of pi to compute (adjust as needed)

        long startTime = System.nanoTime(); // Record start time
        BigDecimal pi = computePi(numDigits);
        long endTime = System.nanoTime(); // Record end time
        long elapsedTime = endTime - startTime; // Calculate elapsed time in nanoseconds

        // Convert elapsed time from nanoseconds to milliseconds
        double elapsedTimeMilliseconds = elapsedTime / 1_000_000.0;

        // Output the computed pi and elapsed time in milliseconds
        System.out.println("Computed " + numDigits + " digits of pi:");
        System.out.println(pi);
        System.out.println("Time taken: " + elapsedTimeMilliseconds + " milliseconds");
    }
}
