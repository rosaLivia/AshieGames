package com.example.stardewvalley.view.LojaView.Compra;

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
            case "Item 1":
                return R.drawable.capa;
            case "Item 2":
                return R.drawable.default_image;
            case "Item 3":
                return R.drawable.a;
            case "Item 4":
                return R.drawable.a;
            case "Item 5":
                return R.drawable.a;
            case "Item 6":
                return R.drawable.a;
            case "Item 7":
                return R.drawable.a;
            case "Item 8":
                return R.drawable.a;
            case "Item 9":
                return R.drawable.a;
            case "Item 10":
                return R.drawable.a;
            default:
                return R.drawable.default_image;
        }
    }

    private double getItemPriceByName(String name) {
        switch (name) {
            case "Item 1":
                return 10.99;
            case "Item 2":
                return 15.49;
            case "Item 3":
                return 7.99;
            case "Item 4":
                return 12.49;
            case "Item 5":
                return 9.99;
            case "Item 6":
                return 11.99;
            case "Item 7":
                return 8.99;
            case "Item 8":
                return 14.49;
            case "Item 9":
                return 13.99;
            case "Item 10":
                return 6.49;
            default:
                return 0.0;
        }
    }

    private Item getItemByName(String name) {
        switch (name) {
            case "Item 1":
                return new Item("Item 1", "https://example.com/image1.jpg", 10.99);
            case "Item 2":
                return new Item("Item 2", "https://example.com/image2.jpg", 15.49);
            case "Item 3":
                return new Item("Item 3", "https://example.com/image3.jpg", 7.99);
            case "Item 4":
                return new Item("Item 4", "https://example.com/image4.jpg", 12.49);
            case "Item 5":
                return new Item("Item 5", "https://example.com/image5.jpg", 9.99);
            case "Item 6":
                return new Item("Item 6", "https://example.com/image6.jpg", 11.99);
            case "Item 7":
                return new Item("Item 7", "https://example.com/image7.jpg", 8.99);
            case "Item 8":
                return new Item("Item 8", "https://example.com/image8.jpg", 14.49);
            case "Item 9":
                return new Item("Item 9", "https://example.com/image9.jpg", 13.99);
            case "Item 10":
                return new Item("Item 10", "https://example.com/image10.jpg", 6.49);
            default:
                return null;
        }
    }
}