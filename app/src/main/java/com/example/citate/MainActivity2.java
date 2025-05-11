package com.example.citate;

//import com.example.citate.databinding.MainBinding;

//import static GetJson.getAllQuotes;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
//
//import java.io.File;
//import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.Reader;

import java.util.Random;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;

import android.view.View;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import org.json.JSONObject;
import org.json.JSONArray;

// LAB1 : Классы, объекты и конструкторы
// LAB2 : Наследование и композиция
// LAB3 : Перезаписывание и перегрузка методов
public class MainActivity2 extends AppCompatActivity {

    String[] authors = {"Стив Джобс", "Альберт Эйнштейн", "Илон Маск", "Смешные Цитаты", "Цитаты из Warcraft3", "Фалькон", "Булэйт"};
    String[] authors_desc = {"Бывший управляющий директора Apple", "Физик-теоретик и ученый", "Предприниматель и инженер", "Самые смешные цитаты на любые темы", "Цитаты из знаменитой игры", "Лучший в мире дед.", "Сэ трэиць домнул колонел"};
    int[] authors_images = {R.drawable.steve_jobs, R.drawable.albert, R.drawable.elon, R.drawable.funny, R.drawable.warcraft, R.drawable.test, R.drawable.anime};
    final String fileName = "quotes_main";
    ArrayList<QuoteData[]> allQuotes = new ArrayList<QuoteData[]>();
    ArrayList<AuthorData> dataList = new ArrayList<AuthorData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            allQuotes = GetJson.getAllQuotes(getApplicationContext(), fileName, authors);
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }


        for (int i = 0; i < authors.length; ++i) {
            dataList.add(new AuthorData(authors[i], authors_desc[i], authors_images[i]));
        }

        AuthorsAdapter authorsAdapter = new AuthorsAdapter(this, dataList);
        ListView listView = findViewById(R.id.author_list_view);
        listView.setAdapter(authorsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("authorName", authors[position]);
                intent.putExtra("authorDesc", authors_desc[position]);
                intent.putExtra("authorImage", authors_images[position]);
                intent.putExtra("authorQuotes", allQuotes.get(position));
                startActivity(intent);
            }
        });

        Button randomAuthorButton = findViewById(R.id.random_button);

        randomAuthorButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = authors.length;
                int idx = randInt(0, len);
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("authorName", authors[idx]);
                intent.putExtra("authorDesc", authors_desc[idx]);
                intent.putExtra("authorImage", authors_images[idx]);
                intent.putExtra("authorQuotes", allQuotes.get(idx));
                startActivity(intent);
            }
        });

        ImageView searchButton = findViewById(R.id.searchButton);
        EditText query = findViewById(R.id.searchQuery);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchQuoteActivity.class);
                intent.putExtra("authorName", authors);
                intent.putExtra("authorDesc", authors_desc);
                intent.putExtra("authorImage", authors_images);
                intent.putExtra("allQuotes", allQuotes);
                intent.putExtra("query", String.valueOf(query.getText()));
                startActivity(intent);
            }
        });
    }

    public static int randInt(int min, int max) {
        // Max exclusive, min inclusive
        Random rand = new Random();
        return rand.ints(min, max)
                .findFirst()
                .getAsInt();
    }


}