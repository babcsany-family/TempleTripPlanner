package com.babcsany.templetripplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_DATE_EDIT_TEXT_VIEW = "dateEditTextView";
    public static final String NAME_IN_EMAIL_SIGNATURE = "name_in_email_signature";
    public static final int EDIT_PATRON_REQUEST = 1;
    public static final int ADD_PATRON_REQUEST = 2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView patronsListView;
    private ITempleHostelPricingCalculator selectedTemple = new FreibergTemplePricingCalculator();

    private class FreibergTemplePricingCalculator implements ITempleHostelPricingCalculator {
        @Override
        public double calculatePatronHostelFee(Patron patron, long days) {
            return (patron.getKind().equals(PatronKind.CHILD) ? days * 1.5 : days < 3 ? days * 6 : days * 4);
        }

        @Override
        public double getReservationFeePercentage() {
            return 0.2d;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new SendEmailToTempleHostelButtonOnClickListener());

        String name = getNameInEmailSignature();

        patronsListView = (RecyclerView) findViewById(R.id.patronsListView);
        final List<Patron> patronList = new ArrayList<>();
        Patron patron = new Patron();
        patron.setKind(PatronKind.ADULT);
        patron.setName(name);
        patronList.add(patron);
        layoutManager = new LinearLayoutManager(this);
        patronsListView.setLayoutManager(layoutManager);
        patronsListView.setAdapter(new PatronAdapter(patronList, new PatronClicks()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PatronTouchSimpleCallback());
        itemTouchHelper.attachToRecyclerView(patronsListView);
        final TextView costText = (TextView) findViewById(R.id.costOfHostel);

        costText.setText(getString(
                R.string.trip_nights_cost_prepay_text,
                getResources().getQuantityString(R.plurals.numberOfNights, 0, 0),
                0.0,
                0.0
        ));
    }

    private String getNameInEmailSignature() {
        String name = PreferenceManager.getDefaultSharedPreferences(this).getString(NAME_IN_EMAIL_SIGNATURE, null);
        if (null == name || "".compareTo(name.trim()) == 0) {
            name = getNameFromUser();
        }
        return name;
    }

    private String getNameFromUser() {
        String name;
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_title_display_name)
                .content(R.string.dialog_content_display_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.input_firstname_lastname_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(NAME_IN_EMAIL_SIGNATURE, input.toString());
                        editor.commit();
                    }
                }).show();
        name = PreferenceManager.getDefaultSharedPreferences(this).getString(NAME_IN_EMAIL_SIGNATURE, null);
        return name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_PATRON_REQUEST:
                if (RESULT_OK == resultCode) {
                    ((PatronAdapter)patronsListView.getAdapter()).add((Patron) data.getParcelableExtra("patron"));
                }
                break;
            case EDIT_PATRON_REQUEST:
                if (RESULT_OK == resultCode) {
                    int patronPosition = data.getIntExtra("patronPosition", -1);
                    ((PatronAdapter)patronsListView.getAdapter()).set(patronPosition, (Patron) data.getParcelableExtra("patron"));
                }
                break;
        }
        calculateCost();
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
        calculateCost();
    }

    private void calculateCost() {
        Calendar arrival = (Calendar) findViewById(R.id.editTextArrivalDate).getTag();
        Calendar leave = (Calendar) findViewById(R.id.editTextLeavingDate).getTag();
        if (arrival != null && leave != null) {
            long tripDuration = leave.getTimeInMillis() - arrival.getTimeInMillis();
            final long tripDays = TimeUnit.DAYS.convert(tripDuration, TimeUnit.MILLISECONDS);
            PatronAdapter adapter = (PatronAdapter) patronsListView.getAdapter();
            double cost = 0.0d;
            final List<Patron> patrons = adapter.getPatrons();
            for (Patron patron : patrons) {
                cost += selectedTemple.calculatePatronHostelFee(patron, tripDays);
            }
            final TextView costTextView = (TextView) findViewById(R.id.costOfHostel);
            costTextView.setText(getString(R.string.trip_nights_cost_prepay_text,
                    getResources().getQuantityString(R.plurals.numberOfNights, new Long(tripDays).intValue(), tripDays),
                    cost,
                    cost * selectedTemple.getReservationFeePercentage()));
        }
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

    public void showSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void addNewPatron(MenuItem item) {
        Intent intent = new Intent(this, PatronActivity.class);
        startActivityForResult(intent, ADD_PATRON_REQUEST);
    }

    private class SendEmailToTempleHostelButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText arrivalDateEdit = (EditText) findViewById(R.id.editTextArrivalDate);
            EditText leavingDateEdit = (EditText) findViewById(R.id.editTextLeavingDate);
            if (arrivalDateEdit.getTag() instanceof Calendar && leavingDateEdit.getTag() instanceof Calendar) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String name = preferences.getString(NAME_IN_EMAIL_SIGNATURE, null);
                if (null != name) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.email_templeHostel), null));
                    String formattedArrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(
                            ((Calendar) arrivalDateEdit.getTag()).getTime());
                    String formattedLeavingDate = new SimpleDateFormat("yyyy-MM-dd").format(
                            ((Calendar) leavingDateEdit.getTag()).getTime());
                    final int patronsCount = patronsListView.getAdapter().getItemCount();
                    final String numberOfPersonsString = getResources().getQuantityString(R.plurals.numberOfPersons, patronsCount, patronsCount);
                    emailIntent.putExtra(
                            Intent.EXTRA_SUBJECT,
                            String.format(
                                    getString(R.string.subject_reservationEmail),
                                    formattedArrivalDate,
                                    formattedLeavingDate,
                                    numberOfPersonsString
                            )
                    );
                    List<Patron> patrons = ((PatronAdapter) patronsListView.getAdapter()).getPatrons();
                    final ListIterator<Patron> patronListIterator = patrons.listIterator();
                    String lines = "";
                    while (patronListIterator.hasNext()) {
                        Patron patron = patronListIterator.next();
                        lines = lines.concat(String.format(
                                '\n' + getString(R.string.patronLine),
                                patron.getName(),
                                getString(patron.getKind().getEmailKind())
                        ));
                    }
                    emailIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            String.format(
                                    getString(R.string.body_emailReservation),
                                    formattedArrivalDate,
                                    formattedLeavingDate,
                                    name,
                                    numberOfPersonsString,
                                    lines
                            )
                    );
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                } else {
                    showSettings(null);
                }
            }
        }
    }

    private class PatronClicks implements PatronAdapter.PatronViewHolder.IPatronClicks {
        @Override
        public void onPatronClick(View patronView, int adapterPosition) {
            Intent intent = new Intent(MainActivity.this, PatronActivity.class);
            final int patronAdapterPosition = patronsListView.getChildAdapterPosition(patronView);
            intent.putExtra("patronPosition", patronAdapterPosition);
            intent.putExtra("patron", Parcels.wrap(((PatronAdapter) patronsListView.getAdapter()).get(patronAdapterPosition)));
            startActivityForResult(intent, EDIT_PATRON_REQUEST);
        }
    }

    private class PatronTouchSimpleCallback extends ItemTouchHelper.SimpleCallback {
        public PatronTouchSimpleCallback() {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int swipedPosition = viewHolder.getAdapterPosition();
            PatronAdapter adapter = (PatronAdapter) patronsListView.getAdapter();
            adapter.remove(swipedPosition);
        }
    }
}
