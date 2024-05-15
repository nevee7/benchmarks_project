package benchmark;

public class BenchmarkInfo {
    double initialScore = 0;
    double time = Double.POSITIVE_INFINITY;

    public BenchmarkInfo(double initialScore, double time) {
        this.initialScore = initialScore;
        this.time = time;
    }
}
