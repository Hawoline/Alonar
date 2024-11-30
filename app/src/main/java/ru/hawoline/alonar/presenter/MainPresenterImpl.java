package ru.hawoline.alonar.presenter;

import android.os.Bundle;
import java.util.Random;
import ru.hawoline.alonar.data.repository.PlayerIdFirebaseDatastore;
import ru.hawoline.alonar.data.repository.PlayerIdRepository;
import ru.hawoline.alonar.domain.IdSavedCallback;
import ru.hawoline.alonar.view.MainView;

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private int playerId;
    private PlayerIdRepository playerIdRepository;

    public MainPresenterImpl() {
        playerId = new Random().nextInt(100);
        playerIdRepository = new PlayerIdRepository(new PlayerIdFirebaseDatastore());
    }

    public void saveId() {
        playerIdRepository.save(new IdSavedCallback() {
            @Override public void onSaved() {

            }
        }, playerId);
    }

    @Override
    public void attachView(MainView view) {
        this.mainView = view;
    }

    @Override
    public void detachView() {
        this.mainView = null;
    }

    @Override
    public void saveInstance(Bundle state) {
    }

    @Override
    public void restoreInstance(Bundle state) {
    }
}
