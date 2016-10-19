package com.example.tanya.wordstar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.org.watsonwrite.lawrence.Lawrence;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;

public class MainActivity extends Activity {



    public Button publishButton;
    public Button deleteButton;

    public EditText haikuPost;
    public TextView line1;
    public TextView line2;
    public TextView line3;
    public TextView syllCount;
    public TextView message;
    public TextView starWordView;
    public ImageButton newWordButton;
    public TextView starCountView;
    public ImageButton timerButton;
    public TextView countdown;
    public ImageView goldStar;
    public ImageButton copy;
    private TextView quote;

    Toast mToast = null;

    private Typeface type1;
    private Typeface type2;
    private Typeface type3;
    private Typeface type4;
    private Typeface type5;

    public StringBuilder haikuBuilder;
    public String haiku;

    private boolean timeBattle = false;
    private boolean beatTimeBattle = false;


    Lawrence lawrence;
    public ArrayList<String> starWords;

    private Random randomGenerator;
    public String starWord = "";

    public HashMap<Integer, ArrayList<String>> linesToStarWords =
            new HashMap<Integer, ArrayList<String>>();


    public TextWatcher tw;

    StringBuilder curWord = new StringBuilder();

    int LINE1_SYLCOUNT = 5;
    int LINE2_SYLCOUNT = 7;
    int LINE3_SYLCOUNT = 5;

    int starCount = 0;
    int count = 0;

    int currentLine = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        starWords = new ArrayList<String>(
                Arrays.asList("island", "fish", "world", "purpose", "flower",
                        "butter", "snake", "floor", "error", "impulse", "chance",
                        "cow", "sidewalk", "pet", "night", "day", "sun", "moon",
                        "icicle", "boulder", "rock", "voice", "thrill", "friend",
                        "mother", "father", "lover", "woman", "man", "sleep", "death",
                        "song", "animal", "air", "train", "wave", "record", "force",
                        "tub", "van", "car", "box", "pain", "pleasure", "wall", "blade",
                        "cream", "metal", "prose", "hook", "hand", "donkey", "machine",
                        "juice", "toe", "sand", "rice", "oven", "pickle", "lunch", "sound",
                        "tent", "sheet", "jelly", "bike", "waste", "trash", "knee", "hell",
                        "heaven", "magic", "bell", "pin", "tooth", "teeth", "fear", "turkey",
                        "chicken", "girl", "boy", "fang", "wish", "worm", "hair", "seat", "person",
                        "cake", "voyage", "oil", "egg", "circle", "cat", "window", "stick", "root",
                        "tree", "dirt", "spoon", "station", "plane", "horse", "nose", "breast", "buttock",
                        "lace", "rat", "space", "eye", "birth", "system", "vein", "head", "time", "tide", "clock",
                        "money", "engine", "letter", "stone", "class", "flame", "fire", "water", "shoe",
                        "frog", "breath", "land", "earth", "whip", "loss", "quiet", "snail", "thumb", "stage",
                        "clown", "alarm", "view", "basin", "summer", "winter", "fall", "spring", "doll",
                        "dinosaur", "flight", "hat", "seed", "grass", "straw", "cannon", "silk", "creator",
                        "cast", "noise", "jewel", "thunder", "rain", "stomach", "gut", "lung", "throat",
                        "mouth", "tongue", "apple", "banana", "pine", "umbrella", "dog", "growth", "rot", "hot",
                        "cold", "ice", "light", "dark", "fuel", "pie", "wing", "tick", "smell", "slave",
                        "river", "mountain", "sea", "ocean", "bay", "beach", "bomb", "art", "wound", "blood",
                        "puss", "sock", "jam", "middle", "white", "poison", "glass", "black", "green", "red",
                        "lamp", "love", "marble", "swing", "fog", "substance", "wire", "nut", "ghost",
                        "monster", "crown", "deer", "house", "home", "baby", "border", "ground", "hole",
                        "cactus", "iron", "hope", "ink", "back", "front", "pill", "sick", "sickle", "axe",
                        "knife", "cave", "hill", "trail", "low", "high", "smoke", "kitten", "belief", "run",
                        "skin", "ant", "quill", "horn", "ring", "bait", "growth", "smile", "frown",
                        "spider", "harmony", "mess", "bone", "machine", "throne", "bush", "spark",
                        "cherry", "shirt", "pants", "bed"));


        randomGenerator = new Random();

        linesToStarWords.put(1, new ArrayList<String>());
        linesToStarWords.put(2, new ArrayList<String>());
        linesToStarWords.put(3, new ArrayList<String>());


