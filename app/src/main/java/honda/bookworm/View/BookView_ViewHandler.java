package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.honda.bookworm.R;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Book;
import honda.bookworm.View.Extra.ImageConverter;

public class BookView_ViewHandler extends AppCompatActivity {

    private final int MIN = 7;
    private String thisBookISBN;
    private IUserManager userManager;
    private IAccessBooks accessBooks;

    private IUserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        userManager = new UserManager();
        accessBooks = new AccessBooks();
        userPreference = new UserPreference();

        Bundle bookInfo = getIntent().getExtras();
        thisBookISBN = bookInfo.getString("isbn");

        /** TODO: apply the proper logic for fetch bookByISBN **/
        Book bk = Services.getBookPersistence(false).getBookByISBN(thisBookISBN);

        assignValues(bk.getName(), bk.getAuthor(), bk.getGenre().toString(), bk.getISBN(), bk.getDescription(), bk.getCover());
        applyHideOnScroll();
    }

    //need to figure out image
    private void assignValues(String bookTitle, String bookAuthor, String bookGenre, String bookIsbn, String bookDescription, String cover) {
        TextView title = findViewById(R.id.book_view_book_title);
        TextView author = findViewById(R.id.book_view_book_author);
        TextView genre = findViewById(R.id.book_view_book_genre);
        TextView isbn = findViewById(R.id.book_view_book_isbn);
        TextView description = findViewById(R.id.book_view_book_description);
        TextView collapseButton = findViewById(R.id.book_view_book_description_toggle);
        ImageView coverView = findViewById(R.id.book_view_book_cover);

        title.setText(String.format("%s %s", title.getText(), bookTitle));
        author.setText(String.format("%s %s", author.getText(), bookAuthor));
        genre.setText(String.format("%s %s", genre.getText(), bookGenre));
        isbn.setText(String.format("%s %s", isbn.getText(), bookIsbn));
        description.setText(String.format("%s %s", description.getText(), bookDescription));

        if(!cover.equals(""))
        {
            coverView.setForeground(null);
            coverView.setImageBitmap(ImageConverter.DecodeToBitmap(cover));
        }

        description.post(new Runnable() {
            @Override
            public void run() {
                if(description.getLineCount()<=MIN){
                    collapseButton.setVisibility(View.GONE);
                }
            }
        });

        String url = String.format("https://www.amazon.ca/s?k=%s", bookTitle.toLowerCase().replaceAll(" ", "+"));
        createPurchaseButtonViewListener(url);
        setUpFavoriting();
    }

    private void createPurchaseButtonViewListener(String url) {
        MaterialButton purchaseButton = findViewById(R.id.book_view_book_purchase_link);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webLink = Uri.parse(url);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webLink);
                startActivity(webIntent);
            }
        });
    }

    public void expandCollapseDescription(View v) {
        TextView toggleText = (TextView) v;
        TextView description = findViewById(R.id.book_view_book_description);
        LinearLayout floatContent = findViewById(R.id.book_view_floating_content);

        if(description.getMaxLines()<=MIN){
            description.setMaxLines(description.getLineCount()+1);
            toggleText.setText("[Collapse]");
        }else{
            description.setMaxLines(MIN);
            toggleText.setText("[Expand]");
            floatContent.setVisibility(View.VISIBLE);
        }

    }

    public void applyHideOnScroll(){
        LinearLayout hideOnScroll = findViewById(R.id.book_view_floating_content);
        ScrollView scrollView = findViewById(R.id.book_view_scrollview);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("Curr Y",""+scrollY);
                Log.i("Old Y",""+oldScrollY);
                if(scrollY>oldScrollY) {
                    hideOnScroll.setVisibility(View.GONE);
                }else{
                    hideOnScroll.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setUpFavoriting(){
        ToggleButton favButton = findViewById(R.id.book_view_fav_book_toggle);
        if(userManager.isUserLoggedIn()){
            favButton.setVisibility(View.VISIBLE);
            favButton.setChecked(userPreference.isBookFavourite(userManager.getActiveUser(),thisBookISBN));
        }
    }

    public void isFavoriteClicked(View view) {
        /** Favorting process goes below**/
        ((ToggleButton) view).setChecked(userPreference.bookFavouriteToggle(thisBookISBN));
    }

}