package com.example.stardewvalley.view.lojaView.Loja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stardewvalley.R;
import com.example.stardewvalley.model.Item;
import com.example.stardewvalley.view.perfil.PerfilUser;

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

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar a lista de itens
        itemList = new ArrayList<>();
        itemList.add(new Item("Item 1", R.drawable.c1, 10.99));
        itemList.add(new Item("Item 2", R.drawable.c2, 12.99));
        itemList.add(new Item("Item 3", R.drawable.c3, 14.99));
        // Adicione mais itens conforme necessário

        // Inicializar o adaptador e configurar o RecyclerView
        itemAdapter = new ItemAdapter(this, itemList);
        recyclerViewItems.setAdapter(itemAdapter);

        // Configurar o ícone de perfil com clique para abrir a tela de perfil
        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilUser.class);
            startActivity(intent);
        });

        // Carregar a foto do perfil do usuário (ou uma imagem padrão)
        // Aqui você pode adicionar a lógica para carregar a foto do usuário
        profileIcon.setImageResource(R.drawable.a);
    }
}
