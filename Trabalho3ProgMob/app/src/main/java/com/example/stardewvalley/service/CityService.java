package com.example.stardewvalley.service;

import com.example.stardewvalley.entity.City;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CityService {
    private final CollectionReference cityCollection;

    public CityService() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        cityCollection = db.collection("cities");
    }

    public void addCity(City city) {
        cityCollection.add(city)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    city.setCidadeID(id);
                    documentReference.set(city); // Atualiza o ID no documento
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void updateCity(City city) {
        cityCollection.document(city.getCidadeID()).set(city)
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void deleteCity(String cidadeID) {
        cityCollection.document(cidadeID).delete()
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void getCity(String cidadeID, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        cityCollection.document(cidadeID).get().addOnCompleteListener(onCompleteListener);
    }
}
