package thecircle.seechange.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import thecircle.seechange.R;
import thecircle.seechange.domain.Message;

public class ChatMessageAdapter extends ArrayAdapter<Message> {

    public ChatMessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        int[] androidColors = convertView.getResources().getIntArray(R.array.androidcolors);
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        convertView.setBackgroundColor(randomAndroidColor);

        Message message = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
        }
        TextView usernameTV = (TextView) convertView.findViewById(R.id.username);
        TextView messageTV = (TextView) convertView.findViewById(R.id.message);

        messageTV.setText(message.getMessage());
        usernameTV.setText(message.getUsername());
        return convertView;
    }
}
