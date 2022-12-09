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
    private MainPresenter mMainPresenter;
    private LinearLayout mGameLogLayout;
    private FrameLayout mContainerLayout;
    private GameFieldView mGameFieldView;
    private InventoryView mInventoryView;

    private TextView mInventoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);

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
        mGameFieldView.render();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mMainPresenter.saveInstance(outState);
        mGameFieldView.getGameFieldPresenter().saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mMainPresenter.restoreInstance(savedInstanceState);
        mGameFieldView.getGameFieldPresenter().restoreInstance(savedInstanceState);
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
        mGameLogLayout = findViewById(R.id.main_gamelogs_linearlayout);
        mContainerLayout = findViewById(R.id.main_container_layout);
        mGameFieldView = new GameFieldViewImpl(this, getLayoutInflater(), mContainerLayout);
        mGameFieldView.setParentGameLogLayout(mGameLogLayout);

        mInventoryTextView = findViewById(R.id.main_inventory_textview);
    }

    @Override
    public void setOnClickListeners() {
        if (mInventoryTextView != null) {
            mInventoryTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContainerLayout.removeAllViews();
                    if (mInventoryView == null) {
                        mInventoryView = new InventoryView(getContext(), getLayoutInflater(), mContainerLayout, mGameFieldView.getGameFieldPresenter().getHero());
                        mInventoryView.setGameFieldView(mGameFieldView);
                    } else {
                        mInventoryView.inflateView(getContext(), getLayoutInflater(), mContainerLayout);
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
}