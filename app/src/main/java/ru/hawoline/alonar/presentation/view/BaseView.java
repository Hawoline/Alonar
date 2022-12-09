package ru.hawoline.alonar.presentation.view;

import android.content.Context;

public interface BaseView {
    Context getContext();

    void initViews();

    void setOnClickListeners();
}
