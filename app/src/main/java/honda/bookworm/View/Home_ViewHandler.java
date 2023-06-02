package honda.bookworm.View;

import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Object.Genre;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.honda.bookworm.R;

import java.text.AttributedCharacterIterator;
import java.util.List;

public class Home_ViewHandler extends AppCompatActivity {
    private final int CARD_WIDTH = 400;
    private final int CARD_HEIGHT = 525;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buildBookView();
    }

    private void buildBookView(){
        Genre [] genres = Genre.class.getEnumConstants();
        BookPersistence bp = new BookPersistenceStub();
        LinearLayout linearBody = findViewById(R.id.home_linear_content_body);
        String genreName;
        List<Book> bookList;
        int scrollViewContainerID;

        for(Genre genre : genres){
            genreName = genre.toString();
            bookList = bp.getBooksByGenre(genre);

            if( bookList != null){
                TextView tv = new TextView(linearBody.getContext());
                tv.setText(genreName);
                tv.setTextSize(25);
                linearBody.addView(tv);

                scrollViewContainerID= createHorizontalView(linearBody);
                for(Book book: bookList){
                    populateHorizontalView(scrollViewContainerID,book);
                }
            }
        }
    }

    public void onSearchPressed(View view) {
        Toast.makeText(getApplicationContext(),
                "under construction: to be implemented",
                Toast.LENGTH_SHORT).show();
    }

    private void populateHorizontalView (int id, Book book){
        LinearLayout viewContainer = (LinearLayout) findViewById(id);
        viewContainer.addView(createBookToView(book),CARD_WIDTH,CARD_HEIGHT);
    }

    private int createHorizontalView(LinearLayout parent) {
        LinearLayout llh = new LinearLayout(this);
        llh.setOrientation(LinearLayout.HORIZONTAL);
        llh.setId(View.generateViewId());

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.addView(llh);

        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(
                HorizontalScrollView.LayoutParams.MATCH_PARENT,
                HorizontalScrollView.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 25, 0, 50);
        hsv.setLayoutParams(params);

        parent.addView(hsv);
        return llh.getId();
    }

    private View createBookToView(Book book) {
        View bookCardView = getLayoutInflater().inflate(R.layout.sub_view_book_card, null, false);
        TextView bookName = (TextView) bookCardView.findViewById(R.id.book_card_title);
        bookName.setText(book.getName());

        bookCardView.setTag(book);

        bookCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),v.getTag().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return bookCardView;
    }


}