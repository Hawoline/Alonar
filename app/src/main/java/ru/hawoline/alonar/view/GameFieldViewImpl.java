package ru.hawoline.alonar.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.domain.model.gamelog.GameLog;
import ru.hawoline.alonar.domain.model.map.LandscapeMap;
import ru.hawoline.alonar.domain.model.personage.Location;
import ru.hawoline.alonar.domain.model.personage.Personage;
import ru.hawoline.alonar.presenter.GameFieldPresenter;
import ru.hawoline.alonar.presenter.GameFieldPresenterImpl;

public class GameFieldViewImpl implements GameFieldView {
    private LinearLayout layout;

    private LinearLayout slotsLayout;
    private GridLayout mapGridLayout;
    private ImageView[][] mapImageViews;
    private ImageView heroImageView;
    private ImageView[] slots;
    private TextView healthTextView;
    private TextView mpTextView;

    private LinearLayout parentGameLogLayout;

    private Context context;
    private GameFieldPresenter gameFieldPresenter = new GameFieldPresenterImpl();

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
        healthTextView = layout.findViewById(R.id.main_hp_textview);
        mpTextView = layout.findViewById(R.id.main_mp_textview);

        mapImageViews = new ImageView[VISIBLE_CELLS][VISIBLE_CELLS];
        for (int row = 0; row < VISIBLE_CELLS; row++) {
            for (int column = 0; column < VISIBLE_CELLS; column++) {
                createMapImageViews(row, column);
            }
        }
        heroImageView = layout.findViewById(R.id.hero_imageview);

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
                    render();
                });
            }
        }
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

    @Override
    public GameFieldPresenter getGameFieldPresenter() {
        return gameFieldPresenter;
    }
}
