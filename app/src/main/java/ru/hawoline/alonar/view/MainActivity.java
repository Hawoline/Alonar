package ru.hawoline.alonar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.presenter.MainPresenter;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

import java.util.ArrayList;

public class MainActivity extends Activity implements MainView {
    private MainPresenter mMainPresenter;
    private GridLayout mMapGridLayout;
    private ImageView[][] mMapImageViews;
    private ImageView mHeroImageView;
    private LinearLayout mEnemiesListLayout;
    private LinearLayout mSlotsLayout;
    private ImageView[] mSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);
        findViews();
        render();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void render() {
        drawMap();
    }

    @Override
    public void drawMap() {
        int[][] map = mMainPresenter.getGameMap();
        int mapSize = map.length;
        Location personageLocation = mMainPresenter.getPersonageLocation();

        int i = 0;
        for (int x = personageLocation.getX() - 2; x < personageLocation.getX() + 3; x++) {
            int j = 0;
            for (int y = personageLocation.getY() - 2; y < personageLocation.getY() + 3; y++) {
                if (x > -1 && y > -1 && x < mapSize && y < mapSize) {
                    Resources resources = getResources();
                    Drawable[] layers = new Drawable[4];

                    if (map[y][x] == Map.GRASS) {
                        layers[0] = ResourcesCompat.getDrawable(resources, R.drawable.green, null);
                    } else if (map[y][x] == Map.MOUNTAIN) {
                        layers[0] = ResourcesCompat.getDrawable(resources, R.drawable.mountains, null);
                    }

                    ArrayList<Integer> enemiesAroundHero = mMainPresenter.findEnemiesAroundHero();

                    for (int enemy: enemiesAroundHero) {
                        Location enemyLocation = mMainPresenter.getEnemyLocationAt(enemy);
                        if (enemyLocation.getX() == x && enemyLocation.getY() == y) {
                            layers[1] = ResourcesCompat.getDrawable(resources, R.drawable.point_1, null);
                            layers[2] = ResourcesCompat.getDrawable(resources, R.drawable.point_2_quest, null);
                            layers[3] = ResourcesCompat.getDrawable(resources, R.drawable.point_3_e, null);
                        }
                    }
                    LayerDrawable layerDrawable = new LayerDrawable(layers);
                    mMapImageViews[j][i].setImageDrawable(layerDrawable);

                } else {
                    mMapImageViews[j][i].setImageResource(R.drawable.mountains);
                }
                j++;
            }
            i++;
        }

        drawPersonage(personageLocation);
    }

    private void drawPersonage(Location personageLocation) {
        if (personageLocation.getDirection() == Location.DIRECTION_BACK) {
            mHeroImageView.setImageResource(R.drawable.hero_back);
        } else if (personageLocation.getDirection() == Location.DIRECTION_FORWARD) {
            mHeroImageView.setImageResource(R.drawable.hero_forward);
        } else if (personageLocation.getDirection() == Location.DIRECTION_LEFT) {
            mHeroImageView.setImageResource(R.drawable.hero_left);
        } else if (personageLocation.getDirection() == Location.DIRECTION_RIGHT) {
            mHeroImageView.setImageResource(R.drawable.hero_right);
        }
    }

    private void findViews() {
        mMapGridLayout = findViewById(R.id.main_map_gridlayout);
        mMapGridLayout.setRowCount(5);
        mMapGridLayout.setColumnCount(5);
        mMapImageViews = new ImageView[5][5];
        for (int row = 0; row < mMapImageViews.length; row++) {
            for (int column = 0; column < mMapImageViews.length; column++) {
                mMapImageViews[row][column] = new ImageView(getContext());
                mMapImageViews[row][column].setId(View.generateViewId());
                mMapGridLayout.addView(mMapImageViews[row][column], row * 5 + column);
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                final int x = j - 2;
                final int y = i - 2;
                mMapImageViews[i][j].setOnClickListener(v -> {
                    mMainPresenter.move(x, y);
                    drawMap();
                });
            }
        }

        mHeroImageView = findViewById(R.id.hero_imageview);

        mEnemiesListLayout = findViewById(R.id.main_enemies_linearlayout);

        mSlotsLayout = findViewById(R.id.main_slots_linearlayout);
        mSlots = new ImageView[10];
        mSlots[0] = findViewById(R.id.main_slot_0);
        mSlots[1] = findViewById(R.id.main_slot_1);
        mSlots[2] = findViewById(R.id.main_slot_2);
        mSlots[3] = findViewById(R.id.main_slot_3);
        mSlots[4] = findViewById(R.id.main_slot_4);
        mSlots[5] = findViewById(R.id.main_slot_5);
        mSlots[6] = findViewById(R.id.main_slot_6);
        mSlots[7] = findViewById(R.id.main_slot_7);
        mSlots[8] = findViewById(R.id.main_slot_8);
        mSlots[9] = findViewById(R.id.main_slot_9);

        mSlots[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnemiesList();
            }
        });
    }

    @Override
    public void showEnemiesList() {
        removeEnemiesTextViews();

        ArrayList<Integer> enemies = mMainPresenter.findEnemiesAroundHero();

        if (enemies.size() < 2) {
            mEnemiesListLayout.setVisibility(View.GONE);
        } else {
            mEnemiesListLayout.setVisibility(View.VISIBLE);
        }

        if (enemies.size() == 1) {
            mMainPresenter.enemyAttacked(enemies.get(0));
            drawMap();
        } else {
            for (int enemy : enemies) {
                TextView enemyNameTextView = new TextView(getContext());
                enemyNameTextView.setText(mMainPresenter.getEnemyAt(enemy).getName());
                enemyNameTextView.setId(enemy);
                enemyNameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                enemyNameTextView.setOnClickListener(v -> {
                    mMainPresenter.enemyAttacked(v.getId());
                    drawMap();
                });
                mEnemiesListLayout.addView(enemyNameTextView);
            }
        }
    }

    private void removeEnemiesTextViews() {
        for (int enemyTextView = 0; enemyTextView < mEnemiesListLayout.getChildCount(); enemyTextView++) {
            if (mEnemiesListLayout.getChildAt(enemyTextView).getId() != R.id.main_chooseenemy_textview) {
                mEnemiesListLayout.removeViewAt(enemyTextView);
            }
        }
    }
}