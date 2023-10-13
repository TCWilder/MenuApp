package com.example.menuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class beginOrder extends AppCompatActivity {

    private ImageView pizzaplanet;
    Button buttonBeginOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_order);

        pizzaplanet = (ImageView) findViewById(R.id.pizzaplanet);
        pizzaplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        buttonBeginOrder = (Button) findViewById(R.id.buttonBeginOrder);
        buttonBeginOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });
    }

    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}