package com.cauquitas.everythingappbd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AllCategoriesActivity extends AppCompatActivity {

    private RecyclerView categoriesRecycler;
    private CategoryAdapter categoryAdapter;
    private List<String> categoryList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        categoriesRecycler = findViewById(R.id.categories_recycler);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList, category -> {
            // Manejar el clic en una categoría
            Intent intent = new Intent(AllCategoriesActivity.this, ProductsActivity.class);
            intent.putExtra("category", category);
            startActivity(intent);
        });
        categoriesRecycler.setAdapter(categoryAdapter);

        // Inicializar Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("categorias");

        // Cargar categorías desde Realtime Database
        loadAllCategories();
    }

    private void loadAllCategories() {
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
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AllCategoriesActivity.this, "Error al cargar categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
