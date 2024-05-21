package firebase;

import benchmark.BenchmarkInfo;
import benchmark.ComputerIdentifier;
import benchmark.SystemSpecs;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Firebase {
    public static void main(String[] args) throws Exception {
        initializeFirebase();
        writeBenchmarkResult(new BenchmarkInfo("CPU",100,1),new ComputerIdentifier());
        //System.out.println(getMyData("CPU",new ComputerIdentifier()));
        ArrayList<String> benchmarks = new ArrayList<>();
        benchmarks.add("ArithmeticOperationBenchmark");
        benchmarks.add("FibonacciBenchmark");
        benchmarks.add("MatrixMultiplicationBenchmark");
        benchmarks.add("PiDigitComputationBenchmark");
        benchmarks.add("GPU benchmark");
        for (String name: benchmarks) {

            System.out.println(Firebase.getMyData(name,new ComputerIdentifier()));
            System.out.println(Firebase.getAllData(name).indexOf(getMyData(name,new ComputerIdentifier())));
        }
    }

    public static void initializeFirebase() throws IOException {
        SystemSpecs.isFirstRun();
        FileInputStream serviceAccount = new FileInputStream("resources/firebase/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);
    }

    public static void writeBenchmarkResult(BenchmarkInfo benchmarkInfo, ComputerIdentifier computerIdentifier) throws Exception {

        writeData(benchmarkInfo);
    }

    public static void writeData(BenchmarkInfo benchmarkInfo) throws Exception {
        ComputerIdentifier computerIdentifier = new ComputerIdentifier();
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(computerIdentifier.getUUID());

        Map<String, Object> data = new HashMap<>();
        data.put("OS", computerIdentifier.getOs());
        data.put("CPU", computerIdentifier.getCpu());
        data.put("GPU", computerIdentifier.getGpu());
        data.put("RAM", computerIdentifier.getRam());
        docRef.set(data);

        docRef = db.collection("users").document(computerIdentifier.getUUID()).collection("benchmarks").document(benchmarkInfo.getBenchmarkName());

        data = new HashMap<>();
        data.put("Score", benchmarkInfo.getScore());
        data.put("Time", benchmarkInfo.getTime());
        ApiFuture<WriteResult> result = docRef.set(data);

        // result.get() blocks execution until response
        result.get();
    }

    public static Double getMyData(String benchmarkName, ComputerIdentifier computerIdentifier) {

        Map<String, Object> data = null;
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(computerIdentifier.getUUID()).collection("benchmarks").document(benchmarkName);

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            data = document.getData();

        } catch (Exception e) {
            return null;
        }
        if (data != null)
            return (Double) data.get("Score");

        return null;
    }

    public static List<Double> getAllData(String benchmarkName) {
        List<Double> data = new ArrayList<>();
        try {
            Firestore db = FirestoreClient.getFirestore();

            // Reference to the users collection
            CollectionReference usersCollection = db.collection("users");

            // Asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = usersCollection.get();

            // QuerySnapshot contains the results of the query
            QuerySnapshot querySnapshot = query.get();

            // Get a list of all user documents in the collection
            List<QueryDocumentSnapshot> users = querySnapshot.getDocuments();

            // Iterate through each user document
            for (QueryDocumentSnapshot userDocument : users) {
                String userId = userDocument.getId();
                // Access the benchmark/benchmark_type document for each user
                DocumentReference benchmarkDocRef = usersCollection
                        .document(userId)
                        .collection("benchmarks")
                        .document(benchmarkName);

                // Asynchronously retrieve the benchmark/benchmark_type document
                ApiFuture<DocumentSnapshot> benchmarkFuture = benchmarkDocRef.get();
                DocumentSnapshot benchmarkDocument = benchmarkFuture.get();

                // Check if the document exists and print its data
                if (benchmarkDocument.exists()) {
                    data.add((Double) benchmarkDocument.getData().get("Score"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.sort(Collections.reverseOrder());
        return data;
    }
}

