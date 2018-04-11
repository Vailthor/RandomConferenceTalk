package com.personal.nathan.randomconferencetalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/*
Changes to make:
instead of random button
make a 'generate' button that returns the chosen talk to main
show the chosen talk to the user
have a 'go to talk' button which will make the intent
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void random(View view) throws Exception {
        RetrievePage task = new RetrievePage(getApplicationContext());
        task.execute();
    }
}
