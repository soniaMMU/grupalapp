package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TiendaDetalleActivity extends AppCompatActivity {

    private TextView textNombreTiendaDetalle;
    private RecyclerView recyclerViewProductos;
    private FloatingActionButton fabAgregarProducto;

    private String nombreTienda, idTienda;
    private String idUsuarioActual; // 🔥 Guardaremos el id del usuario activo

    private List<Producto> listaProductos;
    private ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_detalle);

        textNombreTiendaDetalle = findViewById(R.id.textNombreTienda);
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        fabAgregarProducto = findViewById(R.id.fabAgregarProducto);

        nombreTienda = getIntent().getStringExtra("nombreTienda");
        idTienda = getIntent().getStringExtra("idTienda");
        idUsuarioActual = getIntent().getStringExtra("idUsuario"); // 🔥 Lo pasamos desde el intent

        textNombreTiendaDetalle.setText("Productos de: " + nombreTienda);

        recyclerViewProductos.setLayoutManager(new GridLayoutManager(this, 2));
        listaProductos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(listaProductos);
        recyclerViewProductos.setAdapter(productoAdapter);

        cargarProductos();
        verificarPermisoAgregarProducto(); // 🔥 Verificar si puede o no agregar productos

        fabAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(this, AgregarProductoActivity.class);
            intent.putExtra("idTienda", idTienda);
            startActivity(intent);
        });
    }

    private void cargarProductos() {
        DatabaseReference productosRef = FirebaseDatabase.getInstance()
                .getReference("tiendas")
                .child(idTienda)
                .child("productos");

        productosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaProductos.clear();
                for (DataSnapshot productoSnap : snapshot.getChildren()) {
                    Producto producto = productoSnap.getValue(Producto.class);
                    if (producto != null) {
                        listaProductos.add(producto);
                    }
                }
                productoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(TiendaDetalleActivity.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarPermisoAgregarProducto() {
        if (idUsuarioActual == null) {
            fabAgregarProducto.hide(); // 🔥 Si no tenemos idUsuario, ocultamos el botón por seguridad
            return;
        }

        DatabaseReference tiendaRef = FirebaseDatabase.getInstance()
                .getReference("tiendas")
                .child(idTienda)
                .child("idUsuario"); // Leer el id del creador de la tienda

        tiendaRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String idUsuarioCreador = task.getResult().getValue(String.class);
                if (idUsuarioCreador != null && idUsuarioCreador.equals(idUsuarioActual)) {
                    fabAgregarProducto.show(); // 👑 Es su tienda, mostrar botón
                } else {
                    fabAgregarProducto.hide(); // 🚫 No es su tienda, ocultar botón
                }
            } else {
                Toast.makeText(TiendaDetalleActivity.this, "Error al verificar dueño de la tienda", Toast.LENGTH_SHORT).show();
                fabAgregarProducto.hide(); // Por seguridad, ocultar
            }
        });
    }
}
