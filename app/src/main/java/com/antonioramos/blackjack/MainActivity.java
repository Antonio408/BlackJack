package com.antonioramos.blackjack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener{

    private int [][] cardDrawables = {{R.drawable.heart_ace, R.drawable.heart_two, R.drawable.heart_three,
            R.drawable.heart_four,R.drawable.heart_five,R.drawable.heart_six,R.drawable.heart_seven,
            R.drawable.heart_eight,R.drawable.heart_nine,R.drawable.heart_ten,
            R.drawable.heart_jack, R.drawable.heart_queen,R.drawable.heart_king},

            {R.drawable.diamonds_ace, R.drawable.diamonds_two,R.drawable.diamonds_three,
            R.drawable.diamonds_four,R.drawable.diamonds_five, R.drawable.diamonds_six,
            R.drawable.diamonds_seven, R.drawable.diamonds_eight, R.drawable.diamonds_nine,
            R.drawable.diamonds_ten, R.drawable.diamonds_jack, R.drawable.diamonds_queen,
            R.drawable.diamonds_king},

            {R.drawable.spades_ace,R.drawable.spades_two, R.drawable.spades_three,
            R.drawable.spades_four,R.drawable.spades_five,R.drawable.spades_six,R.drawable.spades_seven,
            R.drawable.spades_eight,R.drawable.spades_nine, R.drawable.spades_ten,
            R.drawable.spades_jack, R.drawable.spades_queen,R.drawable.spades_king},

            {R.drawable.clubs_ace, R.drawable.clubs_two,R.drawable.clubs_three,
            R.drawable.clubs_four,R.drawable.clubs_five, R.drawable.clubs_six,R.drawable.clubs_seven,
            R.drawable.clubs_eight, R.drawable.clubs_nine, R.drawable.clubs_ten,
            R.drawable.clubs_jack, R.drawable.clubs_queen, R.drawable.clubs_king}};


    //load player's imageView id into playersCards array
    private  int [] playersCards ={R.id.player1_imageView, R.id.player2_imageView,R.id.player3_imageView,
            R.id.player4_imageView,R.id.player5_imageView,R.id.player6_imageView,R.id.player7_imageView,
            R.id.player8_imageView};

    //variable will keep track of number of card been dealt
    private int currentCard = 0;

    private int newBet = 0;

    //Declare and assign Random object to variable r
    Random r = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button newDeal = (Button)findViewById(R.id.deal_button);
        newDeal.setOnClickListener(this);
        Button hit = (Button) findViewById(R.id.hit_button);
        hit.setOnClickListener(this);
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

        if(view.getId() == R.id.deal_button){
            deal();
        }
       else if (view.getId() == R.id.hit_button){
            hit();
        }
        else {
            //*************stay button ***************
        }
    }

    //Method will call chooseSuit() and chooseCard() to generate player's dealt card
    //and display card in the correct imageView. Method will also increment currentCard variable.
    public void hit(){
        if(currentCard <= 7) {
            ImageView displayCard = (ImageView)findViewById(playersCards[currentCard]);

            //cardDrawables[chooseSuit()][chooseCard()] selects card and
            //playersCards[currentCard] selects current imageView
            setImageView(cardDrawables[chooseSuit()][chooseCard()], playersCards[currentCard]);
            displayCard.setVisibility(View.VISIBLE);
            currentCard++;
        }
    }

    public void deal(){
        /*******************************************************
         * ***** textView created for debugging  purposes ***
         *********************************************************/
        TextView bet_tv= (TextView)findViewById(R.id.bet_textView);

       //***** condition to play game ******
        if(newBet > 0){
            //for loop will generate player's first two card
            for(int i = 0; i < 2; i++){
                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                setImageView(cardDrawables[chooseSuit()][chooseCard()],playersCards[currentCard]);
                currentCard++;

                /**********************************************************
                 * ****** newBet increased for debugging  purposes ********
                 *********************************************************/
                newBet = 1;
            }
        }
        /*******************************************************
         * ****** else created for debugging  purposes ************
         *********************************************************/
        else{
            bet_tv.setText("place bet");
        }
    }

    //method will set selected drawable to current imageView
    public void setImageView(int drawableId, int imageId){
        ImageView imageView = (ImageView)findViewById(imageId);
        imageView.setImageResource(drawableId);
    }
    //method will generate and return a number between 0 and 3
    //0 = hearts, 1 = diamonds, 2 = spades, 3 = clubs
    public int chooseSuit(){
       return r.nextInt(4 - 0)+0;
    }
    //method will generate and return a number between 0 and 12
    //card value is
    // 0=ace, 1=2, 2=3, 3=4, 4=5, 5=6, 7=8, 8=9, 9=10, 10=jack, 11=queen, 12=king
    public int chooseCard(){
       return r.nextInt(13-0)+0;
    }
}
