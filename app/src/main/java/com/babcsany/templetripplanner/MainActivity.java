package com.babcsany.templetripplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_DATE_EDIT_TEXT_VIEW = "dateEditTextView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectArrivalDate(View view) {
        EditText arrivalDateView = (EditText) findViewById(R.id.editTextArrivalDate);
        Object dateTag = arrivalDateView.getTag();
        Calendar calendar = (dateTag instanceof Calendar) ? (Calendar) dateTag : Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setTitle(getString(R.string.title_selectArrivalDate));
        dpd.autoDismiss(true);
        Bundle args = new Bundle();
        args.putInt(KEY_DATE_EDIT_TEXT_VIEW, R.id.editTextArrivalDate);
        dpd.setArguments(args);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        EditText dateEditTextView = (EditText) findViewById(view.getArguments().getInt(KEY_DATE_EDIT_TEXT_VIEW));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        String format = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        dateEditTextView.setTag(calendar);
        dateEditTextView.setText(format);
    }

    public void selectLeavingDate(View view) {
        EditText arrivalDateView = (EditText) findViewById(R.id.editTextArrivalDate);
        EditText leavingDateView = (EditText) findViewById(R.id.editTextLeavingDate);
        Object arrivalDateTag = arrivalDateView.getTag();
        Object leavingDateTag = leavingDateView.getTag();
        Calendar calendar = (leavingDateTag instanceof Calendar && arrivalDateTag instanceof Calendar)
                && (((Calendar) leavingDateTag).compareTo((Calendar) arrivalDateTag) > 0)
                ? ((Calendar) leavingDateTag) : (arrivalDateTag instanceof Calendar) ? (Calendar) arrivalDateTag : Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivity.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setTitle(getString(R.string.title_selectLeavingDate));
        dpd.autoDismiss(true);
        Bundle args = new Bundle();
        args.putInt(KEY_DATE_EDIT_TEXT_VIEW, R.id.editTextLeavingDate);
        dpd.setArguments(args);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
}
