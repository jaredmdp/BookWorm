package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.honda.bookworm.R;

public class UserSignup_ViewHandler extends AppCompatActivity {

    private final EditText FIRST_NAME = findViewById(R.id.signup_firstname_input);
    private final EditText LAST_NAME = findViewById(R.id.signup_lastname_input);
    private final EditText USER_NAME = findViewById(R.id.signup_username_input);
    private final EditText PASSWORD = findViewById(R.id.signup_password_input);
    private final CheckBox IS_AUTHOR_CHECK = findViewById(R.id.signup_is_author_checkbox);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
    }


    private void processUserInput(String firstName, String lastName, String username, String password, boolean isAuthor) {
        /*TO-DO:
         - take in provided input
         - validate the input
         - provide proper feedback to user via "Toasts" if excpetion thrown
            - if exception resetSignUpFeilds
         - proceed to next page
        */
    }

    private void resetSignUpFields() {
        emptyEditText(FIRST_NAME);
        emptyEditText(LAST_NAME);
        emptyEditText(USER_NAME);
        emptyEditText(PASSWORD);

        IS_AUTHOR_CHECK.setChecked(false);
        switchUserandAuthorImage(false);
    }



    private void emptyEditText(EditText et) {
        if (et != null) {
            et.setText("");
        }
    }


    /* **** CLICK LISTENERS BELOW **** */

    public void isSignupButtonClicked(View view) {
        String firstName = FIRST_NAME.getText().toString();
        String lastName = LAST_NAME.getText().toString();
        String username = USER_NAME.getText().toString();
        String password = PASSWORD.getText().toString();
        boolean isAuthor = IS_AUTHOR_CHECK.isChecked();
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