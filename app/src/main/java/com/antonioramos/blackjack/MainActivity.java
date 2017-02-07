package com.antonioramos.blackjack;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button newDeal = (Button) findViewById(R.id.deal_button);
        newDeal.setOnClickListener(this);
        Button hit = (Button) findViewById(R.id.hit_button);
        hit.setOnClickListener(this);
        Button stay = (Button) findViewById(R.id.stay_button);
        stay.setOnClickListener(this);
        newGame();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        } else {
            computersTurn();
        }
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
        if (checkScore(players_score)) {
            if (currentCard <= 7) {
                ImageView displayCard = (ImageView) findViewById(playersCards[currentCard]);

                cardValue = chooseCard();
                cardSuite = chooseSuit();

                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                setImageView(cardDrawables[cardSuite][cardValue], playersCards[currentCard]);
                displayCard.setVisibility(View.VISIBLE);

                players_score = players_score + calculateScore(cardValue, players_score);
                savePlayersHand(cardSuite, cardValue, currentCard);

                bet_tv.setText("Total score " + players_score);
                currentCard++;
                if (players_score > 21) {
                    computersTurn();
                }

            }

        }

    }

    public void deal() {

        TextView tv = (TextView) findViewById(R.id.playerTotal_textView);


        players_score = 0;
        newBet = 0;
        //*********************************************************************************************************
        currentCard = 0;

        //**************************************************************************************************
        //***** condition to play game ******
        if (newBet == 0) {
            //for loop will generate player's first two card
            for (int i = 0; i < 2; i++) {
                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                cardValue = chooseCard();
                cardSuite = chooseSuit();

                setImageView(cardDrawables[cardSuite][cardValue], playersCards[currentCard]);

                players_score = players_score + calculateScore(cardValue, players_score);

                tv.setText("Total score " + players_score);
                savePlayersHand(cardSuite, cardValue, currentCard);
                currentCard++;

            }
            cardValue = chooseCard();
            cardSuite = chooseSuit();
            tv = (TextView) findViewById(R.id.dealerTotal_textView);
            setImageView(cardDrawables[cardSuite][cardValue], dealerCards[0]);
            saveDealersHand(cardSuite, cardValue, 0);


            computers_score = computers_score + calculateScore(cardValue, computers_score);

            tv.setText("Total score " + computers_score);
            if (players_score == 21) {
                computersTurn();
            }
        }
        /*******************************************************
         * ****** else created for debugging  purposes ************
         *********************************************************/
        else {
            tv.setText("place bet");
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
            playerCardSuit[i] = -1;
            dealerCardSuit[i] = -1;
            playerCardType[i] = -1;
            dealerCardType[i] = -1;
            dealerHoldCardShown = false;
            currentCard = 0;
            newBet = 0;
            bank = 1000;
        }
        redrawTable();
    }

    // method to update or restore the game table to display current values in variables
    private void redrawTable() {
        boolean playerHasAce = false;
        boolean dealerHasAce = false;
        int playerAceValue = 0;
        int dealerAceValue = 0;

        playerTotal = 0;
        dealerTotal = 0;

        for (int i = 0; i <= playerCurrent; i++) {
            // get ImageView interface
            ImageView displayCard = (ImageView) findViewById(playersCards[i]);
            // test if a card is assigned
            if (playerCardSuit[i] > -1) {
                setImageView(cardDrawables[playerCardSuit[i]][playerCardType[i]], playersCards[i]);
                displayCard.setVisibility(View.VISIBLE);

                // Set content description for accessibility
                displayCard.setContentDescription(getString(cardStrings[playerCardSuit[1]][playerCardType[i]]));
                //iv.setVisibility(View.VISIBLE);
                if (playerCardType[i] == 0) {
                    // Aces
                    if (playerHasAce) {
                        playerTotal += 1;
                    } else {
                        if (playerTotal < 11) {
                            playerTotal += 11;
                            playerAceValue = 11;
                        } else {
                            playerTotal += 1;
                            playerAceValue = 1;
                        }
                        playerHasAce = true;
                    }
                } else if (playerCardType[i] < 10) {
                    // cards 2 - 9
                    playerTotal += playerCardType[i] + 1;
                } else {
                    // facecards
                    playerTotal += 10;
                }
            } else {
                // no card is assigned
                displayCard.setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i<= dealerCurrent; i++) {
            // get ImageView of current card
            ImageView displayCard = (ImageView) findViewById(dealerCards[i]);
            // Test if card is assigned
            if (dealerCardSuit[i] > -1) {
                // test if hold card is showing
                if (!(i == 1 && dealerHoldCardShown)) {
                    // show proper card
                    setImageView(cardDrawables[dealerCardSuit[i]][dealerCardType[i]], dealerCards[i]);
                    displayCard.setVisibility(View.VISIBLE);

                    // Set content description for accessibility
                    displayCard.setContentDescription(getString(cardStrings[dealerCardSuit[1]][dealerCardType[i]]));
                    // Aces
                    if (dealerCardType[i] == 0) {
                        if (dealerHasAce) {
                            dealerTotal += 1;
                        } else {
                            if (dealerTotal < 11) {
                                dealerTotal += 11;
                                dealerAceValue = 11;
                            } else {
                                dealerTotal += 1;
                                dealerAceValue = 1;
                            }
                            dealerHasAce = true;
                        }
                        // Cards 2 - 10
                    } else if (dealerCardType[i] < 10) {
                        dealerTotal += dealerCardType[i] + 1;
                    } else {
                        //aces
                        dealerTotal += 10;
                    }
                } else {
                    displayCard.setImageDrawable(getDrawable(R.drawable.back1));
                }
            } else {
                displayCard.setVisibility(View.INVISIBLE);
            }
        }
        // adjust for ace if over 21
        if (playerHasAce && playerAceValue == 11 && playerTotal > 21) {
            playerTotal -= 10;
            playerAceValue = 1;
        }
        // adjust for ace if over 21
        if (dealerHasAce && dealerAceValue == 11 && dealerTotal > 21) {
            dealerTotal -= 10;
            dealerAceValue = 1;
        }
        // add player and dealer totals to table
        TextView tv = (TextView) findViewById(R.id.playerTotal_textView);
        if (playerAceValue == 11) {
            tv.setText("Player " + Integer.toString(playerTotal - 10) + " or " + Integer.toString(playerTotal));
        } else {
            tv.setText("Player " + Integer.toString(playerTotal));
        }
        tv = (TextView) findViewById(R.id.dealerTotal_textView);
        if (dealerAceValue == 11) {
            tv.setText("Dealer " + Integer.toString(dealerTotal - 10) + " or " + Integer.toString(dealerTotal));
        } else {
            tv.setText("Dealer " + Integer.toString(dealerTotal));
        }
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
        currentCard = 1;


        while (currentCard < 10) {
            if (checkScore(computers_score) && bestHand) {
                ImageView displayCard = (ImageView) findViewById(dealerCards[currentCard]);
                cardValue = chooseCard();
                cardSuite = chooseSuit();
                saveDealersHand(cardSuite, cardValue, currentCard);


                //cardDrawables[chooseSuit()][chooseCard()] selects card and
                //playersCards[currentCard] selects current imageView
                setImageView(cardDrawables[chooseSuit()][cardValue], dealerCards[currentCard]);
                displayCard.setVisibility(View.VISIBLE);

                computers_score = computers_score + calculateScore(cardValue, computers_score);
                tv.setText("Total score " + computers_score);
                if (computers_score >= 18) {
                    bestHand = false;
                }

            }
            currentCard++;

        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
