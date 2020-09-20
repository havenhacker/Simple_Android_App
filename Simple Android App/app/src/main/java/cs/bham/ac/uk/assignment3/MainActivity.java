package cs.bham.ac.uk.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private String preference=null;

    private FragmentRefreshListener fragmentRefreshListener;

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        preference=sharedPref.getString("order_preference",null);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favorites)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//        System.out.println(getSupportFragmentManager().findFragmentById(R.id.home_fragment)==null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preference,menu);
        // restore radio box checked option using sharedPreferences
        if (preference!=null){
            if (preference.equals("Breakfast")){
                MenuItem item=menu.findItem(R.id.breakfast);
                item.setChecked(true);
            }else if (preference.equals("Lunch")){
                MenuItem item=menu.findItem(R.id.lunch);
                item.setChecked(true);
            }else if (preference.equals("Dinner")){
                MenuItem item=menu.findItem(R.id.dinner);
                item.setChecked(true);
            }else if (preference.equals("asc")){
                MenuItem item=menu.findItem(R.id.asc);
                item.setChecked(true);
            }else if (preference.equals("desc")){
                MenuItem item=menu.findItem(R.id.desc);
                item.setChecked(true);
            }
        }
        return true;
    }

    // implement option item in menu bar on the top
    // store preferences data using sharedPreferences
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.breakfast:
            sharedPref.edit().putString("order_preference","Breakfast").commit();
            if(getFragmentRefreshListener()!=null){
                // invoke callback method from Home Fragment
                getFragmentRefreshListener().onRefresh();
            }
            item.setChecked(true);
            return true;
        case R.id.lunch:
            sharedPref.edit().putString("order_preference","Lunch").commit();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
            item.setChecked(true);
            return true;
        case R.id.dinner:
            sharedPref.edit().putString("order_preference","Dinner").commit();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
            item.setChecked(true);
            return true;
        case R.id.asc:
            sharedPref.edit().putString("order_preference","asc").commit();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
            item.setChecked(true);
            return true;
        case R.id.desc:
            sharedPref.edit().putString("order_preference","desc").commit();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
            item.setChecked(true);
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
    }

    // callback interface for refreshing recycleView
    public interface FragmentRefreshListener{
        void onRefresh();
    }
}
