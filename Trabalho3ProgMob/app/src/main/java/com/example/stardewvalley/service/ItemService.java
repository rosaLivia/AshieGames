package com.example.stardewvalley.service;

import com.example.stardewvalley.model.Item;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ItemService {

    public interface ItemServiceCallback {
        void onItemsLoaded(List<Item> items);
        void onError(Exception e);
    }

    public void getItems(ItemServiceCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Item> itemList = new ArrayList<>();
                        task.getResult().forEach(document -> {
                            Item item = document.toObject(Item.class);
                            itemList.add(item);
                        });
                        callback.onItemsLoaded(itemList);
                    } else {
                        callback.onError(task.getException());
                    }
                });
    }
}
