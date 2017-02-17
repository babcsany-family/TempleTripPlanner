package com.babcsany.templetripplanner.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.babcsany.templetripplanner.R;
import com.babcsany.templetripplanner.enums.PatronKind;
import com.babcsany.templetripplanner.fragments.MainActivityFragment;
import com.babcsany.templetripplanner.fragments.TempleTripFragment;
import com.babcsany.templetripplanner.fragments.dummy.DummyContent;
import com.babcsany.templetripplanner.interfaces.ITempleHostelPricingCalculator;
import com.babcsany.templetripplanner.parcels.Patron;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TempleTripFragment.OnListFragmentInteractionListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    //    @BindView(R.id.fragment) MainActivityFragment fragment;
//    @BindView(R.id.fab_add) FloatingActionButton fabAdd;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    Fragment fragment;

//    private RecyclerView patronsListView;
    private ITempleHostelPricingCalculator selectedTemple = new FreibergTemplePricingCalculator();

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        System.out.println("Called!");
    }

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

        if (savedInstanceState != null) {
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new MainActivityFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        } else if (fragment != null && fragment.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSettings(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id;
        switch (id = item.getItemId()) {
            case R.id.my_trips:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new TempleTripFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.find_seats:
            case R.id.nav_manage:
            case R.id.nav_share:
            case R.id.nav_send:
                Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
