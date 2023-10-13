package com.example.menuproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class DisplayCart extends AppCompatActivity {

    private FirebaseFirestore db;

    public static ArrayList<String> cartOrderList = new ArrayList<String>();
    public static ArrayList<String> cartIdsList = new ArrayList<String>();
    CartActivity cartActivity = new CartActivity();
    private String sessionId = cartActivity.getSessionId();
    private String id, name, quantity, size, fullString;
    private ListView list;
    private ImageView pizzaplanet;

    private NavigationView nav;
    private Context context = this;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cart);

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
                if (item.getItemId() == R.id.item9) Toast.makeText(DisplayCart.this, "Clicked9", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Button deliveryButton= findViewById(R.id.deliveryButton);
        deliveryButton.setOnClickListener(v -> {
            Toast.makeText(this,"Thank you for ordering, we will notify you when your order is ready", Toast.LENGTH_SHORT).show();
        });

        pizzaplanet = (ImageView) findViewById(R.id.pizzaplanet);
        pizzaplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });


        db = FirebaseFirestore.getInstance();
        list = (ListView) findViewById(R.id.list);

        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<String> cartOrder, ArrayList<String> cartIds) {
                cartOrderList = cartOrder;
                cartIdsList = cartIds;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayCart.this, android.R.layout.simple_list_item_1, cartOrderList);
                list.setAdapter(adapter);


                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView < ? > parent, View view,int position, long id) {



                        String documentName = cartIdsList.get(position);
                        cartOrderList.remove(position);
                        db.collection("PizzaPlanet").document("Cart").collection("List").document("Users").collection("Orders").document(documentName)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Item removed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        adapter.notifyDataSetChanged();

                        openCart();
                    }

                });

            }


        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<String> cartOrder, ArrayList<String> cartIds);
    }

    public void readData(MyCallback myCallback) {
        db.collection("PizzaPlanet").document("Cart").collection("List").document("Users").collection("Orders")
                .whereEqualTo("id", sessionId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> cartOrder = new ArrayList<String>();
                            ArrayList<String> cartIds = new ArrayList<String>();
                            for (DocumentSnapshot document : task.getResult()) {
                                id = document.getId();
                                name = document.getString("name");
                                quantity = document.getString("quantity");
                                size = document.getString("size");
                                fullString = quantity + " - " + name + ", size: " + size;
                                cartOrder.add(fullString);
                                cartIds.add(id);
                            }
                            myCallback.onCallback(cartOrder, cartIds);
                        }
                    }
                });

    }

@RequiresApi(api = Build.VERSION_CODES.O)
public void createNotificationChannel(){

            CharSequence name = "reminderChannel";
            String description= "Channel for reminder";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel= new NotificationChannel("notifySetTime",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager= getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

}
    public void openMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    public void openCart() {
        Intent intent = new Intent(this, DisplayCart.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }
}
