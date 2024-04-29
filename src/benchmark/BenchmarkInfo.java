import java.math.*;
abstract class Benchmark{
    int steps;
    int time;

    public Benchmark(int steps, int time) {
        this.steps = steps;
        this.time = time;
    }

    abstract double getScore();

}
