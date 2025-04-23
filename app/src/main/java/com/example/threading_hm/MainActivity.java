package com.example.threading_hm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;
    int clickCount = 0;
    int secondsElapsed = 0;

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

        btn = findViewById(R.id.button);
        tv = findViewById(R.id.et);

        // 🔁 Click counter
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCount++;
                Toast.makeText(MainActivity.this, "Click: " + clickCount, Toast.LENGTH_SHORT).show();
            }
        });

        // ⏱ Timer thread - updates every 2 seconds
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000); // Wait 2 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    secondsElapsed += 2;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("Seconds since start: " + secondsElapsed);
                        }
                    });
                }
            }
        }).start();
    }
}
