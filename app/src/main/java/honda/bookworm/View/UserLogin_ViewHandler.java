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


    private void proceedToLogin(){
        EditText usernameInput = (EditText) findViewById(R.id.userlogin_username_input);
        EditText passwordInput = (EditText) findViewById(R.id.userlogin_password_input);

        String msg = String.format("Username: %s \nPassword: %s", usernameInput.getText(),
                passwordInput.getText()); //TO REMOVE

        //toast makes the popup notification on the screen
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show(); // TO REMOVE

        /*TO DO
            - input validation
            - show appropriate msg if error
            - move to next Activity
         */
    }


    public void userLoginButtonClicked(View view) {
        proceedToLogin();
    }

    public void userSignUpLinkClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), UserSignup_ViewHandler.class);
        startActivity(intent);
    }
}