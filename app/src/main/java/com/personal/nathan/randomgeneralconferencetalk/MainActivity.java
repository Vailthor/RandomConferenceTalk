package com.personal.nathan.randomgeneralconferencetalk;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        setContentView(com.personal.nathan.randomgeneralconferencetalk.R.layout.activity_main);
        currentTalk = findViewById(com.personal.nathan.randomgeneralconferencetalk.R.id.cTalk);
    }

    public void generate(View view)  {
        RetrievePage task = new RetrievePage();
        try {
            talk = task.execute().get();
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(this, "Problem generating", Toast.LENGTH_SHORT);
            toast.show();
        }
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
        Toast toast = Toast.makeText(this, "New Talk Found", Toast.LENGTH_SHORT);
        toast.show();
        String userTalk = new String(charTitle);
        currentTalk.setText("Current Talk:\n" + month + " " + year + "\n"  + userTalk);
    }

    public void goToTalk(View view) {
        if (talk == null||talk.isEmpty()) {
            RetrievePage task = new RetrievePage();
            try {
                talk = task.execute().get();
            }
            catch (Exception e) {
                Toast toast = Toast.makeText(this, "Problem going to talk", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(talk));
            startActivity(browserIntent);
    }
}
