package ru.hawoline.alonar.view;

import android.content.Context;

public interface AppView {
    Context getContext();

    void findViews();

    void setOnClickListeners();
}
