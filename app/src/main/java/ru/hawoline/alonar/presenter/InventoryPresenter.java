package ru.hawoline.alonar.presenter;

import ru.hawoline.alonar.model.personage.Personage;
import ru.hawoline.alonar.view.InventoryView;

public interface InventoryPresenter extends Presenter<InventoryView> {
    InventoryAdapter getInventoryAdapter();
}
