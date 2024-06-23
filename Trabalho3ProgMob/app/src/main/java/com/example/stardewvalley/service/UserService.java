package com.example.stardewvalley.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.stardewvalley.entity.User;
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
        DocumentReference newUserRef = userCollection.document();
        String userID = newUserRef.getId();
        user.setUserID(userID);

        newUserRef.set(user)
                .addOnSuccessListener(onSuccessListener)
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

    // Obter usuário pelo email
    // Obter usuário pelo email
    public void getUserByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        userCollection.whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }

    public void updateProfileImageUrl(String userId, String profileImageUrl) {
        userCollection.document(userId).update("profileImageUrl", profileImageUrl)
                .addOnSuccessListener(aVoid -> Log.d("UserService", "Profile image URL updated successfully."))
                .addOnFailureListener(e -> Log.w("UserService", "Error updating profile image URL", e));
    }

}
