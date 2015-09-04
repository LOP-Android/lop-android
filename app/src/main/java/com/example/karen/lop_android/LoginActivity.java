package com.example.karen.lop_android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends Activity {
    EditText user;
    EditText pass;
    Button submit;
    TextView tv;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.CENTER_HORIZONTAL;

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        im = new ImageView(this);
        im.setImageDrawable(getResources().getDrawable(R.drawable.logo));
        tv = new TextView(this);
        tv.setText("Learning Object Player");
        tv.setGravity(Gravity.CENTER_HORIZONTAL);

        user = new EditText(this);
        user.setHint("Username");
        user.setGravity(Gravity.CENTER_HORIZONTAL);
        user.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0 ,0 ,0);

        pass = new EditText(this);
        pass.setHint("Password");
        pass.setGravity(Gravity.CENTER_HORIZONTAL);
        pass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.lock, 0 ,0 ,0);

        submit = new Button(this);
        submit.setLayoutParams(lparams);
        submit.setGravity(Gravity.CENTER_HORIZONTAL);
        submit.setText("Submit");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error: Incorrect username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ll.addView(im);
        ll.addView(user);
        ll.addView(pass);
        ll.addView(submit);

        this.setContentView(ll);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
