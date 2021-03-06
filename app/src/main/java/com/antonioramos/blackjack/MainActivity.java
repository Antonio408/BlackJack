package com.antonioramos.blackjack;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    /*************************************************************************************************
    ** double array holds 52 playing cards
    ** The first part of the array chooses the suit and the second part chooses the value. Using a two
    ** dimensional array increases the randomness of the game. The program uses Random class to first
    ** generate  a number 0 thru 3 to choose the suit and then generates a number 0  thru 12 to choose
    ** the value of the card being dealt. -by Antonio
    *************************************************************************************************/
    private final int[][] cardDrawables = {{R.drawable.heart_ace, R.drawable.heart_two, R.drawable.heart_three,
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

    // Variable to hold content description ids for Accessibility  added by Gary
    private final int[][] cardStrings = {{R.string.ace_of_hearts, R.string.two_of_hearts,
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

    // Variables to save and restore state added by Gary
    private static final String PLAYER_CARD_SUIT = "playerCardSuit";
    private static final String DEALER_CARD_SUIT = "dealerCardSuit";
    private static final String PLAYER_CARD_TYPE = "playerCardType";
    private static final String DEALER_CARD_TYPE = "dealerCardType";
    private static final String DEALER_HOLD_CARD_SHOWN = "dealerHoldCardShown";
    private static final String BET = "newBet";
    private static final String BANK = "bank";
    private static final String PLAYER_CURRENT = "playerCurrent";
    private static final String DEALER_CURRENT = "dealerCurrent";
    private static final String PLAYER_TOTAL = "playerTotal";
    private static final String DEALER_TOTAL = "dealerTotal";
    private static final String DATA_FILENAME = "blackjack.txt";

    /***********************************************************************************************
    ** Loads player's imageView ids into playersCards array. Array will be used to load player's
    ** card dealt to correct position and imageView. -by Antonio
    ***********************************************************************************************/
    private final int[] playersCards = {R.id.player1_imageView, R.id.player2_imageView, R.id.player3_imageView,
            R.id.player4_imageView, R.id.player5_imageView, R.id.player6_imageView, R.id.player7_imageView,
            R.id.player8_imageView, R.id.player9_imageView, R.id.player10_imageView, R.id.player11_imageView};

    //load dealer's imageView id into dealerCards array added by Gary
    private final int[] dealerCards = {R.id.dealer1_imageView, R.id.dealer2_imageView, R.id.firstCard_imageView,
            R.id.dealer3_imageView, R.id.dealer4_imageView, R.id.dealer5_imageView,
            R.id.dealer6_imageView, R.id.dealer7_imageView, R.id.dealer8_imageView,
            R.id.dealer9_imageView, R.id.dealer10_imageView, R.id.dealer11_imageView};

    /***********************************************************************************************
     ** Loads deal, hit, and stay button ids into an array. Program will use array in a for-each-loop
     ** to respond to the correct button that was poked. -by Antonio
     **********************************************************************************************/
    private final int [] buttonId = {R.id.deal_button,R.id.hit_button, R.id.stay_button,};

    /***********************************************************************************************
     ** Loads coin button ids ($5, $10, $25, $50, $100) into an array. Program will use array to
     ** increase player's bet value. -by Antonio
     ***********************************************************************************************/
    private final int [] coinButton ={ R.id.coin5_imageButton, R.id.coin25_imageButton,
            R.id.coin50_imageButton, R.id.coin100_imageButton};

    // variables to keep track of cards dealt added by Gary
    private int[] playerCardSuit = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] dealerCardSuit = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] playerCardType = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int[] dealerCardType = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    // variable to determine hold card is shown added by Gary
    private boolean dealerHoldCardShown = false;

    /***********************************************************************************************
     ** Condition flags-
     * bestHand will be set when computer's hand is equal to or greater than 18
     * noWinner will be set when the current game has a winner or the game is a tie. -by Antonio
     **********************************************************************************************/
    private boolean bestHand = true;
    private boolean noWinner = true;

    /***********************************************************************************************
    ** Variable will keep a running total of player's bet. -by Antonio
    ***********************************************************************************************/
    private int newBet = 0;

    // variables for bank and hand totals added by Gary
    private int bank = 1000;
    private int playerTotal;
    private int dealerTotal;

    /***********************************************************************************************
     ** Variables will keep count of player's or dealer's number of cards dealt to index array.
     * -by Antonio
     **********************************************************************************************/
    private int playerCurrent = 0;
    private int dealerCurrent = 0;

    /***********************************************************************************************
     ** Variables will keep track of player's or dealer's number of cards dealt. -by Antonio
     **********************************************************************************************/
    private int cardSuit;
    private int cardValue;

    /***********************************************************************************************
     ** Declare and assign Random object to variable r. Program will use random generator to
     * choose player's or dealers's card. -by Antonio
     **********************************************************************************************/
    private Random r;

    public MainActivity() {
        r = new Random();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*******************************************************************************************
         ** For-each-loops (buttonId, coinButton) will choose the correct id that corresponds to
         * button that was poked -by Antonio
         ******************************************************************************************/
        for(int id : buttonId){
            Button operation = (Button)findViewById(id);
            operation.setOnClickListener(this);
        }
        for(int id : coinButton){
            ImageButton coinOp =(ImageButton)findViewById(id);
            coinOp.setOnClickListener(this);
        }

        // test if instance was not restored initialize game and check for saved game added by Gary
        if (savedInstanceState == null) {
            newGame();
            // Log.i("INFO", "---------- READ CALLED");
            readData();
            redrawTable();
        }
    }

    // prepare menu added by Gary
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

        // options clicked method added by Gary
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
                redrawTable();
            }
            return super.onOptionsItemSelected(item);
        }
    /***********************************************************************************************
     ** Method responds to user pressing game buttons -by Antonio
     **********************************************************************************************/
    @Override
    public void onClick(View view) {
        /*******************************************************************************************
         ** When user pokes coin buttons placeBet method is called to calculate players bet
         * -by Antonio
         ******************************************************************************************/
        if (view.getId() == R.id.coin5_imageButton){
            placeBet(5);
        }
        else if (view.getId() == R.id.coin25_imageButton){
            placeBet(25);
        }
        else if (view.getId() == R.id.coin50_imageButton){
            placeBet(50);
        }
        else if(view.getId() == R.id.coin100_imageButton){
            placeBet(100);
        }
        /*******************************************************************************************
         ** When user pokes (deal, hit, or stay) buttons and players bet is greater than zero game
         *  will continue. -by Antonio
         ******************************************************************************************/
        else if(newBet > 0) {
            /***************************************************************************************
             ** Will reset winMessage to start a new game -by Antonio
             **************************************************************************************/
            ImageView im =(ImageView)findViewById(R.id.winMessage_imageView);
            im.setVisibility(View.INVISIBLE);

            /***************************************************************************************
             ** If all conditions are meet game continues otherwise buttons are disabled. -by Antonio
             **************************************************************************************/
            if (view.getId() == R.id.deal_button && playerCurrent == 0) {
                deal();
            } else if (view.getId() == R.id.hit_button&& playerCurrent > 0 ) {
                hit();
            } else if(view.getId() == R.id.stay_button && playerCurrent > 0) {
                computersTurn();
            }
        }
        /*******************************************************************************************
         ** If current hand is over game will reset and as player to place bet -by Antonio
         ******************************************************************************************/
        else{
            restGame();
            TextView tv = (TextView) findViewById(R.id.playerTotal_textView);
            tv.setText(R.string.place_bet);
        }
    }
    /***********************************************************************************************
     ** Method will calculate player's bet, update, and safe bank and bet totals.
     *  conditions must be meet to place bet
     * -players cards have not been dealt and bank value greater than 5
     * -if after deducting requested bet from bank is less then zero and no cards have been dealt
     *  request player to rest game
     * -else calculate totals and continue game -by Antonio
     **********************************************************************************************/
    private void placeBet(int bet){

        if(playerCurrent == 0 && bank >= 5) {

            if(bank - bet < 0 && newBet ==0){
                TextView tv = (TextView)findViewById(R.id.playerTotal_textView);
                tv.setText(R.string.select_new_game);
            }
            else {
                newBet = newBet + bet;
                bank = bank - bet;
                upDateMoney(newBet, bank);
            }
        }
    }
    /***********************************************************************************************
     ** Method will display current bet and bank totals -by Antonio
     **********************************************************************************************/
    private void upDateMoney(int bet1, int bank1){
        TextView tv=(TextView) findViewById(R.id.bet_textView);
        tv.setText(String.format(Locale.getDefault(), "%d", bet1));
        tv =(TextView)findViewById(R.id.bankAmount_textView);
        tv.setText(String.format(Locale.getDefault(), "%d", bank1));
    }

    // Save instance variables added by Gary
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

    // retrieve instance variables added by Gary
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

            // process variables to proper state of the game added by Gary
            redrawTable();
        }
    }
    /***********************************************************************************************
     ** Method will be called when user requested another card to be dealt(poke hit button).
     * If condition is meet:
     *  -players score not greater than 21 and noWinner flag not set continue game
     * Call chooseSuit() and chooseCard() to generate player's dealt card
     * Call setImageView to set and display card in the correct imageView. Calculate, save and
     * display players hand value. Increment variable playerCurrent by one. If players total is
     * greater 21 end players turn and call computerTurn method. -by Antonio
     **********************************************************************************************/
    private void hit() {
        TextView bet_tv = (TextView) findViewById(R.id.playerTotal_textView);

        if (checkScore(playerTotal)&& noWinner) {
            if (playerCurrent <= 7) {

                cardValue = chooseCard();
                cardSuit = chooseSuit();

                setImageView(cardDrawables[cardSuit][cardValue], playersCards[playerCurrent]);

                playerTotal = playerTotal + calculateScore(cardValue, playerTotal);
                savePlayersHand(cardSuit, cardValue, playerCurrent);

                bet_tv.setText(String.format(Locale.getDefault(), "Total score %d", playerTotal));
                playerCurrent++;

                if (playerTotal > 21) {
                    computersTurn();
                }
            }
        }
    }
    /***********************************************************************************************
     ** Method starts game by dealing player two random cards face up and dealer two cards one
     * face down and the second face up using these methods (chooseCard, chooseSuit and setImageView).
     * Calculates, saves and displays players and dealer's hand value. Then increments playerCurrent
     * and dealerCurrent by one each time a card is dealt to each. If player's first two cards
     * are equal to 21 end player's turn and call computerTurn method. -by Antonio
     **********************************************************************************************/
    private void deal() {
        TextView tv = (TextView) findViewById(R.id.playerTotal_textView);

            //for loop will generate player's first two card
            for (int i = 0; i < 2; i++) {
                cardValue = chooseCard();
                cardSuit = chooseSuit();

                setImageView(cardDrawables[cardSuit][cardValue], playersCards[playerCurrent]);

                playerTotal += calculateScore(cardValue, playerTotal);

                tv.setText(String.format(Locale.getDefault(), "Total score %d", playerTotal));
                savePlayersHand(cardSuit, cardValue, playerCurrent);
                playerCurrent++;
            }

            cardValue = chooseCard();
            cardSuit = chooseSuit();

            tv = (TextView) findViewById(R.id.dealerTotal_textView);
            setImageView(R.drawable.back1, dealerCards[0]);
            dealerCurrent++;

            setImageView(cardDrawables[cardSuit][cardValue], dealerCards[dealerCurrent]);
            saveDealersHand(cardSuit, cardValue, dealerCurrent);
            dealerCurrent++;

            dealerTotal += calculateScore(cardValue, dealerTotal);

            tv.setText(String.format(Locale.getDefault(), "Total score %d", dealerTotal));
            if (playerTotal == 21) {
                computersTurn();
            }
    }
    /***********************************************************************************************
     ** Method will save player's current card suit and value into playerCardType and playerCardSuit
     * arrays. Will use currentHand variable to select array index. -by Antonio
     **********************************************************************************************/
    private void savePlayersHand(int suit, int value, int currentHand) {
        playerCardType[currentHand] = value;
        playerCardSuit[currentHand] = suit;
    }
    /***********************************************************************************************
     ** Method will save dealer's current card suit and value into dealerCardType and dealerCardSuit
     * arrays. Will use currentHand variable to select array index. -by Antonio
     **********************************************************************************************/
    private void saveDealersHand(int suit, int value, int currentHand) {
        dealerCardType[currentHand] = value;
        dealerCardSuit[currentHand] = suit;

    }
    /***********************************************************************************************
     ** Method will display card dealt by loading drawable into the correct imageView and making
     * imageView visible. -by Antonio
     **********************************************************************************************/
    private void setImageView(int drawableId, int imageId) {
        ImageView imageView = (ImageView) findViewById(imageId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(drawableId);
    }
    /***********************************************************************************************
     ** Method will generate and return a number between 0 and 3
     * 0 = hearts, 1 = diamonds, 2 = spades, 3 = clubs -by Antonio
     **********************************************************************************************/
    private int chooseSuit() {
        return r.nextInt(4);
    }
    /***********************************************************************************************
     ** Method will generate and return a number between 0 and 12
     * card value is
     * 0=ace, 1=2, 2=3, 3=4, 4=5, 5=6, 7=8, 8=9, 9=10, 10=jack, 11=queen, 12=king -by Antonio
     **********************************************************************************************/
    private int chooseCard() {
        return r.nextInt(13);
    }

    // Method to reset all variables for a new game added by Gary
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
        }
        dealerHoldCardShown = false;
        newBet = 0;
        bank = 1000;
        playerCurrent = 0;
        dealerCurrent = 0;
        playerTotal = 0;
        dealerTotal = 0;
        redrawTable();
    }

    // method to update or restore the game table to display current values in variables
    // added by Gary
    private void redrawTable() {

        if(playerCurrent > 0) {
            for (int i = 0; i < playerCurrent; i++) {

                // get ImageView interface
                ImageView displayCard = (ImageView) findViewById(playersCards[i]);
                // test if a card is assigned
                if (playerCardSuit[i] > -1 && playerCardType[i] > -1) {
                    setImageView(cardDrawables[playerCardSuit[i]][playerCardType[i]], playersCards[i]);
                    displayCard.setContentDescription(getString(cardStrings[playerCardSuit[1]][playerCardType[i]]));
                }
            }
        }

        if (dealerCurrent > 0) {
            for (int i = 0; i < dealerCurrent; i++) {
                // get ImageView of current card
                ImageView displayCard = (ImageView) findViewById(dealerCards[i]);

                if ((i == 0 && !dealerHoldCardShown)) {
                    setImageView(R.drawable.back1, dealerCards[i]);
                } else {
                    if (dealerCardSuit[i] > -1 && dealerCardType[i] > -1) {
                        // show proper card
                        setImageView(cardDrawables[dealerCardSuit[i]][dealerCardType[i]], dealerCards[i]);
                        displayCard.setContentDescription(getString(cardStrings[dealerCardSuit[1]][dealerCardType[i]]));
                    }

                }
            }
        }

        TextView tv = (TextView) findViewById(R.id.playerTotal_textView);
        tv.setText(String.format(Locale.getDefault(), "Player %d", playerTotal));
        tv = (TextView) findViewById(R.id.dealerTotal_textView);
        tv.setText(String.format(Locale.getDefault(), "Dealer %d", dealerTotal));
        tv = (TextView) findViewById(R.id.bankAmount_textView);
        tv.setText(String.format(Locale.getDefault(), "%d", bank));
        tv = (TextView) findViewById(R.id.bet_textView);
        tv.setText(String.format(Locale.getDefault(), "%d", newBet));

    }

    /***********************************************************************************************
     ** Method will calculate  and return card value
     * if value 1 thru 8 increment value by one and return
     * if value greater than 8 return 10
     * if value equal to 0 call checkAce method -by Antonio
     **********************************************************************************************/
    private int calculateScore(int value, int totalScore) {
        // int checkScore;
        if (value >= 1 && value <= 8) {
            return ++value;
        } else if (value > 8) {
            return 10;
        } else {
            return checkAce(totalScore);
        }
    }
    /***********************************************************************************************
     ** Method will calculated and return ace value
     * if current score plus 11 is greater than 21 return 1
     * else if score is not greater than 21 return 11. -by Antonio
     **********************************************************************************************/
    private int checkAce(int currentScore) {
        if (currentScore + 11 > 21) {
            return 1;
        } else {
            return 11;
        }
    }
    /***********************************************************************************************
     ** Method sets checkScore flag to false if current score is greater than 21 -by Antonio
     **********************************************************************************************/
    private boolean checkScore(int score) {
        return score < 21;
    }
    /***********************************************************************************************
     ** Method sets noWinner flag to false and bestHand flag to true when dealerCurrent is greater
     * then 4. Deals dealer cards using chooseCard chooseSuit setImageView methods until dealer's
     * hand is equal to or greater than 18 or dealer bust (score greater than 21 set bestHand flag to
     * false).Increase dealerCurrent by one and save current hand value each time a card is dealt.
     * call checkWinner to determined who is the winner. -by Antonio
     **********************************************************************************************/
    private void computersTurn() {
        TextView tv = (TextView) findViewById(R.id.dealerTotal_textView);
        noWinner = false;

        //reset bestHand flag when starting new game
        if(dealerCurrent <4){
            bestHand = true;
        }
        while(checkScore(dealerTotal) && bestHand) {

            cardValue = chooseCard();
            cardSuit = chooseSuit();
            saveDealersHand(cardSuit, cardValue, dealerCurrent);

            setImageView(cardDrawables[cardSuit][cardValue], dealerCards[dealerCurrent]);

            dealerTotal = dealerTotal + calculateScore(cardValue, dealerTotal);
            tv.setText(String.format(Locale.getDefault(), "Total score %d", dealerTotal));
            if (dealerTotal >= 18) {
                bestHand = false;
            }
            dealerCurrent++;
        }
        checkWinner();
    }
    /***********************************************************************************************
     ** Method will determine the winner.
     * if player is the winner increase bank total
     * if player is the loser decrease bank total
     * if draw reset bank to previous total
     * Display winnerMessage, update bank total and reset bet (newBet variable) to zero. -by Antonio
     **********************************************************************************************/
    private void checkWinner(){
        int [] message ={R.drawable.winner,R.drawable.loser,R.drawable.draw};

        if((playerTotal > dealerTotal && playerTotal <21)||(playerTotal < dealerTotal
                && dealerTotal > 21)){

            bank += newBet *2;
            winnerMessage(message[0]);
        }
        else if((playerTotal < dealerTotal && dealerTotal <22)||(dealerTotal < playerTotal
                && playerTotal >21)){

            winnerMessage(message[1]);
        }
        else{
            bank+=newBet;
            winnerMessage(message[2]);
        }
        newBet =0;
        upDateMoney(newBet, bank);
    }
    /***********************************************************************************************
     ** Method will display message(Winner, Loser, Draw) when current hand is over. -by Antonio
     **********************************************************************************************/
    private void winnerMessage(int winner){
        ImageView im = (ImageView)findViewById(R.id.winMessage_imageView);
        im.setImageResource(winner);
        im.setVisibility(View.VISIBLE);

    }
    /***********************************************************************************************
     ** Method will reset game table images, messages and condition flags, to allow user to place a
     * new bet and play another game. -by Antonio
     **********************************************************************************************/
    private void restGame() {
        for (int i = 0; i < 11; i++) {
            ImageView iv = (ImageView) findViewById(playersCards[i]);
            iv.setVisibility(View.INVISIBLE);
            iv = (ImageView) findViewById(dealerCards[i]);
            iv.setVisibility(View.INVISIBLE);
            playerCardSuit[i] = -1;
            dealerCardSuit[i] = -1;
            playerCardType[i] = -1;
            dealerCardType[i] = -1;
        }
        dealerHoldCardShown = false;
        newBet = 0;
        playerCurrent = 0;
        dealerCurrent = 0;
        playerTotal = 0;
        dealerTotal = 0;
        noWinner = true;
    }
    // call method to save game state to file added by Gary
    @Override
    protected void onStop() {
        super.onStop();
        // Log.i("INFO", "---------- Write CALLED");
        writeData();
    }

    // read game state from file added by Gary
    private void readData() {

        try {
            // Log.i("INFO", "---------- Entered Read");
            FileInputStream fis = openFileInput(DATA_FILENAME);
            Scanner scanner = new Scanner(fis);

            // Log.i("INFO", "---------- Read Setup Done");
            if (scanner.hasNext()) {
                // Log.i("INFO", "---------- Read has DATA");
                int i;

                for (i = 0; i < 11; i++)
                    playerCardSuit[i] = scanner.nextInt();
                for (i = 0; i < 11; i++)
                    dealerCardSuit[i] = scanner.nextInt();
                for (i = 0; i < 11; i++)
                    playerCardType[i] = scanner.nextInt();
                for (i = 0; i < 11; i++)
                    dealerCardType[i] = scanner.nextInt();
                dealerHoldCardShown = scanner.nextBoolean();
                newBet = scanner.nextInt();
                bank = scanner.nextInt();
                playerTotal = scanner.nextInt();
                dealerTotal = scanner.nextInt();
                playerCurrent = scanner.nextInt();
                dealerCurrent = scanner.nextInt();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // Log.i("INFO", "---------- Read Exception");
            // ok if file does not exist
        }
    }

    // write game state to file added by Gary
    private void writeData() {

        try {
            // Log.i("INFO", "---------- Entered Write");
            FileOutputStream fos = openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw);
            int i;
            // Log.i("INFO", "---------- Write Setup Complete");

            for (i = 0; i < 11; i++)
                pw.println(playerCardSuit[i]);
            for (i = 0; i < 11; i++)
                pw.println(dealerCardSuit[i]);
            for (i = 0; i < 11; i++)
                pw.println(playerCardType[i]);
            for (i = 0; i < 11; i++)
                pw.println(dealerCardType[i]);
            pw.println(dealerHoldCardShown);
            pw.println(newBet);
            pw.println(bank);
            pw.println(playerTotal);
            pw.println(dealerTotal);
            pw.println(playerCurrent);
            pw.println(dealerCurrent);
            // Log.i("INFO", "---------- Write DATA COMPLETE");


            pw.close();
        } catch (FileNotFoundException e) {
            // Log.e("WRITE_ERR", "Cannot save data: " + e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}

