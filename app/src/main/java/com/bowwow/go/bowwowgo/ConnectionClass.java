package com.bowwow.go.bowwowgo;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017-07-17.
 */

public class ConnectionClass extends AsyncTask<String, String, String> {

    private final String TAG = "**ConnectionClass**";

    private String mId;
    private String mPw;

    // URL 문자열 보관 변수 선언
    private String mStrURL;

    // URL 클래스의 주소 보관 변수 선언
    private URL mURL;

    //

    // HttpURLConnection 클래스의 주소 보관 변수 선언
    private HttpURLConnection mHttpURLConnection;

    private int timeOutValue = 1000; // 1초

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

            Log.v(TAG, "doInBackground");

            this.mStrURL = mStrURL + "?id=" + mId + "&pw=" + mPw; // ID & PW 파라미터 전달
            Log.v(TAG, mStrURL);

            this.mURL = new URL(this.mStrURL);
            Log.v(TAG, "this.mURL = new URL(this.mStrURL)");

            this.mHttpURLConnection = (HttpURLConnection)this.mURL.openConnection();
            Log.v(TAG, "this.mURL.openConnection()");
            // POST 방식으로 전달
            this.mHttpURLConnection.setRequestMethod("POST");
            Log.v(TAG, "setRequestMethod(POST)");
            // 1000 밀리세컨드 -> 1초
            this.mHttpURLConnection.setConnectTimeout(timeOutValue);
            // 데이터를 가져오는 시간
            this.mHttpURLConnection.setReadTimeout(timeOutValue);
            // 캐시에서 이름 값을 가져오지 않도록 하기
            this.mHttpURLConnection.setUseCaches(false);
            this.mHttpURLConnection.setDoInput(true);


            this.mHttpURLConnection.connect();
            Log.v(TAG, "this.mHttpURLConnection.connect()");

            if(mHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                Log.v(TAG, "서버로 데이터 전송");

                BufferedReader br = null;
                String line = "";

                InputStream is = this.mHttpURLConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "utf-8"));

                String totalLines = "";

                while((line = br.readLine()) != null) {
                    totalLines = totalLines + line + "\n";
                }

                mHttpURLConnection.disconnect();

                br.close();

                Log.v(TAG, "totalLines : " + totalLines);


            } else {
                Log.v(TAG, "서버로 데이터 전송 실패");

            }


        } catch(Exception e) {

            e.printStackTrace();


        }

        return "";
    }

    // 생성자 : 주소 문자열, 서버로 전송할 데이터를 다른 함수로 부터 받기

    public ConnectionClass(String strUrl, String id, String pw) {

        this.mStrURL = strUrl;
        this.mId = id;
        this.mPw = pw;
    }


}
