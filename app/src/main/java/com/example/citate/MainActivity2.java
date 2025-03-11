package com.example.citate;

//import com.example.citate.databinding.MainBinding;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.AdapterView;

import android.view.View;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    String[] authors = {"Фалькон", "Булэйт", "Автор 1", "Автор 2"};
    String[] authors_desc = {"Лучший в мире дед.", "Сэ трэиць домнул колонел", "Бла-бла-бла-бла-бла", "Бла-бла-бла-бла-бла"};
    int[] authors_images = {R.drawable.test, R.drawable.anime, R.drawable.test, R.drawable.test};
    String[] falco_quotes = {"Женскому уму это не понять.", "Политех не учит программированию, политех учит жизни."};
    ArrayList<AuthorData> dataList = new ArrayList<AuthorData>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                intent.putExtra("authorQuotes", falco_quotes);
                startActivity(intent);
            }

        });

    }
}