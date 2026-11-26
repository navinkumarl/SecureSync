package com.example.securesync;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securesync.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private DatabaseClass databaseClass;
    private ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());

        databaseClass=DatabaseClass.getInstance(this);

        activitySignUpBinding.signUpButton.setOnClickListener(view -> {
            checkValidation();
            if(!checkEmpty(activitySignUpBinding.username) && checkLength(activitySignUpBinding.mobileNo)
                    && checkEmail(activitySignUpBinding.email) && checkPassword(activitySignUpBinding.password)
                    && checkConfirmPassword(activitySignUpBinding.password,activitySignUpBinding.retypePassword)) {
                Toast.makeText(SignUp.this, "Sign Up Successful! Please Sign In", Toast.LENGTH_SHORT).show();
                //noinspection deprecation
                new InsertUserAsyncTask(databaseClass,activitySignUpBinding.username.getText().toString(),
                        activitySignUpBinding.email.getText().toString(),activitySignUpBinding.mobileNo.getText().toString(),
                        activitySignUpBinding.password.getText().toString()).execute();
                Intent myint=new Intent(SignUp.this, MainActivity.class);
                startActivity(myint);
            }
        });
        activitySignUpBinding.cancel.setOnClickListener(view -> showAlert());
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }
    public void showAlert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Go Back");
        builder.setMessage("Are you sure you want to go back?");
        builder.setPositiveButton("Yes",
                (dialogInterface, i) -> {
            Intent myint=new Intent(SignUp.this, MainActivity.class);
            startActivity(myint);
        });
        builder.setNegativeButton("No",(dialogInterface,i)->dialogInterface.dismiss());
        builder.setCancelable(false);
        builder.show();
    }
    public void checkValidation() {
        if(checkEmpty(activitySignUpBinding.username)) {
            activitySignUpBinding.username.setError("Empty User Name Field is Not Allowed");
        }
        if(checkEmpty(activitySignUpBinding.mobileNo)) {
            activitySignUpBinding.mobileNo.setError("Empty Mobile Number Field is Not Allowed");
        }
        else if(!checkLength(activitySignUpBinding.mobileNo)) {
            activitySignUpBinding.mobileNo.setError("Enter Valid Mobile Number");
        }
        if(checkEmpty(activitySignUpBinding.email)) {
            activitySignUpBinding.email.setError("Empty Email Field is Not Allowed");
        }
        else if(!checkEmail(activitySignUpBinding.email)) {
            activitySignUpBinding.email.setError("Enter Valid Email Address");
        }
        if(!checkPassword(activitySignUpBinding.password)) {
            activitySignUpBinding.password.setError("Enter Valid Password");
        }
        if(!checkPassword(activitySignUpBinding.retypePassword)) {
            activitySignUpBinding.retypePassword.setError("Enter Valid Password");
        }
        else if(!checkConfirmPassword(activitySignUpBinding.password,
                activitySignUpBinding.retypePassword)) {
            activitySignUpBinding.retypePassword.setError("Passwords Do Not Match");
        }
    }

    public  boolean checkEmpty(EditText text) {
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean checkLength(EditText text) {
        CharSequence str=text.getText().toString();
        return (str.length()==10);
    }

    public boolean checkEmail(EditText text) {
        CharSequence str=text.getText().toString();
        return (Patterns.EMAIL_ADDRESS.matcher(str).matches());
    }

    public static boolean isvalidPassword(String s) {
        Pattern password_pattern=Pattern.compile("[a-zA-Z0-9@#$_]{8,24}");
        return (password_pattern.matcher(s).matches());
    }

    public boolean checkPassword(EditText text) {
        String str=text.getText().toString();
        return (isvalidPassword(str));
    }

    public boolean checkConfirmPassword(EditText text1,EditText text2) {
        return (text1.getText().toString().equals(text2.getText().toString()));
    }

    private static class InsertUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private final DatabaseClass databaseClass;
        private final String name;
        private final String mail;
        private final String phone;
        private final String pass;

        /** @noinspection deprecation*/
        public InsertUserAsyncTask(DatabaseClass databaseClass,String username,String email,String mobile,String password) {
            this.databaseClass = databaseClass;
            this.name=username;
            this.mail=email;
            this.phone=mobile;
            this.pass=password;
        }

        /** @noinspection deprecation*/
        @Override
        protected Void doInBackground(Void... voids) {
            UserModel model=new UserModel();
            model.setName(name);
            model.setMail(mail);
            model.setPhone(phone);
            model.setPassword(pass);
            databaseClass.daoClass().registerUser(model);
            return null;
        }
    }
}