package com.example.stardewvalley.view.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.User;
import com.example.stardewvalley.service.UserService;
import com.example.stardewvalley.view.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PerfilUser extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private TextView profileName;
    private TextView profileEmail;

    private UserService userService;
    private FirebaseUser currentUser;
    private StorageReference storageReference;

    private Button btnDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        IniciarComponentes();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("profile_pics");
        userService = new UserService();

        if (currentUser != null) {
            loadUserProfile();
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        }

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                navigateToLogin();
            }
        });
    }

    private void loadUserProfile() {
        userService.getUserByEmail(currentUser.getEmail(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        User userData = document.toObject(User.class);
                        if (userData != null) {
                            profileName.setText(userData.getNome() != null ? userData.getNome() : "Nome do Usuário");
                            profileEmail.setText(userData.getEmail() != null ? userData.getEmail() : "Email do Usuário");

                            // Carregar a foto do usuário, se disponível
                            if (userData.getProfileImageUrl() != null) {
                                Picasso.get().load(userData.getProfileImageUrl()).into(profileImageView);
                            } else {
                                profileImageView.setImageResource(R.drawable.a);
                            }
                        }
                    }
                } else {
                    Log.d("PerfilUser", "No user data found for email: " + currentUser.getEmail());
                    Toast.makeText(PerfilUser.this, "Nenhum dado do usuário encontrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            uploadImageToFirebase(imageUri);
        }
    }

    // Método para fazer o upload da imagem para o Firebase Storage
    private void uploadImageToFirebase(Uri imageUri) {
        if (currentUser != null) {
            StorageReference fileReference = storageReference.child(currentUser.getUid() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Atualize a URL da imagem de perfil no Firestore
                        userService.updateProfileImageUrl(currentUser.getUid(), uri.toString());
                        Picasso.get().load(uri).into(profileImageView);
                        Toast.makeText(PerfilUser.this, "Foto de perfil atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }))
                    .addOnFailureListener(e -> Toast.makeText(PerfilUser.this, "Falha ao fazer upload da imagem.", Toast.LENGTH_SHORT).show());
        }
    }

    private void IniciarComponentes() {
        profileImageView = findViewById(R.id.profileImageView);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        btnDeslogar = findViewById(R.id.btnDeslogar);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(PerfilUser.this, Login.class);
        startActivity(intent);
        finish();
    }
}
