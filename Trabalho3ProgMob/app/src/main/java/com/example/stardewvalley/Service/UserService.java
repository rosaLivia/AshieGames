package com.example.stardewvalley.Service;

import com.example.stardewvalley.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserService {
    private final CollectionReference userCollection;

    public UserService() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");
    }

    public void addUser(User user) {
        userCollection.add(user)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    user.setUserID(id);
                    documentReference.set(user); // Atualiza o ID no documento
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void updateUser(User user) {
        userCollection.document(user.getUserID()).set(user)
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void deleteUser(String userID) {
        userCollection.document(userID).delete()
                .addOnSuccessListener(aVoid -> {
                    // Sucesso
                })
                .addOnFailureListener(e -> {
                    // Lidar com falha
                });
    }

    public void getUser(String userID, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        userCollection.document(userID).get().addOnCompleteListener(onCompleteListener);
    }
}
