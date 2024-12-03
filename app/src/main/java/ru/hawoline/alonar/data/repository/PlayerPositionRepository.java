package ru.hawoline.alonar.data.repository;

import ru.hawoline.alonar.data.entity.PlayerPositionEntity;

public class PlayerPositionRepository {
  private PlayerPositionFirebaseDataStore playerPositionDataStore;
  public PlayerPositionRepository(PlayerPositionFirebaseDataStore playerPositionFirebaseDataStore) {
    this.playerPositionDataStore = playerPositionFirebaseDataStore;
  }

  public void save(int id, PlayerPositionEntity playerPositionEntity) {
    playerPositionDataStore.save(id, playerPositionEntity);
  }
}
