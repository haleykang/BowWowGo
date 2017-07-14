package com.bowwow.go.bowwowgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2017-07-10.
 */

public class ResigeterActivity extends Activity {

    private final String TAG = "**ResigeterActivity**";

    private EditText inputId;
    private EditText inputPw;
    private EditText inputPwCheck;

    private String id;
    private String pw;
    private String pwCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.v(TAG, "onCreate()");

        this.inputId = (EditText)this.findViewById(R.id.input_id_et);
        this.inputPw = (EditText)this.findViewById(R.id.input_pw_et);
        this.inputPwCheck = (EditText)this.findViewById(R.id.check_pw_et);

        // 자꾸 값이 안 넘어옴 - 시작하자마자 이 값을 가져와서 그런건가??? -> onClick으로 넘겨보자
        /*this.id = this.inputId.getText().toString().trim();
        this.pw = this.inputPw.getText().toString().trim();
        this.pwCheck = this.inputPwCheck.getText().toString().trim();*/

    } // end of onCreate

    public void onClick(View v) {
        // 회원 가입 버튼 클릭
        Log.v(TAG, "Click register buton");

        this.id = this.inputId.getText().toString().trim().toUpperCase();
        this.pw = this.inputPw.getText().toString().trim();
        this.pwCheck = this.inputPwCheck.getText().toString().trim();

        Log.v(TAG, "id : " + this.id + ",pw : " + this.pw + ",pwCheck : " + this.pwCheck);


       /* // 1. id, pw, pw 확인 값 입력 됐는지 확인
        // 값을 넣어야지 -_-
        if(id == null || pw == null || pwCheck == null) {

            Toast.makeText(this, "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();

            result = false;


        } else {

            // 2. pw, pw확인 값 동일한지 확인
            if(this.pw != this.pwCheck) {

                Toast.makeText(this, "입력된 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                result = false;

            } else {

                result = true;
            }

        }*/

        // 1. id, pw,pw 확인 값 입력 됐는지 확인
        boolean result = checkNull();

        // 2. pw, pw확인 값 동일한지 확인
        if(result) {

            result = checkPw();

        }


        // 3. id 값 db에 있는 값인지 확인


        // 4. 다 확인 됐으면 저장 후 로그인 페이지로 이동
        if(result == true) {

            Log.v(TAG, "id : " + this.id + ",pw : " + this.pw + ",pwCheck : " + this.pwCheck);
            // Log.v("my", "id:" + id + "pw:" + pw + "pwCheck:" + pwCheck);

            // 1) db에 회원정보 저장

            // 2) 로그인 페이지로 이동
            startActivity(new Intent(this, LoginActivity.class));
        }


    } // end of onClick()


    // null check 함수..?
    public boolean checkNull() {

        boolean result = true;

        if(this.id == null || this.pw == null || this.pwCheck == null) {

            Log.v(TAG, "checkNull() = Null");

            result = false;

        }
        if(this.id.equals("")) {
            shortToast("아이디를 입력해주세요.");
            result = false;
        } else if(this.pw.equals("")) {
            shortToast("비밀번호를 입력해주세요.");
            result = false;
        } else if(this.pwCheck.equals("")) {
            shortToast("비밀번호 확인을 입력해주세요.");
            result = false;
        }
/*
        if(this.id == null || ) {
            Log.v(TAG, "ID : NULL");
            result = false;

        } else if(this.pw == null) {

            Log.v(TAG, "PW : NULL");
            result = false;

        } else if(this.pwCheck == null) {

            Log.v(TAG, "PWCHECK : NULL");
            result = false;

        } else {

            if(this.id.equals("")) {
                shortToast("아이디를 입력해주세요.");
                result = false;

            } else if(this.pw.equals("")) {
                shortToast("비밀번호를 입력해주세요.");
                result = false;

            } else if(this.pwCheck.equals("")) {
                shortToast("비밀번호 확인을 입력해주세요.");
                result = false;

            } else {
                // 모두 입력된 경우
                result = true;
            }

        }*/


        return result;
    } // end of checkNull()


    public boolean checkPw() {
        boolean result = true;

        if(!this.pw.equals(this.pwCheck)) {
            shortToast("입력하신 비밀번호가 일치하지 않습니다.");
            result = false;
        }

        return result;
    }

    public void shortToast(String msg) {
        Toast.makeText(ResigeterActivity.this, msg, Toast.LENGTH_SHORT).show();
    }


}
