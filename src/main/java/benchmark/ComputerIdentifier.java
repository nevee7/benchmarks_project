package benchmark;

import java.io.IOException;

public class ComputerIdentifier {
    String os;
    String cpu;
    String gpu;
    String ram;
    String UUID;
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
