package com.example.stardewvalley.LojaView;

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

public class ItemDetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailName;
    private TextView detailPrice;
    private Button btnComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);
        btnComprar = findViewById(R.id.btnComprar);

        Item item = (Item) getIntent().getSerializableExtra("item");
        if (item != null) {
            // Usando um drawable padrão
            detailImage.setImageResource(R.drawable.default_image);
            detailName.setText(item.getName());
            detailPrice.setText(String.format("$%.2f", item.getPrice()));

            btnComprar.setOnClickListener(v -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    PurchaseService purchaseService = new PurchaseService();
                    purchaseService.purchaseItem(user.getUid(), item, new PurchaseService.PurchaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ItemDetailActivity.this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(ItemDetailActivity.this, "Falha ao realizar compra. Tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ItemDetailActivity.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
