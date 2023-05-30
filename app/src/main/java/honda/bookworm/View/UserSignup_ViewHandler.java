package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.honda.bookworm.R;

public class UserSignup_ViewHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
    }


    private void processUserInput() {
        /*TO-DO:
         - take in provided input
         - validate the input
         - provide proper feedback to user via "Toasts" if something wrong
         - proceed to next page
        */
    }

    private void resetSignUpFields() {
        EditText firstName = findViewById(R.id.signup_firstname_input);
        EditText lastName = findViewById(R.id.signup_lastname_input);

        EditText userName = findViewById(R.id.signup_username_input);
        EditText password = findViewById(R.id.signup_password_input);

        CheckBox isAuthorCheckBox = findViewById(R.id.signup_is_author_checkbox);

        emptyEditText(firstName);
        emptyEditText(lastName);
        emptyEditText(userName);
        emptyEditText(password);

        isAuthorCheckBox.setChecked(false);
        switchUserandAuthorImage(false);
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


    private void emptyEditText(EditText et) {
        if (et != null) {
            et.setText("");
        }
    }


    /* **** CLICK LISTENERS BELOW **** */

    public void isSignupButtonClicked(View view) {
        processUserInput();
        resetSignUpFields();
    }

    public void isAuthorCheckBoxClicked(View view) {
        if (view != null && view instanceof CheckBox) {
            boolean isAuthor = ((CheckBox) (view)).isChecked();
            switchUserandAuthorImage(isAuthor);
        }
    }

}