        haikuPost = (EditText) findViewById(R.id.haikuInput);
        haikuPost.clearFocus();
        publishButton = (Button) findViewById(R.id.publish);
        deleteButton = (Button) findViewById(R.id.scrap);
        message = (TextView) findViewById(R.id.confirmation);
        starWordView = (TextView) findViewById(R.id.starWord);
        starCountView = (TextView) findViewById(R.id.starCount);
        newWordButton = (ImageButton) findViewById(R.id.refresh);
        timerButton = (ImageButton) findViewById(R.id.timer);
        countdown = (TextView) findViewById(R.id.countDown);
        goldStar = (ImageView) findViewById(R.id.goldStar);
        copy = (ImageButton) findViewById(R.id.copy);
        quote = (TextView) findViewById(R.id.quote);

        starCountView.setText(Integer.toString(starCount));


        message.setVisibility(View.INVISIBLE);

        publishButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        copy.setVisibility(View.GONE);
        goldStar.setVisibility(View.INVISIBLE);

        line1 = (TextView) findViewById(R.id.line1);
        line2 = (TextView) findViewById(R.id.line2);
        line3 = (TextView) findViewById(R.id.line3);


        addListenersOnLines();
        addListenersOnButtons();

        // handle enter click as space
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (source.charAt(i) == '\n') {

                        return " ";
                    }
                }
                return null;
            }
        };

        haikuPost.setFilters(new InputFilter[]{filter});



        type1 = Typeface.createFromAsset(getAssets(), "fonts/basic.ttf");
        type2 = Typeface.createFromAsset(getAssets(), "fonts/fancy.ttf");
        type3 = Typeface.createFromAsset(getAssets(), "fonts/hacker.ttf");
        type4 = Typeface.createFromAsset(getAssets(), "fonts/playful.ttf");
        type5 = Typeface.createFromAsset(getAssets(), "fonts/comic.ttf");

        // set deafault fonts
        line1.setTypeface(type1);
        line2.setTypeface(type1);
        line3.setTypeface(type1);

        quote.setTypeface(type2);

        starWordView.setTypeface(type1);
        starWordView.setTextColor(Color.BLUE);


        message.setTypeface(type2);

        // set star word
        generateStarWord();

        // set up syllable library
        try {
            lawrence = new Lawrence();



        } catch (IOException ie) {

            ie.printStackTrace();
        }


        tw = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {


                syllCount = (TextView) findViewById(R.id.syllCount);

                // list of star words used in this line
                ArrayList<String> usedWords = linesToStarWords.get(currentLine);


                int syllables = 0;


                String str = s.toString();
                syllCount.setTextColor(Color.RED);

                if (str.trim().length() <= 0) {
                    syllables = 0;
                    syllCount.setText(Integer.toString(syllables));
                }

                if (str.length() > 0) {


                    String lettersOnly = str.replaceAll("[^a-zA-Z ]", "").toLowerCase();
                    String[] words = lettersOnly.split(" ");


                    // ensure no used star words have been erased
                    int j = 0;


                    while (j < usedWords.size()) {

                        boolean found = false;
                        int k = 0;
                        while (k < words.length) {
                            // check if the previously used starword is in this word
                            if (words[k].contains(usedWords.get(j))) {

                                found = true;
                                break;
                            }

                            k++;
                        }
                        if (!found) {
                            decrementStarCount();
                            // this starword is no longer used in this line
                            usedWords.remove(j);
                            // update map
                            linesToStarWords.put(currentLine, usedWords);
                        }
                        found = false;
                        j++;
                    }


                    int i = 0;
                    syllables = 0;

                    while (i < words.length) {

                        // count syllables in each word in line


                        if (words[i].trim().length() > 0) {   // if there is a word (not
                            //just whitepaces) count syllables
                            int c = lawrence.getSyllable(words[i].trim());
                            syllables = syllables + c;
                        }



                        // check if star word is in line

                        if (words[i].contains(starWord)) {


                            // update map of star words used per line
                            usedWords = linesToStarWords.get(currentLine);


                            usedWords.add(words[i]);

                            linesToStarWords.put(currentLine, usedWords);

                            // generate new star word
                            generateStarWord();

                            // increment star count
                            incrementStarCount();

                            break;
                        }

                        i++;
                    }

                    syllCount.setText(Integer.toString(syllables));


                    if (syllables == LINE1_SYLCOUNT && currentLine == 1) {

                        syllCount.setTextColor(Color.BLUE);

                        if (str.charAt(str.length() - 1) == ' ') {

                            // display if space or enter is pressed
                            line1.setText(s);

                            // clear
                            haikuPost.removeTextChangedListener(tw);
                            haikuPost.setText("");
                            haikuPost.addTextChangedListener(tw);


                            currentLine++;
                            syllCount.setText("0");
                            syllCount.setTextColor(Color.RED);

                        }

                    } else if (syllables == LINE2_SYLCOUNT && currentLine == 2) {

                        syllCount.setTextColor(Color.BLUE);

                        if (str.charAt(str.length() - 1) == ' ') {
                            // display if space or enter is pressed
                            line2.setText(s);

                            // clear
                            haikuPost.removeTextChangedListener(tw);
                            haikuPost.setText("");
                            haikuPost.addTextChangedListener(tw);


                            currentLine++;
                            syllCount.setText("0");
                            syllCount.setTextColor(Color.RED);


                        }
                    } else if (syllables == LINE3_SYLCOUNT && currentLine == 3) {

                        syllCount.setTextColor(Color.BLUE);

                        if (str.charAt(str.length() - 1) == ' ') {
                            // display if space or enter is pressed
                            line3.setText(s);

                            // put all lines together
                            buildHaiku();

                            // clear
                            haikuPost.removeTextChangedListener(tw);
                            haikuPost.setText("");
                            haikuPost.addTextChangedListener(tw);

                            timerButton.setVisibility(View.GONE);


                            syllCount.setText("0");
                            syllCount.setTextColor(Color.RED);


                            // hide input field and syllable count view
                            haikuPost.setEnabled(false);
                            haikuPost.setVisibility(View.INVISIBLE);
                            syllCount.setVisibility(View.INVISIBLE);

                            // display publish button
                            publishButton.setVisibility(View.VISIBLE);
                            deleteButton.setText("SCRAP IT");
                            deleteButton.setVisibility(View.VISIBLE);
                            copy.setVisibility(View.VISIBLE);


                            // check if beat time battle
                            if (timeBattle) {  // true if in time battle and time not up
                                // show gold star
                                beatTimeBattle = true;
                                goldStar.setVisibility(View.VISIBLE);

                                // stop timer
                                countdown.setText("");


                            }


                        }
                    }
                }
            }

        };

        haikuPost.addTextChangedListener(tw);


        // TODO Auto-generated method stub


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

    public void addListenersOnButtons() {


        deleteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                deleteCurrentHaiku();

                // haikuPost reappear
                haikuPost.setEnabled(true);
                haikuPost.setVisibility(View.VISIBLE);
                // syll count reappear
                syllCount.setVisibility(View.VISIBLE);
                // this and publish button dissappear
                publishButton.setVisibility(View.INVISIBLE);
                deleteButton.setVisibility(View.INVISIBLE);

                timerButton.setVisibility(View.VISIBLE);

                goldStar.setVisibility(View.INVISIBLE);
                copy.setVisibility(View.GONE);

                // reset
                restart();

                // if sent here by publish button click TEMP ******
                message.setVisibility(View.INVISIBLE);


            }

        });

        publishButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                publishButton.setVisibility(View.INVISIBLE);
                // display confirmation message
                message.setVisibility(View.VISIBLE);

                deleteButton.setText("WRITE ANOTHER");

            }
        });//closing the setOnClickListener method

        copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("haiku", haiku);
                clipboard.setPrimaryClip(clip);

                // notify user
                if (mToast != null) mToast.cancel();
                mToast = Toast.makeText(getApplicationContext(), "Copied haiku to clipboard!", Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                mToast.show();


            }
        });//closing the setOnClickListener method

        newWordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                generateStarWord();

            }
        });//closing the setOnClickListener method

        timerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // reset haiku
                restart();

                // start countdown timer
                timeBattle = true;
                new CountDownTimer(20000, 1000) {


                    public void onTick(long millisUntilFinished) {

                        // check if finished haiku

                        if (!beatTimeBattle) {

                            countdown.setText("seconds remaining: " + millisUntilFinished / 1000
                            );

                            // notify user of almost time up
                            if (millisUntilFinished / 1000 <= 5){
                              countdown.setTextColor(Color.RED);
                            };

                        } else {     // user finished haiku in time

                            this.cancel();
                        }
                    }

                    public void onFinish() {

                        if (!beatTimeBattle) {
                            countdown.setText("Times up!");
                            timeBattle = false;
                            // notify user of time up
                            if (mToast != null) mToast.cancel();
                            mToast = Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_LONG);
                            mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                            mToast.show();

                            // restart haiku
                            countdown.setText("");
                            restart();
                        }
                    }

                }.start();

            }
        });//closing the setOnClickListener method


    }

    private void addListenersOnLines() {

        line1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {



                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(MainActivity.this, publishButton);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {


                            String choice = item.getTitle().toString();

                            switch (choice) {
                                case "Basic Font":  // basic font
                                    line1.setTypeface(type1);
                                    line2.setTypeface(type1);
                                    line3.setTypeface(type1);
                                    break;


                                case "Fancy Font":  // fancy font
                                    line1.setTypeface(type2);
                                    line2.setTypeface(type2);
                                    line3.setTypeface(type2);


                                    break;


                                case "Hacker Font":  // hacker font
                                    line1.setTypeface(type3);
                                    line2.setTypeface(type3);
                                    line3.setTypeface(type3);


                                    break;

                                case "Fun Font": // playful font
                                    line1.setTypeface(type4);
                                    line2.setTypeface(type4);
                                    line3.setTypeface(type4);
                                    break;

                                case "Comic Font": // playful font
                                    line1.setTypeface(type5);
                                    line2.setTypeface(type5);
                                    line3.setTypeface(type5);
                                    break;


                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu


                }
                return true;

            }
        });

        line2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {



                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(MainActivity.this, publishButton);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {


                            String choice = item.getTitle().toString();

                            switch (choice) {
                                case "Basic Font":  // basic font
                                    line1.setTypeface(type1);
                                    line2.setTypeface(type1);
                                    line3.setTypeface(type1);
                                    break;


                                case "Fancy Font":  // fancy font
                                    line1.setTypeface(type2);
                                    line2.setTypeface(type2);
                                    line3.setTypeface(type2);


                                    break;


                                case "Hacker Font":  // hacker font
                                    line1.setTypeface(type3);
                                    line2.setTypeface(type3);
                                    line3.setTypeface(type3);


                                    break;

                                case "Fun Font": // playful font
                                    line1.setTypeface(type4);
                                    line2.setTypeface(type4);
                                    line3.setTypeface(type4);

                                    break;

                                case "Comic Font": // playful font
                                    line1.setTypeface(type5);
                                    line2.setTypeface(type5);
                                    line3.setTypeface(type5);
                                    break;


                            }

                            return true;
                        }
                    });

                    popup.show();//showing popup menu


                }

                return true;
            }
        });


        line3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {




                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(MainActivity.this, publishButton);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {


                            String choice = item.getTitle().toString();

                            switch (choice) {
                                case "Basic Font":  // basic font
                                    line1.setTypeface(type1);
                                    line2.setTypeface(type1);
                                    line3.setTypeface(type1);
                                    break;


                                case "Fancy Font":  // fancy font
                                    line1.setTypeface(type2);
                                    line2.setTypeface(type2);
                                    line3.setTypeface(type2);

                                    break;


                                case "Hacker Font":  // hacker font
                                    line1.setTypeface(type3);
                                    line2.setTypeface(type3);
                                    line3.setTypeface(type3);

                                    break;

                                case "Fun Font": // playful font
                                    line1.setTypeface(type4);
                                    line2.setTypeface(type4);
                                    line3.setTypeface(type4);
                                    break;

                                case "Comic Font": // comic sans font
                                    line1.setTypeface(type5);
                                    line2.setTypeface(type5);
                                    line3.setTypeface(type5);
                                    break;


                            }


                            return true;
                        }
                    });

                    popup.show();//showing popup menu



                }
                return true;
            }
        });


    }


    private void deleteCurrentHaiku() {

        // erase haiku lines
        line1.setText("");
        line2.setText("");
        line3.setText("");

        currentLine = 1;


    }


    private void generateStarWord() {
        int index = randomGenerator.nextInt(starWords.size());
        starWord = starWords.get(index);




        starWordView.setText(starWord);

    }

    private void restart() {
        deleteCurrentHaiku();

        haikuPost.setText("");

        beatTimeBattle = false;
        timeBattle = false;

        starCount = 0;
        starCountView.setText(Integer.toString(starCount));

        linesToStarWords.clear();
        linesToStarWords.put(1, new ArrayList<String>());
        linesToStarWords.put(2, new ArrayList<String>());
        linesToStarWords.put(3, new ArrayList<String>());

        // get a new welcome quote
        setWelcomeQuote();


        generateStarWord();

    }

    private void incrementStarCount() {
        starCount++;
        starCountView.setText(Integer.toString(starCount));
    }

    private void decrementStarCount() {
        starCount--;
        starCountView.setText(Integer.toString(starCount));
    }

    private void buildHaiku() {
        haikuBuilder = new StringBuilder();
        haikuBuilder.append(line1.getText());
        haikuBuilder.append("\n");
        haikuBuilder.append(line2.getText());
        haikuBuilder.append("\n");
        haikuBuilder.append(line3.getText());


        haiku = haikuBuilder.toString();

    }

    private void setWelcomeQuote() {
        Random generator = new Random();
        int i = generator.nextInt(4) + 1;

        if (i == 1) {
            quote.setText(getResources().getString(R.string.quote1));
        } else if (i == 2) {

            quote.setText(getResources().getString(R.string.quote2));
        } else {
            quote.setText(getResources().getString(R.string.quote3));


        }

    }
}
