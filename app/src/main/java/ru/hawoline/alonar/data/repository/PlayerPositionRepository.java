package ru.hawoline.alonar.data.repository;

import ru.hawoline.alonar.data.entity.PlayerPositionEntity;
import ru.hawoline.alonar.domain.IdSavedCallback;

public class PlayerPositionRepository {
  private PlayerPositionDataStore playerPositionDataStore;
  public PlayerPositionRepository(PlayerPositionDataStore playerPositionDataStore) {
    this.playerPositionDataStore = playerPositionDataStore;
  }

  public void save(int id, PlayerPositionEntity playerPositionEntity) {
    playerPositionDataStore.save(id, playerPositionEntity);
  }
}
