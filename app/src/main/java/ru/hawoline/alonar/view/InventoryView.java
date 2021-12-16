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
    private LinearLayout mLayout;
    private Context mContext;
    private TextView mToGameTextView;
    private TextView mItemCountTextView;
    private RecyclerView mItemsRecyclerView;

    private LayoutInflater mLayoutInflater;
    private FrameLayout mRootLayout;

    private GameFieldView mGameFieldView;

    private InventoryPresenter mInventoryPresenter;

    public InventoryView(Context context, LayoutInflater layoutInflater, FrameLayout root, Personage personage) {
        mContext = context;
        mRootLayout = root;
        mInventoryPresenter = new InventoryPresenterImpl(personage);
        mInventoryPresenter.attachView(this);
        inflateView(context, layoutInflater, root);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void initViews() {
        mToGameTextView = mLayout.findViewById(R.id.inventory_togame_textview);
        mItemCountTextView = mLayout.findViewById(R.id.inventory_itemcount_textview);
        mItemsRecyclerView = mLayout.findViewById(R.id.inventory_items_recyclerview);
        mItemsRecyclerView.setAdapter(mInventoryPresenter.getInventoryAdapter());
    }

    @Override
    public void setOnClickListeners() {
        mToGameTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootLayout.removeAllViews();
                mGameFieldView.inflateLayout(getContext(), mLayoutInflater, mRootLayout);
            }
        });
    }

    public void inflateView(Context context, LayoutInflater layoutInflater, FrameLayout containerLayout) {
        mLayout = layoutInflater.inflate(R.layout.layout_inventory, containerLayout).findViewById(R.id.inventory_layout);
        mLayoutInflater = layoutInflater;
        initViews();
        setOnClickListeners();
    }

    public void setGameFieldView(GameFieldView gameFieldView) {
        mGameFieldView = gameFieldView;
    }

    public InventoryPresenter getInventoryPresenter() {
        return mInventoryPresenter;
    }
}
