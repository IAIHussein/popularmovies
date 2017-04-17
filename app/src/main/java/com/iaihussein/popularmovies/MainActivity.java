package com.iaihussein.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.iaihussein.popularmovies.api.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<Result> mResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, new MainActivityFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().executePendingTransactions();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, new MainActivityFragment()).commit();
        }
    }
}
