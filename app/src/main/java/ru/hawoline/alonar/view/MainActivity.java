package ru.hawoline.alonar.view;

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
import ru.hawoline.alonar.model.gamelog.GameLog;
import ru.hawoline.alonar.presenter.MainPresenter;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainAppView {
    private MainPresenter mMainPresenter;
    private LinearLayout mGameLogLayout;
    private FrameLayout mContainerLayout;
    private GameFieldView mGameFieldView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);

        findViews();
        setOnClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mMainPresenter.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mMainPresenter.restoreInstance(savedInstanceState);
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
    public void findViews() {
        mGameLogLayout = findViewById(R.id.main_gamelogs_linearlayout);
        mContainerLayout = findViewById(R.id.main_container_layout);
        mGameFieldView = new GameFieldAppView(this, getLayoutInflater(), mContainerLayout);
        mGameFieldView.setParentGameLogLayout(mGameLogLayout);
    }

    @Override
    public void setOnClickListeners() {

    }

    protected void createLogTextViews() {
        mGameLogLayout.removeAllViews();
        String[] log = GameLog.getInstance().showLog();
        for (String s : log) {
            TextView logTextView = new TextView(getContext());
            logTextView.setText(s);
            setTextColor(logTextView, R.color.text_color);
            logTextView.setId(View.generateViewId());
            mGameLogLayout.addView(logTextView);
        }
        mGameLogLayout.setVisibility(View.VISIBLE);
    }

    private void setTextColor(TextView textView, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(getResources().getColor(color, getTheme()));
        } else {
            textView.setTextColor(getResources().getColor(color));
        }
    }
}