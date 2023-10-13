package com.example.menuproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainMenu extends AppCompatActivity {
    private ImageView pizzaplanet;
    public TextView mainTextView1, mainTextView2, mainTextView3, mainTextView4, mainTextView5;
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    private FloatingActionButton floatingActionButton;
    private FirebaseFirestore db;
    public static String[] nameList = new String[6];

    private NavigationView nav;
    private Context context = this;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = FirebaseFirestore.getInstance();
        mainTextView1 = (TextView) findViewById(R.id.mainTextView1);
        mainTextView2 = (TextView) findViewById(R.id.mainTextView2);
        mainTextView3 = (TextView) findViewById(R.id.mainTextView3);
        mainTextView4 = (TextView) findViewById(R.id.mainTextView4);
        mainTextView5 = (TextView) findViewById(R.id.mainTextView5);

        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("NAV", "onNavigationItemSelected: ");
                if (item.getItemId() == R.id.item1) startActivity(sideNav.openMainMenu(context));
                if (item.getItemId() == R.id.item2) startActivity(sideNav.openSquare1(context));
                if (item.getItemId() == R.id.item3) startActivity(sideNav.openSquare2(context));
                if (item.getItemId() == R.id.item4) startActivity(sideNav.openSquare3(context));
                if (item.getItemId() == R.id.item5) startActivity(sideNav.openSquare4(context));
                if (item.getItemId() == R.id.item6) startActivity(sideNav.openSquare5(context));
                if (item.getItemId() == R.id.item7) startActivity(sideNav.openSquare6(context));
                if (item.getItemId() == R.id.item8) startActivity(sideNav.openCart(context));
                if (item.getItemId() == R.id.item9) Toast.makeText(context, "Clicked9", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        db.collection("PizzaPlanet/Inventory/Food ")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int a = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nameList[a] = document.getString("name");
                                //Build your own pizza has a special menu layout, always in first square
                                if (nameList[a].equals("Build Your Own Pizza")) {
                                    String temp = nameList[0];
                                    nameList[0] = document.getString("name");
                                    nameList[a] = temp;
                                }
                                a++;
                            }
                        }
                        mainTextView1.setText(nameList[0]);
                        mainTextView2.setText(nameList[1]);
                        mainTextView3.setText(nameList[2]);
                        mainTextView4.setText(nameList[3]);
                        mainTextView5.setText(nameList[4]);
                    }
                });

        pizzaplanet = (ImageView) findViewById(R.id.pizzaplanet);
        pizzaplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        cardView1 = (CardView) findViewById(R.id.cardView1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare1();
            }
        });

        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare2();
            }
        });

        cardView3 = (CardView) findViewById(R.id.cardView3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare3();
            }
        });

        cardView4 = (CardView) findViewById(R.id.cardView4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare4();
            }
        });

        cardView5 = (CardView) findViewById(R.id.cardView5);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare5();
            }
        });

        cardView6 = (CardView) findViewById(R.id.cardView6);
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSquare6();
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openCart(); }

        });
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void openSquare1() {
        Intent intent = new Intent(this, square1.class);
        startActivity(intent);
    }

    public void openSquare4() {
        Intent intent = new Intent(this, square4.class);
        startActivity(intent);
    }

    public void openSquare3() {
        Intent intent = new Intent(this, square3.class);
        startActivity(intent);
    }

    public void openSquare5() {
        Intent intent = new Intent(this, square5.class);
        startActivity(intent);
    }

    public void openSquare2() {
        Intent intent = new Intent(this, square2.class);
        startActivity(intent);
    }

    public void openSquare6() {
        Intent intent = new Intent(this, square6.class);
        startActivity(intent);
    }

    public void openCart() {
        Intent intent = new Intent(this, DisplayCart.class);
        startActivity(intent);
    }

    public String returnDocumentName(int temp) {
        String tempFinal = "";

        if (temp == 1) {
            tempFinal = "Build-Your-Own-Pizza";
        }
        else if (temp == 2) {
            tempFinal = MainMenu.nameList[1];
        }
        else if (temp == 3) {
            tempFinal = MainMenu.nameList[2];
        }
        else if (temp == 4) {
            tempFinal = MainMenu.nameList[3];
        }
        else if (temp == 5) {
            tempFinal = MainMenu.nameList[4];
        }

        return tempFinal;
    }
}