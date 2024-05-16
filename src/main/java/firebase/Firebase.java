package firebase;

import benchmark.BenchmarkInfo;
import benchmark.ComputerIdentifier;
import benchmark.SystemSpecs;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firebase {
    public static void main(String[] args) throws Exception {
        writeBenchmarkResult(new BenchmarkInfo("CPU",100,1));
    }
    public static void writeBenchmarkResult(BenchmarkInfo benchmarkInfo) throws Exception {
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\Florin\\IdeaProjects\\DC-PROJECT\\resources\\firebase\\serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);

        SystemSpecs.isFirstRun();

        ComputerIdentifier computerIdentifier = new ComputerIdentifier();

        writeData(benchmarkInfo,computerIdentifier);
    }

    private static void writeData(BenchmarkInfo benchmarkInfo, ComputerIdentifier computerIdentifier) throws ExecutionException, InterruptedException, IOException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(computerIdentifier.getUUID());
        Map<String, Object> data = new HashMap<>();
        data.put("OS", computerIdentifier.getOs());
        data.put("CPU", computerIdentifier.getCpu());
        data.put("GPU", computerIdentifier.getGpu());
        data.put("RAM", computerIdentifier.getRam());
        ApiFuture<WriteResult> result = docRef.set(data);

        docRef = db.collection("users").document(computerIdentifier.getUUID()).collection("benchmarks").document(SystemSpecs.generateUUID());
        data = new HashMap<>();
        data.put("Benchmark Name", benchmarkInfo.getBenchmarkName());
        data.put("Score", benchmarkInfo.getScore());
        data.put("Time", benchmarkInfo.getTime());
        result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
    }
}