package ru.hawoline.alonar.data.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ru.hawoline.alonar.data.entity.PlayerPositionEntity;

public class PlayerPositionDataStore {
  private FirebaseDatabase database;
  private final String MAIN_DIRECTORY = "positions";

  public void save(int id, PlayerPositionEntity playerPositionEntity) {
    database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child(MAIN_DIRECTORY).child(String.valueOf(id));
    myRef.setValue(playerPositionEntity);
  }
}
