package honda.bookworm.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.honda.bookworm.R;

import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidCommentException;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.ICommentManager;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.CommentManager;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Book;
import honda.bookworm.View.Extra.ImageConverter;

public class BookView_ViewHandler extends AppCompatActivity {

    private final int MIN = 7;
    private Book book;
    private IUserManager userManager;
    private IAccessBooks accessBooks;
    private IAccessUsers accessUsers;
    private ICommentManager commentManager;
    private IUserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        userManager = new UserManager();
        accessBooks = new AccessBooks();
        userPreference = new UserPreference();
        accessUsers = new AccessUsers();
        commentManager = new CommentManager();

        Bundle bookInfo = getIntent().getExtras();
        String bookISBN = bookInfo.getString("isbn");

        try{
            this.book = accessBooks.getBookByISBN(bookISBN);
        } catch(InvalidISBNException e)
        {
            //what do we want to happen
        }


        assignValues();
        applyHideOnScroll();
    }

    //need to figure out image
    private void assignValues() {
        TextView title = findViewById(R.id.book_view_book_title);
        TextView author = findViewById(R.id.book_view_book_author);
        TextView genre = findViewById(R.id.book_view_book_genre);
        TextView isbn = findViewById(R.id.book_view_book_isbn);
        TextView description = findViewById(R.id.book_view_book_description);
        TextView collapseButton = findViewById(R.id.book_view_book_description_toggle);
        ImageView coverView = findViewById(R.id.book_view_book_cover);

        title.setText(String.format("%s %s", title.getText(), book.getName()));
        author.setText(String.format("%s %s", author.getText(), book.getAuthor()));
        genre.setText(String.format("%s %s", genre.getText(), book.getGenre()));
        isbn.setText(String.format("%s %s", isbn.getText(), book.getISBN()));
        description.setText(String.format("%s %s", description.getText(), book.getDescription()));

        if (!book.getCover().equals("")) {
            coverView.setForeground(null);
            coverView.setImageBitmap(ImageConverter.DecodeToBitmap(book.getCover()));
        }

        description.post(new Runnable() {
            @Override
            public void run() {
                if (description.getLineCount() <= MIN) {
                    collapseButton.setVisibility(View.GONE);
                }
            }
        });


        createPurchaseButtonViewListener();
        setUpFavoriting();
    }

    private void createPurchaseButtonViewListener() {
        MaterialButton purchaseButton = findViewById(R.id.book_view_book_purchase_link);

        if (book.getPurchaseable()) {
            String url = String.format("https://www.amazon.ca/s?k=%s",
                    book.getName().toLowerCase().replaceAll(" ", "+"));

            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri webLink = Uri.parse(url);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webLink);
                    startActivity(webIntent);
                }
            });
        } else {
            purchaseButton.setVisibility(View.GONE);
        }
    }

    public void expandCollapseDescription(View v) {
        TextView toggleText = (TextView) v;
        TextView description = findViewById(R.id.book_view_book_description);
        LinearLayout floatContent = findViewById(R.id.book_view_floating_content);

        if (description.getMaxLines() <= MIN) {
            description.setMaxLines(description.getLineCount() + 1);
            toggleText.setText("[Collapse]");
        } else {
            description.setMaxLines(MIN);
            toggleText.setText("[Expand]");
            floatContent.setVisibility(View.VISIBLE);
        }

    }

    public void applyHideOnScroll() {
        LinearLayout hideOnScroll = findViewById(R.id.book_view_floating_content);
        ScrollView scrollView = findViewById(R.id.book_view_scrollview);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("Curr Y", "" + scrollY);
                Log.i("Old Y", "" + oldScrollY);
                if (scrollY > oldScrollY) {
                    hideOnScroll.setVisibility(View.GONE);
                } else {
                    hideOnScroll.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setUpFavoriting() {
        ToggleButton favButton = findViewById(R.id.book_view_fav_book_toggle);
        if (userManager.isUserLoggedIn()) {
            try{
                favButton.setVisibility(View.VISIBLE);
                favButton.setChecked(userPreference.isBookFavourite(userManager.getActiveUser(), book.getISBN()));
            } catch(UserException e){
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void isFavoriteClicked(View view) {
        try{
            ((ToggleButton) view).setChecked(userPreference.bookFavouriteToggle(book.getISBN()));
        }catch(UserException e){
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }

    public void onAuthorNameClicked(View view) {
        try{
            String username = accessUsers.fetchUsernameOfAuthor(book.getAuthorID());
            Intent userProfile = new Intent(this, UserProfile_ViewHandler.class);
            userProfile.putExtra(UserProfile_ViewHandler.REQUEST_CODE, username);
            startActivity(userProfile);
        }catch (AuthorNotFoundException e){
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }

    public void submitComment(View view){
        EditText commentInput = (EditText) findViewById(R.id.book_view_comment_input);

        try{
            commentManager.leaveComment(book.getISBN(), commentInput.getText().toString());
        } catch(UserException | InvalidCommentException | GeneralPersistenceException e){
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        commentInput.setText("");
    }
}