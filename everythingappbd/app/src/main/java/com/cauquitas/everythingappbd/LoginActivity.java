package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText editCorreo, editClave;
    private Button btnLogin;
    private DatabaseReference databaseUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");

        editCorreo = findViewById(R.id.editCorreo);
        editClave = findViewById(R.id.editClave);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> loginUsuario());

        TextView textRegistrar = findViewById(R.id.textRegistrar);

        textRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void loginUsuario() {
        String correo = editCorreo.getText().toString();
        String clave = editClave.getText().toString();

        if (TextUtils.isEmpty(correo) || TextUtils.isEmpty(clave)) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseUsuarios.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean encontrado = false;
                for (DataSnapshot usuarioSnapshot : task.getResult().getChildren()) {
                    Usuario usuario = usuarioSnapshot.getValue(Usuario.class);
                    if (usuario != null && usuario.getCorreo().equals(correo) && usuario.getClave().equals(clave)) {
                        encontrado = true;

                        // Login exitoso, pasa el correo Y TAMBIÉN el ID (key) del usuario a HomeActivity
                        String idUsuario = usuarioSnapshot.getKey(); // 🔥 Obtener el ID desde el snapshot
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("correo", correo);
                        intent.putExtra("idUsuario", idUsuario); // 🔥 enviar también el id
                        startActivity(intent);
                        finish();
                        break;
                    }
                }

                if (!encontrado) {
                    Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
