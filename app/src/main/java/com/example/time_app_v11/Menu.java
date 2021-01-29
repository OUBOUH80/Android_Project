package com.example.time_app_v11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity{
    private CardView c1;
    private CardView c2;
    private CardView c4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        c1=(CardView)findViewById(R.id.cardAjouter);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent (Menu.this, Add_Activity.class);
                startActivity(i);
            }
        });
        c2=(CardView)findViewById(R.id.cardVoir);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent (Menu.this, MesActivites.class);
                startActivity(i);
            }
        });
        c4=(CardView)findViewById(R.id.cardCategorie);
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent (Menu.this, Categories.class);
                startActivity(i);
            }
        });

    }
}