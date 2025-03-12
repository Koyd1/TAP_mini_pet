package com.example.citate;

//import com.example.citate.databinding.MainBinding;

//import static GetJson.getAllQuotes;


import java.io.InputStreamReader;
import java.util.ArrayList;
//import java.util.HashMap;
//
//import java.io.File;
//import java.io.FileInputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.Reader;

import java.util.Random;

import android.os.Bundle;
import android.util.Log;
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

public class MainActivity2 extends AppCompatActivity {

    String[] authors = {"Фалькон", "Булэйт", "Автор 1", "Автор 2"};
    String[] authors_desc = {"Лучший в мире дед.", "Сэ трэиць домнул колонел", "Бла-бла-бла-бла-бла", "Бла-бла-бла-бла-бла"};
    int[] authors_images = {R.drawable.test, R.drawable.anime, R.drawable.test, R.drawable.test};
    final String fileName = "quotes_main";
    ArrayList<String[]> allQuotes = new ArrayList<String[]>();
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

//        allQuotes = getAllQuotes(fileName, authors);
        allQuotes = GetJson.getAllQuotes(getApplicationContext(), fileName, authors);
        for(int i = 0; i < allQuotes.size(); ++i) {
            Log.d("Author", authors[i]);
            for(int j = 0; j < allQuotes.get(i).length; ++j) {
                Log.d("Quote" + j, allQuotes.get(i)[j]);
            }
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
    }

    public static int randInt(int min, int max) {
        // Max exclusive, min inclusive
        Random rand = new Random();
        return rand.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public ArrayList<String[]> getAllQuotes(String fileName, String[] authorsNames) {
//        InputStream jsonData = getApplicationContext().getResources().openRawResource(
//                getApplicationContext().getResources().getIdentifier(
//                        fileName, "raw", getApplicationContext().getPackageName()
//                )
//        );

        InputStream jsonData = getApplicationContext().getResources().openRawResource(R.raw.quotes_main);

        Reader reader = new InputStreamReader(jsonData);
        BufferedReader streamReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        for(String authorName : authorsNames) {
            String inputStr;
            try {
                while ((inputStr = streamReader.readLine()) != null) {
                    stringBuilder.append(inputStr);
                }

                JSONObject json = new JSONObject(stringBuilder.toString());
                JSONArray resArrJson = json.getJSONArray(authorName);
                int len = resArrJson.length();
                String[] resArr = new String[len];

                // JSONArray to String[]
                for (int i = 0; i < len; ++i) {
                    resArr[i] = resArrJson.getString(i);
                }
                allQuotes.add(resArr);
            }
            catch(Exception e) {
                e.printStackTrace();
                Log.d("Error", e.toString());
            }
        }

        return allQuotes;
    }
}