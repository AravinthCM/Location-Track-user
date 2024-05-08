package com.example.locationtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<UserData> {

    private Context context;
    private int resource;
    private List<UserData> userDataList;

    public CustomAdapter(Context context, int resource, List<UserData> userDataList) {
        super(context, resource, userDataList);
        this.context = context;
        this.resource = resource;
        this.userDataList = userDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView busNumberTextView = convertView.findViewById(R.id.busNumberTextView);

        UserData userData = userDataList.get(position);
        nameTextView.setText(userData.getName());
        emailTextView.setText(userData.getEmailAddress());
        String busNumberString = String.valueOf(userData.getBusNumber());
        busNumberTextView.setText(busNumberString);

        return convertView;
    }
}
