package i.am.eipeks.rims._activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import i.am.eipeks.rims.Constants;
import i.am.eipeks.rims.R;
import i.am.eipeks.rims._utils.SessionUtils;
import i.am.eipeks.rims._fragments.dashboard.Dashboard;
import i.am.eipeks.rims._fragments.Feedback;
import i.am.eipeks.rims._fragments.Home;
import i.am.eipeks.rims._fragments.Journey;

public class Main extends AppCompatActivity implements
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener ,
        Home.GoToTrip{

    private ActionBarDrawerToggle toggle;

    private DrawerLayout drawerLayout;

    private int currentItem;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.rims_icon));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        TextView owner_s_info = navigationView.getHeaderView(0).findViewById(R.id.owner_s_name);
        TextView owner_s_id = navigationView.getHeaderView(0).findViewById(R.id.owner_s_id);

        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setItemBackgroundResource(R.color.colorPrimaryDark);

        owner_s_id.setText(SessionUtils.getUserId());
        owner_s_info.setText(SessionUtils.getUserLoggedIn());


        setSupportActionBar(toolbar);

        navigationView.setItemIconTintList(null);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            currentItem = R.id.home_menu_item;
            selectItem(currentItem);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_new_trip:
                startActivity(new Intent(Main.this, Journey.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(Gravity.START);
        } else {
            if (!(getSupportFragmentManager().findFragmentByTag("current_fragment") instanceof Home)){
                selectItem(R.id.home_menu_item);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm Quit")
                        .setCancelable(false)
                        .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }})
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }}).create().show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItem(item.getItemId());
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    public void selectItem(int itemId) {
        Fragment fragment;

        switch (itemId){
            case R.id.dashboard_menu_item:
                currentItem = R.id.dashboard_menu_item;
                fragment = new Dashboard();
                getSupportActionBar().setTitle("Dashboard");
                break;
            case R.id.add_a_trip_menu_item:
                currentItem = R.id.add_a_trip_menu_item;
                fragment = new Journey();
                getSupportActionBar().setTitle("Add Trip");
                break;
            case R.id.feedback_menu_item:
                currentItem = R.id.feedback_menu_item;
                fragment = new Feedback();
                getSupportActionBar().setTitle("Feedback");
                break;
            case R.id.home_menu_item:
            default:
                currentItem = R.id.home_menu_item;
                fragment = new Home();
                getSupportActionBar().setTitle("Home");
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "current_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.LAST_ITEM_OUT_STATE, currentItem);
    }

    @Override
    public void goToTrip(int itemId) {
        selectItem(itemId);
    }

}
