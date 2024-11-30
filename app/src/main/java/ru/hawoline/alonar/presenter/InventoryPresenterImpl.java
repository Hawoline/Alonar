package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.view.InventoryView;

public class InventoryPresenterImpl implements InventoryPresenter {
    private InventoryView inventoryView;
    private InventoryAdapter inventoryAdapter;

    private Personage personage;

    public InventoryPresenterImpl(Personage personage) {
        this.personage = personage;
        inventoryAdapter = new InventoryAdapter(personage.getInventory());
    }

    @Override
    public void attachView(InventoryView view) {
        inventoryView = view;
    }

    @Override
    public void detachView() {
        inventoryView = null;
    }

    @Override
    public void saveInstance(Bundle state) {

    }

    @Override
    public void restoreInstance(Bundle state) {

    }

    @Override
    public InventoryAdapter getInventoryAdapter() {
        return inventoryAdapter;
    }
}
