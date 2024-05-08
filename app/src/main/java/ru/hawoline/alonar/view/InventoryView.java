package ru.hawoline.alonar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ru.hawoline.alonar.R;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.presenter.InventoryPresenter;
import ru.hawoline.alonar.presenter.InventoryPresenterImpl;

public class InventoryView implements BaseView {
    private LinearLayout layout;
    private Context context;
    private TextView toGameTextView;
    private TextView itemCountTextView;
    private RecyclerView itemsRecyclerView;

    private LayoutInflater layoutInflater;
    private FrameLayout rootLayout;

    private GameFieldView gameFieldView;

    private InventoryPresenter inventoryPresenter;

    public InventoryView(Context context, LayoutInflater layoutInflater, FrameLayout root, Personage personage) {
        this.context = context;
        rootLayout = root;
        inventoryPresenter = new InventoryPresenterImpl(personage);
        inventoryPresenter.attachView(this);
        inflateView(context, layoutInflater, root);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initViews() {
        toGameTextView = layout.findViewById(R.id.inventory_togame_textview);
        itemCountTextView = layout.findViewById(R.id.inventory_itemcount_textview);
        itemsRecyclerView = layout.findViewById(R.id.inventory_items_recyclerview);
        itemsRecyclerView.setAdapter(inventoryPresenter.getInventoryAdapter());
    }

    @Override
    public void setOnClickListeners() {
        toGameTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rootLayout.removeAllViews();
                gameFieldView.inflateLayout(getContext(), layoutInflater, rootLayout);
            }
        });
    }

    public void inflateView(Context context, LayoutInflater layoutInflater, FrameLayout containerLayout) {
        layout = layoutInflater.inflate(R.layout.layout_inventory, containerLayout).findViewById(R.id.inventory_layout);
        this.layoutInflater = layoutInflater;
        initViews();
        setOnClickListeners();
    }

    public void setGameFieldView(GameFieldView gameFieldView) {
        this.gameFieldView = gameFieldView;
    }

    public InventoryPresenter getInventoryPresenter() {
        return inventoryPresenter;
    }
}
