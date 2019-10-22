package com.cmpe277.studentmarketplace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String currentUserEmail="";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent loginIntent = getIntent();
        sp = getApplicationContext().getSharedPreferences(getString(R.string.app_pref),MODE_PRIVATE);
        currentUserEmail = sp.getString("email","");//dataBundle.getString("email");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return NavigationUI.onNavDestinationSelected(menuItem, navController);
    }

    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        return NavigationUI.navigateUp(navController,drawer) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int x = menuItem.getItemId();
        switch (x) {
            case R.id.home:
                NavigationView navView = findViewById(R.id.nav_view);
                onNavigationItemSelected(navView.getMenu().getItem(0));
                break;
            case R.id.logout:
                currentUserEmail = "";
                sp.edit().putBoolean("logged",false).apply();
                sp.edit().putString("email","");
                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.addpost:
                NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public String getCurrentUserEmail(){
        return currentUserEmail;
    }
}
