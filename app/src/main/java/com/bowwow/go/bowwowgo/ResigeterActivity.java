package com.bowwow.go.bowwowgo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017-07-10.
 */

public class ResigeterActivity extends Activity {


    // 내부 클래스 - 스레드 클래스 만들고 -> 스프링 연동
    // HttpURLConnection - post

    private final String TAG = "**ResigeterActivity**";

    private EditText inputId;
    private EditText inputPw;
    private EditText inputPwCheck;
    private TextView textView;

    private String id;
    private String pw;
    private String pwCheck;


    // private ConnectionClass con;

    private class MyConnection extends AsyncTask<String, String, String> {


        private final String NTAG = "**ConnectionClass**";
        private String totalLines = "";

        private String mId;
        private String mPw;

        // URL 문자열 보관 변수 선언
        private String mStrURL;

        // URL 클래스의 주소 보관 변수 선언
        private URL mURL;

        //


        public String getTotalLines() {
            return totalLines;
        }

        // HttpURLConnection 클래스의 주소 보관 변수 선언
        private HttpURLConnection mHttpURLConnection;

        private int timeOutValue = 2000; // 1초

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {


            try {

                Log.v(NTAG, "doInBackground");

                this.mStrURL = mStrURL + "?id=" + mId + "&pw=" + mPw; // ID & PW 파라미터 전달
                Log.v(NTAG, mStrURL);

                this.mURL = new URL(this.mStrURL);
                Log.v(NTAG, "this.mURL = new URL(this.mStrURL)");

                this.mHttpURLConnection = (HttpURLConnection)this.mURL.openConnection();
                Log.v(NTAG, "this.mURL.openConnection()");
                // POST 방식으로 전달
                this.mHttpURLConnection.setRequestMethod("POST");
                Log.v(NTAG, "setRequestMethod(POST)");
                // 1000 밀리세컨드 -> 1초
                this.mHttpURLConnection.setConnectTimeout(timeOutValue);
                // 데이터를 가져오는 시간
                this.mHttpURLConnection.setReadTimeout(timeOutValue);
                // 캐시에서 이름 값을 가져오지 않도록 하기
                this.mHttpURLConnection.setUseCaches(false);
                this.mHttpURLConnection.setDoInput(true);


                this.mHttpURLConnection.connect();
                Log.v(NTAG, "this.mHttpURLConnection.connect()");

                if(mHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    Log.v(NTAG, "서버로 데이터 전송");

                    BufferedReader br = null;
                    String line = "";

                    InputStream is = this.mHttpURLConnection.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is, "utf-8"));

                    totalLines = "";

                    while((line = br.readLine()) != null) {
                        totalLines = totalLines + line + "\n";
                    }

                    mHttpURLConnection.disconnect();

                    br.close();

                    Log.v(NTAG, "totalLines : " + totalLines);

                    if(totalLines.equals("false")) {
                        Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Log.v(NTAG, "서버로 데이터 전송 실패");

                }


            } catch(Exception e) {

                e.printStackTrace();


            }

            return "";
        }

        // 생성자 : 주소 문자열, 서버로 전송할 데이터를 다른 함수로 부터 받기

        public MyConnection(String strUrl, String id, String pw) {

            this.mStrURL = strUrl;
            this.mId = id;
            this.mPw = pw;
        }


    } // end of MyConnection


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.v(TAG, "onCreate()");

        this.inputId = (EditText)this.findViewById(R.id.input_id_et);
        this.inputPw = (EditText)this.findViewById(R.id.input_pw_et);
        this.inputPwCheck = (EditText)this.findViewById(R.id.check_pw_et);
        this.textView = (TextView)this.findViewById(R.id.testTv);


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

            // 두 값이 동일하면
            if(result = checkPw()) {


                // 3. id 값 db에 있는 값인지 확인

                // 3-1) 서버로 입력된 id, pw 전송
                String url = "http://192.168.0.161:8080/bowwow/idCheck";
                MyConnection con = new MyConnection(url, this.id, this.pw);
                con.execute();


            }


        }




        /*if(con.getTotalLines().equals("false")) {

            result = false;
            Toast.makeText(this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
        }*/


        /*con = new ConnectionClass(url,id,pw);
        con.execute();*/


        // 3-2 ) 중복된 아이디 값이 있으면 동일한 아이디가 있습니다 메세지 출력

        // 3-3) 아니면 저장


        // 4. 다 확인 됐으면 저장 후 로그인 페이지로 이동
        if(result == true) {

            Log.v(TAG, "id : " + this.id + ",pw : " + this.pw + ",pwCheck : " + this.pwCheck);
            // Log.v("my", "id:" + id + "pw:" + pw + "pwCheck:" + pwCheck);


            // 1) db에 회원정보 저장


            // 2) 로그인 페이지로 이동
            // startActivity(new Intent(this, LoginActivity.class));
        }


    } // end of onClick()s


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
