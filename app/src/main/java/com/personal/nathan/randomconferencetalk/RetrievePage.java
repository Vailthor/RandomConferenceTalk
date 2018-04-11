package com.personal.nathan.randomconferencetalk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Will Retrieve a random general conference talk and open it
 */
class RetrievePage extends AsyncTask<Void, Void, Set<String>> {

    Context context;
    public RetrievePage(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * This will randomly choose and year and month to look up and
     * make a set of the given talks for that year and month.
     * @return The set of conference talks for the chosen year and month
     */
    @Override
    protected Set<String> doInBackground(Void... params) {
        Random rand = new Random();
        int monthNum = rand.nextInt(2);
        int yearNum = rand.nextInt(48) + 1971;
        String month;
        //Log.d("RETRIEVE", "Working");
        if (monthNum == 0 || yearNum == 2018)
            month = "04";
        else
            month = "10";
        String url = "https://www.lds.org/general-conference/" + yearNum + "/" + month;
        StringBuilder html = new StringBuilder();
        // Build and set timeout values for the request.
        try {

            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);
            }
            in.close();
        }
        catch (Exception e) {
            Log.d("RETRIEVE", "Problem " + e);
        }
        String page = html.toString();
        String search = "general-conference/" + yearNum + "/" + month + "/";
        int index = 0;
        int count = 0;
        Set<String> talks= new HashSet<>();
        boolean keepGoing = true;
        while (keepGoing && count < 60)
        {
            index = page.indexOf(search, index);
            int indexEnd = page.indexOf("?", index);
            if (index != -1) {
                String line = page.substring(index, indexEnd);
                keepGoing = talks.add("https://www.lds.org/" + line);
                //Log.i("RETRIEVE", "https://www.lds.org/" + line);
            }
            count++;
            index++;
        }
        //Log.i("RETRIEVE", "Count: " + talks.size());
        return talks;

    }

    /**
     * Will select a random talk from talks and open it in a browser.
     * @param talks The talks for the given year and month
     */
    @Override
    protected void onPostExecute(Set<String> talks) {
        Random rand = new Random();
        int talk = rand.nextInt(talks.size());
        String[] checkTalks = talks.toArray(new String[talks.size()]);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(checkTalks[talk]));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);

    }
}
