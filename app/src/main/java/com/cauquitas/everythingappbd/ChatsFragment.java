package com.cauquitas.everythingappbd;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatsFragment extends Fragment {

    private ListView listViewChats;
    private ArrayList<String> chatList;
    private BaseAdapter adapter;

    public ChatsFragment() {
        // Requiere un constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        listViewChats = view.findViewById(R.id.listViewChats);
        chatList = new ArrayList<>();
        adapter = new ChatAdapter(chatList);
        listViewChats.setAdapter(adapter);

        // Cargar datos desde Firebase
        loadChatsFromFirebase();

        listViewChats.setOnItemClickListener((parent, view1, position, id) -> {
            String chatId = chatList.get(position).split("\n")[0].replace("Chat ID: ", "");

            ChatDetailFragment chatDetailFragment = new ChatDetailFragment();
            Bundle args = new Bundle();
            args.putString("chatId", chatId);
            chatDetailFragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, chatDetailFragment)
                .addToBackStack(null)
                .commit();
        });

        return view;
    }

    private void loadChatsFromFirebase() {
        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("chats");
        chatsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                chatList.clear(); // Limpiar la lista antes de agregar nuevos datos
                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    String compradorId = chatSnapshot.child("compradorId").getValue(String.class);
                    if ("usuario3".equals(compradorId)) { // Filtrar por compradorId
                        String chatId = chatSnapshot.getKey();
                        String lastMessage = chatSnapshot.child("mensajes").child("mensaje2").child("mensaje").getValue(String.class);
                        chatList.add("Chat ID: " + chatId + "\nÚltimo mensaje: " + lastMessage);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class ChatAdapter extends BaseAdapter {
        private ArrayList<String> chats;

        public ChatAdapter(ArrayList<String> chats) {
            this.chats = chats;
        }

        @Override
        public int getCount() {
            return chats.size();
        }

        @Override
        public Object getItem(int position) {
            return chats.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
            }

            String chat = chats.get(position);
            String[] parts = chat.split("\n");

            TextView tvChatId = convertView.findViewById(R.id.tvChatId);
            TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);

            tvChatId.setText(parts[0]);
            tvLastMessage.setText(parts[1]);

            return convertView;
        }
    }
}