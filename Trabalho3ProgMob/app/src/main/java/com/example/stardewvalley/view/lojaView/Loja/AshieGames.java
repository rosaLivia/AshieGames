package com.example.stardewvalley.view.lojaView.Loja;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.User;
import com.example.stardewvalley.model.Item;
import com.example.stardewvalley.service.UserService;
import com.example.stardewvalley.view.perfil.PerfilUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AshieGames extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private ImageView profileIcon;
    private UserService userService;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashie_games);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new GridLayoutManager(this, 2)); // Define GridLayoutManager com 2 colunas

        // Inicializar a lista de itens
        itemList = new ArrayList<>();
        itemList.add(new Item("Alface", R.drawable.c1, 5.99));
        itemList.add(new Item("Cenoura", R.drawable.c2, 3.56));
        itemList.add(new Item("Baiacu", R.drawable.c3, 48.90));
        // Adicione mais itens conforme necessário

        // Inicializar o adaptador e configurar o RecyclerView
        itemAdapter = new ItemAdapter(this, itemList);
        recyclerViewItems.setAdapter(itemAdapter);

        // Configurar o ícone de perfil com clique para abrir a tela de perfil
        profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, PerfilUser.class);
            startActivity(intent);
        });

        // Carregar a foto do perfil do usuário (ou uma imagem padrão)
        userService = new UserService();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            CarregarImagemPerfil();
        } else {
            profileIcon.setImageResource(R.drawable.a);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser != null) {
            CarregarImagemPerfil();
        } else {
            profileIcon.setImageResource(R.drawable.a);
        }
    }

    private void CarregarImagemPerfil() {
        userService.getUserByEmail(currentUser.getEmail(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        User userData = document.toObject(User.class);
                        if (userData != null && userData.getProfileImageUrl() != null) {
                            Picasso.get().load(userData.getProfileImageUrl()).into(profileIcon);
                        } else {
                            profileIcon.setImageResource(R.drawable.a); // Imagem padrão
                        }
                    }
                } else {
                    profileIcon.setImageResource(R.drawable.a); // Imagem padrão
                }
            }
        });
    }
}
