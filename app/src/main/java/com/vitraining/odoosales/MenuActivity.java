package com.vitraining.odoosales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onPartnerClick(View v){
        Log.d("MENU", "partner button clicked");

        Intent intent = new Intent(MenuActivity.this, CustomerListActivity.class);
        MenuActivity.this.startActivity(intent);
    }

    public void onSaleOrderClick(View v){
        Log.d("MENU", "sale order button clickec");
    }
}
