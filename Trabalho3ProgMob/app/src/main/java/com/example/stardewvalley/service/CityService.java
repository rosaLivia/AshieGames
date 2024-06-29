// CityService.java
package com.example.stardewvalley.service;

import com.example.stardewvalley.entity.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class CityService {
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "cities";

    public CityService() {
        db = FirebaseFirestore.getInstance();
    }

    public void addCity(City city, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        db.collection(COLLECTION_NAME).add(city)
                .addOnSuccessListener(documentReference -> onSuccessListener.onSuccess(null))
                .addOnFailureListener(onFailureListener);
    }

    public void getCityByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        db.collection(COLLECTION_NAME).whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }
}
