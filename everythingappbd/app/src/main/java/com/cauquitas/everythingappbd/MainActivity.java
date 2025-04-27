package com.cauquitas.everythingappbd;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);

        // Verificar conexión
        if (FirebaseApp.getApps(this).size() > 0) {
            Toast.makeText(this, "✅ Conectado a Firebase correctamente", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "❌ No conectado a Firebase", Toast.LENGTH_LONG).show();
        }
    }
}
