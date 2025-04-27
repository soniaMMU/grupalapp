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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MisTiendasFragment extends Fragment {

    private RecyclerView recyclerView;
    private TiendaAdapter tiendaAdapter;
    private ArrayList<Tienda> listaTiendas;
    private DatabaseReference databaseTiendas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_tiendas, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMisTiendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listaTiendas = new ArrayList<>();
        tiendaAdapter = new TiendaAdapter(listaTiendas, getActivity());
        recyclerView.setAdapter(tiendaAdapter);

        databaseTiendas = FirebaseDatabase.getInstance().getReference("tiendas");

        cargarMisTiendas(); // 👈 corregido

        return view;
    }

    private void cargarMisTiendas() {
        String idUsuarioActual = ((HomeActivity) requireActivity()).getIdUsuario(); // 🔥 obtener idUsuario desde HomeActivity

        if (idUsuarioActual == null) {
            Toast.makeText(getActivity(), "Error: usuario no identificado", Toast.LENGTH_SHORT).show();
            return;
        }

        Query query = databaseTiendas.orderByChild("idUsuario").equalTo(idUsuarioActual);

        query.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(getActivity(), "Error al cargar tus tiendas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
