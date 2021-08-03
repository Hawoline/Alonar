package ru.hawoline.alonar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.map.Map;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.presenter.MainPresenter;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainView {
    private MainPresenter mMainPresenter;
    private GridLayout mMapGridLayout;
    private ImageView[][] mMapImageViews;
    private ImageView mHeroImageView;
    private LinearLayout mEnemiesListLayout;

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
        int[][] enemiesMap = mMainPresenter.getEnemiesMap();
        int mapSize = map.length;
        Personage personage = mMainPresenter.getPersonage();

        int i = 0;
        for (int x = personage.getX() - 2; x < personage.getX() + 3; x++) {
            int j = 0;
            for (int y = personage.getY() - 2; y < personage.getY() + 3; y++) {
                if (x > -1 && y > -1 && x < mapSize && y < mapSize) {
                    Resources resources = getResources();
                    Drawable[] layers = new Drawable[4];

                    if (map[x][y] == Map.GRASS) {
                        layers[0] = ResourcesCompat.getDrawable(resources, R.drawable.green, null);
                    } else if (map[y][x] == Map.MOUNTAIN) {
                        layers[0] = ResourcesCompat.getDrawable(resources, R.drawable.mountains, null);
                    }
                    if (enemiesMap[y][x] == Map.ENEMY_RAT) {
                        layers[1] = ResourcesCompat.getDrawable(resources, R.drawable.point_1, null);
                        layers[2] = ResourcesCompat.getDrawable(resources, R.drawable.point_2_quest, null);
                        layers[3] = ResourcesCompat.getDrawable(resources, R.drawable.point_3_e, null);
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

        if (personage.getDirection() == Personage.DIRECTION_BACK) {
            mHeroImageView.setImageResource(R.drawable.hero_back);
        } else if (personage.getDirection() == Personage.DIRECTION_FORWARD) {
            mHeroImageView.setImageResource(R.drawable.hero_forward);
        } else if (personage.getDirection() == Personage.DIRECTION_LEFT) {
            mHeroImageView.setImageResource(R.drawable.hero_left);
        } else if (personage.getDirection() == Personage.DIRECTION_RIGHT) {
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

        mHeroImageView = findViewById(R.id.hero_imageview);

        mEnemiesListLayout = findViewById(R.id.main_enemies_linearlayout);

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
    }
}