package benchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
public class SystemSpecs {
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    public static String getOSVersion(){
        return System.getProperty("os.name");
    }
    public static String getCpuModel() {
        return System.getenv("PROCESSOR_ARCHITECTURE") + " " + Runtime.getRuntime().availableProcessors() + " cores";
    }
    public static String getGPUModel() throws IOException {
        String line;
        Process p = Runtime.getRuntime().exec("wmic PATH Win32_videocontroller GET description");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            if (line.contains("AMD") || line.contains("NVIDIA") || line.contains("INTEL"))
                return line;
        }
        input.close();

        return null;
    }


}

