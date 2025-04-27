package com.cauquitas.everythingappbd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> productos;
    private Context context; // 🔥 Ahora guardamos el context para lanzar actividades

    public ProductoAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext(); // 🔥 Obtenemos el context aquí
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);

        holder.tvNombreProducto.setText(producto.getNombreProducto());
        holder.tvPrecioProducto.setText("Bs. " + producto.getPrecioProducto());
        holder.tvDescripcionProducto.setText(producto.getDescripcionProducto()); // 🔥 Mostrar descripción

        Glide.with(context)
                .load(producto.getFotoProducto())
                .placeholder(R.drawable.default_image)
                .into(holder.imgFotoProducto);

        // 🔥 Al hacer clic, abrir ProductoDetalleActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductoDetalleActivity.class);
            intent.putExtra("nombreProducto", producto.getNombreProducto());
            intent.putExtra("precioProducto", producto.getPrecioProducto());
            intent.putExtra("descripcionProducto", producto.getDescripcionProducto());
            intent.putExtra("fotoProducto", producto.getFotoProducto());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreProducto, tvPrecioProducto, tvDescripcionProducto;
        ImageView imgFotoProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
            tvDescripcionProducto = itemView.findViewById(R.id.tvDescripcionProducto); // 🔥 Nuevo TextView para descripción
            imgFotoProducto = itemView.findViewById(R.id.imgFotoProducto);
        }
    }
}
