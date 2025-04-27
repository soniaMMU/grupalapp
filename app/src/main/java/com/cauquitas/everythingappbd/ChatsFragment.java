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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Toast;
import com.cauquitas.everythingappbd.models.Chat;

public class ChatsFragment extends Fragment {

    private ListView listViewChats;
    private ArrayList<String> chatList;
    private ArrayAdapter<String> adapter;

    public ChatsFragment() {
        // Requiere un constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        TextView tvChatsTitulo = view.findViewById(R.id.tvChatsTitulo);
        tvChatsTitulo.setText("Chats de compras");

        listViewChats = view.findViewById(R.id.listViewChats);

        // Cargar datos desde Firebase
        loadChatsFromFirebase();

        listViewChats.setOnItemClickListener((parent, view1, position, id) -> {
            Chat chat = (Chat) listViewChats.getAdapter().getItem(position);

            ChatDetailFragment chatDetailFragment = new ChatDetailFragment();
            Bundle args = new Bundle();
            args.putString("chatId", chat.getChatId());
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
        DatabaseReference productosRef = FirebaseDatabase.getInstance().getReference("productos");
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios");

        chatsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                List<Chat> chatList = new ArrayList<>();

                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    String compradorId = chatSnapshot.child("compradorId").getValue(String.class);
                    if ("usuario3".equals(compradorId)) { // Filtrar por usuario3
                        String chatId = chatSnapshot.getKey();
                        String productoId = chatSnapshot.child("productoId").getValue(String.class);
                        String vendedorId = chatSnapshot.child("vendedorId").getValue(String.class);

                        productosRef.child(productoId).get().addOnCompleteListener(productTask -> {
                            if (productTask.isSuccessful()) {
                                String productoNombre = productTask.getResult().child("nombre").getValue(String.class);

                                usuariosRef.child(vendedorId).get().addOnCompleteListener(userTask -> {
                                    if (userTask.isSuccessful()) {
                                        String vendedorNombre = userTask.getResult().child("nombre").getValue(String.class);

                                        Chat chat = new Chat(chatId, productoNombre, vendedorId, vendedorNombre);
                                        chatList.add(chat);

                                        // Actualizar el adaptador
                                        ChatAdapter adapter = new ChatAdapter(chatList);
                                        listViewChats.setAdapter(adapter);
                                    }
                                });
                            }
                        });
                    }
                }
            }
        });
    }

    public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
        private List<Message> messages;

        public MessageAdapter(List<Message> messages) {
            this.messages = messages;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            Message message = messages.get(position);
            holder.tvChatId.setText(message.getSender());
            holder.tvLastMessage.setText(message.getText()); // Asegúrate de que tvLastMessage existe en el diseño
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        // Clase interna para el ViewHolder
        class MessageViewHolder extends RecyclerView.ViewHolder {
            TextView tvChatId, tvLastMessage;

            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                tvChatId = itemView.findViewById(R.id.tvChatId);
                tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            }
        }
    }

    private class ChatAdapter extends BaseAdapter {
        private List<Chat> chats;

        public ChatAdapter(List<Chat> chats) {
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

            Chat chat = chats.get(position);

            TextView tvChatId = convertView.findViewById(R.id.tvChatId);
            TextView tvProductoNombre = convertView.findViewById(R.id.tvProductoNombre);
            TextView tvVendedorInfo = convertView.findViewById(R.id.tvVendedorInfo);

            tvChatId.setText("ID: " + chat.getChatId());
            tvProductoNombre.setText(chat.getProductoNombre());
            tvVendedorInfo.setText("Vendedor: " + chat.getVendedorNombre() + " (ID: " + chat.getVendedorId() + ")");

            return convertView;
        }
    }
}