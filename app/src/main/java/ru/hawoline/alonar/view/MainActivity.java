package ru.hawoline.alonar.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.Map;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.presenter.MainPresenter;
import ru.hawoline.alonar.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainView {
    private MainPresenter mMainPresenter;
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
        mMapImageViews = new ImageView[5][5];
        mMapImageViews[0][0] = findViewById(R.id.map_0_0);
        mMapImageViews[0][1] = findViewById(R.id.map_0_1);
        mMapImageViews[0][2] = findViewById(R.id.map_0_2);
        mMapImageViews[0][3] = findViewById(R.id.map_0_3);
        mMapImageViews[0][4] = findViewById(R.id.map_0_4);
        mMapImageViews[1][0] = findViewById(R.id.map_1_0);
        mMapImageViews[1][1] = findViewById(R.id.map_1_1);
        mMapImageViews[1][2] = findViewById(R.id.map_1_2);
        mMapImageViews[1][3] = findViewById(R.id.map_1_3);
        mMapImageViews[1][4] = findViewById(R.id.map_1_4);
        mMapImageViews[2][0] = findViewById(R.id.map_2_0);
        mMapImageViews[2][1] = findViewById(R.id.map_2_1);
        mMapImageViews[2][2] = findViewById(R.id.map_2_2);
        mMapImageViews[2][3] = findViewById(R.id.map_2_3);
        mMapImageViews[2][4] = findViewById(R.id.map_2_4);
        mMapImageViews[3][0] = findViewById(R.id.map_3_0);
        mMapImageViews[3][1] = findViewById(R.id.map_3_1);
        mMapImageViews[3][2] = findViewById(R.id.map_3_2);
        mMapImageViews[3][3] = findViewById(R.id.map_3_3);
        mMapImageViews[3][4] = findViewById(R.id.map_3_4);
        mMapImageViews[4][0] = findViewById(R.id.map_4_0);
        mMapImageViews[4][1] = findViewById(R.id.map_4_1);
        mMapImageViews[4][2] = findViewById(R.id.map_4_2);
        mMapImageViews[4][3] = findViewById(R.id.map_4_3);
        mMapImageViews[4][4] = findViewById(R.id.map_4_4);

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