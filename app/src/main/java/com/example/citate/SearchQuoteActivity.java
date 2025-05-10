package com.example.citate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SearchQuoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_quote);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.quote_list_view);
        TextView notFoundView = findViewById(R.id.notFoundView);
        ArrayList<QuoteData> allMatches;

        Intent intent = this.getIntent();
        if( intent != null) {
            String query = intent.getStringExtra("query");
            ArrayList<QuoteData[]> allQuotes = (ArrayList<QuoteData[]>) intent.getSerializableExtra("allQuotes");
            if(query.trim().startsWith("#")) {
                allMatches = searchTag(query, allQuotes);
            }
            else {
                allMatches = searchQuote(query, allQuotes);
            }

            SearchQuoteAdapter quoteAdapter = new SearchQuoteAdapter(this, allMatches);
            listView.setAdapter(quoteAdapter);

            if(!allMatches.isEmpty()) {
                notFoundView.setText("Были найдены следующие цитаты");
            }

        }
        else {
            notFoundView.setText("Ошибка открытия страницы!");
        }

        // GoBackButton
        ImageView goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private ArrayList<QuoteData> searchQuote(String query, ArrayList<QuoteData[]> allQuotes) {
        ArrayList<QuoteData> allMatches = new ArrayList<QuoteData>();
        for(int i = 0; i < allQuotes.size(); ++i) {
            for(int j = 0; j < allQuotes.get(i).length; ++j) {
                Matcher m = Pattern.compile("(.*?)"+query.toLowerCase().trim()+"(.*?)").matcher(allQuotes.get(i)[j].quoteText.toLowerCase());
                if(m.find()) {
                    allMatches.add(allQuotes.get(i)[j]);
                }
            }
        }

        return allMatches;
    }

    private ArrayList<QuoteData> searchTag(String query, ArrayList<QuoteData[]> allQuotes) {
        ArrayList<QuoteData> allMatches = new ArrayList<QuoteData>();
        for(int i = 0; i < allQuotes.size(); ++i) {
            for(int j = 0; j < allQuotes.get(i).length; ++j) {
                Matcher m = Pattern.compile("(.*?)"+query.toLowerCase().trim()+"(.*?)").
                        matcher(strArrToStr(allQuotes.get(i)[j].quoteTags, " ").toLowerCase().trim());
                if(m.find()) {
                    allMatches.add(allQuotes.get(i)[j]);
                }
            }
        }
        return allMatches;
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