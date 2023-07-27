package honda.bookworm.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.honda.bookworm.R;

import java.util.List;

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
import honda.bookworm.Object.Comment;
import honda.bookworm.View.Extra.Adapters.Comment_RecyclerViewAdapter;
import honda.bookworm.View.Extra.ImageConverter;

public class BookView_ViewHandler extends AppCompatActivity {

    private final int MIN = 7;
    private Book book;
    private IUserManager userManager;
    private IAccessBooks accessBooks;
    private IAccessUsers accessUsers;
    private ICommentManager commentManager;
    private IUserPreference userPreference;
    private String author_username;

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


        try {
            this.author_username = accessUsers.fetchUsernameOfAuthor(book.getAuthorID());
        }catch (Exception e){
            author_username = "";
        }

        assignValues();
        populateCommentSection();
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
            coverView.setTag(true);
            System.out.println(coverView.getTag());
            coverView.setForeground(null);
            coverView.setImageBitmap(ImageConverter.DecodeToBitmap(book.getCover()));
        } else {
            coverView.setTag(false);
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
        purchaseButton.setTag(book.getPurchaseable());

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

        if (description.getMaxLines() <= MIN) {
            description.setMaxLines(description.getLineCount() + 1);
            toggleText.setText("[Collapse]");
        } else {
            description.setMaxLines(MIN);
            toggleText.setText("[Expand]");
        }
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
        if(!author_username.isEmpty()) {
            Intent userProfile = new Intent(this, UserProfile_ViewHandler.class);
            userProfile.putExtra(UserProfile_ViewHandler.REQUEST_CODE, author_username);
            startActivity(userProfile);
        }
    }

    public void submitComment(View view){
        EditText commentInput = (EditText) findViewById(R.id.book_view_comment_input);

        try{
            commentManager.leaveComment(book.getISBN(), commentInput.getText().toString());
            populateCommentSection();
        } catch(UserException | InvalidCommentException | GeneralPersistenceException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        commentInput.setText("");
    }


    public void onDiscussionTabClicked(View view) {
        TextView tab = (TextView) view;
        LinearLayout discussion = findViewById(R.id.book_view_discussion_section);

        switch (discussion.getVisibility()){
            case View.GONE:
                tab.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_down,0);
                discussion.setVisibility(View.VISIBLE);
                break;
            case View.VISIBLE:
                tab.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_right_24,0);
                discussion.setVisibility(View.GONE);
                break;
        }
    }


    private void populateCommentSection(){
        RecyclerView commentRecycler = findViewById(R.id.book_view_discussions);

        new Handler().post(new Runnable(){
            @Override
            public void run() {
                Comment_RecyclerViewAdapter cAdapter;
                List<Comment> commentList;
                try{
                    commentList = commentManager.getCommentsOnBook(book.getISBN());
                    Log.i("Comments Size: ", commentList.size()+"");
                    if(!commentList.isEmpty()) {
                        cAdapter = new Comment_RecyclerViewAdapter(BookView_ViewHandler.this, commentList, author_username);
                        commentRecycler.setAdapter(cAdapter);
                        commentRecycler.setLayoutManager(new LinearLayoutManager(BookView_ViewHandler.this));
                        commentRecycler.setVisibility(View.VISIBLE);
                    }else{
                        commentRecycler.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    commentRecycler.setVisibility(View.GONE);
                }
            }
        });

    }


}