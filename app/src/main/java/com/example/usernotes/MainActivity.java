package com.example.usernotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView Btnav;
    static Boolean f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref=getSharedPreferences("navSwitch",MODE_PRIVATE);
        f=pref.getBoolean("on",false);
        Btnav=findViewById(R.id.nav_view);

            Btnav.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
                int id=item.getItemId();
                if(pref.getBoolean("on",false)){
                    if(id==R.id.navigation_home){
                        loodFrag(new home(),false);
                    }else if(id==R.id.navigation_notifications){
                        loodFrag(new logged(),false);
                    }else{
                        loodFrag(new note(),false);
                    }
                }else{
                        if(id==R.id.navigation_notifications) {
                            loodFrag(new logged(), false);
                        }

                }
                return true;
            });


        Btnav.setSelectedItemId(R.id.navigation_notifications);



    }
    public void loodFrag(Fragment f, boolean b) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (b == true) {
            ft.add(R.id.container,f);
        }else {
            ft.replace(R.id.container,f);
            ft.commit();
        }
    }
}