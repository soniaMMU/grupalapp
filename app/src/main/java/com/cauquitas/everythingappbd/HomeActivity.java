package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_tienda) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new TiendaFragment())
                    .commit();
                return true;
            } else if (itemId == R.id.nav_agregar_tienda) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new AgregarTiendaFragment())
                    .commit();
                return true;
            } else if (itemId == R.id.nav_favoritos) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new FavoritosFragment())
                    .commit();
                return true;
            } else if (itemId == R.id.navigation_search) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SearchFragment())
                    .commit();
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
