package com.cmpe277.studentmarketplace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    String currentUserEmail=""; //current logged in user
    String profileUser =""; // user whose profile needs to be viewed
    SharedPreferences sp;
    RecyclerView homePostsRecyclerView,postedPostRecyclerView,purchasedPostRecylerView;
    ArrayList<Post> allPostList,postedPostList,purchasedPostList;
    Database db;
    HomeActivity currentActivity;
    Post currentPost; // accesed by ViewPost fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getApplicationContext().getSharedPreferences(getString(R.string.app_pref),MODE_PRIVATE);
        currentUserEmail = sp.getString("email","");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        MenuItem m = (MenuItem)navView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //on click of my stuff menuitem the current user profile should display
                setViewProfileOf(getCurrentUserEmail());
                onNavigationItemSelected(menuItem);
                return true;
            }
        });
        db = new Database(this);
        //First load will have all the posts
        allPostList = db.GetAllPosts();
        postedPostList = db.GetPostedPosts();
        purchasedPostList = db.GetPurchasedPosts();
        currentActivity = this;
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
        menu.findItem(R.id.sold).setVisible(false);
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
            case R.id.sold: soldMenuClick();break;

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
    public void setViewProfileOf(String email){
        profileUser = email;
    }
    public String getViewProfileOf(){
        return profileUser;
    }

    public void setHomePostsRecyclerview(RecyclerView rv){
        homePostsRecyclerView = rv;
    }

    public void  displayHomePosts(){
        if(homePostsRecyclerView == null) return;
        RecyclerView.Adapter mAdapter;
        // specify an adapter
        if(allPostList == null || allPostList.size() == 0){
            View contextView = this.findViewById(R.id.nav_host_fragment);
            Snackbar.make(contextView, "No HomePosts to Display", Snackbar.LENGTH_LONG).show();
            return;
        }
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = homePostsRecyclerView.indexOfChild(v);
                currentPost = allPostList.get(pos);
                NavController navController = Navigation.findNavController(currentActivity,R.id.nav_host_fragment);
                navController.navigate(R.id.viewpost);
            }
        };
        mAdapter = new MyAdapter(allPostList, clickListener);
        homePostsRecyclerView.setAdapter(mAdapter);
    }

    public void setPostedRecyclerview(RecyclerView rv){
        postedPostRecyclerView = rv;
    }

    public void  displayPostedPosts(){
        if(postedPostRecyclerView == null) return;
        RecyclerView.Adapter mAdapter;
        // specify an adapter
        if(postedPostList == null || postedPostList.size() == 0){
            View contextView = this.findViewById(R.id.nav_host_fragment);
            Snackbar.make(contextView, "No Posted Posts to Display", Snackbar.LENGTH_LONG).show();
            return;
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = postedPostRecyclerView.indexOfChild(v);
                currentPost = postedPostList.get(pos);
                NavController navController = Navigation.findNavController(currentActivity,R.id.nav_host_fragment);
                navController.navigate(R.id.viewpost);
            }
        };

        mAdapter = new MyAdapter(postedPostList, clickListener);
        postedPostRecyclerView.setAdapter(mAdapter);
    }

    public void setPurchasedRecyclerview(RecyclerView rv){
        purchasedPostRecylerView = rv;
    }

    public void  displayPurchasedPosts(){
        if(purchasedPostRecylerView== null) return;
        RecyclerView.Adapter mAdapter;
        // specify an adapter
        if(purchasedPostList == null || purchasedPostList.size() == 0){
            View contextView = this.findViewById(R.id.nav_host_fragment);
            Snackbar.make(contextView, "No Purchased Posts to Display", Snackbar.LENGTH_LONG).show();
            return;
        }
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = purchasedPostRecylerView.indexOfChild(v);
                currentPost = purchasedPostList.get(pos);
                NavController navController = Navigation.findNavController(currentActivity,R.id.nav_host_fragment);
                navController.navigate(R.id.viewpost);
            }
        };
        mAdapter = new MyAdapter(purchasedPostList,clickListener);
        purchasedPostRecylerView.setAdapter(mAdapter);
    }

    public Post getCurrentPost(){
        return currentPost;
    }

    public void soldMenuClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity,R.style.AppTheme_Dark_Dialog);//getActivity () for fragment?
        builder.setMessage("Are you sure you want to mark "+getCurrentPost().getName()+" as sold?")
                .setTitle("Mark as sold");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Toast.makeText(currentActivity,"Item marked as sold",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
