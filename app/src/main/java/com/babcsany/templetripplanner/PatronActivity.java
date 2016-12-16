package com.babcsany.templetripplanner;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
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
        Intent intent = new Intent("com.babcsany.templetripplanner.PATRON_RESULT_ACTION");
        intent.putExtra("patronPosition", patronPosition);
        final Patron patron = Patron.builder()
                .name(((TextInputEditText) findViewById(R.id.name_textInput)).getText().toString())
                .kind(PatronKind.ADULT)
                .build();
        intent.putExtra("patron", patron);
        setResult(RESULT_OK, intent);
        finish();
    }
}
