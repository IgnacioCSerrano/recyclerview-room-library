package dam.iesaugustobriga.cityproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dam.iesaugustobriga.cityproject.R;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("City World");

        button = findViewById(R.id.floatingActionButton);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, CityFormActivity.class);
            startActivity(intent);
        });
    }

}