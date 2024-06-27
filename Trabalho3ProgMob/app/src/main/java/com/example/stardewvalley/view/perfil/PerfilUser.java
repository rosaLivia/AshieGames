package com.example.stardewvalley.view.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.City;
import com.example.stardewvalley.entity.Seed;
import com.example.stardewvalley.entity.User;
import com.example.stardewvalley.service.UserService;
import com.example.stardewvalley.view.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class PerfilUser extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profileSeed;
    private TextView profileCity;
    private UserService userService;
    private FirebaseUser currentUser;
    private ImageView btnDeslogar;
    private ImageView btnVoltar;
    private ConstraintLayout PerfilUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        IniciarComponentes();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userService = new UserService();

        if (currentUser != null) {
            loadUserProfile();
        } else {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            navigateToLogin();
        }

        PerfilUser.setOnTouchListener((v, event) -> {
            hideKeyboard(v);
            return false;
        });

        btnDeslogar.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            navigateToLogin();
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void loadUserProfile() {
        userService.getUserByEmail(currentUser.getEmail(), task -> {
            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                for (DocumentSnapshot document : task.getResult()) {
                    User userData = document.toObject(User.class);
                    if (userData != null) {
                        profileName.setText(userData.getNome() != null ? userData.getNome() : "Nome do Usuário");
                        profileEmail.setText(userData.getEmail() != null ? userData.getEmail() : "Email do Usuário");

                        if (userData.getProfileImageUrl() != null) {
                            Picasso.get().load(userData.getProfileImageUrl()).into(profileImageView);
                        } else {
                            profileImageView.setImageResource(R.drawable.a);
                        }

                        // Buscar detalhes de Seed e City
                        userService.getSeedById(userData.getSeedId(), seedTask -> {
                            if (seedTask.isSuccessful() && seedTask.getResult() != null) {
                                Seed seed = seedTask.getResult().toObject(Seed.class);
                                if (seed != null) {
                                    profileSeed.setText(seed.getDescricao());
                                }
                            }
                        });

                        userService.getCityById(userData.getCityId(), cityTask -> {
                            if (cityTask.isSuccessful() && cityTask.getResult() != null) {
                                City city = cityTask.getResult().toObject(City.class);
                                if (city != null) {
                                    String cityInfo = city.getCidade() + ", " + city.getEstado();
                                    profileCity.setText(cityInfo);
                                }
                            }
                        });
                    }
                }
            } else {
                Log.d("PerfilUser", "No user data found for email: " + currentUser.getEmail());
                Toast.makeText(PerfilUser.this, "Nenhum dado do usuário encontrado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeProfilePicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            uploadImageToFirebase(imageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (currentUser != null) {
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("profile_pics").child(currentUser.getUid() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
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
        profileSeed = findViewById(R.id.profileSeed);
        profileCity = findViewById(R.id.profileCity);
        btnDeslogar = findViewById(R.id.btnDeslogar);
        btnVoltar = findViewById(R.id.btnVoltar);
        PerfilUser = findViewById(R.id.loginMain);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(PerfilUser.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && view != null) {
            Log.d("EditCity", "Hiding keyboard from view: " + view.toString());
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            if (imm == null) {
                Log.d("EditCity", "InputMethodManager is null");
            }
            if (view == null) {
                Log.d("EditCity", "View is null");
            }
        }
    }
}
