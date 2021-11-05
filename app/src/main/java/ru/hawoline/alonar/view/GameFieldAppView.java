package ru.hawoline.alonar.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.core.content.res.ResourcesCompat;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.gamelog.GameLog;
import ru.hawoline.alonar.model.map.LandscapeMap;
import ru.hawoline.alonar.model.personage.Location;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presenter.GameFieldPresenterImpl;

import java.util.ArrayList;

public class GameFieldAppView implements GameFieldView {
    private LinearLayout mLayout;

    private LinearLayout mSlotsLayout;
    private LinearLayout mEnemiesListLayout;
    private GridLayout mMapGridLayout;
    private TextView mChooseEnemyTextView;
    private ImageView[][] mMapImageViews;
    private ImageView mHeroImageView;
    private ImageView[] mSlots;
    private TextView mHealthTextView;
    private TextView mMpTextView;

    private LinearLayout mParentGameLogLayout;

    private Context mContext;
    private GameFieldPresenter mGameFieldPresenter;

    private int mRemovableViewId;

    private final int VISIBLE_CELLS = 5;

    public GameFieldAppView(Context context, LayoutInflater layoutInflater, FrameLayout root) {
        mContext = context;
        mLayout = layoutInflater.inflate(R.layout.layout_gamefield, root).findViewById(R.id.gamefield_layout);
        mGameFieldPresenter = new GameFieldPresenterImpl();
        mGameFieldPresenter.attachView(this);
        findViews();
        setOnClickListeners();
        render();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void findViews() {
        mMapGridLayout = mLayout.findViewById(R.id.main_map_gridlayout);
        mMapGridLayout.setRowCount(VISIBLE_CELLS);
        mMapGridLayout.setColumnCount(VISIBLE_CELLS);
        mChooseEnemyTextView = mLayout.findViewById(R.id.main_chooseenemy_textview);
        mHealthTextView = mLayout.findViewById(R.id.main_hp_textview);
        mMpTextView = mLayout.findViewById(R.id.main_mp_textview);
        mMapImageViews = new ImageView[VISIBLE_CELLS][VISIBLE_CELLS];
        for (int row = 0; row < VISIBLE_CELLS; row++) {
            for (int column = 0; column < VISIBLE_CELLS; column++) {
                mMapImageViews[row][column] = new ImageView(getContext());
                mMapImageViews[row][column].setId(android.view.View.generateViewId());
                mMapGridLayout.addView(mMapImageViews[row][column], row * 5 + column);
            }
        }
        mHeroImageView = mLayout.findViewById(R.id.hero_imageview);

        mEnemiesListLayout = mLayout.findViewById(R.id.main_enemies_linearlayout);

        mSlotsLayout = mLayout.findViewById(R.id.main_slots_linearlayout);
        mSlots = new ImageView[10];
        Resources resources = mContext.getResources();
        for (int slot = 0; slot < 10; slot++) {
            mSlots[slot] = mLayout.findViewById(
                    resources.getIdentifier("main_slot_" + slot, "id", "ru.hawoline.alonar"));
        }
    }

    @Override
    public void setOnClickListeners() {
        for (int i = 0; i < VISIBLE_CELLS; i++) {
            final int y = i - 2;
            for (int j = 0; j < VISIBLE_CELLS; j++) {
                final int x = j - 2;
                mMapImageViews[i][j].setOnClickListener(v -> {
                    mGameFieldPresenter.onPersonageMove(x, y);
                    render();
                });
            }
        }
        mSlots[0].setOnClickListener(view -> showEnemiesList(0));
        mSlots[1].setOnClickListener(view -> showEnemiesList(1));
    }

    @Override
    public void render() {
        drawMap();
        showVitalityValues();
        if (mParentGameLogLayout != null) {
            createLogTextViews();
        }
    }

    public void drawMap() {
        int[][] map = mGameFieldPresenter.getGameMap();
        int mapSize = map.length;
        Location personageLocation = mGameFieldPresenter.getPersonageLocation();

        int xLocation = personageLocation.getX();
        int yLocation = personageLocation.getY();
        int i = 0;
        for (int x = xLocation - 2; x < xLocation + 3; x++) {
            int j = 0;
            for (int y = yLocation - 2; y < yLocation + 3; y++) {
                int landscapeResourceId = R.drawable.mountains;
                if (x > -1 && y > -1 && x < mapSize && y < mapSize) {
                    landscapeResourceId = getLandscapeDrawableId(map[y][x]);
                }
                mMapImageViews[j][i].setImageResource(landscapeResourceId);
                j++;
            }
            i++;
        }

        drawEnemies(map, personageLocation);
        drawPersonage(personageLocation);
    }

    private void showVitalityValues() {
        Personage personage = mGameFieldPresenter.getPersonage();
        mHealthTextView.setText(String.valueOf(personage.getHealth()));
        mMpTextView.setText(String.valueOf(personage.getMp()));
    }

    private int getLandscapeDrawableId(int landscapeType) {
        int drawableId = R.drawable.mountains;
        if (landscapeType == LandscapeMap.GRASS) {
            drawableId = R.drawable.green;
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

    private void drawEnemies(int[][] map, Location personageLocation) {
        ArrayList<Enemy> enemiesAroundHero = mGameFieldPresenter.findEnemiesAroundHero();

        Resources resources = getContext().getResources();
        for (Enemy enemy : enemiesAroundHero) {
            Drawable[] layers = new Drawable[4];
            Location enemyLocation = mGameFieldPresenter.getEnemyLocation(enemy);
            layers[0] = ResourcesCompat.getDrawable(resources,
                    getLandscapeDrawableId(map[enemyLocation.getY()][enemyLocation.getX()]), null);
            layers[1] = ResourcesCompat.getDrawable(resources, R.drawable.point_1, null);
            layers[2] = ResourcesCompat.getDrawable(resources, R.drawable.point_2_quest, null);
            layers[3] = ResourcesCompat.getDrawable(resources, R.drawable.point_3_e, null);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            mMapImageViews[enemyLocation.getY() - personageLocation.getY() + 2]
                    [enemyLocation.getX() - personageLocation.getX() + 2].setImageDrawable(layerDrawable);
        }
    }

    public void showEnemiesList(int slotIndex) {
        removeEnemyTextViews();

        ArrayList<Enemy> enemies = mGameFieldPresenter.findEnemiesAroundHero(slotIndex);

        if (enemies.size() < 2) {
            mEnemiesListLayout.setVisibility(View.GONE);
            mChooseEnemyTextView.setVisibility(View.GONE);
            if (enemies.size() == 1) {
                mGameFieldPresenter.enemyAttacked(enemies.get(0), slotIndex);
                render();
            }
        } else {
            mEnemiesListLayout.setVisibility(View.VISIBLE);
            mChooseEnemyTextView.setVisibility(View.VISIBLE);
            for (Enemy enemy : enemies) {
                createEnemyTextView(enemy, slotIndex);
            }
        }
    }

    private void createEnemyTextView(Enemy enemy, int slotIndex) {
        TextView enemyNameTextView = new TextView(getContext());
        enemyNameTextView.setText(enemy.getName());
        setTextColor(enemyNameTextView, R.color.text_color);
        enemyNameTextView.setId(View.generateViewId());
        enemyNameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        enemyNameTextView.setOnClickListener(v -> {
            mRemovableViewId = v.getId();
            mGameFieldPresenter.enemyAttacked(enemy, slotIndex);
            render();
        });
        mEnemiesListLayout.addView(enemyNameTextView);
    }

    public void removeEnemyTextView() {
        mEnemiesListLayout.removeView(mEnemiesListLayout.findViewById(mRemovableViewId));
    }

    private void removeEnemyTextViews() {
        mEnemiesListLayout.removeAllViews();
    }

    private void setTextColor(TextView textView, int color) {
        Resources resources = getContext().getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(resources.getColor(color, getContext().getTheme()));
        } else {
            textView.setTextColor(resources.getColor(color));
        }
    }

    @Override
    public void setParentGameLogLayout(LinearLayout parentGameLogLayout) {
        mParentGameLogLayout = parentGameLogLayout;
    }

    private void createLogTextViews() {
        mParentGameLogLayout.removeAllViews();
        String[] log = GameLog.getInstance().showLog();
        for (String s : log) {
            TextView logTextView = new TextView(getContext());
            logTextView.setText(s);
            setTextColor(logTextView, R.color.text_color);
            logTextView.setId(View.generateViewId());
            mParentGameLogLayout.addView(logTextView);
        }
        mParentGameLogLayout.setVisibility(View.VISIBLE);
    }
}
