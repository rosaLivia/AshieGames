package com.example.stardewvalley.service;

import android.util.Log;

import com.example.stardewvalley.entity.Compra;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PurchaseService {

    private final FirebaseFirestore firestore;
    private final CollectionReference comprasCollection;

    public PurchaseService() {
        firestore = FirebaseFirestore.getInstance();
        comprasCollection = firestore.collection("compras");
    }

    public void purchaseItem(String userId, Compra compra, PurchaseCallback callback) {
        comprasCollection.document(userId).collection("user_compras").add(compra)
                .addOnSuccessListener(documentReference -> callback.onSuccess())
                .addOnFailureListener(callback::onError);
    }

    public void getComprasPorEmail(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        CollectionReference userComprasCollection = firestore.collection("compras")
                .document(userId)
                .collection("user_compras");
        userComprasCollection.get().addOnCompleteListener(onCompleteListener);
    }

    public interface PurchaseCallback {
        void onSuccess();
        void onError(Exception e);
    }
}
