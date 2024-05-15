package benchmark;

public class ComputerIdentifier {
    String cpu;
    String gpu;
    String ram;
    String UUID;
    public ComputerIdentifier(String cpu, String gpu, String ram, String UUID) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.UUID = UUID;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public String getRam() {
        return ram;
    }

    public String getUUID() {
        return UUID;
    }
}
