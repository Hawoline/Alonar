package ru.hawoline.alonar.view;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import ru.hawoline.alonar.R;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void drawMap() {

    }
}