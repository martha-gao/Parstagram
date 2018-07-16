package me.martha_gao.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText createUsername;
    private EditText createPassword;
    private Button createuserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createUsername = findViewById(R.id.etNewUser);
        createPassword = findViewById(R.id.etNewPass);
        createuserButton = findViewById(R.id.btCreateUser);

        createuserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = new ParseUser();
                user.setUsername(createUsername.getText().toString());
                user.setPassword(createPassword.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SignupActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Toast.makeText(SignupActivity.this, "Sign-up failed: try a different username?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}