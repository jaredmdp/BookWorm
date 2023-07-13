package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.honda.bookworm.R;

import honda.bookworm.Business.Exceptions.Users.InvalidPasswordException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Exceptions.*;

public class UserLogin_ViewHandler extends AppCompatActivity {

    private IAccessUsers accessUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        accessUsers = new AccessUsers();

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

    private void proceedToLogin(String username, String password) {
        try {
            accessUsers.verifyUser(username, password);

            Intent intent = new Intent(this, Home_ViewHandler.class);
            startActivity(intent);
            finishAffinity();

        } catch (UserNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        } catch (InvalidPasswordException e) {
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }
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