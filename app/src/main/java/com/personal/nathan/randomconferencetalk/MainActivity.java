package com.personal.nathan.randomconferencetalk;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/*
Changes to make:
instead of random button
make a 'generate' button that returns the chosen talk to main
show the chosen talk to the user
have a 'go to talk' button which will make the intent
 */

public class MainActivity extends AppCompatActivity {

    String talk;
    TextView currentTalk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentTalk = findViewById(R.id.cTalk);
    }

    public void generate(View view) throws Exception {
        RetrievePage task = new RetrievePage(getApplicationContext());
        talk = task.execute().get();
        String title = talk.substring(47);
        String year = talk.substring(39, 43);
        String month = talk.substring(44, 46);
        if (month.equals("04"))
            month = "April";
        else
            month = "October";
        title = title.replace('-', ' ');

        char[] charTitle = title.toCharArray();

        charTitle[0] = Character.toUpperCase(charTitle[0]);
        for (int i = 1; i < title.length(); i++) {
            if (title.charAt(i) == ' ') {
                i++;
                char temp = Character.toUpperCase(title.charAt(i));
                charTitle[i] = temp;
            }
        }
        String userTalk = new String(charTitle);
        currentTalk.setText("Current Talk:\n" + month + " " + year + "\n"  + userTalk);
    }

    public void goToTalk(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(talk));
        startActivity(browserIntent);
    }
}
