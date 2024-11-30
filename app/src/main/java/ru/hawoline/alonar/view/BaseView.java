package ru.hawoline.alonar.view;

import android.content.Context;

public interface BaseView {
    Context getContext();

    void initViews();

    void setOnClickListeners();
}
