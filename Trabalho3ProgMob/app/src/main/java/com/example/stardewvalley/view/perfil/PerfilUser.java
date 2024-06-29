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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.City;
import com.example.stardewvalley.entity.Seed;
import com.example.stardewvalley.entity.User;
import com.example.stardewvalley.service.CityService;
import com.example.stardewvalley.service.SeedService;
import com.example.stardewvalley.service.UserService;
import com.example.stardewvalley.view.Login;
import com.example.stardewvalley.view.MainActivity;
import com.example.stardewvalley.view.lojaView.Compra.HistoricoComprasActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class PerfilUser extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String TAG = "PerfilUser";

    private ImageView profileImageView;
    private TextView profileName;
    private TextView profileEmail;
    private TextView profileSeedDescription;
    private TextView profileSeedAddress;
    private TextView profileCityState;
    private UserService userService;
    private SeedService seedService;
    private CityService cityService;
    private FirebaseUser currentUser;
    private ImageView btnDeslogar;
    private ImageView btnVoltar;

    private Button buttonHistoricoCompras;
    private ConstraintLayout PerfilUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        try {
            IniciarComponentes();

            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            userService = new UserService();
            seedService = new SeedService();
            cityService = new CityService();

            if (currentUser != null) {
                loadUserProfile();
            } else {
                Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }

            btnDeslogar.setOnClickListener(v -> {
                // Criar o diálogo de confirmação
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Tem certeza que deseja deslogar?");
                builder.setPositiveButton("Sim", (dialog, which) -> {
                    // Executar logout
                    FirebaseAuth.getInstance().signOut();
                    telaInicial();
                });
                builder.setNegativeButton("Não", (dialog, which) -> {
                    // Não fazer nada, continuar na tela atual
                });

                // Mostrar o diálogo
                builder.show();
            });


            btnVoltar.setOnClickListener(v -> finish());
        } catch (Exception e) {
            Log.e(TAG, "Erro na inicialização: " + e.getMessage(), e);
        }



        buttonHistoricoCompras.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilUser.this, HistoricoComprasActivity.class);
            startActivity(intent);
        });
    }



    private void telaInicial(){
        Intent intent = new Intent(PerfilUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadUserProfile() {
        try {
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

                            loadSeedInfo();
                            loadCityInfo();
                        }
                    }
                } else {
                    Log.d(TAG, "No user data found for email: " + currentUser.getEmail());
                    Toast.makeText(PerfilUser.this, "Nenhum dado do usuário encontrado.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar perfil do usuário: " + e.getMessage(), e);
        }
    }

    private void loadSeedInfo() {
        try {
            seedService.getSeedByEmail(currentUser.getEmail(), seedTask -> {
                if (seedTask.isSuccessful() && seedTask.getResult() != null && !seedTask.getResult().isEmpty()) {
                    for (DocumentSnapshot seedDoc : seedTask.getResult()) {
                        Seed seed = seedDoc.toObject(Seed.class);
                        if (seed != null) {
                            profileSeedDescription.setText(seed.getDescricao() != null ? seed.getDescricao() : "Sem descrição");
                            profileSeedAddress.setText(seed.getEndereco() != null ? seed.getEndereco() : "Sem endereço");
                        } else {
                            profileSeedDescription.setText("Sem descrição");
                            profileSeedAddress.setText("Sem endereço");
                        }
                    }
                } else {
                    profileSeedDescription.setText("Sem descrição");
                    profileSeedAddress.setText("Sem endereço");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar informações da semente: " + e.getMessage(), e);
        }
    }

    private void loadCityInfo() {
        try {
            cityService.getCityByEmail(currentUser.getEmail(), cityTask -> {
                if (cityTask.isSuccessful() && cityTask.getResult() != null && !cityTask.getResult().isEmpty()) {
                    for (DocumentSnapshot cityDoc : cityTask.getResult()) {
                        City city = cityDoc.toObject(City.class);
                        if (city != null) {
                            profileCityState.setText(city.getCidade() + ", " + city.getEstado());
                        } else {
                            profileCityState.setText("Sem cidade");
                        }
                    }
                } else {
                    profileCityState.setText("Sem cidade");
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar informações da cidade: " + e.getMessage(), e);
        }
    }

    public void goToAddSeedCity(View view) {
        try {
            Intent intent = new Intent(PerfilUser.this, AddSeedCityActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao abrir AddSeedCityActivity: " + e.getMessage(), e);
        }
    }

    public void changeProfilePicture(View view) {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao escolher imagem de perfil: " + e.getMessage(), e);
        }
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
        try {
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
        } catch (Exception e) {
            Log.e(TAG, "Erro ao fazer upload da imagem: " + e.getMessage(), e);
        }
    }

    private void IniciarComponentes() {
        profileImageView = findViewById(R.id.profileImageView);
        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileSeedDescription = findViewById(R.id.profileSeedDescription);
        profileSeedAddress = findViewById(R.id.profileSeedAddress);
        profileCityState = findViewById(R.id.profileCityState);
        btnDeslogar = findViewById(R.id.btnDeslogar);
        btnVoltar = findViewById(R.id.btnVoltar);
        PerfilUser = findViewById(R.id.loginMain);
        buttonHistoricoCompras = findViewById(R.id.button_historico_compras);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(PerfilUser.this, Login.class);
        startActivity(intent);
        finish();
    }


}
