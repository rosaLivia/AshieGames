// SeedService.java
package com.example.stardewvalley.service;

import com.example.stardewvalley.entity.Seed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SeedService {
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "seeds";

    public SeedService() {
        db = FirebaseFirestore.getInstance();
    }

    public void addSeed(Seed seed, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).add(seed)
                .addOnSuccessListener(documentReference -> onSuccessListener.onSuccess(null))
                .addOnFailureListener(onFailureListener);
    }

    public void getSeedByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }

}

