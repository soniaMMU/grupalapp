package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ChatDetailFragment extends Fragment {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private String chatId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);

        recyclerView = view.findViewById(R.id.rvMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MessageAdapter(getContext(), messageList);
        recyclerView.setAdapter(adapter);

        // Obtener el chatId desde los argumentos
        if (getArguments() != null) {
            chatId = getArguments().getString("chatId");
            if (chatId != null && !chatId.isEmpty()) {
                loadMessagesFromFirebase(chatId);
            } else {
                Toast.makeText(getContext(), "Error: ID del chat no válido", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }

    private void loadMessagesFromFirebase(String chatId) {
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId);
        chatRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot chatSnapshot = task.getResult();
                DataSnapshot mensajesSnapshot = chatSnapshot.child("mensajes");

                messageList.clear();
                for (DataSnapshot messageSnapshot : mensajesSnapshot.getChildren()) {
                    String sender = messageSnapshot.child("de").getValue(String.class);
                    String text = messageSnapshot.child("mensaje").getValue(String.class);
                    String timestamp = messageSnapshot.child("timestamp").getValue(String.class);

                    if (sender != null && text != null && timestamp != null) {
                        messageList.add(new Message(sender, text, timestamp));
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Error al cargar el chat", Toast.LENGTH_SHORT).show();
            }
        });
    }
}