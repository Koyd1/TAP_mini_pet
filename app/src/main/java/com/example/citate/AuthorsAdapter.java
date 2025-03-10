package com.example.citate;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.zip.Inflater;

public class AuthorsAdapter extends ArrayAdapter<AuthorData> {
    public AuthorsAdapter(@NonNull Context context, ArrayList<AuthorData> arr ) {
        super(context, R.layout.list_item, arr);
//        super(context, 0, arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View currentView = view;

        if (currentView == null) {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        AuthorData authorData = getItem(position);
        assert authorData != null;

        ImageView authorImage = currentView.findViewById(R.id.listView_image);
        TextView authorName = currentView.findViewById(R.id.listView_name);
        TextView authorDesc = currentView.findViewById(R.id.listView_desc);

        authorImage.setImageResource(authorData.image);
        authorName.setText(authorData.name);
        authorDesc.setText(authorData.desc);

        return currentView;
    }

}
