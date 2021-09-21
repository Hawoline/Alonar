package ru.hawoline.alonar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.personage.Enemy;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.presenter.MainPresenter;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

import java.util.ArrayList;

public class MainActivity extends Activity implements MainView {
    private MainPresenter mMainPresenter;
    private LinearLayout mSlotsLayout;
    private LinearLayout mEnemiesListLayout;
    private GridLayout mMapGridLayout;
    private TextView mChooseEnemyTextView;
    private ImageView[][] mMapImageViews;
    private ImageView mHeroImageView;
    private ImageView[] mSlots;

    private int mRemovableViewId;

    private final int VISIBLE_CELLS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainPresenter = new MainPresenterImpl();
        mMainPresenter.attachView(this);
        findViews();
        setOnClickListeners();
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

        int xLocation = personageLocation.getX();
        int yLocation = personageLocation.getY();
        int i = 0;
        for (int x = xLocation - 2; x < xLocation + 3; x++) {
            int j = 0;
            for (int y = yLocation - 2; y < yLocation + 3; y++) {
                if (x > -1 && y > -1 && x < mapSize && y < mapSize) {
                    Resources resources = getResources();
                    Drawable[] layers = new Drawable[4];

                    layers[0] = ResourcesCompat.getDrawable(resources, getLandscapeDrawableId(map[y][x]), null);

                    ArrayList<Enemy> enemiesAroundHero = mMainPresenter.findEnemiesAroundHero();

                    for (Enemy enemy : enemiesAroundHero) {
                        Location enemyLocation = mMainPresenter.getEnemyLocation(enemy);
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

    private int getLandscapeDrawableId(int landscapeType) {
        int drawableId;
        if (landscapeType == LandscapeMap.GRASS) {
            drawableId = R.drawable.green;
        } else if (landscapeType == LandscapeMap.MOUNTAIN) {
            drawableId = R.drawable.mountains;
        } else {
            drawableId = R.drawable.mountains;
        }
        return drawableId;
    }

    private void drawPersonage(Location personageLocation) {
        int direction = personageLocation.getDirection();
        if (direction == Location.DIRECTION_BACK) {
            mHeroImageView.setImageResource(R.drawable.hero_back);
        } else if (direction == Location.DIRECTION_FORWARD) {
            mHeroImageView.setImageResource(R.drawable.hero_forward);
        } else if (direction == Location.DIRECTION_LEFT) {
            mHeroImageView.setImageResource(R.drawable.hero_left);
        } else if (direction == Location.DIRECTION_RIGHT) {
            mHeroImageView.setImageResource(R.drawable.hero_right);
        }
    }

    private void findViews() {
        mMapGridLayout = findViewById(R.id.main_map_gridlayout);
        mMapGridLayout.setRowCount(VISIBLE_CELLS);
        mMapGridLayout.setColumnCount(VISIBLE_CELLS);
        mChooseEnemyTextView = findViewById(R.id.main_chooseenemy_textview);
        mMapImageViews = new ImageView[VISIBLE_CELLS][VISIBLE_CELLS];
        for (int row = 0; row < VISIBLE_CELLS; row++) {
            for (int column = 0; column < VISIBLE_CELLS; column++) {
                mMapImageViews[row][column] = new ImageView(getContext());
                mMapImageViews[row][column].setId(View.generateViewId());
                mMapGridLayout.addView(mMapImageViews[row][column], row * 5 + column);
            }
        }
        for (int i = 0; i < VISIBLE_CELLS; i++) {
            for (int j = 0; j < VISIBLE_CELLS; j++) {
                final int x = j - 2;
                final int y = i - 2;
                mMapImageViews[i][j].setOnClickListener(v -> {
                    mMainPresenter.onPersonageMove(x, y);
                    drawMap();
                });
            }
        }

        mHeroImageView = findViewById(R.id.hero_imageview);

        mEnemiesListLayout = findViewById(R.id.main_enemies_linearlayout);

        mSlotsLayout = findViewById(R.id.main_slots_linearlayout);
        mSlots = new ImageView[10];
        Resources resources = getResources();
        for (int slot = 0; slot < 10; slot++) {
            mSlots[slot] = findViewById(
                    resources.getIdentifier("main_slot_" + slot, "id", "ru.hawoline.alonar"));
        }
    }

    private void setOnClickListeners() {
        mSlots[1].setOnClickListener(v -> showEnemiesList());
    }

    @Override
    public void showEnemiesList() {
        removeEnemyTextViews();

        ArrayList<Enemy> enemies = mMainPresenter.findEnemiesAroundHero();

        if (enemies.size() < 2) {
            mEnemiesListLayout.setVisibility(View.GONE);
            mChooseEnemyTextView.setVisibility(View.GONE);
            if (enemies.size() == 1) {
                mMainPresenter.enemyAttacked(enemies.get(0));
                drawMap();
            }
        } else {
            mEnemiesListLayout.setVisibility(View.VISIBLE);
            mChooseEnemyTextView.setVisibility(View.VISIBLE);
            for (Enemy enemy : enemies) {
                createEnemyTextView(enemy);
            }
        }
    }

    private void createEnemyTextView(Enemy enemy) {
        TextView enemyNameTextView = new TextView(getContext());
        enemyNameTextView.setText(enemy.getName());
        enemyNameTextView.setId(View.generateViewId());
        enemyNameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        enemyNameTextView.setOnClickListener(v -> {
            mRemovableViewId = v.getId();
            mMainPresenter.enemyAttacked(enemy);
            drawMap();
        });
        mEnemiesListLayout.addView(enemyNameTextView);
    }

    @Override
    public void removeEnemyTextView() {
        mEnemiesListLayout.removeView(mEnemiesListLayout.findViewById(mRemovableViewId));
    }

    private void removeEnemyTextViews() {
        mEnemiesListLayout.removeAllViews();
    }
}