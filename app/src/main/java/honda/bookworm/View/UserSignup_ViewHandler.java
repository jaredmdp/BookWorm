package honda.bookworm.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.honda.bookworm.R;

import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Object.Factories.IUserFactory;
import honda.bookworm.Object.Factories.UserFactory;
import honda.bookworm.Object.User;

public class UserSignup_ViewHandler extends AppCompatActivity {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox isAuthorCheckbox;
    private IAccessUsers accessUsers;
    private IUserFactory userFactory;

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
        userFactory = new UserFactory();
    }


    private void processUserInput(String firstName, String lastName, String username, String password, boolean isAuthor) {
        boolean signUpState = false;
        String message = "";

        try {
            accessUsers.addNewUser(userFactory.make(firstName, lastName, username, password, isAuthor ? "Author":"User"));
            signUpState = true;
        } catch (UserException e) {
            message = e.getMessage();
        }

        //redirect to Homepage if signup was successful
        if (signUpState) {
            Intent intent = new Intent (this, Home_ViewHandler.class);
            startActivity(intent);
            finishAffinity();
        } else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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