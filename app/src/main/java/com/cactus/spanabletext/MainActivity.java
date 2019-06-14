package com.cactus.spanabletext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = (String) this.getClass().getSimpleName();
    TextView textDatabse, textUser, textResults;
    String dbString[], userString[];

    //memo
    int[][] memo;

    List<Integer> indices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textDatabse = findViewById(R.id.tvFromDatabase);
        textUser = findViewById(R.id.tvFromUser);
        textResults = findViewById(R.id.results);

        dbString = textDatabse.getText().toString().split(" ");
        userString = textUser.getText().toString().split(" ");
        Log.d(TAG, Arrays.toString(dbString));
        Log.d(TAG, Arrays.toString(userString));


        int max = Math.max(dbString.length, userString.length);
        memo = new int[max][max];
        for (int[] arr : memo)
            Arrays.fill(arr, -1);

        lcsDp_idx(0, 0, 0);

        Spannable spannable = new SpannableStringBuilder(textDatabse.getText().toString());


        for (int i = 0; i < dbString.length; i++) {

            if (!indices.contains(i)) {
                SpannableString spannableString = new SpannableString(dbString[i]);
                spannableString.setSpan(
                        new ForegroundColorSpan(Color.RED),
                        0, dbString[i].length() ,
                        Spannable.SPAN_COMPOSING);
                textResults.append(spannableString);
            } else
                textResults.append(dbString[i]);
            textResults.append(" ");
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private int getEnd(int idx, String dbString) {
        int end = dbString.indexOf(' ', idx);
        if (end == -1) {
            return dbString.length() - 1;
        }

        return end;
    }

    private int lcsDp_idx(int d, int i1, int i2) {
        if (i1 >= dbString.length || i2 >= userString.length)
            return 0;


        if (memo[i1][i2] != -1)
            return memo[i1][i2];

        if (dbString[i1].equals(userString[i2])) {
            Log.d(TAG, i1 + "");
            indices.add(i1);
            return memo[i1][i2] = 1 + lcsDp_idx(d, i1 + 1, i2 + 1);
        } else {
            return memo[i1][i2] = Math.max(lcsDp_idx(d, i1 + 1, i2), lcsDp_idx(d, i1, i2 + 1));
        }

    }
}
