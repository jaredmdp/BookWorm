package honda.bookworm.View;

import honda.bookworm.Application.Main;
import honda.bookworm.Application.Services;
import honda.bookworm.Business.AccessBooks;
import honda.bookworm.Business.AccessUsers;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.honda.bookworm.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Home_ViewHandler extends AppCompatActivity {
    private final int CARD_WIDTH = 400; // bookCard Width
    private final int CARD_HEIGHT = 525; // bookCard Height

    private AccessUsers accessUsers;

    private AccessBooks accessBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDatabaseToDevice();
        accessUsers = new AccessUsers();
        accessBooks = new AccessBooks();
        customizeToUser();
        buildBookView();
    }

    private void customizeToUser() {
        User activeUser = accessUsers.getActiveUser();
        TextView userName = findViewById(R.id.home_username);
        if (activeUser != null) {
            userName.setText(activeUser.getUsername());
            userName.setVisibility(View.VISIBLE);
        }
    }

    private void buildBookView() {
        Genre[] genres = Genre.class.getEnumConstants();

        String genreName;
        List<Book> bookList;
        LinearLayout linearBody = findViewById(R.id.home_linear_content_body);
        int scrollViewContainerID;

        for (Genre genre : genres) {
            genreName = genre.toString();
            try {
                bookList = accessBooks.getBooksGenre(genre);
                if (bookList != null) {
                    TextView tv = new TextView(linearBody.getContext());
                    tv.setText(genreName);
                    tv.setTextSize(25);
                    linearBody.addView(tv);

                    scrollViewContainerID = createHorizontalView(linearBody);
                    for (Book book : bookList) {
                        populateHorizontalView(scrollViewContainerID, book);
                    }
                }
            } catch (NullPointerException e) {
                String msg = String.format("Invalid Genre: %s", e.getMessage());
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onUserPressed(View view) {
        MaterialButton logoutBtn = findViewById(R.id.home_userProfile_logoutButton);
        Intent intent;
        if (accessUsers.getActiveUser() != null) {
            viewVisibilityToggle(logoutBtn);
        } else {
            intent = new Intent(getApplicationContext(), UserLogin_ViewHandler.class);
            startActivity(intent);
        }

    }

    private void populateHorizontalView(int id, Book book) {
        LinearLayout viewContainer = (LinearLayout) findViewById(id);
        viewContainer.addView(createBookCard(book), CARD_WIDTH, CARD_HEIGHT);
    }

    private int createHorizontalView(LinearLayout parent) {
        LinearLayout llh = new LinearLayout(this);
        llh.setOrientation(LinearLayout.HORIZONTAL);
        llh.setId(View.generateViewId());

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.addView(llh);
        hsv.setHorizontalScrollBarEnabled(false);

        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(
                HorizontalScrollView.LayoutParams.MATCH_PARENT,
                HorizontalScrollView.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 10, 0, 100);
        hsv.setLayoutParams(params);

        parent.addView(hsv);
        return llh.getId();
    }

    private View createBookCard(Book book) {
        View bookCardView = getLayoutInflater().inflate(R.layout.sub_view_book_card, null, false);
        TextView bookName = (TextView) bookCardView.findViewById(R.id.book_card_title);
        bookName.setText(accessBooks.getTrimmedBookName(book));

        bookCardView.setTag(book);
        addClickListenerToBookCard(bookCardView);

        return bookCardView;
    }

    private void addClickListenerToBookCard(View bookCard) {
        bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookView_ViewHandler.class);
                Book book = (Book) v.getTag();
                intent.putExtra("title", book.getName());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("genre", book.getGenreAsString());
                intent.putExtra("isbn", book.getISBN());
                intent.putExtra("description", book.getDescription());

                startActivity(intent);
            }
        });

    }

    private void viewVisibilityToggle(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onLogoutPressed(View view) {
        if (Services.getActiveUser() != null) {
            accessUsers.logOutActiveUser();
            TextView userName = findViewById(R.id.home_username);
            userName.setVisibility(View.GONE);
            viewVisibilityToggle(view);
        }
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

}