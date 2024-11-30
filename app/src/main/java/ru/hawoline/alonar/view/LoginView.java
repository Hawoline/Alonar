package ru.hawoline.alonar.view;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import ru.hawoline.alonar.R;

public class LoginView implements BaseView {
  private LinearLayout layout;
  private Context context;
  private LayoutInflater layoutInflater;
  private MainView mainView;
  private EditText nicknameEditText;

  public LoginView(MainView mainView, LinearLayout layout, Context context, LayoutInflater layoutInflater) {
    this.mainView = mainView;
    this.layout = layout;
    this.context = context;
    this.layoutInflater = layoutInflater;
  }
  public void inflateView(LayoutInflater layoutInflater, FrameLayout containerLayout) {
    layout = layoutInflater.inflate(R.layout.layout_login, containerLayout).findViewById(R.id.login_layout);
    this.layoutInflater = layoutInflater;
    initViews();
    setOnClickListeners();
  }
  @Override public Context getContext() {
    return context;
  }

  @Override public void initViews() {
    nicknameEditText = layout.findViewById(R.id.login_nickname_edittext);
  }

  @Override public void setOnClickListeners() {
    nicknameEditText.setOnKeyListener(new View.OnKeyListener() {
      @Override public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
          String nickname = nicknameEditText.getText().toString();
          mainView.closeLoginView(nickname);
          return true;
        }
        return false;
      }
    });
  }
}
