package com.example.stardewvalley.view.lojaView.Compra;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stardewvalley.R;
import com.example.stardewvalley.entity.Compra;
import com.example.stardewvalley.service.PurchaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.ArrayList;
import java.util.List;

public class HistoricoComprasActivity extends AppCompatActivity {

    private ListView listViewHistoricoCompras;
    private List<String> listaCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_compras);
        listViewHistoricoCompras = findViewById(R.id.listView_historico_compras);

        listaCompras = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            carregarHistoricoDeCompras(userId);
        }
    }

    private void carregarHistoricoDeCompras(String userId) {
        PurchaseService purchaseService = new PurchaseService();
        purchaseService.getComprasPorEmail(userId, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    listaCompras.clear();
                    for (com.google.firebase.firestore.DocumentSnapshot document : task.getResult().getDocuments()) {
                        Compra compra = document.toObject(Compra.class);
                        if (compra != null) {
                            listaCompras.add(compra.getNomeItem());
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoricoComprasActivity.this, android.R.layout.simple_list_item_1, listaCompras);
                    listViewHistoricoCompras.setAdapter(adapter);
                } else {
                    Toast.makeText(HistoricoComprasActivity.this, "Erro ao carregar histórico de compras.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
