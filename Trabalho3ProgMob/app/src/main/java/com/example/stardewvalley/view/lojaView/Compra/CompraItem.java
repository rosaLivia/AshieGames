package com.example.stardewvalley.view.lojaView.Compra;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.Compra;
import com.example.stardewvalley.service.PurchaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CompraItem extends AppCompatActivity {
    private ImageView compraImage;
    private TextView compraName;
    private TextView compraPrice;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_item);

        compraImage = findViewById(R.id.compraImage);
        compraName = findViewById(R.id.compraName);
        compraPrice = findViewById(R.id.compraPrice);
        btnComprar = findViewById(R.id.btnComprar);

        String itemName = getIntent().getStringExtra("itemName");
        int itemImageResourceId = getIntent().getIntExtra("itemImageResourceId", R.drawable.default_image); // Receber o identificador do recurso

        if (itemName != null) {
            compraImage.setImageResource(itemImageResourceId); // Usar o identificador do recurso recebido
            compraName.setText(itemName);
            compraPrice.setText(String.format("R$%.2f", getItemPriceByName(itemName)));
        }

        btnComprar.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Compra compra = new Compra(
                        UUID.randomUUID().toString(),
                        itemName,
                        getItemPriceByName(itemName),
                        new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()),
                        user.getEmail()
                );

                PurchaseService purchaseService = new PurchaseService();
                purchaseService.purchaseItem(user.getUid(), compra, new PurchaseService.PurchaseCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(CompraItem.this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(CompraItem.this, "Erro ao realizar compra: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(CompraItem.this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private int getDrawableResourceByName(String name) {
        // Remover caracteres especiais e espaços
        String resourceName = name.toLowerCase().replaceAll("[^a-z0-9]", "");
        int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());

        // Se não encontrar o recurso, retornar uma imagem padrão
        if (resourceId == 0) {
            resourceId = R.drawable.default_image;
        }

        return resourceId;
    }


    private double getItemPriceByName(String name) {
        return 10.0; // Exemplo de preço fixo
    }
}
