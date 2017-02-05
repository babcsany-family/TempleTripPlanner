package com.babcsany.templetripplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static final String NAME_IN_EMAIL_SIGNATURE = "name_in_email_signature";
    public static final int ADD_PATRON_REQUEST = 2;

    @BindView(R.id.toolbar) Toolbar toolbar;
//    @BindView(R.id.fragment) MainActivityFragment fragment;
    @BindView(R.id.fab) FloatingActionButton fab;

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
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        patronsListView = (RecyclerView) findViewById(R.id.patronsListView);
//        fragment.setTempleHostelPricingCalculator(selectedTemple);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_PATRON_REQUEST:
                if (RESULT_OK == resultCode) {
                    ((PatronAdapter) patronsListView.getAdapter())
                            .add(Parcels.<Patron>unwrap(data.getParcelableExtra("patron")));
                }
                break;
        }
//        fragment.updateTripCost(selectedTemple);
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

    public void showSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void addNewPatron(MenuItem item) {
        Intent intent = new Intent(this, PatronActivity.class);
        startActivityForResult(intent, ADD_PATRON_REQUEST);
    }

    @OnClick(R.id.fab)
    public void onSendEmailToTempleHostelButtonClick(View view) {
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
