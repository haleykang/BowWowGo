package com.bowwow.go.bowwowgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private final String TAG = "**LoginActivity**";

    private EditText inputId;
    private EditText inputPw;
    // private Button loginBt;

    private String id;
    private String pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.v(TAG, "onCreate()");

        this.inputId = (EditText)this.findViewById(R.id.input_id_et);
        this.inputPw = (EditText)this.findViewById(R.id.input_pw_et);
    }

    public void onLogin(View v) {
        // 로그인 버튼 클릭
        Log.v(TAG, "onLogin()");
        boolean result = true;

        // 1. 입력된 id, pw 가져오기
        this.id = this.inputId.getText().toString().trim().toUpperCase();
        this.pw = this.inputPw.getText().toString().trim();

        // 2. null, 미입력 체크
        if(this.id == null || this.pw == null) {

            Log.v(TAG, "onLogin() NULL");
            result = false;
        }

        if(this.id.equals("")) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        } else if(this.id.equals("")) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            result = false;
        }

        // 3. 데이터베이스에 저장된 값인지 확인s

        // 4. 회원가입 시 저장한 비밀번호와 아이디가 일치하는지

        // 5. 일치하면 페이지 이동
        if(result) {

        }

    }

    public void onIntentClick(View v) {
        switch(v.getId()) {
            case R.id.register_tv:
                startActivity(new Intent(LoginActivity.this, ResigeterActivity.class));
                break;
            case R.id.login_bt:
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                break;
        }

    }
}
