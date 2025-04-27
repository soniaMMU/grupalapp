package com.cauquitas.everythingappbd;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarProductoActivity extends AppCompatActivity {

    private EditText etNombreProducto, etDescripcionProducto, etPrecioProducto, etFotoProducto;
    private Button btnAgregarProducto;

    private String idTienda;
    private DatabaseReference databaseProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        etNombreProducto = findViewById(R.id.etNombreProducto);
        etDescripcionProducto = findViewById(R.id.etDescripcionProducto);
        etPrecioProducto = findViewById(R.id.etPrecioProducto);
        etFotoProducto = findViewById(R.id.etFotoProducto);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);

        idTienda = getIntent().getStringExtra("idTienda");
        databaseProductos = FirebaseDatabase.getInstance().getReference("tiendas").child(idTienda).child("productos");

        btnAgregarProducto.setOnClickListener(v -> {
            String nombreProducto = etNombreProducto.getText().toString().trim();
            String descripcionProducto = etDescripcionProducto.getText().toString().trim();
            String precioString = etPrecioProducto.getText().toString().trim();
            String fotoProducto = etFotoProducto.getText().toString().trim();

            if (!nombreProducto.isEmpty() && !descripcionProducto.isEmpty() && !precioString.isEmpty() && !fotoProducto.isEmpty()) {
                double precio = Double.parseDouble(precioString);
                String productoId = databaseProductos.push().getKey();

                if (productoId != null) {
                    Producto nuevoProducto = new Producto(productoId, nombreProducto, descripcionProducto, precio, fotoProducto);

                    databaseProductos.child(productoId).setValue(nuevoProducto)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                                finish(); // Cerrar actividad y volver
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
