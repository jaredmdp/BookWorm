package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;
import honda.bookworm.View.Extra.Book_horizontalscroll_constructor;
import honda.bookworm.View.Extra.GenreFavoritingConstructor;

public class UserProfile_ViewHandler extends AppCompatActivity {
    private IAccessUsers accessUsers;
    private IAccessBooks accessBooks;
    private IUserManager userManager;
    private IUserPreference userPreference;
    private ISearchManager searchManager;
    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_view_handler);

        accessUsers = new AccessUsers();
        accessBooks = new AccessBooks();
        userManager = new UserManager();
        userPreference = new UserPreference();
        searchManager = new SearchManager();

        activeUser = userManager.getActiveUser();

        setUserInfo();
        setUpGenreFavoriting();
        buildFavoriteBooks();
        buildWrittenBooks();
    }


    private void setUserInfo(){
        TextView userFullName = findViewById(R.id.userprofile_user_fullname);
        TextView userName = findViewById(R.id.userprofile_user_username);

        String username;
        String fullName;

        if (activeUser != null) {
            username = activeUser.getUsername();
            fullName = activeUser.getFirstName() + " " + activeUser.getLastName();
            userFullName.setText(fullName);
            userName.setText("@"+username);
        }
    }

    private void buildWrittenBooks(){
        List<Book> writtenBooks;
        LinearLayout linearBody = findViewById(R.id.userprofile_linear_content_body);

      if(userManager.isAuthorActive()){
            User author = userManager.getActiveUser();
            writtenBooks = searchManager.performSearchAuthor(author.getUsername());

            if(!writtenBooks.isEmpty()){
                View horizontalScrollContainer = Book_horizontalscroll_constructor.create(UserProfile_ViewHandler.this,"Books by you",writtenBooks);
                linearBody.addView(horizontalScrollContainer);
                setButtonVisible(horizontalScrollContainer);
            }
       }
        
    }

    private void buildFavoriteBooks(){
        List<Book> favoriteBooks;
        LinearLayout linearBody = findViewById(R.id.userprofile_linear_content_body);

            favoriteBooks = accessBooks.getFavoriteBookList(activeUser);

            if(!favoriteBooks.isEmpty()){
                View horizontalScrollContainer = Book_horizontalscroll_constructor.create(UserProfile_ViewHandler.this,"Favorite Books",favoriteBooks);
                linearBody.addView(horizontalScrollContainer);

            }
    }

    public void HorizontalViewButtonClicked(View view){
        if(userManager.isAuthorActive()){
            Intent intent = new Intent(this,AddBook_ViewHandler.class);
            startActivity(intent);
        }
    }

    private void setButtonVisible(View view){
        ImageButton buttonView = view.findViewById(R.id.books_horizontal_button);
        buttonView.setVisibility(View.VISIBLE);
    }


    private void setUpGenreFavoriting() {
       GenreFavoritingConstructor genrefavouriter = new GenreFavoritingConstructor(this,userPreference,activeUser);
        LinearLayout parent = findViewById(R.id.userprofile_linear_content_body);
        parent.addView(genrefavouriter .getView());
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}