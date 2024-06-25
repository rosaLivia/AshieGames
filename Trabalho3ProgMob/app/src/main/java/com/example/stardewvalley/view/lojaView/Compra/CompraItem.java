package com.example.stardewvalley.view.lojaView.Compra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.example.stardewvalley.model.Item;
import com.example.stardewvalley.service.PurchaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        // Recebe o nome do item clicado do Intent
        String itemName = getIntent().getStringExtra("itemName");

        // Configura a imagem com base no nome do item
        int imageResource = getDrawableResourceByName(itemName);
        compraImage.setImageResource(imageResource);

        // Configura o nome e o preço do item
        compraName.setText(itemName);
        compraPrice.setText(String.format("$%.2f", getItemPriceByName(itemName)));

        // Configura o botão de compra
        btnComprar.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Supondo que você tenha um método para obter o item atual pelo nome
                Item item = getItemByName(itemName);
                PurchaseService purchaseService = new PurchaseService();
                purchaseService.purchaseItem(user.getUid(), item, new PurchaseService.PurchaseCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(CompraItem.this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(CompraItem.this, "Falha ao realizar compra. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(CompraItem.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getDrawableResourceByName(String name) {
        if (name == null) return R.drawable.default_image;

        switch (name) {
            case "Alface":
                return R.drawable.c1;
            case "Cenoura":
                return R.drawable.c2;
            case "Baiacu":
                return R.drawable.c3;

            default:
                return R.drawable.default_image;
        }
    }

    private double getItemPriceByName(String name) {
        switch (name) {
            case "Alface":
                return 5.99;
            case "Cenoura":
                return 3.56;
            case "Baiacu":
                return 48.90;

            default:
                return 0.0;
        }
    }

    private Item getItemByName(String name) {
        switch (name) {
            case "Alface":
                return new Item("Alface", R.drawable.c1, 5.99);
            case "Cenoura":
                return new Item("Cenoura", R.drawable.c2, 3.56);
            case "Baiacu":
                return new Item("Baiacu", R.drawable.c3, 48.90);

            default:
                return null;
        }
    }
}
