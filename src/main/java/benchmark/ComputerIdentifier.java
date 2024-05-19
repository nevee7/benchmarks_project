package benchmark;

public class ComputerIdentifier {
    private String os;
    private String cpu;
    private String gpu;
    private String ram;
    private String UUID;
    public ComputerIdentifier() throws Exception {
        this.os = SystemSpecs.getOSVersion();
        this.cpu = SystemSpecs.getCpuModel();
        this.gpu = SystemSpecs.getGPUModel();
        this.ram = SystemSpecs.getRamCapacity();
        this.UUID = SystemSpecs.computerUUID;
    }

    public String getOs() {
        return os;
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
