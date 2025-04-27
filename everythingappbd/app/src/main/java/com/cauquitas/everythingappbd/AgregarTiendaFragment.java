package com.cauquitas.everythingappbd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class AgregarTiendaFragment extends Fragment {

    private EditText etNombreTienda, etDescripcionTienda, etFotoTienda;
    private Button btnAgregarTienda;
    private DatabaseReference databaseTiendas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_tienda, container, false);

        etNombreTienda = view.findViewById(R.id.etNombreTienda);
        etDescripcionTienda = view.findViewById(R.id.etDescripcionTienda);
        etFotoTienda = view.findViewById(R.id.etFotoTienda);
        btnAgregarTienda = view.findViewById(R.id.btnAgregarTienda);

        databaseTiendas = FirebaseDatabase.getInstance().getReference("tiendas");

        btnAgregarTienda.setOnClickListener(v -> {
            String nombreTienda = etNombreTienda.getText().toString().trim();
            String descripcionTienda = etDescripcionTienda.getText().toString().trim();
            String fotoTienda = etFotoTienda.getText().toString().trim();

            if (!nombreTienda.isEmpty() && !descripcionTienda.isEmpty() && !fotoTienda.isEmpty()) {
                String tiendaId = databaseTiendas.push().getKey();
                String idUsuario = ((HomeActivity) requireActivity()).getIdUsuario(); // 🔥 obtener ID del usuario de HomeActivity

                if (tiendaId != null && idUsuario != null) {
                    Map<String, Object> tiendaData = new HashMap<>();
                    tiendaData.put("id", tiendaId);
                    tiendaData.put("nombreTienda", nombreTienda);
                    tiendaData.put("descripcionTienda", descripcionTienda);
                    tiendaData.put("fotoTienda", fotoTienda);
                    tiendaData.put("idUsuario", idUsuario); // 👈 Guardamos correctamente el idUsuario
                    tiendaData.put("productos", new HashMap<>());

                    databaseTiendas.child(tiendaId).setValue(tiendaData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Tienda agregada correctamente", Toast.LENGTH_SHORT).show();
                                etNombreTienda.setText("");
                                etDescripcionTienda.setText("");
                                etFotoTienda.setText("");
                                irAFragmentoTiendas();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Error al agregar tienda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos de usuario", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void irAFragmentoTiendas() {
        TiendasFragment tiendasFragment = new TiendasFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, tiendasFragment);
        transaction.commit();
    }
}
