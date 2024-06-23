package com.example.stardewvalley.service;

import com.example.stardewvalley.entity.Seed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SeedService {
    private final CollectionReference seedCollection;

    public SeedService() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        seedCollection = db.collection("seeds");
    }

    public void addSeed(Seed seed) {
        seedCollection.add(seed)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    seed.setEnderecoID(id);
                    documentReference.set(seed); // Atualiza o ID no documento
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void updateSeed(Seed seed) {
        seedCollection.document(seed.getEnderecoID()).set(seed)
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void deleteSeed(String enderecoID) {
        seedCollection.document(enderecoID).delete()
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void getSeed(String enderecoID, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        seedCollection.document(enderecoID).get().addOnCompleteListener(onCompleteListener);
    }
}
