package com.cauquitas.everythingappbd;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductoDetalleActivity extends AppCompatActivity {

    private ImageView imgFotoProductoDetalle;
    private TextView tvNombreProductoDetalle, tvPrecioProductoDetalle, tvDescripcionProductoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        imgFotoProductoDetalle = findViewById(R.id.imgFotoProductoDetalle);
        tvNombreProductoDetalle = findViewById(R.id.tvNombreProductoDetalle);
        tvPrecioProductoDetalle = findViewById(R.id.tvPrecioProductoDetalle);
        tvDescripcionProductoDetalle = findViewById(R.id.tvDescripcionProductoDetalle);

        // Obtener los datos enviados
        String nombre = getIntent().getStringExtra("nombreProducto");
        String precio = getIntent().getStringExtra("precioProducto");
        String descripcion = getIntent().getStringExtra("descripcionProducto");
        String fotoUrl = getIntent().getStringExtra("fotoProducto");

        // Setear los datos en la vista
        tvNombreProductoDetalle.setText(nombre);
        tvPrecioProductoDetalle.setText("Bs. " + precio);
        tvDescripcionProductoDetalle.setText(descripcion);

        Glide.with(this)
                .load(fotoUrl)
                .placeholder(R.drawable.default_image)
                .into(imgFotoProductoDetalle);
    }
}
