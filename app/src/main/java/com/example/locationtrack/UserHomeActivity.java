package com.example.locationtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {

    TextView txt;
    ImageView img;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);
        frameLayout=findViewById(R.id.frameLayout);
        navigationView=findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(UserHomeActivity.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        txt=findViewById(R.id.track);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserHomeActivity.this,DriverActivity.class);
                startActivity(intent);
            }
        });

        ImageView img2=findViewById(R.id.bus);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserHomeActivity.this,DriverActivity.class);
                startActivity(intent);
            }
        });

        img=findViewById(R.id.serviceReq);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserHomeActivity.this,ServiceRequestActivity.class);
                startActivity(intent);
            }
        });

        ImageView bus=findViewById(R.id.documentSub);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserHomeActivity.this,LandingPageActivity.class);
                startActivity(intent);
            }
        });


        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.nav_admin){
                    startActivity(new Intent(UserHomeActivity.this, MainActivity.class));
                    return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_admin){
                    startActivity(new Intent(UserHomeActivity.this, LoginAdminActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}


/*

<com.google.android.material.navigation.NavigationView
            android:id="@+id/navigationView"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            app:headerLayout="@layout/nav_header"
            android:background="@color/plpl"
            app:itemIconTint="@color/black"
            app:itemTextColor="@color/black"
            app:menu="@menu/nav_menu" />

*/