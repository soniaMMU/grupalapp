package com.cauquitas.everythingappbd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TiendasFragment extends Fragment {

    private RecyclerView recyclerView;
    private TiendaAdapter tiendaAdapter;
    private ArrayList<Tienda> listaTiendas;
    private DatabaseReference databaseTiendas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tiendas, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTiendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listaTiendas = new ArrayList<>();
        // Aquí se pasa el contexto al constructor de TiendaAdapter
        tiendaAdapter = new TiendaAdapter(listaTiendas, getActivity());
        recyclerView.setAdapter(tiendaAdapter);

        databaseTiendas = FirebaseDatabase.getInstance().getReference("tiendas");

        databaseTiendas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaTiendas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tienda tienda = snapshot.getValue(Tienda.class);
                    listaTiendas.add(tienda);
                }
                tiendaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error al cargar las tiendas", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
