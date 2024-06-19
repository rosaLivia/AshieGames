package com.example.stardewvalley.LojaView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stardewvalley.R;
import com.example.stardewvalley.model.Item;

import java.util.ArrayList;
import java.util.List;

public class AshieGames extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashie_games);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new GridLayoutManager(this, 2)); // Configura o GridLayoutManager com 2 colunas

        // Adiciona dados de exemplo
        itemList = new ArrayList<>();
        itemList = new ArrayList<>();
        itemList.add(new Item("Item 1", "https://example.com/image1.jpg", 10.99));
        itemList.add(new Item("Item 2", "https://example.com/image2.jpg", 15.49));
        itemList.add(new Item("Item 3", "https://example.com/image3.jpg", 7.99));
        itemList.add(new Item("Item 4", "https://example.com/image4.jpg", 12.49));
        itemList.add(new Item("Item 5", "https://example.com/image5.jpg", 9.99));
        itemList.add(new Item("Item 6", "https://example.com/image6.jpg", 11.99));
        itemList.add(new Item("Item 7", "https://example.com/image7.jpg", 8.99));
        itemList.add(new Item("Item 8", "https://example.com/image8.jpg", 14.49));
        itemList.add(new Item("Item 9", "https://example.com/image9.jpg", 13.99));
        itemList.add(new Item("Item 10", "https://example.com/image10.jpg", 6.49));


        itemAdapter = new ItemAdapter(this, itemList);
        recyclerViewItems.setAdapter(itemAdapter);
    }
}
