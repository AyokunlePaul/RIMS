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

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._authentication.Session;
import i.am.eipeks.rims._fragments.dashboard.Dashboard;
import i.am.eipeks.rims._fragments.Feedback;
import i.am.eipeks.rims._fragments.Home;
import i.am.eipeks.rims._fragments.Journey;
import i.am.eipeks.rims._fragments.Terms;

public class Main extends AppCompatActivity implements
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener ,
        Home.GoToTrip{

    private ActionBarDrawerToggle toggle;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Session session = new Session(this);

        String owner_s_info_array[] = session.getUserLoggedIn().split("_");

//        DateFormat.getDateInstance().t

//        Toast.makeText(this, Integer.toString(Calendar.getInstance().get(Calendar.MONTH)), Toast.LENGTH_SHORT).show();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        TextView owner_s_info = (TextView) navigationView.getHeaderView(0).findViewById(R.id.owner_s_name);
        TextView owner_s_id = (TextView) navigationView.getHeaderView(0).findViewById(R.id.owner_s_id);

        navigationView.setNavigationItemSelectedListener(this);

        owner_s_id.setText(owner_s_info_array[1].trim());
        owner_s_info.setText(owner_s_info_array[0].trim());


        setSupportActionBar(toolbar);

        navigationView.setItemIconTintList(null);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null){
            selectItem(R.id.home_menu_item);
        }

//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                FragmentManager manager = getSupportFragmentManager();
//                Fragment currentFragment = manager.findFragmentByTag("current_fragment");
//                if (currentFragment instanceof Home){
//                    selectItem(R.id.home_menu_item);
//                } else if (currentFragment instanceof Dashboard){
//                    selectItem(R.id.dashboard_menu_item);
//                } else if (currentFragment instanceof Journey){
//                    selectItem(R.id.add_a_trip_menu_item);
//                } else if (currentFragment instanceof Feedback){
//                    selectItem(R.id.feedback_menu_item);
//                } else if (currentFragment instanceof Terms){
//                    selectItem(R.id.terms_menu_item);
//                }
//            }
//        });

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

    public void selectItem(int itemId) {
        Fragment fragment;

        switch (itemId){
            case R.id.dashboard_menu_item:
                fragment = new Dashboard();
                getSupportActionBar().setTitle("Dashboard");
                break;
            case R.id.add_a_trip_menu_item:
                fragment = new Journey();
                getSupportActionBar().setTitle("Add Trip");
                break;
            case R.id.feedback_menu_item:
                fragment = new Feedback();
                getSupportActionBar().setTitle("Feedback");
                break;
            case R.id.terms_menu_item:
                fragment = new Terms();
                getSupportActionBar().setTitle("Terms and Conditions");
                break;
            case R.id.home_menu_item:
            default:
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
    public void goToTrip(int itemId) {
        selectItem(itemId);
    }
}
