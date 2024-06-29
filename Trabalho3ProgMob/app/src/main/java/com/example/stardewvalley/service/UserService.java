package com.example.stardewvalley.service;

import android.util.Log;

import com.example.stardewvalley.entity.City;
import com.example.stardewvalley.entity.Seed;
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

    private final FirebaseFirestore firestore;
    private final CollectionReference userCollection;
    private final CollectionReference seedCollection;
    private final CollectionReference cityCollection;

    public UserService() {
        firestore = FirebaseFirestore.getInstance();
        userCollection = firestore.collection("users");
        seedCollection = firestore.collection("seeds");
        cityCollection = firestore.collection("cities");
    }

    public void addUser(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        userCollection.document(user.getUserID())
                .set(user)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void updateProfileImageUrl(String userId, String profileImageUrl) {
        userCollection.document(userId).update("profileImageUrl", profileImageUrl)
                .addOnSuccessListener(aVoid -> Log.d("UserService", "Profile image URL updated successfully."))
                .addOnFailureListener(e -> Log.w("UserService", "Error updating profile image URL", e));
    }

    public void getUserByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        userCollection.whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }

    public void getUserDetails(String userId, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        userCollection.document(userId).get().addOnCompleteListener(onCompleteListener);
    }

    // Dentro de SeedService
    public void getSeedByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        seedCollection.whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }

    // Dentro de CityService
    public void getCityByEmail(String email, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        cityCollection.whereEqualTo("email", email).get().addOnCompleteListener(onCompleteListener);
    }



}
