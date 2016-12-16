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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PatronActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final int patronPosition = intent.getIntExtra("patronPosition", -1);
        if (patronPosition >= 0) {
            final Patron patron = intent.getParcelableExtra("patron");
            TextInputEditText patronNameEditText = (TextInputEditText) findViewById(R.id.name_textInput);
            patronNameEditText.setText(patron.getName());
            RadioGroup patronKindRadioGroup = (RadioGroup) findViewById(R.id.radioGroup_patronKind);
            final PatronKind[] patronKinds = PatronKind.values();
            for (int i = 0; i < patronKinds.length; i++) {
                final RadioButton radioButton = (RadioButton) patronKindRadioGroup.getChildAt(i);
                if (patron.getKind().equals(patronKinds[i])) {
                    radioButton.setChecked(true);
                }
            }
        }
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
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_patronKind);
        final int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (-1 == checkedRadioButtonId) {
            Toast.makeText(this, R.string.toast_please_fill_the_form_properly, Toast.LENGTH_LONG).show();
        } else {
            final RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedRadioButtonId);
            final Patron patron = Patron.builder()
                    .name(((TextInputEditText) findViewById(R.id.name_textInput)).getText().toString())
                    .kind(PatronKind.values()[radioGroup.indexOfChild(radioButton)])
                    .build();
            intent.putExtra("patron", patron);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
