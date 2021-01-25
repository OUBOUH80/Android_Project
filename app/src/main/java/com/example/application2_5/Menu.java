package com.example.application2_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity  implements View.OnClickListener{
    private CardView c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        c2=(CardView)findViewById(R.id.cardVoir);
        c2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        i=new Intent(this , MesActivites.class);
        startActivity(i);
    }
}