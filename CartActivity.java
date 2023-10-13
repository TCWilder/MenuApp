package com.example.menuproject;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    public static DocumentReference uniqueIDRef;
    public static DocumentReference itemsInCartRef;
    public static String sessionId;

    public void createCart() {
        Map<String , Object> data = new HashMap<>();
        data.put("timestamp", FieldValue.serverTimestamp());

        db = FirebaseFirestore.getInstance();
        uniqueIDRef = db.collection("PizzaPlanet").document("Cart").collection("List").document();
        sessionId = uniqueIDRef.getId();
        db.collection("PizzaPlanet").document("Cart").collection("List");
                uniqueIDRef.set(data);
        }

    public String getSessionId() {
        return sessionId;
    }

    public void addToDatabase(String itemID, int amount, String size) {
        Map<String, String> data = new HashMap<>();
        data.put("id", uniqueIDRef.getId());
        data.put("name", itemID);
        data.put("quantity", String.valueOf(amount));
        data.put("size", size);

        db = FirebaseFirestore.getInstance();
        itemsInCartRef = db.collection("PizzaPlanet").document("Cart").collection("List").document("Users").collection("Orders").document();

        db.collection("PizzaPlanet").document("Cart").collection("List");
            itemsInCartRef.set(data);
        }
    }





