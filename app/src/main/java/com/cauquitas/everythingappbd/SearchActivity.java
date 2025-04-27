package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private LinearLayout categoriesContainer;
    private Button moreCategoriesButton;
    private SeekBar priceFilterSeekBar;
    private RecyclerView recyclerView;

    private DatabaseReference databaseReference;
    private List<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.search_bar);
        categoriesContainer = findViewById(R.id.categories_container);
        moreCategoriesButton = findViewById(R.id.more_categories_button);
        priceFilterSeekBar = findViewById(R.id.price_filter_seekbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(categoryList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("categorias");

        // Cargar categorías desde Realtime Database
        loadCategories();

        // Configurar botón "Más categorías"
        moreCategoriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, AllCategoriesActivity.class);
            startActivity(intent);
        });
    }

    private void loadCategories() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String category = snapshot.getValue(String.class);
                    if (category != null) {
                        categoryList.add(category);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
            }
        });
    }
}
