package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChatDetailFragment extends Fragment {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private String chatId;

    private TextView tvProductName, tvProductPrice, tvSellerInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);

        recyclerView = view.findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Pasar el contexto al adaptador
        adapter = new MessageAdapter(getContext(), messageList);
        recyclerView.setAdapter(adapter);

        tvProductName = view.findViewById(R.id.tvProductName);
        tvProductPrice = view.findViewById(R.id.tvProductPrice);
        tvSellerInfo = view.findViewById(R.id.tvSellerInfo);

        Button btnCompraEnTienda = view.findViewById(R.id.btnCompraEnTienda);
        Button btnDefinirLugar = view.findViewById(R.id.btnDefinirLugar);

        if (getArguments() != null) {
            chatId = getArguments().getString("chatId");
            if (chatId != null && !chatId.isEmpty()) {
                loadChatDetails(chatId);

                // Configurar botones
                btnCompraEnTienda.setOnClickListener(v -> enviarMensaje(chatId, "Compra en tienda", btnCompraEnTienda, btnDefinirLugar));
                btnDefinirLugar.setOnClickListener(v -> enviarMensaje(chatId, "Definir lugar de encuentro", btnDefinirLugar, btnCompraEnTienda));
            } else {
                Toast.makeText(getContext(), "Error: ID del chat no válido", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void loadChatDetails(String chatId) {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId);
        DatabaseReference productosRef = FirebaseDatabase.getInstance().getReference("productos");
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");
        DatabaseReference mensajesRef = chatRef.child("mensajes");

        chatRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot chatSnapshot = task.getResult();

                // Obtener IDs relacionados
                String productoId = chatSnapshot.child("productoId").getValue(String.class);
                String vendedorId = chatSnapshot.child("vendedorId").getValue(String.class);

                // Cargar detalles del producto
                productosRef.child(productoId).get().addOnCompleteListener(productTask -> {
                    if (productTask.isSuccessful() && productTask.getResult().exists()) {
                        DataSnapshot productSnapshot = productTask.getResult();
                        String productName = productSnapshot.child("nombre").getValue(String.class);
                        int productPrice = productSnapshot.child("precio").getValue(Integer.class);

                        tvProductName.setText(productName);
                        tvProductPrice.setText("Precio: $" + productPrice);
                    }
                });

                // Cargar detalles del vendedor
                usuariosRef.child(vendedorId).get().addOnCompleteListener(userTask -> {
                    if (userTask.isSuccessful() && userTask.getResult().exists()) {
                        DataSnapshot userSnapshot = userTask.getResult();
                        String sellerName = userSnapshot.child("nombre").getValue(String.class);

                        tvSellerInfo.setText("Vendedor: " + sellerName + " (ID: " + vendedorId + ")");
                    }
                });

                // Escuchar los mensajes en tiempo real
                mensajesRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Cada vez que se agrega un nuevo mensaje
                        String sender = snapshot.child("de").getValue(String.class);
                        String text = snapshot.child("mensaje").getValue(String.class);
                        String timestamp = snapshot.child("timestamp").getValue(String.class);

                        if (sender != null && text != null && timestamp != null) {
                            messageList.add(new Message(sender, text, timestamp));
                            adapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(messageList.size() - 1); // Desplazar al último mensaje
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Opcional: manejar cambios en los mensajes si es necesario
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        // Opcional: manejar eliminación de mensajes si es necesario
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Opcional: manejar reordenamiento de mensajes si es necesario
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error al cargar mensajes: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Error al cargar el chat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarMensaje(String chatId, String mensaje, Button botonPresionado, Button botonDeshabilitar) {
        DatabaseReference mensajesRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("mensajes");

        // Crear un nuevo mensaje
        String mensajeId = mensajesRef.push().getKey();
        if (mensajeId != null) {
            String usuarioActual = "usuario3"; // Usuario actual
            String timestamp = String.valueOf(System.currentTimeMillis());

            Message nuevoMensaje = new Message(usuarioActual, mensaje, timestamp);
            mensajesRef.child(mensajeId).setValue(nuevoMensaje).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    botonPresionado.setEnabled(false); // Deshabilitar el botón presionado
                    botonDeshabilitar.setEnabled(false); // Deshabilitar el otro botón
                } else {
                    Toast.makeText(getContext(), "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}