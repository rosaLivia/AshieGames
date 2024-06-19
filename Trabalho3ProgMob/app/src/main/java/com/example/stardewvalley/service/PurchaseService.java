package com.example.stardewvalley.service;

import com.example.stardewvalley.model.Item;
import com.google.firebase.firestore.FirebaseFirestore;

public class PurchaseService {

    public interface PurchaseCallback {
        void onSuccess();
        void onError(Exception e);
    }

    public void purchaseItem(String userId, Item item, PurchaseCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).collection("purchases").add(item)
                .addOnSuccessListener(documentReference -> callback.onSuccess())
                .addOnFailureListener(callback::onError);
    }
}
