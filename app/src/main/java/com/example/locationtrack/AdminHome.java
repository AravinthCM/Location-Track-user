package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class AdminHome extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    TextView text,text2;

    LinearLayout routeTrack,trackBus;
    CardView serviceLayout;
    CardView publish;
    CardView cardREQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        cardREQ=findViewById(R.id.cardREQ);

        publish=findViewById(R.id.publish);

        // Inside onCreate method
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Announcement");
        databaseReference.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String latestAnnouncement = snapshot.child("announce").getValue(String.class);

                    // Assuming you have a TextView with id "Announcement" in your layout
                    TextView announcementTextView = findViewById(R.id.Announcement);
                    announcementTextView.setText(latestAnnouncement);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, PublishAnnouncement.class);
                startActivity(intent);
            }
        });

        routeTrack=findViewById(R.id.routeTrack);
        trackBus=findViewById(R.id.trackBus);
        routeTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,LandingPageActivity.class);
                startActivity(intent);
            }
        });

        trackBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        cardREQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this,ViewServiceReqActivity.class);
                startActivity(intent);
            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic("News")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });


        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);
        frameLayout=findViewById(R.id.frameLayout);
        navigationView=findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(AdminHome.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.nav_admin){
                    startActivity(new Intent(AdminHome.this, MainActivity.class));
                    return true;
                }
                return false;
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_admin){
                    startActivity(new Intent(AdminHome.this, UserHomeActivity.class));
                    return true;
                }
                return false;
            }
        });

    }
}