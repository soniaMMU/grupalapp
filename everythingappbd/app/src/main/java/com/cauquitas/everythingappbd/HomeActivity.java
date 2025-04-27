package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textBienvenida;
    private ImageView imagePerfil;
    private Button btnMiPerfil; // Botón Mi Perfil

    private String idUsuario; // 🔥 Aquí guardaremos el id del usuario logueado
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        textBienvenida = findViewById(R.id.textBienvenida);
        imagePerfil = findViewById(R.id.imagePerfil);
        btnMiPerfil = findViewById(R.id.btnMiPerfil);

        // Obtener el correo e idUsuario que pasamos desde LoginActivity
        correoUsuario = getIntent().getStringExtra("correo");
        idUsuario = getIntent().getStringExtra("idUsuario"); // 🔥 idUsuario guardado

        // Buscar el usuario en Realtime Database para cargar nombre y foto
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");
        usuariosRef.child(idUsuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Usuario usuario = task.getResult().getValue(Usuario.class);
                if (usuario != null) {
                    textBienvenida.setText("Bienvenido, " + usuario.getNombre());
                    Glide.with(this)
                            .load(usuario.getFotoPerfil())
                            .placeholder(R.drawable.ic_launcher_background)
                            .circleCrop()
                            .into(imagePerfil);
                }
            }
        });

        // Fragmento por defecto
        loadFragment(new TiendasFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if (id == R.id.nav_tienda) {
                selectedFragment = new TiendasFragment();
            } else if (id == R.id.nav_agregar_tienda) {
                selectedFragment = new AgregarTiendaFragment();
            } else if (id == R.id.nav_favoritos) {
                selectedFragment = new FavoritosFragment();
            } else if (id == R.id.nav_mis_tiendas) {
                selectedFragment = new MisTiendasFragment();
            }
            loadFragment(selectedFragment);
            return true;
        });

        // Acción del botón Mi Perfil
        btnMiPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
            intent.putExtra("correo", correoUsuario);
            startActivity(intent);
        });
    }

    public String getIdUsuario() { // 🔥 getter para que los fragments accedan
        return idUsuario;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
