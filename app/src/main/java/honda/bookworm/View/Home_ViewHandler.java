package honda.bookworm.View;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.honda.bookworm.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import honda.bookworm.Application.Main;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.CommentManager;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Book_horizontalscroll_constructor;
import honda.bookworm.View.Extra.Messages;

public class Home_ViewHandler extends AppCompatActivity {
    private IAccessBooks accessBooks;
    private IUserManager userManager;
    private ISearchManager searchManager;
    private IUserPreference userPreference;
    private Vibrator sysVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        copyDatabaseToDevice();
        startAllPersistence();
        sysVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        customizeToUser();
        buildBookView();
    }

    private void startAllPersistence(){
        accessBooks = new AccessBooks();
        userManager = new UserManager();
        searchManager = new SearchManager();
        userPreference = new UserPreference();
        new CommentManager(); // start it in services
        new AccessUsers(); //start it in services
    }

    private void customizeToUser() {
        TextView userFullname = findViewById(R.id.home_user_fullname);
        TextView userName = findViewById(R.id.home_username);
        ImageButton profile = findViewById(R.id.home_userProfile_button);
        String username;
        String fullname;

        if (userManager.isUserLoggedIn()) {
            User activeUser = userManager.getActiveUser();
            username = activeUser.getUsername();
            fullname = activeUser.getFirstName() + " " + activeUser.getLastName();
            profile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.worm_skin));
            userFullname.setText(fullname);
            userName.setText("@" + username);
        } else {
            profile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.inactive_grey));
        }
    }

    private void buildBookView() {
        processRecommendedRequest();
        List<Genre> genres = accessBooks.getAllAvailableGenres();
        processGenresRequest(genres, 0);
    }

    private void processRecommendedRequest() {
        final List<Book> bookList = userPreference.getBookRecommendations();
        addScrollView(bookList, "Recommended Books", 0);
    }

    private void processGenresRequest(List<Genre> list, int i) {
        if (i < list.size()) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    final List<Book> bookList = searchManager.performSearchGenre(list.get(i).toString());
                    processGenresRequest(list, i + 1);
                    addScrollView(bookList, list.get(i).toString(), -1);
                }
            };
            new Handler().post(r);
        }
    }


    private void addScrollView(List<Book> bookList, String heading, int at) {
        LinearLayout linearBody = findViewById(R.id.home_linear_content_body);
        if (!bookList.isEmpty()) {
            View horizontalScrollContainer = Book_horizontalscroll_constructor.create(Home_ViewHandler.this, heading, bookList);

            if (at == -1) {
                linearBody.addView(horizontalScrollContainer);
            } else {
                linearBody.addView(horizontalScrollContainer, at); //add at specific location '0 = the top'
            }

        }
    }

    public void onUserPressed(View view) {
        LinearLayout profileView = findViewById(R.id.home_userProfile_options);
        Intent intent;
        if (userManager.isUserLoggedIn()) {
            viewVisibilityToggle(profileView);
        } else {
            intent = new Intent(getApplicationContext(), UserLogin_ViewHandler.class);
            startActivity(intent);
        }

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
        if (userManager.isUserLoggedIn()) {
            userManager.logOutActiveUser();
            TextView userName = findViewById(R.id.home_username);
            userName.setText("");
            viewVisibilityToggle(profileView);
            profile.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.inactive_grey));
            onRestart();
        }
    }


    public void onSearchPressed(View view) {
        Intent intent = new Intent(this, Search_ViewHandler.class);
        startActivity(intent);
    }

    public void onViewProfilePressed(View view) {
        //place holder for the User profile view intent
        sysVibrator.vibrate(5);
        if (userManager.isUserLoggedIn()) {
            Intent intent = new Intent(this, UserProfile_ViewHandler.class);
            startActivity(intent);
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

        } catch (final IOException | GeneralPersistenceException e) {
            Messages.warning(this, "Unable to access application data: " + e.getMessage());
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

    @Override
    protected void onRestart() {
        super.onRestart();
        LinearLayout linearBody = findViewById(R.id.home_linear_content_body);
        LinearLayout profileView = findViewById(R.id.home_userProfile_options);
        linearBody.removeAllViews();
        buildBookView();
        profileView.setVisibility(View.GONE);
    }
}