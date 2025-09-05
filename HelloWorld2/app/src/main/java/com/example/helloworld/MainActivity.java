package com.example.helloworld;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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
        Button button = findViewById(R.id.button);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Hello ISU!");
        strings.add("Hello World!");
        strings.add("Hello Ames!");
        strings.add("Hello Iowa!");
        strings.add("Hello USA!");
        button.setOnClickListener(v -> {
            Random rand = new Random();
            int num = Math.abs(rand.nextInt()) % 5;
            TextView textView = findViewById(R.id.textView);
            if(textView.getText() == strings.get(num)){
                num = ++num % 5;
            }
            textView.setText(strings.get(num));
        });
    }
}