package honda.bookworm.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Book_horizontalscroll_constructor;
import honda.bookworm.View.Extra.GenreFavoritingConstructor;

public class UserProfile_ViewHandler extends AppCompatActivity {

    private IAccessBooks accessBooks;
    private IUserManager userManager;
    private IUserPreference userPreference;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view_handler);

        accessBooks = new AccessBooks();
        userManager = new UserManager();
        userPreference = new UserPreference();

        activeUser = userManager.getActiveUser();

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

        if (userManager.isAuthorActive()) {
            Author author = (Author) userManager.getActiveUser();
            writtenBooks = accessBooks.getAuthorIDBookList(author.getAuthorID());
            View horizontalScrollContainer = Book_horizontalscroll_constructor.create(UserProfile_ViewHandler.this, "Books by you", writtenBooks);
            linearBody.addView(horizontalScrollContainer);
            setButtonVisible(horizontalScrollContainer);

            if (writtenBooks.isEmpty()) {
                TextView tv = horizontalScrollContainer.findViewById(R.id.books_horizontal_msg_on_empty);
                tv.setVisibility(View.VISIBLE);
                tv.setText("We dont have any books written by you in our record. Please feel free to add one of your books by pressing the + button.");
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

    private void setButtonVisible(View view) {
        ImageButton buttonView = view.findViewById(R.id.books_horizontal_button);
        buttonView.setVisibility(View.VISIBLE);
    }


    private void setUpGenreFavoriting() {
        GenreFavoritingConstructor genrefavouriter = new GenreFavoritingConstructor(this, userPreference, activeUser);
        LinearLayout parent = findViewById(R.id.userprofile_linear_content_body);
        parent.addView(genrefavouriter.getView());
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}