package com.example.menuproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class square1 extends AppCompatActivity {
    private ImageView pizzaplanet;
    private Switch meatSwitch1, meatSwitch2, meatSwitch3, meatSwitch4, meatSwitch5;
    private Switch vegSwitch1, vegSwitch2, vegSwitch3, vegSwitch4, vegSwitch5;
    private Spinner spinner1;
    private Button cartButton;
    private FirebaseFirestore db;
    String[] nameList = new String[10];
    MainMenu MainMenu = new MainMenu();
    String documentName = MainMenu.returnDocumentName(1);
    //-----SideNav stuff
    private NavigationView nav;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square1);

        pizzaplanet = (ImageView) findViewById(R.id.pizzaplanet);
        pizzaplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        db = FirebaseFirestore.getInstance();

        spinner1 = (Spinner)findViewById(R.id.spinner1);

        meatSwitch1 = (Switch) findViewById(R.id.meatSwitch1);
        meatSwitch2 = (Switch) findViewById(R.id.meatSwitch2);
        meatSwitch3 = (Switch) findViewById(R.id.meatSwitch3);
        meatSwitch4 = (Switch) findViewById(R.id.meatSwitch4);
        meatSwitch5 = (Switch) findViewById(R.id.meatSwitch5);

        db.collection("PizzaPlanet").document("Inventory").collection("Food ").document(documentName).collection("Meats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int a = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nameList[a] = document.getId();
                                a++;
                            }
                        }
                        meatSwitch1.setText(nameList[0]);
                        meatSwitch2.setText(nameList[1]);
                        meatSwitch3.setText(nameList[2]);
                        meatSwitch4.setText(nameList[3]);
                        meatSwitch5.setText(nameList[4]);
                        Log.d("mytag", "xd + " + nameList[0]);
                    }
                });

        vegSwitch1 = (Switch) findViewById(R.id.vegSwitch1);
        vegSwitch2 = (Switch) findViewById(R.id.vegSwitch2);
        vegSwitch3 = (Switch) findViewById(R.id.vegSwitch3);
        vegSwitch4 = (Switch) findViewById(R.id.vegSwitch4);
        vegSwitch5 = (Switch) findViewById(R.id.vegSwitch5);
        db.collection("PizzaPlanet").document("Inventory").collection("Food ").document(documentName).collection("Veggies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int a = 5;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                nameList[a] = document.getId();
                                a++;
                            }
                        }
                        vegSwitch1.setText(nameList[5]);
                        vegSwitch2.setText(nameList[6]);
                        vegSwitch3.setText(nameList[7]);
                        vegSwitch4.setText(nameList[8]);
                        vegSwitch5.setText(nameList[9]);
                    }
                });

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        cartButton = (Button)findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartAddCheck(spinner1.getSelectedItem().toString());
            }
        });
        if (vegSwitch1.isChecked()) {
            vegSwitch1.getText();
        };

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
                if (item.getItemId() == R.id.item9) Toast.makeText(square1.this, "Clicked9", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void cartAddCheck(String spinner) {
        CartActivity cartActivity = new CartActivity();
        String pizzaToppings = "Custom Pizza, Cheese";
        if (spinner.equals("Choose a size")) {
            Toast.makeText(getApplicationContext(), "Please choose a size", Toast.LENGTH_SHORT).show();
        }
        else {
            if (meatSwitch1.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + meatSwitch1.getText();
            }
            if (meatSwitch2.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + meatSwitch2.getText();
            }
            if (meatSwitch3.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + meatSwitch3.getText();
            }
            if (meatSwitch4.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + meatSwitch4.getText();
            }
            if (meatSwitch5.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + meatSwitch5.getText();
            }
            if (vegSwitch1.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + vegSwitch1.getText();
            }
            if (vegSwitch2.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + vegSwitch2.getText();
            }
            if (vegSwitch3.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + vegSwitch3.getText();
            }
            if (vegSwitch4.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + vegSwitch4.getText();
            }
            if (vegSwitch5.isChecked()) {
                pizzaToppings = pizzaToppings + ", " + vegSwitch5.getText();
            }
            cartActivity.addToDatabase(pizzaToppings, 1, spinner);
            Toast.makeText(getApplicationContext(), "Items added to cart!", Toast.LENGTH_SHORT).show();
        }

    }

}