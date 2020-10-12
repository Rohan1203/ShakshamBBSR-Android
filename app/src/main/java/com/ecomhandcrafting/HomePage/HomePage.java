package com.ecomhandcrafting.HomePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.ecomhandcrafting.HomePage.Homeadapter.FeaturedAdapter;
import com.ecomhandcrafting.HomePage.Homeadapter.FeaturedHelperClass;
import com.ecomhandcrafting.HomePage.Homeadapter.SuggestedAdapter;
import com.ecomhandcrafting.HomePage.Homeadapter.SuggestedHelperClass;
import com.ecomhandcrafting.MainActivity;
import com.ecomhandcrafting.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GoogleSignInClient mGoogleSignInClient;

    //Featured Recycler view
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;

    //Suggested Recycler View
    RecyclerView suggestedRecycler;

    //Grid Layout variables
    GridLayout mainGridLayout;
    //Variables
    ImageView menuicon,footwearicon;


    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomePage.this);
        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();

//            nameTV.setText("Name: "+personName);
//            emailTV.setText("Email: "+personEmail);
//            idTV.setText("ID: "+personId);
//            Glide.with(this).load(personPhoto).into(photoIV);
        }

        LayoutInflater inflater = LayoutInflater.from(HomePage.this); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.menu_header, null);

        //Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);

        //Hooks
        menuicon = findViewById(R.id.menu_icon);
        footwearicon = findViewById(R.id.footwear_icon);

        //Recycler view
        featuredRecycler = findViewById(R.id.featured_recycler);
        suggestedRecycler = findViewById(R.id.suggested_recycler);


        //Grid Layout hooks
        mainGridLayout = (GridLayout) findViewById(R.id.mainGridLayout);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        //Recycle view
        featuredRecycler();
        suggestedRecycler();

        //Grid layout
        setSingleEvent(mainGridLayout);

        footwearicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, FootWear.class);
                startActivity(i);
            }
        });

    }


    //Suggested Recycler view Functions
    private void suggestedRecycler() {
        suggestedRecycler.setHasFixedSize(true);
        suggestedRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        //Pass array to our design or to adapter
        ArrayList<SuggestedHelperClass> suggestedLocations = new ArrayList<>();
        suggestedLocations.add(new SuggestedHelperClass(R.drawable.sambalpuri, "Sambalpuri"));
        suggestedLocations.add(new SuggestedHelperClass(R.drawable.sambalpuri, "Sambalpuri"));

        adapter = new SuggestedAdapter(suggestedLocations);
        suggestedRecycler.setAdapter(adapter);

      //  GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    //Featured Recycler view Functions
    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));

        //Pass array to our design or to adapter
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sambalpuri, "Sambalpuri"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sambalpuri, "Sambalpuri"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

     //   GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }

    //Grid layout function
    private void setSingleEvent(GridLayout mainGridLayout) {
        for(int i=0;i<mainGridLayout.getChildCount();i++)
        {
            CardView cardview = (CardView)mainGridLayout.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0)
                    {
                        Intent intent = new Intent(HomePage.this, Product1.class);
                        startActivity(intent);
                    }
                    else   if(finalI==1)
                    {
                        Intent intent = new Intent(HomePage.this, Product1.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


    // Navigation Drawer Function
    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_order:
                startActivity(new Intent(getApplicationContext(), Myorders.class));
                break;
            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), HomePage.class));
                break;

            case R.id.nav_signout :
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(HomePage.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(HomePage.this, MainActivity.class));
                                finish();
                            }
                        });
        }
        return true;
    }

}
