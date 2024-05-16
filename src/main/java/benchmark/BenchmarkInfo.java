package benchmark;

public class BenchmarkInfo {
    String benchmarkName;
    double score = 0;
    double time = Double.POSITIVE_INFINITY;

    public BenchmarkInfo(String benchmarkName, double score, double time) {
        this.benchmarkName = benchmarkName;
        this.score = score;
        this.time = time;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public double getScore() {
        return score;
    }

    public double getTime() {
        return time;
    }
}
