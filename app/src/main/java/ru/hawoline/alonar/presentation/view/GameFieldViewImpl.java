package ru.hawoline.alonar.presentation.view;

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
import ru.hawoline.alonar.domain.model.gamelog.GameLog;
import ru.hawoline.alonar.domain.model.map.LandscapeMap;
import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.domain.model.personage.enemy.Enemy;
import ru.hawoline.alonar.presentation.presenter.GameFieldPresenterImpl;
import ru.hawoline.alonar.presentation.presenter.GameFieldPresenter;

import java.util.ArrayList;

public class GameFieldViewImpl implements GameFieldView {
    private LinearLayout layout;

    private LinearLayout slotsLayout;
    private LinearLayout enemiesListLayout;
    private GridLayout mapGridLayout;
    private TextView chooseEnemyTextView;
    private ImageView[][] mapImageViews;
    private ImageView heroImageView;
    private ImageView[] slots;
    private TextView healthTextView;
    private TextView mpTextView;
    private LinearLayout nearbyEnemiesLayout;

    private LinearLayout parentGameLogLayout;

    private Context context;
    private GameFieldPresenter gameFieldPresenter = new GameFieldPresenterImpl();

    private int removableViewId;

    private final int VISIBLE_CELLS = 5;

    public GameFieldViewImpl(Context context, LayoutInflater layoutInflater, FrameLayout root) {
        gameFieldPresenter.attachView(this);
        inflateLayout(context, layoutInflater, root);
    }

    public void inflateLayout(Context context, LayoutInflater layoutInflater, FrameLayout root) {
        this.context = context;
        layout = layoutInflater.inflate(R.layout.layout_gamefield, root).findViewById(R.id.gamefield_layout);
        initViews();
        setOnClickListeners();
        render();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initViews() {
        mapGridLayout = layout.findViewById(R.id.main_map_gridlayout);
        mapGridLayout.setRowCount(VISIBLE_CELLS);
        mapGridLayout.setColumnCount(VISIBLE_CELLS);
        chooseEnemyTextView = layout.findViewById(R.id.main_chooseenemy_textview);
        healthTextView = layout.findViewById(R.id.main_hp_textview);
        mpTextView = layout.findViewById(R.id.main_mp_textview);
        nearbyEnemiesLayout = layout.findViewById(R.id.gamefield_nearbyenemies_layout);

        mapImageViews = new ImageView[VISIBLE_CELLS][VISIBLE_CELLS];
        for (int row = 0; row < VISIBLE_CELLS; row++) {
            for (int column = 0; column < VISIBLE_CELLS; column++) {
                createMapImageViews(row, column);
            }
        }
        heroImageView = layout.findViewById(R.id.hero_imageview);

        enemiesListLayout = layout.findViewById(R.id.main_enemies_linearlayout);

        slotsLayout = layout.findViewById(R.id.main_slots_linearlayout);
        slots = new ImageView[10];
        Resources resources = context.getResources();
        for (int slot = 0; slot < 10; slot++) {
            slots[slot] = layout.findViewById(
                    resources.getIdentifier("main_slot_" + slot, "id", getContext().getPackageName()));
        }
    }

    @Override
    public void setOnClickListeners() {
        for (int i = 0; i < VISIBLE_CELLS; i++) {
            final int y = i - 2;
            for (int j = 0; j < VISIBLE_CELLS; j++) {
                final int x = j - 2;
                mapImageViews[i][j].setOnClickListener(v -> {
                    gameFieldPresenter.onPersonageMove(x, y);
                    showNearbyEnemies();
                    render();
                });
            }
        }
        slots[0].setOnClickListener(view -> showEnemiesList(0));
        slots[1].setOnClickListener(view -> showEnemiesList(1));
    }

    @Override
    public void render() {
        drawMap();
        showVitalityValues();
        if (parentGameLogLayout != null) {
            createLogTextViews();
        }
    }

    private void createMapImageViews(int row, int column) {
        mapImageViews[row][column] = new ImageView(getContext());
        int borderSizeOfMapGridLayout = getContext().getResources().getDimensionPixelSize(R.dimen.border_size);
        int firstTopPadding = 0;
        int firstLeftPadding = 0;
        int lastBottomPadding = 0;
        int lastRightPadding = 0;
        if (row == 0) {
            firstTopPadding = borderSizeOfMapGridLayout;
        } else if (row == VISIBLE_CELLS - 1) {
            lastBottomPadding = borderSizeOfMapGridLayout;
        }
        if (column == 0) {
            firstLeftPadding = borderSizeOfMapGridLayout;
        } else if (column == VISIBLE_CELLS - 1) {
            lastRightPadding = borderSizeOfMapGridLayout;
        }
        mapImageViews[row][column].setPadding(firstLeftPadding, firstTopPadding, lastRightPadding, lastBottomPadding);
        mapImageViews[row][column].setId(android.view.View.generateViewId());
        mapGridLayout.addView(mapImageViews[row][column], row * 5 + column);
    }

    @Override
    public void drawMap() {
        int[][] map = gameFieldPresenter.getGameMap();
        int mapSize = map.length;
        Location personageLocation = gameFieldPresenter.getPersonageLocation();

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
                mapImageViews[j][i].setImageResource(landscapeResourceId);
                j++;
            }
            i++;
        }

