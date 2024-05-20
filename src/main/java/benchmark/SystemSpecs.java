package benchmark;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.UUID;

public class SystemSpecs {
    static String computerUUID;
    public static String generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getOSVersion(){
        return System.getProperty("os.name");
    }

    public static String getCpuModel() throws IOException {
        StringBuilder cpuInfo = new StringBuilder();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            try {
                Process process = Runtime.getRuntime().exec("wmic cpu get name, manufacturer");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                boolean header = true;
                while ((line = reader.readLine()) != null) {
                    if (!header) {
                        cpuInfo.append(line.trim());
                    } else {
                        header = false;
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            cpuInfo.append("This method is for Windows only.");
        }

        return cpuInfo.toString().replaceAll("GenuineIntel ","").replaceAll("AuthenticAMD ","");
    }
    public static String getRamCapacity(){
        // Use platform-specific MXBean for other OS (less reliable)
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean) {
            return Math.round((float) sunOsBean.getTotalMemorySize() / 1024 / 1024 / 1024) +" GB";
        }
        return null;
    }
    public static String getGPUModel() throws IOException {
        String line;
        Process p = Runtime.getRuntime().exec("wmic PATH Win32_videocontroller GET description");
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            if (line.contains("AMD") || line.contains("NVIDIA") || line.contains("INTEL"))
                return line.trim();
        }
        input.close();

        return null;
    }

    public static void isFirstRun() {
        String REGISTRY_PATH = "Software\\StressingTitans";
        String REGISTRY_KEY = "UUID";
        String UUID = generateUUID();
        try {
            if (!Advapi32Util.registryKeyExists(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH)) {
                // The registry key does not exist, meaning it is the first run
                Advapi32Util.registryCreateKey(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH);
                Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, REGISTRY_KEY, UUID);
                computerUUID = UUID;
            } else {
                // The registry key exists, check if the FirstRun value exists
                computerUUID = Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, REGISTRY_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






