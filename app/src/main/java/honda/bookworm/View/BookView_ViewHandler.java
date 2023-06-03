package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.honda.bookworm.R;

public class BookView_ViewHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        Bundle bookInfo = getIntent().getExtras();

        String title = bookInfo.getString("title");
        String author =  bookInfo.getString("author");
        String genre =   bookInfo.getString("genre");
        String isbn =    bookInfo.getString("isbn");
        String description = bookInfo.getString("description","[Empty]");

        assignValues(title,author,genre,isbn,description);
    }


    //need to figure out image
    private void assignValues(String bookTitle, String bookAuthor, String bookGenre, String bookIsbn, String bookDescription){
        TextView title = findViewById(R.id.book_view_book_title);
        TextView author = findViewById(R.id.book_view_book_author);
        TextView genre = findViewById(R.id.book_view_book_genre);
        TextView isbn = findViewById(R.id.book_view_book_isbn);
        TextView description = findViewById(R.id.book_view_book_description);

        title.setText(String.format("%s %s",title.getText(),bookTitle));
        author.setText(String.format("%s %s",author.getText(),bookAuthor));
        genre.setText(String.format("%s %s",genre.getText(),bookGenre));
        isbn.setText(String.format("%s %s",isbn.getText(),bookIsbn));
        description.setText(String.format("%s %s",description.getText(),bookDescription));

        String url = String.format("https://www.amazon.ca/s?k=%s",bookTitle.toLowerCase().replaceAll(" ","+"));
        createPurchaseButtonViewListener(url);
        Log.d("URL TAG: ", url);

    }

    private void createPurchaseButtonViewListener(String url){
        MaterialButton purchaseButton= findViewById(R.id.book_view_book_purchase_link);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webLink = Uri.parse(url);
                Intent webIntent = new Intent(Intent.ACTION_VIEW,webLink);
                startActivity(webIntent);
            }
        });
    }
}