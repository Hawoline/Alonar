package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.view.InventoryView;

public class InventoryPresenterImpl implements InventoryPresenter {
    private InventoryView mInventoryView;
    private InventoryAdapter mInventoryAdapter;

    private Personage mPersonage;

    public InventoryPresenterImpl(Personage personage) {
        mPersonage = personage;
        mInventoryAdapter = new InventoryAdapter(personage.getInventory());
    }

    @Override
    public void attachView(InventoryView view) {
        mInventoryView = view;
    }

    @Override
    public void detachView() {
        mInventoryView = null;
    }

    @Override
    public void saveInstance(Bundle state) {

    }

    @Override
    public void restoreInstance(Bundle state) {

    }

    @Override
    public InventoryAdapter getInventoryAdapter() {
        return mInventoryAdapter;
    }
}
