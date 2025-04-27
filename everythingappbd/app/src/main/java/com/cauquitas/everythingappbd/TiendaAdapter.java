package com.cauquitas.everythingappbd;

import android.content.Context;
import android.content.Intent; // IMPORTANTE: importamos Intent
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.TiendaViewHolder> {

    private List<Tienda> tiendasList;
    private Context context;

    public TiendaAdapter(List<Tienda> tiendasList, Context context) {
        this.tiendasList = tiendasList;
        this.context = context;
    }

    @NonNull
    @Override
    public TiendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tienda, parent, false);
        return new TiendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaViewHolder holder, int position) {
        Tienda tienda = tiendasList.get(position);
        holder.nombreTienda.setText(tienda.getNombreTienda());
        holder.descripcionTienda.setText(tienda.getDescripcionTienda());

        // Cargar la imagen de la tienda con Glide
        Glide.with(context)
                .load(tienda.getFotoTienda())
                .placeholder(R.drawable.default_image)
                .into(holder.imagenTienda);

        // --------- NUEVO: Detectar clic en la card ----------
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TiendaDetalleActivity.class);
            intent.putExtra("idTienda", tienda.getId());
            intent.putExtra("nombreTienda", tienda.getNombreTienda());

            // 🔥 Agregar idUsuario actual
            if (context instanceof HomeActivity) {
                String idUsuario = ((HomeActivity) context).getIdUsuario();
                intent.putExtra("idUsuario", idUsuario);
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tiendasList.size();
    }

    public static class TiendaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTienda, descripcionTienda;
        ImageView imagenTienda;

        public TiendaViewHolder(View itemView) {
            super(itemView);
            nombreTienda = itemView.findViewById(R.id.nombreTienda);
            descripcionTienda = itemView.findViewById(R.id.descripcionTienda);
            imagenTienda = itemView.findViewById(R.id.imagenTienda);
        }
    }
}
