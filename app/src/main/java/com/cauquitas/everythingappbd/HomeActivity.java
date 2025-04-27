package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Por defecto cargamos el fragment Tiendas
        loadFragment(new TiendasFragment());

        // Usamos 'setOnItemSelectedListener' con 'if-else' en lugar de 'switch'
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_tienda) {
                selectedFragment = new TiendasFragment();
            } else if (item.getItemId() == R.id.nav_agregar_tienda) {
                selectedFragment = new AgregarTiendaFragment();
            } else if (item.getItemId() == R.id.nav_favoritos) {
                selectedFragment = new FavoritosFragment();
            } else if (item.getItemId() == R.id.nav_chats) { // Nueva sección
                selectedFragment = new ChatsFragment();
            } else if (item.getItemId() == R.id.nav_recomendaciones) {
                selectedFragment = new RecomendacionesFragment();
            }

            loadFragment(selectedFragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
