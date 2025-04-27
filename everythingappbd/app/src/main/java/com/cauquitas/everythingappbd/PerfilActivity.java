package com.cauquitas.everythingappbd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerfilActivity extends AppCompatActivity {

    private ImageView imagePerfil;
    private TextView textNombre, textCorreo, textTelefono, textContrasena;
    private Button btnLogout;
    private String correoUsuario;

    private FirebaseAuth auth;
    private DatabaseReference usuariosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        auth = FirebaseAuth.getInstance();
        usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");

        imagePerfil = findViewById(R.id.imagePerfil);
        textNombre = findViewById(R.id.textNombre);
        textCorreo = findViewById(R.id.textCorreo);
        textTelefono = findViewById(R.id.textTelefono);
        textContrasena = findViewById(R.id.textContrasena);
        btnLogout = findViewById(R.id.btnLogout);

        // Obtener el correo del usuario desde el Intent
        correoUsuario = getIntent().getStringExtra("correo");

        if (correoUsuario != null) {
            cargarDatosUsuario(correoUsuario);
        }

        btnLogout.setOnClickListener(v -> confirmarCerrarSesion());
    }

    private void cargarDatosUsuario(String correo) {
        usuariosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    if (usuario != null && usuario.getCorreo().equals(correo)) {
                        textNombre.setText(usuario.getNombre());
                        textCorreo.setText(usuario.getCorreo());
                        textTelefono.setText(usuario.getTelefono());

                        // Mostrar contraseña oculta pero con últimas 3 letras visibles
                        textContrasena.setText(formatearContrasena(usuario.getClave()));

                        Glide.with(this)
                                .load(usuario.getFotoPerfil())
                                .placeholder(R.drawable.ic_launcher_background)
                                .circleCrop()
                                .into(imagePerfil);
                        break;
                    }
                }
            } else {
                Toast.makeText(this, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatearContrasena(String contrasenaReal) {
        if (contrasenaReal == null) return "********";

        if (contrasenaReal.length() > 3) {
            String ultimasTres = contrasenaReal.substring(contrasenaReal.length() - 3);
            return "*******" + ultimasTres; // Puedes poner más o menos '*' como quieras
        } else {
            return "*******" + contrasenaReal;
        }
    }

    private void confirmarCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Seguro que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    auth.signOut();
                    startActivity(new Intent(PerfilActivity.this, LoginActivity.class));
                    finish();
                    Toast.makeText(PerfilActivity.this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
