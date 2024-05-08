package benchmark;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;
public class SystemSpecs {

    public static void main(String[] args) throws IOException {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        System.out.println("Your UUID is: " + uuidAsString);
        System.out.println(getOSVersion());
        System.out.println(getCpuModel());
        System.out.println(getGPUModel());

    }
    private static String getOSVersion(){
        return System.getProperty("os.name");
    }
    private static String getCpuModel() {
        return System.getenv("PROCESSOR_ARCHITECTURE") + " " + Runtime.getRuntime().availableProcessors() + " cores";
    }
    static String getGPUModel() throws IOException {
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

