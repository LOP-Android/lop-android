package com.example.karen.lop_android;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hebi5 on 9/24/2015.
 */
public class Register {
    String urlTest="http://hmkcode.appspot.com/jsonservlet";
    public static final String URL = "http://192.168.1.43:8080/InformatronYX/informatron/title/signup";
    boolean registerSuccess = false;
    String jsonRegister = "a";

    public void registerUser(Context ctx, String username, String password, String email, String firstName, String lastName){

        InputStream inputStream = null;
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("uploadDate", email);


            jsonRegister = jsonObject.toString();

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlTest);

            StringEntity se = new StringEntity(jsonRegister);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            result = convertInputStreamToString(inputStream);
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();

            registerSuccess = true;
        }catch(Exception e){
            Toast.makeText(ctx, "error: "+e.toString(), Toast.LENGTH_SHORT).show(); };
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
