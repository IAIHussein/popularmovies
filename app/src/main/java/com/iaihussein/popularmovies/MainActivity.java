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
    static String sType;
    static String sIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (sIndex != null) {
            DetailsFragment mDetailsFragment = new DetailsFragment();
            Bundle mBundle = new Bundle();
            mBundle.putInt(Var.ARG_EXTRA, Integer.parseInt(sIndex));
            mDetailsFragment.setArguments(mBundle);
            if (findViewById(R.id.detail_fragment) == null)
                getSupportFragmentManager().beginTransaction().addToBackStack("detail").replace(R.id.fragment, mDetailsFragment).commit();
            else
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment, mDetailsFragment).commit();
        } else
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
            sIndex = null;
        }
    }
}
