package com.antonioramos.blackjack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


/*
 * Created by antonio on 2/8/2017.
 */

public class title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);

       Button b = (Button) findViewById(R.id.play_button);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        b = (Button) findViewById(R.id.about_button);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent);
            }
        });

    }

}
