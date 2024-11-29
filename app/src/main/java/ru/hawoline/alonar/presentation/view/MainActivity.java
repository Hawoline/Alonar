package ru.hawoline.alonar.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.presentation.presenter.MainPresenter;
import ru.hawoline.alonar.presentation.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainView {
    private MainPresenter mainPresenter;
    private LinearLayout gameLogLayout;
    private FrameLayout containerLayout;
    private GameFieldView gameFieldView;
    private InventoryView inventoryView;

    private TextView inventoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenterImpl();
        mainPresenter.saveId();
        mainPresenter.attachView(this);
        initViews();
        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameFieldView.render();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mainPresenter.saveInstance(outState);
        gameFieldView.getGameFieldPresenter().saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mainPresenter.restoreInstance(savedInstanceState);
        gameFieldView.getGameFieldPresenter().restoreInstance(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void initViews() {
        gameLogLayout = findViewById(R.id.main_gamelogs_linearlayout);
        containerLayout = findViewById(R.id.main_container_layout);
        gameFieldView = new GameFieldViewImpl(this, getLayoutInflater(), containerLayout);
        gameFieldView.setParentGameLogLayout(gameLogLayout);
        inventoryTextView = findViewById(R.id.main_inventory_textview);
    }

    @Override
    public void setOnClickListeners() {
        if (inventoryTextView != null) {
            inventoryTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    containerLayout.removeAllViews();
                    if (inventoryView == null) {
                        inventoryView = new InventoryView(getContext(), getLayoutInflater(),
                            containerLayout, gameFieldView.getGameFieldPresenter().getHero());
                        inventoryView.setGameFieldView(gameFieldView);
                    } else {
                        inventoryView.inflateView(getContext(), getLayoutInflater(),
                            containerLayout);
                    }
                }
            });
        }
    }

    private void setTextColor(TextView textView, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(getResources().getColor(color, getTheme()));
        } else {
            textView.setTextColor(getResources().getColor(color));
        }
    }

    @Override public void closeLoginView(String nickname) {

    }

    @Override public void showGameFieldView() {

    }
}