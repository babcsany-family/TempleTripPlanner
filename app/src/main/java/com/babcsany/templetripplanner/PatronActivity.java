package com.babcsany.templetripplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PatronActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patron, menu);
        return true;
    }

    public void doPatronAction(MenuItem item) {
        final int patronPosition = getIntent().getIntExtra("patronPosition", -1);
        if (patronPosition < 0) {
            Toast.makeText(getBaseContext(), "ADD!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Editing TBD!", Toast.LENGTH_LONG).show();
        }
    }
}
