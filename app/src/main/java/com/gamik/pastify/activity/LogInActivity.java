package com.gamik.pastify.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gamik.pastify.R;
import com.gamik.pastify.store.DataStore;

public class LogInActivity extends AppCompatActivity {
    private int clicks = 0;
    private String passcode = "";
    private ImageView fieldOne;
    private ImageView fieldTwo;
    private ImageView fieldThree;
    private ImageView fieldFour;
    private DataStore dataStore;
    private String userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        dataStore = new DataStore(this);
        userPass = dataStore.getData("passCode");
        fieldOne = (ImageView) findViewById(R.id.field_1);
        fieldTwo = (ImageView) findViewById(R.id.field_2);
        fieldThree = (ImageView) findViewById(R.id.field_3);
        fieldFour = (ImageView) findViewById(R.id.field_4);
        TextView textView = (TextView) findViewById(R.id.tv_login);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"century_gothic.ttf");
        textView.setTypeface(typeface);
    }

    public void numberClick(View view) {
        Button button = (Button) view;
        clicks++;
        passcode += button.getText();
        fillImage(clicks);
        if (clicks == 4) {
            checkPasscode();
        }
    }

    public void fillImage(int i) {
        switch (i) {
            case 1:
                fieldOne.setImageDrawable(getResources().getDrawable(R.drawable.shadow_filled_circle));
                break;
            case 2:
                fieldTwo.setImageDrawable(getResources().getDrawable(R.drawable.shadow_filled_circle));
                break;
            case 3:
                fieldThree.setImageDrawable(getResources().getDrawable(R.drawable.shadow_filled_circle));
                break;
            case 4:
                fieldFour.setImageDrawable(getResources().getDrawable(R.drawable.shadow_filled_circle));
                break;
        }
    }

    public void checkPasscode() {
        if (passcode.equals(userPass)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            restart();
        }
    }

    private void restart() {
        YoYo.with(Techniques.Shake).duration(750).playOn(findViewById(R.id.circle_frame_log));
        fieldOne.setImageDrawable(getResources().getDrawable(R.drawable.circle));
        fieldTwo.setImageDrawable(getResources().getDrawable(R.drawable.circle));
        fieldThree.setImageDrawable(getResources().getDrawable(R.drawable.circle));
        fieldFour.setImageDrawable(getResources().getDrawable(R.drawable.circle));
        clicks = 0;
        passcode = "";
    }
}
