package ru.hawoline.alonar.data.repository;

import ru.hawoline.alonar.domain.IdSavedCallback;

public class PlayerIdRepository {
  private PlayerIdFirebaseDatastore playerIdFirebaseDatastore;

  public PlayerIdRepository(PlayerIdFirebaseDatastore playerIdFirebaseDatastore) {
    this.playerIdFirebaseDatastore = playerIdFirebaseDatastore;
  }

  public void save(IdSavedCallback idSavedCallback, int id) {
    playerIdFirebaseDatastore.save(id);
  }
}
