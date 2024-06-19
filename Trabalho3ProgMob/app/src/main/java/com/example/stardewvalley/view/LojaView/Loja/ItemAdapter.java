package com.example.stardewvalley.view.LojaView.Loja;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stardewvalley.view.LojaView.Compra.CompraItem;
import com.example.stardewvalley.R;
import com.example.stardewvalley.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_item_adapter, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.format("$%.2f", item.getPrice()));

        // Carregar a imagem do recurso (drawable) correspondente ao item
        int imageResource = getDrawableResourceByName(item.getName());
        holder.itemImage.setImageResource(imageResource);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CompraItem.class);
            intent.putExtra("itemName", item.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
