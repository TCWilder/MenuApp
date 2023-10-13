package com.example.menuproject;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class square3 extends AppCompatActivity {
    private ImageView pizzaplanet;
    private TextView textView1, textView2, textView3, textView4, pageHeader;
    private Button cartButton;
    private LinearLayout linearGroup1, linearGroup2, linearGroup3, linearGroup4;
    private TableLayout tableGroup1, tableGroup2, tableGroup3, tableGroup4;
    private Spinner spinner1, spinner2, spinner3, spinner4;
    private FirebaseFirestore db;
    public static String[] nameList = new String[4];
    MainMenu MainMenu = new MainMenu();
    String documentName = MainMenu.returnDocumentName(3);

    private NavigationView nav;
    private Context context = this;


    private int counterInt1 = 0;
    Button plusButton1;
    Button minusButton1;
    TextView counterText1;

    private int counterInt2 = 0;
    Button plusButton2;
    Button minusButton2;
    TextView counterText2;

    private int counterInt3 = 0;
    Button plusButton3;
    Button minusButton3;
    TextView counterText3;

    private int counterInt4 = 0;
    Button plusButton4;
    Button minusButton4;
    TextView counterText4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square3);

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
                if (item.getItemId() == R.id.item9) Toast.makeText(square3.this, "Clicked9", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        pizzaplanet = (ImageView) findViewById(R.id.pizzaplanet);
        pizzaplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        db = FirebaseFirestore.getInstance();
        pageHeader = (TextView) findViewById(R.id.pageHeader);
        pageHeader.setText(documentName);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        db.collection("PizzaPlanet").document("Inventory").collection("Food ").document(documentName).collection("Type")
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
                        textView1.setText(nameList[0]);
                        textView2.setText(nameList[1]);
                        textView3.setText(nameList[2]);
                        textView4.setText(nameList[3]);
                    }
                });

        boolean availableList[] = { false, false, false, false };
        tableGroup1 = (TableLayout) findViewById(R.id.tableGroup1);
        tableGroup2 = (TableLayout) findViewById(R.id.tableGroup2);
        tableGroup3 = (TableLayout) findViewById(R.id.tableGroup3);
        tableGroup4 = (TableLayout) findViewById(R.id.tableGroup4);
        linearGroup1 = (LinearLayout) findViewById(R.id.linearGroup1);
        linearGroup2 = (LinearLayout) findViewById(R.id.linearGroup2);
        linearGroup3 = (LinearLayout) findViewById(R.id.linearGroup3);
        linearGroup4 = (LinearLayout) findViewById(R.id.linearGroup4);

        db.collection("PizzaPlanet").document("Inventory").collection("Food ").document(documentName).collection("Type")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int a = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                availableList[a] = document.getBoolean("Availability");
                                a++;
                            }
                        }
                        if (!availableList[0]) {
                            tableGroup1.setVisibility(View.GONE);
                            linearGroup1.setVisibility((View.GONE));
                            spinner1.setVisibility(View.GONE);
                        }
                        if (!availableList[1]) {
                            tableGroup2.setVisibility(View.GONE);
                            linearGroup2.setVisibility((View.GONE));
                            spinner2.setVisibility(View.GONE);
                        }
                        if (!availableList[2]) {
                            tableGroup3.setVisibility(View.GONE);
                            linearGroup3.setVisibility((View.GONE));
                            spinner3.setVisibility(View.GONE);
                        }
                        if (!availableList[3]) {
                            tableGroup4.setVisibility(View.GONE);
                            linearGroup4.setVisibility((View.GONE));
                            spinner4.setVisibility(View.GONE);
                        }
                    }
                });

        plusButton1 = (Button)findViewById(R.id.plusButton1);
        minusButton1 = (Button)findViewById(R.id.minusButton1);
        counterText1 = (TextView)findViewById(R.id.counterText1);
        plusButton1.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                counterInt1++;
                counterText1.setText(Integer.toString(counterInt1));
            }
        });
        minusButton1.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (counterInt1 > 0)
                {
                    counterInt1--;
                }
                counterText1.setText(Integer.toString(counterInt1));
            }
        });

        plusButton2 = (Button)findViewById(R.id.plusButton2);
        minusButton2 = (Button)findViewById(R.id.minusButton2);
        counterText2 = (TextView)findViewById(R.id.counterText2);
        plusButton2.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                counterInt2++;
                counterText2.setText(Integer.toString(counterInt2));
            }
        });
        minusButton2.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (counterInt2 > 0)
                {
                    counterInt2--;
                }
                counterText2.setText(Integer.toString(counterInt2));
            }
        });

        plusButton3 = (Button)findViewById(R.id.plusButton3);
        minusButton3 = (Button)findViewById(R.id.minusButton3);
        counterText3 = (TextView)findViewById(R.id.counterText3);
        plusButton3.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                counterInt3++;
                counterText3.setText(Integer.toString(counterInt3));
            }
        });
        minusButton3.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (counterInt3 > 0)
                {
                    counterInt3--;
                }
                counterText3.setText(Integer.toString(counterInt3));
            }
        });

        plusButton4 = (Button)findViewById(R.id.plusButton4);
        minusButton4 = (Button)findViewById(R.id.minusButton4);
        counterText4 = (TextView)findViewById(R.id.counterText4);
        plusButton4.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                counterInt4++;
                counterText4.setText(Integer.toString(counterInt4));
            }
        });
        minusButton4.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (counterInt4 > 0)
                {
                    counterInt4--;
                }
                counterText4.setText(Integer.toString(counterInt4));
            }
        });

        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        cartButton = (Button)findViewById(R.id.cartButton);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartAddCheck(counterInt1, 1, spinner1.getSelectedItem().toString());
                cartAddCheck(counterInt2, 2, spinner2.getSelectedItem().toString());
                cartAddCheck(counterInt3, 3, spinner3.getSelectedItem().toString());
                cartAddCheck(counterInt4, 4, spinner4.getSelectedItem().toString());
            }
        });
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void cartAddCheck(Integer counter, Integer ID, String spinner) {
        CartActivity cartActivity = new CartActivity();
        if (counter > 0) {
            if (spinner.equals("Choose a size")) {
                Toast.makeText(getApplicationContext(), "Please choose a size", Toast.LENGTH_SHORT).show();
            }
            else if (ID > 0 && ID < 5) {
                ID--;
                cartActivity.addToDatabase(square3.nameList[ID] + " " + documentName, counter, spinner);
                Toast.makeText(getApplicationContext(), "Items added to cart!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}