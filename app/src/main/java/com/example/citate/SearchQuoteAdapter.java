package com.example.citate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SearchQuoteAdapter extends ArrayAdapter<QuoteData> {

    public SearchQuoteAdapter(@NonNull Context context, ArrayList<QuoteData> arr ) {
        super(context, R.layout.list_search_item, arr);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        View currentView = view;

        if (currentView == null) {
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.list_search_item, parent, false);
        }

        QuoteData quoteData = getItem(position);
        assert quoteData != null;

        TextView quoteView = currentView.findViewById(R.id.listView_quote);
        TextView authorView = currentView.findViewById(R.id.listView_author);
        TextView tagsView = currentView.findViewById(R.id.listView_tags);

        quoteView.setText(quoteData.quoteText);
        authorView.setText(quoteData.authorName);
        tagsView.setText(strArrToStr(quoteData.quoteTags, " "));

        return currentView;
    }

    public String strArrToStr(String[] arr, String delimiter) {
        String res = "";
        for(int i = 0; i < arr.length; ++i) {
            res += arr[i];
            res += delimiter;
        }
        return res.trim();
    }

}
