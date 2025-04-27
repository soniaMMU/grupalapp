package com.cauquitas.everythingappbd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Message> messages;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messages.get(position);

        if (convertView == null) {
            if ("usuario3".equals(message.getSender())) {
                convertView = LayoutInflater.from(context).inflate(R.layout.message_bubble_user, parent, false);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.message_bubble_other, parent, false);
            }
        }

        TextView textView = convertView.findViewById(R.id.textViewMessage);
        textView.setText(message.getText());

        return convertView;
    }
}