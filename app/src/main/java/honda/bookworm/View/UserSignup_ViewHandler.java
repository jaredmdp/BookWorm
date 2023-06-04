package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.honda.bookworm.R;

import honda.bookworm.Business.AccessUsers;

public class UserSignup_ViewHandler extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox isAuthorCheckbox;
    private AccessUsers accessUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        firstNameEditText= findViewById(R.id.signup_firstname_input);
        lastNameEditText = findViewById(R.id.signup_lastname_input);
        usernameEditText = findViewById(R.id.signup_username_input);
        passwordEditText = findViewById(R.id.signup_password_input);
        isAuthorCheckbox = findViewById(R.id.signup_is_author_checkbox);

        accessUsers = new AccessUsers();
    }


    private void processUserInput(String firstName, String lastName, String username, String password, boolean isAuthor) {
        /*
         - take in provided input
         - validate the input
         - provide proper feedback to user via "Toasts" if excpetion thrown
            - if exception resetSignUpFeilds
         - proceed to next page
        */
        boolean signUpState = false;
        String msg = "";

        try {
            accessUsers.addNewUser(firstName, lastName, username, password, isAuthor);
            signUpState = true;
        } catch (IllegalStateException e) {
            msg = String.format(("Invalid Sign-up: %s"), e.getMessage());
        }

        //redirect if signup to Homepage if successful
        if (signUpState) {
            Intent intent = new Intent (this, Home_ViewHandler.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    private void resetSignUpFields() {
        emptyEditText(firstNameEditText);
        emptyEditText(lastNameEditText);
        emptyEditText(usernameEditText);
        emptyEditText(passwordEditText);

        isAuthorCheckbox.setChecked(false);
        switchUserandAuthorImage(false);
    }



    private void emptyEditText(EditText et) {
        if (et != null) {
            et.setText("");
        }
    }


    /* **** CLICK LISTENERS BELOW **** */

    public void isSignupButtonClicked(View view) {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isAuthor = isAuthorCheckbox.isChecked();
        processUserInput(firstName,lastName,username,password,isAuthor);
    }

    public void isAuthorCheckBoxClicked(View view) {
        if (view != null && view instanceof CheckBox) {
            boolean isAuthor = ((CheckBox) (view)).isChecked();
            switchUserandAuthorImage(isAuthor);
        }
    }

    private void switchUserandAuthorImage(boolean result) {
        ImageView userImage = findViewById(R.id.signup_user_img);
        ImageView authorImage = findViewById(R.id.signup_author_img);

        if (result) {
            userImage.setVisibility(View.GONE);
            authorImage.setVisibility(View.VISIBLE);
        } else {
            authorImage.setVisibility(View.GONE);
            userImage.setVisibility(View.VISIBLE);
        }
    }

}