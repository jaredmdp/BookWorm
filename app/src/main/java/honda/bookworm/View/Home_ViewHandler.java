package honda.bookworm.View;

import honda.bookworm.Application.Main;
import honda.bookworm.Application.Services;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.honda.bookworm.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Home_ViewHandler extends AppCompatActivity {
    private final int CARD_WIDTH = 400; // bookCard Width
    private final int CARD_HEIGHT = 525; // bookCard Height

    private IAccessUsers accessUsers;

    private IAccessBooks accessBooks;
    private IUserManager userManager;
    private Vibrator sysVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDatabaseToDevice();
        accessUsers = new AccessUsers();
        accessBooks = new AccessBooks();
        userManager = new UserManager();

        sysVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        customizeToUser();
        buildBookView();
    }

    private void customizeToUser() {
        User activeUser = userManager.getActiveUser();
        TextView userFullname = findViewById(R.id.home_user_fullname);
        TextView userName = findViewById(R.id.home_username);
        ImageButton profile = findViewById(R.id.home_userProfile_button);
        String username;
        String fullname;

        if (activeUser != null) {
            username = activeUser.getUsername();
            fullname = activeUser.getFirstName()+" "+activeUser.getLastName();
            profile.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.worm_skin));
            userFullname.setText(fullname);
            userName.setText("@"+username);
        }else{profile.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.inactive_grey));}
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
                if (!bookList.isEmpty()) {
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
        LinearLayout profileView = findViewById(R.id.home_userProfile_options);
        Intent intent;
        if (userManager.getActiveUser() != null) {
            viewVisibilityToggle(profileView);
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
                intent.putExtra("isbn", book.getISBN());
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
        sysVibrator.vibrate(250);
        LinearLayout profileView = findViewById(R.id.home_userProfile_options);
        ImageButton profile = findViewById(R.id.home_userProfile_button);
        if (Services.getActiveUser() != null) {
            userManager.logOutActiveUser();
            TextView userName = findViewById(R.id.home_username);
            userName.setText("");
            viewVisibilityToggle(profileView);
            profile.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.inactive_grey));
        }
    }


    public void onSearchPressed(View view) {
        Intent intent = new Intent(this,Search_ViewHandler.class);
        startActivity(intent);
    }

    public void onViewProfilePressed(View view) {
        //place holder for the User profile view intent
        sysVibrator.vibrate(5);
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