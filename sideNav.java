package com.example.menuproject;

import android.content.Context;
import android.content.Intent;

public class sideNav {

    public static Intent openMainMenu(Context context) {
        Intent intent = new Intent(context, MainMenu.class);
        return intent;
    }

    public static Intent openSquare1(Context context) {
        Intent intent = new Intent(context, square1.class);
        return intent;
    }

    public static Intent openSquare2(Context context) {
        Intent intent = new Intent(context, square2.class);
        return intent;
    }

    public static Intent openSquare3(Context context) {
        Intent intent = new Intent(context, square3.class);
        return intent;
    }

    public static Intent openSquare4(Context context) {
        Intent intent = new Intent(context, square4.class);
        return intent;
    }

    public static Intent openSquare5(Context context) {
        Intent intent = new Intent(context, square5.class);
        return intent;
    }

    public static Intent openSquare6(Context context) {
        Intent intent = new Intent(context, square6.class);
        return intent;
    }

    public static Intent openCart(Context context) {
        Intent intent = new Intent(context, DisplayCart.class);
        return intent;
    }

}
