package com.omouravictor.feeling;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omouravictor.feeling.Fragments.HomeFragment;
import com.omouravictor.feeling.Fragments.NotificationFragment;
import com.omouravictor.feeling.Fragments.ProfileFragment;
import com.omouravictor.feeling.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    Fragment selectedfragment = null;
    static int notificationCount;
    TextView badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationCount = -1;

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        badge = findViewById(R.id.badge);
        Bundle intent = getIntent().getExtras();

        if (intent != null) {
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        nrNotifications();
    }

    private void nrNotifications() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(currentUserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (notificationCount >= 0) {
                    notificationCount++;
                    badge.setVisibility(View.VISIBLE);
                } else {
                    notificationCount++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedfragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedfragment = new SearchFragment();
                            break;
                        case R.id.nav_add:
                            selectedfragment = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        case R.id.nav_heart:
                            badge.setVisibility(View.GONE);
                            notificationCount = 0;
                            selectedfragment = new NotificationFragment();
                            break;
                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            selectedfragment = new ProfileFragment();
                            break;
                    }
                    if (selectedfragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedfragment).commit();
                    }

                    return true;
                }
            };
}
