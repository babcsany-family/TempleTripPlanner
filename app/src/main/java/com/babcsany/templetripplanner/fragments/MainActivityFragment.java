package com.babcsany.templetripplanner.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.babcsany.templetripplanner.interfaces.ITempleHostelPricingCalculator;
import com.babcsany.templetripplanner.adapters.PatronAdapter;
import com.babcsany.templetripplanner.R;
import com.babcsany.templetripplanner.activities.PatronActivity;
import com.babcsany.templetripplanner.enums.PatronKind;
import com.babcsany.templetripplanner.parcels.Patron;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import org.parceler.Parcels;

import static butterknife.ButterKnife.findById;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private static final String KEY_DATE_EDIT_TEXT_VIEW = "dateEditTextView";
    private static final String NAME_IN_EMAIL_SIGNATURE = "name_in_email_signature";
    private static final int EDIT_PATRON_REQUEST = 1;

    @BindView(R.id.editTextArrivalDate) EditText editTextArrivalDate;
    @BindView(R.id.textViewDash) TextView textViewDash;
    @BindView(R.id.editTextLeavingDate) EditText editTextLeavingDate;
    @BindView(R.id.costOfHostel) TextView costOfHostel;
    @BindView(R.id.patronsListView) RecyclerView patronsListView;
    @BindView(R.id.content_main) RelativeLayout contentMain;
    private RecyclerView.LayoutManager layoutManager;
    private Unbinder unbinder;

    private ITempleHostelPricingCalculator templeHostelPricingCalculator;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        costOfHostel.setText(getString(
                R.string.trip_nights_cost_prepay_text,
                getResources().getQuantityString(R.plurals.numberOfNights, 0, 0),
                0.0,
                0.0
        ));

        final List<Patron> patronList = new ArrayList<>();
        Patron patron = new Patron();
        patron.setKind(PatronKind.ADULT);
        patron.setName(getNameInEmailSignature());
        patronList.add(patron);
        layoutManager = new LinearLayoutManager(this.getActivity());
        patronsListView.setLayoutManager(layoutManager);
        patronsListView.setAdapter(new PatronAdapter(patronList, new PatronClicks()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PatronTouchSimpleCallback());
        itemTouchHelper.attachToRecyclerView(patronsListView);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private ITempleHostelPricingCalculator getTempleHostelPricingCalculator() {
        return templeHostelPricingCalculator;
    }

    public void setTempleHostelPricingCalculator(ITempleHostelPricingCalculator templeHostelPricingCalculator) {
        this.templeHostelPricingCalculator = templeHostelPricingCalculator;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (EDIT_PATRON_REQUEST == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                int patronPosition = data.getIntExtra("patronPosition", -1);
                ((PatronAdapter)patronsListView.getAdapter())
                        .set(patronPosition, Parcels.<Patron>unwrap(data.getParcelableExtra("patron")));
            }
        }
//        updateTripCost();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateTripCost() {
        updateTripCost(getTempleHostelPricingCalculator());
    }

    @OnClick(R.id.editTextArrivalDate)
    public void onArrivalDateClick() {
        Object dateTag = editTextArrivalDate.getTag();
        Calendar calendar = (dateTag instanceof Calendar) ? (Calendar) dateTag : Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivityFragment.this,
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
        EditText dateEditTextView = findById(this.getView(), view.getArguments().getInt(KEY_DATE_EDIT_TEXT_VIEW));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        String format = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        dateEditTextView.setTag(calendar);
        dateEditTextView.setText(format);
    }

    @OnClick(R.id.editTextLeavingDate)
    public void selectLeavingDate() {
        Object arrivalDateTag = editTextArrivalDate.getTag();
        Object leavingDateTag = editTextLeavingDate.getTag();
        Calendar calendar = (leavingDateTag instanceof Calendar && arrivalDateTag instanceof Calendar)
                && (((Calendar) leavingDateTag).compareTo((Calendar) arrivalDateTag) > 0)
                ? ((Calendar) leavingDateTag) : (arrivalDateTag instanceof Calendar) ? (Calendar) arrivalDateTag : Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MainActivityFragment.this,
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

    private long getTripDays() {
        return TimeUnit.DAYS.convert(
                ((Calendar) editTextLeavingDate.getTag()).getTimeInMillis()
                        - ((Calendar) editTextArrivalDate.getTag()).getTimeInMillis(),
                TimeUnit.MILLISECONDS);
    }

    private String getNameInEmailSignature() {
        String name = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString(NAME_IN_EMAIL_SIGNATURE, null);
        if (null == name || "".compareTo(name.trim()) == 0) {
            name = getNameFromUser();
        }
        return name;
    }

    private String getNameFromUser() {
        String name;
        new MaterialDialog.Builder(this.getActivity())
                .title(R.string.dialog_title_display_name)
                .content(R.string.dialog_content_display_name)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.input_firstname_lastname_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivityFragment.this.getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(NAME_IN_EMAIL_SIGNATURE, input.toString());
                        editor.apply();
                    }
                }).show();
        name = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).getString(NAME_IN_EMAIL_SIGNATURE, null);
        return name;
    }

    private void updateTripCost(ITempleHostelPricingCalculator selectedTemple) {
        long tripDays = getTripDays();
        double cost = calculateTripCost(
                selectedTemple,
                ((PatronAdapter) patronsListView.getAdapter()).getPatrons(),
                tripDays
        );

        costOfHostel.setText(getString(R.string.trip_nights_cost_prepay_text,
                getResources().getQuantityString(R.plurals.numberOfNights, Long.valueOf(tripDays).intValue(), tripDays),
                cost,
                cost * selectedTemple.getReservationFeePercentage()));
    }

    private double calculateTripCost(
            @NonNull ITempleHostelPricingCalculator selectedTemple,
            @NonNull List<Patron> patronList,
            long tripDays
    ) {
        double cost = 0.0d;
        for (Patron patron : patronList) {
            cost += selectedTemple.calculatePatronHostelFee(patron, tripDays);
        }

        return cost;
    }

    private class PatronClicks implements PatronAdapter.IPatronClicks {
        @Override
        public void onPatronClick(View patronView, int adapterPosition) {
            Intent intent = new Intent(MainActivityFragment.this.getActivity(), PatronActivity.class);
            final int patronAdapterPosition = patronsListView.getChildAdapterPosition(patronView);
            intent.putExtra("patronPosition", patronAdapterPosition);
            intent.putExtra("patron", Parcels.wrap(((PatronAdapter) patronsListView.getAdapter()).get(patronAdapterPosition)));
            startActivityForResult(intent, EDIT_PATRON_REQUEST);
        }
    }

    private class PatronTouchSimpleCallback extends ItemTouchHelper.SimpleCallback {
        PatronTouchSimpleCallback() {
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
