package com.example.citate;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// LAB1 : Классы, объекты и конструкторы
// LAB2 : Наследование и композиция
// LAB3 : Перезаписывание и перегрузка методов
public class MainActivity extends AppCompatActivity {

    String[] authors = {"Стив Джобс", "Альберт Эйнштейн", "Илон Маск", "Смешные Цитаты", "Цитаты из Warcraft3"};
    int[] authors_images = {R.drawable.steve_jobs, R.drawable.albert, R.drawable.elon, R.drawable.funny, R.drawable.warcraft};

    final String fileName = "quotes_start";
    ArrayList<String[]> allQuotes = new ArrayList<String[]>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            allQuotes = GetJson.getAllStartQuotes(getApplicationContext(), fileName, authors);
        }
        catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }


        // Set authors image, name and quote
        int len = authors.length;
        int authorIdx = MainActivity2.randInt(0, len);
        int quoteIdx = MainActivity2.randInt(0, allQuotes.get(authorIdx).length);


        ImageView authorAvatar = findViewById(R.id.authorAvatar);
        TextView authorName = findViewById(R.id.authorName);
        TextView quoteText = findViewById(R.id.quoteText);
        TextView extraText = findViewById(R.id.extraText);

        authorAvatar.setImageResource(authors_images[authorIdx]);
        authorName.setText(authors[authorIdx]);
        quoteText.setText(allQuotes.get(authorIdx)[quoteIdx]);
        extraText.setText("Цитата №" + (quoteIdx+1));

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}