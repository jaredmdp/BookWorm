package honda.bookworm.View.Extra;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;

public class GenreFavoritingConstructor {
    private IUserPreference userPreferences;
    private GenreAdapter favGenreAdapter;
    private List<Genre> favGenreList;
    private View container;
    private Spinner genreSpinner;
    private TextView noGenresFound;
    private ImageButton addButton;
    private FlexboxLayout genreContainer;
    private boolean inEditMode;


    public GenreFavoritingConstructor(Context context, IUserPreference userPreference, User user) {
        this.container = LayoutInflater.from(context).inflate(R.layout.sub_view_genre_favoriting, null, false);
        this.userPreferences = userPreference;

        try{
            this.favGenreList = userPreference.getFavoriteGenreList(user);
        } catch(UserException e){
            Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
        }

        this.favGenreAdapter = new GenreAdapter(context, favGenreList);
        this.setup();
    }

    public View getView() {
        return container;
    }

    private void setup() {
        inEditMode = false;
        noGenresFound = container.findViewById(R.id.userfavgenre_no_fav_genres);
        genreContainer = container.findViewById(R.id.userfavgenre_container);
        addButton = container.findViewById(R.id.userfavgenre_add_genre);
        this.genreSpinner = container.findViewById(R.id.userfavgenre_genre_spinner);
        genreSpinner.setAdapter(favGenreAdapter);
        addFavouriteGenreClicked(addButton);

        for (Genre g : favGenreList) {
            addFavGenre(g);
        }
    }

    private void addFavouriteGenreClicked(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (genreSpinner.getSelectedItem() instanceof Genre) {
                    Genre g = (Genre) genreSpinner.getSelectedItem();
                    favGenreList.add(g);

                    try{
                        userPreferences.genreFavouriteToggle(g);
                    }catch(UserException e){
                        Toast.makeText(v.getContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
                    }

                    addFavGenre(g);
                    favGenreAdapter.update();
                    genreSpinner.setSelection(0);
                    containerMsgCheck();
                }
            }
        });
    }


    private void addFavGenre(Genre g) {
        View genrePill = LayoutInflater.from(container.getContext()).inflate(R.layout.fav_genre_pill, genreContainer, false);
        genreContainer.addView(genrePill);
        fillGenrePill(genrePill, g);
        containerMsgCheck();
    }

    private void fillGenrePill(View pill, Genre g) {
        ((TextView) pill.findViewById(R.id.fav_genre_pill_text))
                .setText(g.toString());
        View rmvButton = pill.findViewById(R.id.fav_genre_pill_remove);
        rmvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favGenreList.remove(g);

                try{
                    userPreferences.genreFavouriteToggle(g);
                }catch(UserException e){
                    Toast.makeText(v.getContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
                }

                genreContainer.removeView(pill);
                favGenreAdapter.update();
                containerMsgCheck();
            }
        });

        if (inEditMode) {
            rmvButton.setVisibility(View.VISIBLE);
        }
    }

    public void enterEditMode() {
        View spinnerContainer = container.findViewById(R.id.userfavgenre_spinner_container);
        spinnerContainer.setVisibility(View.VISIBLE);
        inEditMode = true;

        for (int i = 1; i < genreContainer.getChildCount(); i++) {
            View pill = genreContainer.getChildAt(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (pill.getSourceLayoutResId() == R.layout.fav_genre_pill) {
                    pill.findViewById(R.id.fav_genre_pill_remove).setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void exitEditMode() {
        View spinnerContainer = container.findViewById(R.id.userfavgenre_spinner_container);
        spinnerContainer.setVisibility(View.GONE);
        inEditMode = false;
        for (int i = 1; i < genreContainer.getChildCount(); i++) {
            View pill = genreContainer.getChildAt(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (pill.getSourceLayoutResId() == R.layout.fav_genre_pill) {
                    pill.findViewById(R.id.fav_genre_pill_remove).setVisibility(View.GONE);
                }
            }
        }
    }

    private void containerMsgCheck() {
        if (genreContainer.getChildCount() > 1) {
            this.noGenresFound.setVisibility(View.GONE);
        } else {
            this.noGenresFound.setVisibility(View.VISIBLE);
        }
    }
}
