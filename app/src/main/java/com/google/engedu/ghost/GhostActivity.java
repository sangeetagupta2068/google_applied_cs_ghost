/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class GhostActivity extends AppCompatActivity {

    private GhostDictionary dictionary;
    private boolean userTurn, computerMove;
    private Random random;
    int countUser, countComputer;
    AssetManager assetManager;
    String temp;

    Button button, button2, button3;
    TextView textView, textView2, textView3;
    EditText editText;

    public void initialize() {

        random = new Random();
        userTurn = true;
        computerMove = true;
        countUser = 0;
        countComputer = 0;
        button = findViewById(R.id.ghost_user_button);
        button2 = findViewById(R.id.ghost_user_challenge);
        button3 = findViewById(R.id.ghost_user_finish);
        textView = findViewById(R.id.ghostText);
        textView2 = findViewById(R.id.ghostScore);
        textView3 = findViewById(R.id.computerScore);
        editText = findViewById(R.id.ghost_edit_text);


        try {

            assetManager = getAssets();
            dictionary = new SimpleDictionary(getAssets().open("words.txt"));
        } catch (Exception exception) {

            exception.printStackTrace();
        }
    }

    public void setListeners() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validation()) {

                    temp = textView.getText().toString() + editText.getText().toString();
                    textView.setText(temp);
                    editText.setText("");
                    scoreGenerator();
                    computerPlay();

                }

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp = textView.getText().toString();
                Toast.makeText(GhostActivity.this, "You challenged computer!", Toast.LENGTH_SHORT).show();
                if (!(dictionary.getAnyWordStartingWith(temp).equals(null) || dictionary.getAnyWordStartingWith(temp).equals(""))) {

                    textView.setText(dictionary.getAnyWordStartingWith(temp));
                    countComputer = countComputer + 1;
                    textView3.setText("Computer Score : " + countComputer);

                } else {
                    countUser = countUser + 1;
                    textView2.setText("Your Score : " + countUser);
                    textView.setText("");
                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countUser > countComputer) {
                    Toast.makeText(GhostActivity.this, "You win!", Toast.LENGTH_SHORT).show();
                } else {
                    if (countComputer == countUser) {
                        Toast.makeText(GhostActivity.this, "Draw!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GhostActivity.this, "You lose!", Toast.LENGTH_SHORT).show();
                    }
                }

                textView.setText("");
                textView2.setText("Your Score : 0");
                textView3.setText("Computer Score : 0");
            }
        });
    }

    public void scoreGenerator() {

        if (dictionary.isWord(temp)) {

            if (!(userTurn)) {
                countUser = countUser + 1;
                textView2.setText("Your Score : " + countUser);
            } else {
                countComputer = countComputer + 1;
                textView3.setText("Computer Score : " + countComputer);
            }

        }
    }

    public void computerPlay() {

        String alpha = "abcdefghijklmnopqrstuvwxyz";
        random = new Random();

        char computerValue = alpha.charAt(random.nextInt(alpha.length()));
        computerMove = random.nextBoolean();
        if (computerMove || textView.getText().toString().length() <= 2) {
            textView.setText(textView.getText().toString() + computerValue);
            scoreGenerator();
        } else {
            Toast.makeText(GhostActivity.this, "Computer Challenged you!", Toast.LENGTH_SHORT).show();
            if (dictionary.getAnyWordStartingWith(temp).equals(null) || dictionary.getAnyWordStartingWith(temp).equals("")) {

                textView.setText(dictionary.getAnyWordStartingWith(temp));
                countComputer = countComputer + 1;
                textView3.setText("Computer Score : " + countComputer);

            } else {
                countUser = countUser + 1;
                textView2.setText("Your Score : " + countUser);
                textView.setText("");
            }

        }
        userTurn = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        initialize();
        setListeners();
    }

    public boolean validation() {

        boolean value = false;

        String val = editText.getText().toString().toLowerCase();

        if (val.length() == 1 && (val.charAt(0) >= 'a' && val.charAt(0) <= 'z')) {

            value = true;

        } else {
            Toast.makeText(GhostActivity.this, "Enter only one alphabet at a time", Toast.LENGTH_SHORT).show();
            editText.setText("");

        }


        return value;

    }

}
