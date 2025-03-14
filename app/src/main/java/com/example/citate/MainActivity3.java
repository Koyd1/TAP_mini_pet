package com.example.citate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// LAB1 : Классы, объекты и конструкторы
// LAB2 : Наследование и композиция
// LAB3 : Перезаписывание и перегрузка методов
public class MainActivity3 extends AppCompatActivity {

    int currQuote = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView image = findViewById(R.id.imageView);
        TextView quote = findViewById(R.id.textView);
        TextView authorName = findViewById(R.id.authorNameView);
        TextView quoteNum = findViewById(R.id.quoteNumView);

        ImageView arrowRight = findViewById(R.id.arrow_right);
        ImageView arrowLeft = findViewById(R.id.arrow_left);

        Intent intent = this.getIntent();
        if(intent != null) {
            image.setImageResource(intent.getIntExtra("authorImage", R.drawable.arrow_left));
            authorName.setText(intent.getStringExtra("authorName"));
            String[] quotesArr = intent.getStringArrayExtra("authorQuotes");
            if (quotesArr != null) {
                quote.setText(quotesArr[0]);
                quoteNum.setText("Цитата №" + 1);

                // Next quote
                arrowRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currQuote++;
                        if (currQuote == quotesArr.length) {
                            currQuote = 0;
                        }
                        quote.setText(quotesArr[currQuote]);
                        quoteNum.setText("Цитата №" + (currQuote + 1));
                    }
                });

                // Previous quote
                arrowLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currQuote > 0) {
                            currQuote--;
                            quote.setText(quotesArr[currQuote]);
                            quoteNum.setText("Цитата №" + (currQuote + 1));
                        }

                    }
                });
            }
            else
                quote.setText("Цитаты не найдены.");
        }

        ImageView goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}