package com.example.stardewvalley.service;

import androidx.annotation.NonNull;

import com.example.stardewvalley.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserService {
    private final CollectionReference userCollection;

    public UserService() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userCollection = db.collection("users");
    }

    // Adicionar usuário ao Firestore
    public void addUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        userCollection.add(user)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    user.setUserID(id);
                    userCollection.document(id).set(user)
                            .addOnSuccessListener(onSuccessListener)
                            .addOnFailureListener(onFailureListener);
                })
                .addOnFailureListener(onFailureListener);
    }

    // Atualizar usuário no Firestore
    public void updateUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        userCollection.document(user.getUserID()).set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Deletar usuário do Firestore
    public void deleteUser(String userID, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        userCollection.document(userID).delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    // Obter usuário pelo ID
    public void getUser(String userID, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        userCollection.document(userID).get().addOnCompleteListener(onCompleteListener);
    }

    // Obter usuário pelo email e senha
    public void getUserByEmailAndPassword(String email, String password, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        userCollection.whereEqualTo("email", email)
                .whereEqualTo("senha", password)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }
}
