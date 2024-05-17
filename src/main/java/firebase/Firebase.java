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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firebase {
    public static void main(String[] args) throws Exception {
        SystemSpecs.isFirstRun();
        writeBenchmarkResult(new BenchmarkInfo("CPU",100,1),new ComputerIdentifier());
        System.out.println(getMyData("CPU",new ComputerIdentifier()));
    }
    public static void writeBenchmarkResult(BenchmarkInfo benchmarkInfo, ComputerIdentifier computerIdentifier) throws Exception {
        FileInputStream serviceAccount = new FileInputStream("resources/firebase/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        FirebaseApp.initializeApp(options);

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
        docRef.set(data);

        docRef = db.collection("users").document(computerIdentifier.getUUID()).collection("benchmarks").document(benchmarkInfo.getBenchmarkName());

        data = new HashMap<>();
        data.put("Score", benchmarkInfo.getScore());
        data.put("Time", benchmarkInfo.getTime());
        ApiFuture<WriteResult> result = docRef.set(data);

        // result.get() blocks execution until response
        result.get();
    }
    public static Double getMyData(String benchmarkName, ComputerIdentifier computerIdentifier){

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
        return (Double) data.get("Score");
    }

    public static void getAllData(){
        try {
            List<QueryDocumentSnapshot> documents = getQueryDocumentSnapshots();

            // Iterate through all the documents
            for (QueryDocumentSnapshot document : documents) {
                System.out.println("Document ID: " + document.getId());
                System.out.println("Document Data: " + document.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<QueryDocumentSnapshot> getQueryDocumentSnapshots() throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();

        CollectionReference collectionRef = db.collection("your-collection");

        // Asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> query = collectionRef.get();

        // QuerySnapshot contains the results of the query
        QuerySnapshot querySnapshot = query.get();

        // Get a list of all documents in the collection
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        return documents;
    }
}




