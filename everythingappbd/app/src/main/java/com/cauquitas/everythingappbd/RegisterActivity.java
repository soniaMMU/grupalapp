package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editNombre, editCorreo, editClave, editTelefono, editFotoPerfil;
    private Button btnRegistrar;
    private DatabaseReference usuariosRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNombre = findViewById(R.id.editNombre);
        editCorreo = findViewById(R.id.editCorreo);
        editClave = findViewById(R.id.editClave);
        editTelefono = findViewById(R.id.editTelefono);
        editFotoPerfil = findViewById(R.id.editFotoPerfil);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = editNombre.getText().toString().trim();
        String correo = editCorreo.getText().toString().trim();
        String clave = editClave.getText().toString().trim();
        String telefono = editTelefono.getText().toString().trim();
        String fotoPerfil = editFotoPerfil.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || telefono.isEmpty() || fotoPerfil.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, correo, clave, telefono, fotoPerfil);

        usuariosRef.push().setValue(nuevoUsuario).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
