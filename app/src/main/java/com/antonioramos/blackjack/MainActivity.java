package com.antonioramos.blackjack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener{

    private int [] heartsImage = {R.drawable.heart_ace, R.drawable.heart_two, R.drawable.heart_three,
            R.drawable.heart_four,R.drawable.heart_five,R.drawable.heart_six,R.drawable.heart_seven,
            R.drawable.heart_eight,R.drawable.heart_nine,R.drawable.heart_ten,
            R.drawable.heart_jack, R.drawable.heart_queen,R.drawable.heart_king};
    private int [] diamondImage = {R.drawable.diamonds_ace, R.drawable.diamonds_two,R.drawable.diamonds_three,
            R.drawable.diamonds_four,R.drawable.diamonds_five, R.drawable.diamonds_six,
            R.drawable.diamonds_seven, R.drawable.diamonds_eight, R.drawable.diamonds_nine,
            R.drawable.diamonds_ten, R.drawable.diamonds_jack, R.drawable.diamonds_queen,
            R.drawable.diamonds_king};
    private int [] spadesImage = {R.drawable.spades_ace,R.drawable.spades_two, R.drawable.spades_three,
            R.drawable.spades_four,R.drawable.spades_five,R.drawable.spades_six,R.drawable.spades_seven,
            R.drawable.spades_eight,R.drawable.spades_nine, R.drawable.spades_ten,
            R.drawable.spades_jack, R.drawable.spades_queen,R.drawable.spades_king};
    private int [] clubsImage = {R.drawable.clubs_ace, R.drawable.clubs_two,R.drawable.clubs_three,
            R.drawable.clubs_four,R.drawable.clubs_five, R.drawable.clubs_six,R.drawable.clubs_seven,
            R.drawable.clubs_eight, R.drawable.clubs_nine, R.drawable.clubs_ten,
            R.drawable.clubs_jack, R.drawable.clubs_queen, R.drawable.clubs_king};

    //array needed to choose suit
    private int [] suits = {1,2,3,4};

    Random r = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        })*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    
    public int chooseSuit(){
        int set_suit = r.nextInt(4 -1 )+1;
        return set_suit;
    }
    public int chooseCard(){
        int set_card = r.nextInt(13-1)+1;
        return set_card;
    }
}
