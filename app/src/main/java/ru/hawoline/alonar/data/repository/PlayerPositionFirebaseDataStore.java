package ru.hawoline.alonar.data.repository;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ru.hawoline.alonar.data.entity.PlayerPositionEntity;

public class PlayerPositionFirebaseDataStore {
  private FirebaseDatabase database;
  private final String MAIN_DIRECTORY = "positions";
  public PlayerPositionFirebaseDataStore(ChildEventListener childEventListener) {
    database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child(MAIN_DIRECTORY);
    databaseReference.addChildEventListener(childEventListener);
  }

  public void save(int id, PlayerPositionEntity playerPositionEntity) {
    DatabaseReference myRef = database.getReference().child(MAIN_DIRECTORY).child(String.valueOf(id));
    myRef.setValue(playerPositionEntity);
  }
}
