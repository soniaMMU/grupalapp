package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class ChatDetailFragment extends Fragment {

    private ListView listViewMessages;
    private ArrayList<Message> messageList;
    private MessageAdapter adapter;
    private String chatId;

    public ChatDetailFragment() {
        // Requiere un constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);

        listViewMessages = view.findViewById(R.id.listViewMessages);
        messageList = new ArrayList<>();
        adapter = new MessageAdapter(getContext(), messageList);
        listViewMessages.setAdapter(adapter);

        // Obtener el chatId desde los argumentos
        if (getArguments() != null) {
            chatId = getArguments().getString("chatId");
            loadMessagesFromFirebase(chatId);
        }

        return view;
    }

    private void loadMessagesFromFirebase(String chatId) {
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("mensajes");
        messagesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                messageList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String sender = messageSnapshot.child("de").getValue(String.class);
                    String text = messageSnapshot.child("mensaje").getValue(String.class);
                    messageList.add(new Message(sender, text));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}