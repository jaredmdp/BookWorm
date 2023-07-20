package honda.bookworm.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Book_horizontalscroll_constructor;
import honda.bookworm.View.Extra.GenreFavoritingConstructor;

public class UserProfile_ViewHandler extends AppCompatActivity {
    public final static String REQUEST_CODE = "requestUser";
    private IAccessBooks accessBooks;
    private IUserManager userManager;
    private IUserPreference userPreference;

    private User activeUser;
    GenreFavoritingConstructor genrefavouriter;
    View authorWrittenBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view_handler);
        String requestedUser;
        Bundle bundle = getIntent().getExtras();

        accessBooks = new AccessBooks();
        userManager = new UserManager();
        userPreference = new UserPreference();

        try{
            requestedUser = bundle.getString(REQUEST_CODE);
            IAccessUsers accessUsers = new AccessUsers();
            activeUser = accessUsers.fetchUser(requestedUser);
            setup();
        }catch (UserNotFoundException unfe){
            //this is a failsafe and should ideally never happen
            Toast.makeText(this,unfe.getMessage(),Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }catch (NullPointerException e) {
            activeUser = userManager.getActiveUser();
            setup();
        }
    }

    private void setup() {
        if (!userManager.isUserLoggedIn() ||
                !activeUser.getUsername().equals(userManager.getActiveUser().getUsername())) {
            findViewById(R.id.userprofile_edit_toggle).setVisibility(View.GONE);
        }

        setUserInfo();
        setUpGenreFavoriting();
        buildFavoriteBooks();
        buildWrittenBooks();
    }

    private void setUserInfo() {
        TextView userFullName = findViewById(R.id.userprofile_user_fullname);
        TextView userName = findViewById(R.id.userprofile_user_username);

        String username;
        String fullName;

        if (activeUser != null) {
            username = activeUser.getUsername();
            fullName = activeUser.getFirstName() + " " + activeUser.getLastName();
            userFullName.setText(fullName);
            userName.setText("@" + username);
        }
    }

    private void buildWrittenBooks() {
        List<Book> writtenBooks;
        LinearLayout linearBody = findViewById(R.id.userprofile_linear_content_body);
        String fullName;

        if (activeUser instanceof  Author) {
            Author author = (Author) activeUser;
            writtenBooks = accessBooks.getAuthorIDBookList(author.getAuthorID());
            authorWrittenBooks = Book_horizontalscroll_constructor.create(UserProfile_ViewHandler.this, "Books by you", writtenBooks);
            linearBody.addView(authorWrittenBooks);

            if (writtenBooks.isEmpty()) {
                fullName = activeUser.getFirstName() + " " + activeUser.getLastName();
                TextView tv = authorWrittenBooks.findViewById(R.id.books_horizontal_msg_on_empty);
                tv.setVisibility(View.VISIBLE);
                tv.setText("We dont have any books written by "+fullName+" in our record.");
            }
        }

    }

    private void buildFavoriteBooks() {
        List<Book> favoriteBooks;
        LinearLayout linearBody = findViewById(R.id.userprofile_linear_content_body);

        favoriteBooks = userPreference.getFavoriteBookList(activeUser);

        View horizontalScrollContainer = Book_horizontalscroll_constructor.create(UserProfile_ViewHandler.this,"Favorite Books",favoriteBooks);
        linearBody.addView(horizontalScrollContainer);

        if(favoriteBooks.isEmpty()){
            TextView tv = horizontalScrollContainer.findViewById(R.id.books_horizontal_msg_on_empty);
            tv.setVisibility(View.VISIBLE);
            tv.setText("No favorite books found. Try favoriting the books that catches you attention and try comming back");
        }
    }

    public void HorizontalViewButtonClicked(View view) {
        if (userManager.isAuthorActive()) {
            Intent intent = new Intent(this, AddBook_ViewHandler.class);
            startActivity(intent);
        }
    }

    private void addBookButtonToggle(int visibilityModifier) {
        ImageButton buttonView = authorWrittenBooks.findViewById(R.id.books_horizontal_button);
        buttonView.setVisibility(visibilityModifier);
    }


    private void setUpGenreFavoriting() {
        genrefavouriter = new GenreFavoritingConstructor(this, userPreference, activeUser);
        LinearLayout parent = findViewById(R.id.userprofile_linear_content_body);
        parent.addView(genrefavouriter.getView());
    }

    public void editProfileClicked(View view) {
        if(view instanceof  ToggleButton) {
            ToggleButton editButton = (ToggleButton) view;
            if(editButton.isChecked()){
                genrefavouriter.enterEditMode();
                if(activeUser instanceof  Author)
                    addBookButtonToggle(View.VISIBLE);
            }else{
                genrefavouriter.exitEditMode();
                if(activeUser instanceof  Author)
                    addBookButtonToggle(View.GONE);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}