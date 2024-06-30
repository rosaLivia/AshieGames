package com.example.stardewvalley.view.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.City;
import com.example.stardewvalley.entity.Seed;
import com.example.stardewvalley.service.CityService;
import com.example.stardewvalley.service.SeedService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddSeedCityActivity extends AppCompatActivity {

    private EditText seedDescription;
    private EditText endereco;
    private EditText latitude;
    private EditText longitude;
    private EditText cityName;
    private EditText cityState;
    private Button btnSave;
    private SeedService seedService;
    private CityService cityService;

    private ImageButton btnVolta;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seed_city);

        seedDescription = findViewById(R.id.seedDescription);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        cityName = findViewById(R.id.cityName);
        cityState = findViewById(R.id.cityState);
        btnSave = findViewById(R.id.btnSave);
        endereco = findViewById(R.id.Endereco);
        btnVolta = findViewById(R.id.btnVolta);

        seedService = new SeedService();
        cityService = new CityService();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        btnSave.setOnClickListener(v -> saveSeedAndCity());

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*                Intent intent = new Intent(AddSeedCityActivity.this , PerfilUser.class);
                startActivity(intent);*/
            }
        });
    }

    private void saveSeedAndCity() {
        String seedDesc = seedDescription.getText().toString();
        String lat = latitude.getText().toString();
        String lon = longitude.getText().toString();
        String city = cityName.getText().toString();
        String state = cityState.getText().toString();
        String enderecoStr = endereco.getText().toString();

        if (seedDesc.isEmpty() || lat.isEmpty() || lon.isEmpty() || city.isEmpty() || state.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitudeValue = Double.parseDouble(lat);
        double longitudeValue = Double.parseDouble(lon);

        Seed seed = new Seed();
        seed.setDescricao(seedDesc);
        seed.setLatitude(latitudeValue);
        seed.setEndereco(enderecoStr);
        seed.setLongitude(longitudeValue);
        seed.setEmail(currentUser.getEmail());

        City newCity = new City();
        newCity.setCidade(city);
        newCity.setEstado(state);
        newCity.setEmail(currentUser.getEmail());

        cityService.addCity(newCity, cityDocRef -> {
            seedService.addSeed(seed, seedDocRef -> {
                Toast.makeText(this, "Endereço e Cidade adicionados com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }, e -> Toast.makeText(this, "Erro ao salvar Endereço", Toast.LENGTH_SHORT).show());
        }, e -> Toast.makeText(this, "Erro ao salvar Cidade", Toast.LENGTH_SHORT).show());
    }
}
