package com.example.locationtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHomeActivity extends AppCompatActivity {

    TextView txt;
    ImageView img;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;
    TextView text;
    LinearLayout routeTrack,trackBus;
    CardView serviceLayout;
    OnBackPressedListener onBackPressedListener;

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

        routeTrack=findViewById(R.id.routeTrack);
        trackBus=findViewById(R.id.trackBus);
        serviceLayout=findViewById(R.id.cardREQ);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Announcement");

        databaseReference.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String latestAnnouncement = snapshot.child("announce").getValue(String.class);
                    TextView announcementTextView = findViewById(R.id.Announcement);
                    announcementTextView.setText(latestAnnouncement);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        routeTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,LandingPageActivity.class);
                startActivity(intent);
            }
        });

        ImageView profile=findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        trackBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,BusdetailsActivity.class);
                startActivity(intent);
            }
        });
        serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this,ServiceRequestActivity.class);
                startActivity(intent);
            }
        });

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.nav_admin){
                    startActivity(new Intent(UserHomeActivity.this, MainActivity.class));
                    return true;
                } else if (item.getItemId()==R.id.nav_logout) {
                    logout();
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
    private void logout() {
        Intent intent = new Intent(UserHomeActivity.this, LoginUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        onBackPressedListener = null;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            super.onBackPressed();
        }
    }

    public interface OnBackPressedListener {
        void doBack();
    }
}
