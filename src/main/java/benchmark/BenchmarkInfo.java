package benchmark;

public class BenchmarkInfo {
    String benchmarkName;
    double initialScore = 0;
    double time = Double.POSITIVE_INFINITY;

    public BenchmarkInfo(String benchmarkName, double initialScore, double time) {
        this.benchmarkName = benchmarkName;
        this.initialScore = initialScore;
        this.time = time;
    }
}
