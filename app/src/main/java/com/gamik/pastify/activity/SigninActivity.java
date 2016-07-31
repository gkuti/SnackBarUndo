package com.gamik.pastify.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gamik.pastify.R;
import com.gamik.pastify.util.Validator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    TextInputLayout confrimPasswordInputLayout, emailInputLayout, passwordInputLayout, passwordRecoveryInputLayout;
    EditText emailEditText, passwordEditText, confirmPasswordEditText, passwordRecoveryEditText;
    Button signInButton, signUpButton;
    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signInButton = (Button) findViewById(R.id.bt_sign_in);
        signUpButton = (Button) findViewById(R.id.bt_sign_up);
        emailInputLayout = (TextInputLayout) findViewById(R.id.til_email);
        confrimPasswordInputLayout = (TextInputLayout) findViewById(R.id.til_confirm_password);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.til_password);
       // passwordRecoveryInputLayout = (TextInputLayout) findViewById(R.id.til_password_recovery);
        emailEditText = (EditText) findViewById(R.id.et_email);
        passwordEditText = (EditText) findViewById(R.id.et_password);
        confirmPasswordEditText = (EditText) findViewById(R.id.et_confirm_password);
        //passwordRecoveryEditText = (EditText) findViewById(R.id.et_password_recovery);
        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));
        confirmPasswordEditText.addTextChangedListener(new MyTextWatcher(confirmPasswordEditText));
        //passwordRecoveryEditText.addTextChangedListener(new MyTextWatcher(passwordRecoveryEditText));
        //linearLayout = (LinearLayout) findViewById(R.id.ll_password_recovery);
        TextView textView = (TextView) findViewById(R.id.textView3);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"arial_rounded_bold.ttf");
        textView.setTypeface(typeface);
    }

    public void signUp(View view) {
        if (mode == 0) {
            YoYo.with(Techniques.Landing).duration(1000).playOn(findViewById(R.id.ll_form));
            confrimPasswordInputLayout.setVisibility(View.VISIBLE);
            //linearLayout.setVisibility(View.VISIBLE);
            signUpButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            signUpButton.setTextColor(getResources().getColor(R.color.white));
            signInButton.setTextColor(getResources().getColor(R.color.colorAccent));
            signInButton.setBackgroundColor(getResources().getColor(R.color.whitish));
            mode = 1;
        }
    }

    public void signIn(View view) {
        if (mode == 1) {
            YoYo.with(Techniques.Landing).duration(1000).playOn(findViewById(R.id.ll_form));
            confrimPasswordInputLayout.setVisibility(View.GONE);
            //linearLayout.setVisibility(View.GONE);
            signInButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            signInButton.setTextColor(getResources().getColor(R.color.white));
            signUpButton.setTextColor(getResources().getColor(R.color.colorAccent));
            signUpButton.setBackgroundColor(getResources().getColor(R.color.whitish));
            mode = 0;
        } else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        //signed in
                    } else {

                    }
                }
            };
            //Firebase firebase = new Firebase(Constant.FIREBASE_URL);
//            firebaseAuth.createUserWithEmailAndPassword("kuti.gbolahan@andela.com", "gamik1")
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d("Pastify", "createUserWithEmail:onComplete:" + task.isSuccessful());
//                            // If sign in fails, display a message to the user. If sign in succeeds
//                            // the auth state listener will be notified and logic to handle the
//                            // signed in user can be handled in the listener.
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(SigninActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//
//                            // ...
//                        }
//                    });

        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "kuti.gbolahan@andela.com";

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Pastify", "Email sent.");
                        }
                    }
                });
    }

    private boolean validateEmail() {
        if (emailEditText.getText().toString().trim().isEmpty()) {
            emailInputLayout.setError("Enter email address");
            return false;
        } else if (!Validator.isValidEmail(emailEditText.getText().toString())) {
            emailInputLayout.setError("Invalid email address");
            return false;
        }
        emailInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword() {
        if (passwordEditText.getText().toString().trim().isEmpty() || passwordEditText.getText().toString().trim().length() < 6) {
            passwordInputLayout.setError("Password is too short");
            return false;
        } else if (!Validator.isValidPassword(passwordEditText.getText().toString())) {
            passwordInputLayout.setError("Most contain letters and numbers without spaces");
            return false;
        }
        passwordInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validateConfirmPassword() {
        if (confirmPasswordEditText.getText().toString().trim().isEmpty() || !confirmPasswordEditText.getText().toString().trim().equals(passwordEditText.getText().toString().trim())) {
            confrimPasswordInputLayout.setError("Password does not match");
            return false;
        }
        confrimPasswordInputLayout.setErrorEnabled(false);
        return true;
    }

    private boolean validatePasswordRecovery() {
        if (passwordRecoveryEditText.getText().toString().trim().isEmpty()) {
            passwordRecoveryInputLayout.setError("Answer cannot be empty");
            return false;
        }
        passwordRecoveryInputLayout.setErrorEnabled(false);
        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            validator();
        }

        public void afterTextChanged(Editable editable) {
            validator();
        }

        void validator() {
            switch (view.getId()) {
                case R.id.et_email:
                    validateEmail();
                    break;
                case R.id.et_confirm_password:
                    validateConfirmPassword();
                    break;
                case R.id.et_password:
                    validatePassword();
                    break;
//                case R.id.et_password_recovery:
//                    validatePasswordRecovery();
//                    break;
            }
        }
    }
}