        drawEnemies(map, personageLocation);
        drawPersonage(personageLocation);
    }

    private void showVitalityValues() {
        Personage personage = gameFieldPresenter.getHero();
        healthTextView.setText(String.valueOf(personage.getHealth()));
        mpTextView.setText(String.valueOf(personage.getMp()));
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
            heroImageView.setImageResource(R.drawable.hero_back);
        } else if (direction == Location.DIRECTION_FORWARD) {
            heroImageView.setImageResource(R.drawable.hero_forward);
        } else if (direction == Location.DIRECTION_LEFT) {
            heroImageView.setImageResource(R.drawable.hero_left);
        } else if (direction == Location.DIRECTION_RIGHT) {
            heroImageView.setImageResource(R.drawable.hero_right);
        }
    }

    private void drawEnemies(int[][] map, Location personageLocation) {
        ArrayList<Enemy> enemiesAroundHero = gameFieldPresenter.findEnemiesAroundHero();

        Resources resources = getContext().getResources();
        for (Enemy enemy : enemiesAroundHero) {
            Drawable[] layers = new Drawable[4];
            Location enemyLocation = gameFieldPresenter.getEnemyLocation(enemy);
            layers[0] = ResourcesCompat.getDrawable(resources,
                    getLandscapeDrawableId(map[enemyLocation.getY()][enemyLocation.getX()]), null);
            layers[1] = ResourcesCompat.getDrawable(resources, R.drawable.point_1, null);
            layers[2] = ResourcesCompat.getDrawable(resources, R.drawable.point_2_quest, null);
            layers[3] = ResourcesCompat.getDrawable(resources, R.drawable.point_3_e, null);
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            mapImageViews[enemyLocation.getY() - personageLocation.getY() + 2]
                    [enemyLocation.getX() - personageLocation.getX() + 2].setImageDrawable(layerDrawable);
        }
    }

    @Override
    public void showEnemiesList(int slotIndex) {
        removeEnemyTextViews();

        ArrayList<Enemy> enemies = gameFieldPresenter.findEnemiesAroundHero(slotIndex);

        if (enemies.size() < 2) {
            enemiesListLayout.setVisibility(View.GONE);
            chooseEnemyTextView.setVisibility(View.GONE);
            if (enemies.size() == 1) {
                gameFieldPresenter.attackEnemy(enemies.get(0), slotIndex);
                render();
                showNearbyEnemies();
            }
        } else {
            enemiesListLayout.setVisibility(View.VISIBLE);
            chooseEnemyTextView.setVisibility(View.VISIBLE);
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
            removableViewId = v.getId();
            gameFieldPresenter.attackEnemy(enemy, slotIndex);
            render();
            showNearbyEnemies();
        });
        enemiesListLayout.addView(enemyNameTextView);
    }

    public void removeEnemyTextView() {
        enemiesListLayout.removeView(enemiesListLayout.findViewById(removableViewId));
    }

    private void removeEnemyTextViews() {
        enemiesListLayout.removeAllViews();
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
        this.parentGameLogLayout = parentGameLogLayout;
    }

    private void createLogTextViews() {
        parentGameLogLayout.removeAllViews();
        String[] logs = GameLog.getInstance().show();
        for (String logText : logs) {
            TextView logTextView = new TextView(getContext());
            logTextView.setText(logText);
            setTextColor(logTextView, R.color.text_color);
            logTextView.setId(View.generateViewId());
            parentGameLogLayout.addView(logTextView);
        }
        parentGameLogLayout.setVisibility(View.VISIBLE);
    }

    private void showNearbyEnemies() {
        nearbyEnemiesLayout.removeAllViews();
        ArrayList<Enemy> nearbyEnemies = gameFieldPresenter.getNearbyEnemies();
        if (nearbyEnemies.isEmpty()) {
            return;
        }
        showNearbyEnemiesTextView();
        for (Enemy enemy: nearbyEnemies) {
            showNearbyEnemy(enemy);
        }
    }
    private void showNearbyEnemiesTextView() {
        TextView textView = new TextView(getContext());
        textView.setText(getContext().getString(R.string.nearby_enemies));
        setTextColor(textView, R.color.text_color);
        textView.setId(View.generateViewId());
        nearbyEnemiesLayout.addView(textView);
    }
    private void showNearbyEnemy(Enemy enemy) {
        TextView textView = new TextView(getContext());
        textView.setText(enemy.getName());
        setTextColor(textView, R.color.text_color);
        textView.setId(View.generateViewId());
        nearbyEnemiesLayout.addView(textView);
    }

    @Override
    public GameFieldPresenter getGameFieldPresenter() {
        return gameFieldPresenter;
    }
}
