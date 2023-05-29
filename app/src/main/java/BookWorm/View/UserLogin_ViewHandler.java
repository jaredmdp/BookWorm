package BookWorm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.honda.bookworm.R;

public class UserLogin_ViewHandler extends AppCompatActivity {

    private EditText userNameInput;
    private EditText passwordInput;

    private Button loginButton;
    private TextView linkToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //get reference to layout components
        userNameInput = findViewById(R.id.userlogin_username_input);
        passwordInput = findViewById(R.id.userlogin_password_input);
        loginButton = findViewById(R.id.userlogin_login_button);
        linkToSignup = findViewById(R.id.userlogin_signup_link);

        stylizeLink();
    }

    private void stylizeLink(){
        linkToSignup.setPaintFlags(linkToSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }



}