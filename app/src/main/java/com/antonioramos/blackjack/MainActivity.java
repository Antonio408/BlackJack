package com.antonioramos.blackjack;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private int[][] cardDrawables = {{R.drawable.heart_ace, R.drawable.heart_two, R.drawable.heart_three,
            R.drawable.heart_four, R.drawable.heart_five, R.drawable.heart_six, R.drawable.heart_seven,
            R.drawable.heart_eight, R.drawable.heart_nine, R.drawable.heart_ten,
            R.drawable.heart_jack, R.drawable.heart_queen, R.drawable.heart_king},

            {R.drawable.diamonds_ace, R.drawable.diamonds_two, R.drawable.diamonds_three,
                    R.drawable.diamonds_four, R.drawable.diamonds_five, R.drawable.diamonds_six,
                    R.drawable.diamonds_seven, R.drawable.diamonds_eight, R.drawable.diamonds_nine,
                    R.drawable.diamonds_ten, R.drawable.diamonds_jack, R.drawable.diamonds_queen,
                    R.drawable.diamonds_king},

            {R.drawable.spades_ace, R.drawable.spades_two, R.drawable.spades_three,
                    R.drawable.spades_four, R.drawable.spades_five, R.drawable.spades_six, R.drawable.spades_seven,
                    R.drawable.spades_eight, R.drawable.spades_nine, R.drawable.spades_ten,
                    R.drawable.spades_jack, R.drawable.spades_queen, R.drawable.spades_king},

            {R.drawable.clubs_ace, R.drawable.clubs_two, R.drawable.clubs_three,
                    R.drawable.clubs_four, R.drawable.clubs_five, R.drawable.clubs_six, R.drawable.clubs_seven,
                    R.drawable.clubs_eight, R.drawable.clubs_nine, R.drawable.clubs_ten,
                    R.drawable.clubs_jack, R.drawable.clubs_queen, R.drawable.clubs_king}};

    // Variable to hold content description ids for accessibilty
    private int[][] cardStrings = {{R.string.ace_of_hearts, R.string.two_of_hearts,
            R.string.three_of_hearts, R.string.four_of_hearts, R.string.five_of_hearts,
            R.string.six_of_hearts, R.string.seven_of_hearts, R.string.eight_of_hearts,
            R.string.nine_of_hearts, R.string.ten_of_hearts, R.string.jack_of_hearts,
            R.string.queen_of_hearts, R.string.king_of_hearts},

            {R.string.ace_of_diamonds, R.string.two_of_diamonds, R.string.three_of_diamonds,
                    R.string.four_of_diamonds, R.string.five_of_diamonds, R.string.six_of_diamonds,
                    R.string.seven_of_diamonds, R.string.eight_of_diamonds,
                    R.string.nine_of_diamonds, R.string.ten_of_diamonds, R.string.jack_of_diamonds,
                    R.string.queen_of_diamonds, R.string.king_of_diamonds},

            {R.string.ace_of_spades, R.string.two_of_spades, R.string.three_of_spades,
                    R.string.four_of_spades, R.string.five_of_spades, R.string.six_of_spades,
                    R.string.seven_of_spades, R.string.eight_of_spades, R.string.nine_of_spades,
                    R.string.ten_of_spades, R.string.jack_of_spades, R.string.queen_of_spades,
                    R.string.king_of_spades},

            {R.string.ace_of_clubs, R.string.two_of_clubs, R.string.three_of_clubs,
                    R.string.four_of_clubs, R.string.five_of_clubs, R.string.six_of_spades,
                    R.string.seven_of_clubs, R.string.eight_of_clubs, R.string.nine_of_clubs,
                    R.string.ten_of_clubs, R.string.jack_of_clubs, R.string.queen_of_clubs,
                    R.string.king_of_clubs}};

    // Variables to save and restore state
    private static final String PLAYER_CARD_SUIT = "playerCardSuit";
    private static final String DEALER_CARD_SUIT = "dealerCardSuit";
    private static final String PLAYER_CARD_TYPE = "playerCardType";
    private static final String DEALER_CARD_TYPE = "dealerCardType";
    private static final String DEALER_HOLD_CARD_SHOWN = "dealerHoldCardShown";
    private static final String BET = "newBet";
    private static final String BANK = "bamk";
    private static final String PLAYER_CURRENT = "playerCurrent";
    private static final String DEALER_CURRENT = "dealerCurrent";
    private static final String PLAYER_TOTAL = "playerTotal";
    private static final String DEALER_TOTAL = "dealerTotal";

    //load player's imageView id into playersCards array
    private int[] playersCards = {R.id.player1_imageView, R.id.player2_imageView, R.id.player3_imageView,
            R.id.player4_imageView, R.id.player5_imageView, R.id.player6_imageView, R.id.player7_imageView,
            R.id.player8_imageView, R.id.player9_imageView, R.id.player10_imageView, R.id.player11_imageView};

    //load dealer's imageView id into dealerCards array
    private int[] dealerCards = {R.id.imageViewDealer01, R.id.imageViewDealer02,
            R.id.imageViewDealer03, R.id.imageViewDealer04, R.id.imageViewDealer05,
            R.id.imageViewDealer06, R.id.imageViewDealer07, R.id.imageViewDealer08,
            R.id.imageViewDealer09, R.id.imageViewDealer10, R.id.imageViewDealer11};

    //load coin buttons id
    private int [] buttonId = {R.id.deal_button,R.id.hit_button, R.id.stay_button,};

    private int [] coinButton ={ R.id.coin5_imageButton, R.id.coin25_imageButton,
            R.id.coin50_imageButton, R.id.coin100_imageButton};

    // variables to keep track of cards dealt
    private int[] playerCardSuit = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] dealerCardSuit = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] playerCardType = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] dealerCardType = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private boolean dealerHoldCardShown = false;

    //variable will keep track of number of card been dealt
    private int currentCard = 0;

    private int newBet = 0;

    private int bank = 1000;
    private int playerTotal;
    private int dealerTotal;

    private int playerCurrent = 0;
    private int dealerCurrent = 0;

    private int cardSuite;

    //Declare and assign Random object to variable r
    Random r = new Random();
    int players_score = 0;
    int computers_score = 0;
    boolean bestHand = true;

    int cardValue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for(int id : buttonId){
            Button operation = (Button)findViewById(id);
            operation.setOnClickListener(this);
        }
        for(int id : coinButton){
            ImageButton coinOp =(ImageButton)findViewById(id);
            coinOp.setOnClickListener(this);
        }


        if (savedInstanceState == null) {
            newGame();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /*
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            } else if (id == R.id.action_new_game) {
                newGame();
            }

            return super.onOptionsItemSelected(item);
        }
    */
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.deal_button) {
            deal();
        } else if (view.getId() == R.id.hit_button) {
            hit();
        } else if (view.getId() == R.id.stay_button) {
            computersTurn();
        }
        else if (view.getId() == R.id.coin5_imageButton){
            placeBet(5);
        }
        else if (view.getId() == R.id.coin25_imageButton){
            placeBet(25);
        }
        else if (view.getId() == R.id.coin50_imageButton){
            placeBet(50);
        }
        else{
            placeBet(100);

        }
    }
    public void placeBet(int bet){
        if(playerCurrent == 0) {
            newBet = newBet + bet;
            bank = bank - bet;
            upDateMoney(newBet, bank);
        }
    }
    public void upDateMoney(int bet1, int bank1){
        TextView tv=(TextView) findViewById(R.id.bet_textView);
        tv.setText(Integer.toString(bet1));
        tv =(TextView)findViewById(R.id.textView5);
        tv.setText(Integer.toString(bank1));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        /* save member variables in order to restore game state later */
        outState.putIntArray(PLAYER_CARD_SUIT, playerCardSuit);
        outState.putIntArray(DEALER_CARD_SUIT, dealerCardSuit);
        outState.putIntArray(PLAYER_CARD_TYPE, playerCardType);
        outState.putIntArray(DEALER_CARD_TYPE, dealerCardType);
        outState.putBoolean(DEALER_HOLD_CARD_SHOWN, dealerHoldCardShown);
        outState.putInt(BET, newBet);
        outState.putInt(BANK, bank);
        outState.putInt(PLAYER_CURRENT, playerCurrent);
        outState.putInt(DEALER_CURRENT, dealerCurrent);
        outState.putInt(PLAYER_TOTAL, playerTotal);
        outState.putInt(DEALER_TOTAL, dealerTotal);


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
        /* retrieve member variables to restore game state */
            playerCardSuit = savedInstanceState.getIntArray(PLAYER_CARD_SUIT);
            dealerCardSuit = savedInstanceState.getIntArray(DEALER_CARD_SUIT);
            playerCardType = savedInstanceState.getIntArray(PLAYER_CARD_TYPE);
            dealerCardType = savedInstanceState.getIntArray(DEALER_CARD_TYPE);
            dealerHoldCardShown = savedInstanceState.getBoolean(DEALER_HOLD_CARD_SHOWN);
            newBet = savedInstanceState.getInt(BET);
            bank = savedInstanceState.getInt(BANK);
            playerCurrent = savedInstanceState.getInt(PLAYER_CURRENT);
            dealerCurrent = savedInstanceState.getInt(DEALER_CURRENT);
            playerTotal = savedInstanceState.getInt(PLAYER_TOTAL);
            dealerTotal = savedInstanceState.getInt(DEALER_TOTAL);

        /* process variables to proper state of the game */
            redrawTable();
        } else {
            newGame();
        }
    }


    //Method will call chooseSuit() and chooseCard() to generate player's dealt card
    //and display card in the correct imageView. Method will also increment currentCard variable.
    public void hit() {
        TextView bet_tv = (TextView) findViewById(R.id.playerTotal_textView);
        if (checkScore(playerTotal)) {
            if (playerCurrent <= 7) {


                cardValue = chooseCard();
                cardSuite = chooseSuit();

                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                setImageView(cardDrawables[cardSuite][cardValue], playersCards[playerCurrent]);


                playerTotal = playerTotal + calculateScore(cardValue, playerTotal);
                savePlayersHand(cardSuite, cardValue, playerCurrent);

                bet_tv.setText("Total score " + playerTotal);
                playerCurrent++;
                if (playerTotal > 21) {
                    computersTurn();
                }

            }

        }

    }

    public void deal() {

        TextView tv = (TextView) findViewById(R.id.playerTotal_textView);


        //playerTotal = 0;




        //***** condition to play game ******
        if (newBet != 0) {
            //for loop will generate player's first two card
            for (int i = 0; i < 2; i++) {
                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                cardValue = chooseCard();
                cardSuite = chooseSuit();

                setImageView(cardDrawables[cardSuite][cardValue], playersCards[playerCurrent]);

                playerTotal = playerTotal + calculateScore(cardValue, playerTotal);

                tv.setText("Total score " + playerTotal);
                savePlayersHand(cardSuite, cardValue, playerCurrent);
                playerCurrent++;

            }
            cardValue = chooseCard();
            cardSuite = chooseSuit();
            tv = (TextView) findViewById(R.id.dealerTotal_textView);
            setImageView(cardDrawables[cardSuite][cardValue], dealerCards[dealerCurrent]);
            saveDealersHand(cardSuite, cardValue, dealerCurrent);
            setImageView(R.drawable.back1, dealerCards[1]);
            dealerCurrent++;


            dealerTotal = dealerTotal + calculateScore(cardValue, dealerTotal);

            tv.setText("Total score " + dealerTotal);
            if (playerTotal == 21) {
                computersTurn();
            }
        }
        /*******************************************************
         * ****** else created for debugging  purposes ************
         *********************************************************/
        else {
            tv.setText("PLACE BET!!!");
        }
    }

    public void savePlayersHand(int suit, int value, int currentHand) {
        playerCardType[currentHand] = value;
        playerCardSuit[currentHand] = suit;
    }

    public void saveDealersHand(int suit, int value, int currentHand) {
        dealerCardType[currentHand] = value;
        dealerCardSuit[currentHand] = suit;

    }


    //method will set selected drawable to current imageView
    public void setImageView(int drawableId, int imageId) {
        ImageView imageView = (ImageView) findViewById(imageId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(drawableId);
    }

    //method will generate and return a number between 0 and 3
    //0 = hearts, 1 = diamonds, 2 = spades, 3 = clubs
    public int chooseSuit() {
        return r.nextInt(4 - 0) + 0;
    }

    //method will generate and return a number between 0 and 12
    //card value is
    // 0=ace, 1=2, 2=3, 3=4, 4=5, 5=6, 7=8, 8=9, 9=10, 10=jack, 11=queen, 12=king
    public int chooseCard() {
        return r.nextInt(13 - 0) + 0;
    }

    // Method to reset all variables for a new game
    private void newGame() {
        for (int i = 0; i < 11; i++) {
            ImageView iv = (ImageView) findViewById(playersCards[i]);
            iv.setVisibility(View.INVISIBLE);
            iv = (ImageView) findViewById(dealerCards[i]);
            iv.setVisibility(View.INVISIBLE);
            playerCardSuit[i] = 0;
            dealerCardSuit[i] = 0;
            playerCardType[i] = 0;
            dealerCardType[i] = 0;
            dealerHoldCardShown = false;
            currentCard = 0;
            newBet = 0;
            bank = 1000;
            playerCurrent = 0;
            dealerCurrent = 0;
            playerTotal = 0;
            dealerTotal = 0;
        }
        redrawTable();
    }

    // method to update or restore the game table to display current values in variables
    private void redrawTable() {

        if(playerCurrent > 0) {
            TextView tv = (TextView) findViewById(R.id.playerTotal_textView);
            tv.setText("Player " + playerTotal);
            for (int i = 0; i < playerCurrent; i++) {
                setImageView(cardDrawables[playerCardSuit[i]][playerCardType[i]], playersCards[i]);


                // get ImageView interface
                //ImageView displayCard = (ImageView) findViewById(playersCards[i]);
                // test if a card is assigned
                //if (playerCardSuit[i] > -1) {
                //setImageView(cardDrawables[playerCardSuit[i]][playerCardType[i]], playersCards[i]);
                //  displayCard.setContentDescription(getString(cardStrings[playerCardSuit[1]][playerCardType[i]]));
            }


/*
        for (int i = 0; i < dealerCurrent; i++) {
            // get ImageView of current card
            ImageView displayCard = (ImageView) findViewById(dealerCards[i]);

            if ((i == 0 && dealerHoldCardShown)) {
                displayCard.setImageDrawable(getDrawable(R.drawable.back1));
            } else {
                // show proper card
                setImageView(cardDrawables[dealerCardSuit[i]][dealerCardType[i]], dealerCards[i]);
                displayCard.setContentDescription(getString(cardStrings[dealerCardSuit[1]][dealerCardType[i]]));

            }
        }*/
            tv =(TextView)findViewById(R.id.dealerTotal_textView);
            tv.setText("Dealer "+dealerTotal);


                for (int i = 0; i < dealerCurrent; i++) {
                    setImageView(cardDrawables[dealerCardSuit[i]][dealerCardType[i]], dealerCards[i]);
                }
            if (dealerCurrent == 1) {
                setImageView(R.drawable.back1,dealerCards[1]);

            }



        }
        upDateMoney(newBet,bank);
    }


    private int calculateScore(int value, int totalScore) {
        int checkScore;
        if (value >= 1 && value <= 8) {
            return ++value;
        } else if (value > 8) {
            return 10;
        } else {
            return checkAce(totalScore);

        }

    }

    private int checkAce(int currentScore) {

        if (currentScore + 11 > 21) {
            return 1;
        } else {
            return 11;
        }
    }

    private boolean checkScore(int score) {
        if (score >= 21) {
            return false;
        } else return true;
    }


    private void computersTurn() {
        TextView tv = (TextView) findViewById(R.id.dealerTotal_textView);

            while(checkScore(dealerTotal) && bestHand) {
                ImageView displayCard = (ImageView) findViewById(dealerCards[dealerCurrent]);
                cardValue = chooseCard();
                cardSuite = chooseSuit();
                saveDealersHand(cardSuite, cardValue, dealerCurrent);


                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                setImageView(cardDrawables[cardSuite][cardValue], dealerCards[dealerCurrent]);


                dealerTotal = dealerTotal + calculateScore(cardValue, dealerTotal);
                tv.setText("Total score " + dealerTotal);
                if (dealerTotal >= 18) {
                    bestHand = false;
                }
                dealerCurrent++;
            }

        checkWinner();

    }
    public void checkWinner(){
        TextView tv =(TextView)findViewById(R.id.textView);
        if(playerTotal > dealerTotal){
            bank += newBet *2;
            tv.setText("Player Wins!!");
        }
        else if(playerTotal < dealerTotal){
            bank -= newBet;
            tv.setText("Player Loses!!");
        }
        else{
            tv.setText("No winner !!");
        }
    }
}

