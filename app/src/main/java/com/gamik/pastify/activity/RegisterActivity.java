package com.gamik.pastify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gamik.pastify.R;
import com.gamik.pastify.activity.LogInActivity;
import com.gamik.pastify.store.DataStore;

public class RegisterActivity extends AppCompatActivity {
    private int clicks = 0;
    private int turn = 1;
    private String passCode = "";
    private String previousCode = "";
    private TextView fieldOne;
    private TextView fieldTwo;
    private TextView fieldThree;
    private TextView fieldFour;
    private TextView label;
    private DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStore = new DataStore(this);
        if (dataStore.getData("reg").equals("yes")) {
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_register);
            fieldOne = (TextView) findViewById(R.id.reg_num_1);
            fieldTwo = (TextView) findViewById(R.id.reg_num_2);
            fieldThree = (TextView) findViewById(R.id.reg_num_3);
            fieldFour = (TextView) findViewById(R.id.reg_num_4);
            label = (TextView) findViewById(R.id.label);
        }
    }

    public void numberClick(View view) {
        Button button = (Button) view;
        clicks++;
        passCode += button.getText();
        fillImage(clicks, button.getText().toString());
        if (clicks == 4 && turn == 2) {
            checkPasscode();
        }
        if (clicks == 4) {
            previousCode = passCode;
            turn = 2;
            label.setText("Confirm your secret code");
            restart();
        }
    }

    public void fillImage(int i, String text) {
        switch (i) {
            case 1:
                fieldOne.setText(text);
                break;
            case 2:
                fieldTwo.setText(text);
                break;
            case 3:
                fieldThree.setText(text);
                break;
            case 4:
                fieldFour.setText(text);
                break;
        }
    }

    public void checkPasscode() {
        if (passCode.equals(previousCode)) {
            dataStore.saveData("passCode", passCode);
            dataStore.saveData("reg", "yes");
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();
        } else {
            restart();
        }
    }

    private void restart() {
        YoYo.with(Techniques.SlideInLeft).duration(500).playOn(findViewById(R.id.circle_frame));
        fieldOne.setText("");
        fieldTwo.setText("");
        fieldThree.setText("");
        fieldFour.setText("");
        clicks = 0;
        passCode = "";
    }
}
