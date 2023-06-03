package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.honda.bookworm.R;

public class UserLogin_ViewHandler extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        stylizeLink();
    }

    private void stylizeLink() {
        TextView linkToSignup = findViewById(R.id.userlogin_signup_link);
        linkToSignup.setPaintFlags(linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void userLoginButtonClicked(View view) {
        EditText usernameInput = (EditText) findViewById(R.id.userlogin_username_input);
        EditText passwordInput = (EditText) findViewById(R.id.userlogin_password_input);
        proceedToLogin(usernameInput.getText().toString(),passwordInput.getText().toString());
    }

    private void proceedToLogin(String username, String password){
        /*TO DO:
            - get input validation from the userVerification Business layer
            - show appropriate msg if error(throws exception) using Toast (toast structure present below)
                - also call resetLoginFields
            - move to next Activity (home) if no exception thrown
         */

        String msg = String.format("Username: %s \nPassword: %s", username, password);
        //toast makes the popup notification on the screen
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    private void resetLoginFeilds(){
        EditText usernameInput = (EditText) findViewById(R.id.userlogin_username_input);
        EditText passwordInput = (EditText) findViewById(R.id.userlogin_password_input);
        usernameInput.setText("");
        passwordInput.setText("");
    }


    public void userSignUpLinkClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), UserSignup_ViewHandler.class);
        startActivity(intent);
    }
}