package ru.hawoline.alonar.presentation.presenter;

import ru.hawoline.alonar.presentation.view.InventoryView;

public interface InventoryPresenter extends Presenter<InventoryView> {
    InventoryAdapter getInventoryAdapter();
}
