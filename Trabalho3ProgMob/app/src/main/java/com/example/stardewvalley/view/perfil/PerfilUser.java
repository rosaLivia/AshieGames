package com.example.stardewvalley.view.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PerfilUser extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private TextView profileName;
    private TextView profileEmail;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        profileImageView = findViewById(R.id.profileImageView);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Referência ao nó "users" no Realtime Database
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

            // Consulta para buscar o usuário pelo email
            Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String nome = userSnapshot.child("nome").getValue(String.class);
                            String email = userSnapshot.child("email").getValue(String.class);

                            // Debugging: Log para verificar os dados recuperados
                            Log.d("PerfilUser", "Nome: " + nome + ", Email: " + email);

                            // Definir o nome e email do usuário nos TextViews
                            profileName.setText(nome != null ? nome : "Nome do Usuário");
                            profileEmail.setText(email != null ? email : "Email do Usuário");
                        }
                    } else {
                        Log.d("PerfilUser", "No user data found for email: " + user.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("PerfilUser", "Failed to read user info.", error.toException());
                }
            });

            // Mostrar a foto do usuário, se disponível
            if (user.getPhotoUrl() != null) {
                Picasso.get().load(user.getPhotoUrl()).into(profileImageView);
            } else {
                profileImageView.setImageResource(R.drawable.a);
            }
        }
    }

    // Método chamado quando o botão "Alterar Foto" é clicado
    public void changeProfilePicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), PICK_IMAGE_REQUEST);
    }

    // Método para lidar com o resultado da seleção de imagem
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Aqui você deve implementar a lógica para salvar a nova imagem de perfil no Firebase Storage, por exemplo
            // e então atualizar a imagem exibida usando Picasso ou outra biblioteca de carregamento de imagem
            Picasso.get().load(imageUri).into(profileImageView);
        }
    }
}
