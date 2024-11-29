package ru.hawoline.alonar.data.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ru.hawoline.alonar.data.entity.PlayerIdEntity;

public class PlayerIdFirebaseDatastore {
  private FirebaseDatabase database;
  public void save(int id) {
    database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("ids").child("id" + id);
    myRef.setValue(new PlayerIdEntity(id));
  }
}
