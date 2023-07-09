package honda.bookworm.View.Extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Adapters.GenreAdapter;

public class GenreFavoritingConstructor {
    private Context context;
   private IUserPreference userPreferences;
   private GenreAdapter favGenreAdapter;
   private List<Genre> favGenreList;
   private boolean isActiveUser;
   private View container;
   private Spinner genreSpinner;
   private TextView noGenresFound, addFavGenres;
   private ImageButton doneButton , addButton;
   private FlexboxLayout genreContainer;


    public GenreFavoritingConstructor(Context context, IUserPreference userPreference, User user){
        this.context = context;
        this.container = LayoutInflater.from(context).inflate(R.layout.sub_view_genre_favoriting,null,false);
        this.userPreferences = userPreference;
        this.favGenreList = userPreference.getFavoriteGenreList(user);
        this.favGenreAdapter = new GenreAdapter(context,favGenreList);
        this.isActiveUser= true;
        this.setup();
    }

    public GenreFavoritingConstructor(Context context,User user, IUserPreference userPreference, boolean isActiveUser){
        this(context,userPreference,user);
        this.isActiveUser = isActiveUser;
    }

    public View getView(){return container; }

    private void setup(){
        noGenresFound = container.findViewById(R.id.userfavgenre_no_fav_genres);
        genreContainer = container.findViewById(R.id.userfavgenre_container);

        if(isActiveUser) {
            container.findViewById(R.id.userfavgenre_spinner_container).setVisibility(View.VISIBLE);
            doneButton = container.findViewById(R.id.userfavgenre_done_changes);
            addButton = container.findViewById(R.id.userfavgenre_add_genre);
            addFavGenres = container.findViewById(R.id.userfavgenre_addgenretext);
            this.genreSpinner = container.findViewById(R.id.userfavgenre_genre_spinner);
            genreSpinner.setAdapter(favGenreAdapter);
            addFavouriteGenreClicked(addButton);
            genreDoneClicked(doneButton);
        }else {
            container.findViewById(R.id.userfavgenre_spinner_container).setVisibility(View.GONE);
        }

        for (Genre g: favGenreList) {
            addFavGenre(g);
        }
    }

    private void addFavouriteGenreClicked(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(genreSpinner.getVisibility() == View.GONE){
                    genreSpinner.setVisibility(View.VISIBLE);
                    doneButton.setVisibility(View.VISIBLE);
                    addFavGenres.setVisibility(View.GONE);
                }else{
                    if(genreSpinner.getSelectedItem() instanceof  Genre){
                        Genre g = (Genre )genreSpinner.getSelectedItem();
                        favGenreList.add(g);
                        userPreferences.genreFavouriteToggle(g);
                        addFavGenre(g);
                        favGenreAdapter.update();
                        genreSpinner.setSelection(0);
                        containerMsgCheck();
                    }
                }
            }
        });
    }

    public void genreDoneClicked(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
                genreSpinner.setVisibility(View.GONE);
                addFavGenres.setVisibility(View.VISIBLE);
                genreSpinner.setSelection(0);
            }
        });
    }

    private void addFavGenre(Genre g){
        View genrePill = LayoutInflater.from(container.getContext()).inflate(R.layout.fav_genre_pill,genreContainer,false);
        genreContainer.addView(genrePill);
        fillGenrePill(genrePill,g);
        containerMsgCheck();
    }

    private void fillGenrePill(View pill, Genre g){
        ((TextView) pill.findViewById(R.id.fav_genre_pill_text))
                .setText(g.toString());

        if(isActiveUser) {
            pill.findViewById(R.id.fav_genre_pill_remove)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favGenreList.remove(g);
                            userPreferences.genreFavouriteToggle(g);
                            genreContainer.removeView(pill);
                            favGenreAdapter.update();
                            containerMsgCheck();
                        }
                    });
        }else{
            pill.findViewById(R.id.fav_genre_pill_remove).setVisibility(View.GONE);
        }
    }


    private void containerMsgCheck(){
        if(genreContainer.getChildCount()>1){
            this.noGenresFound.setVisibility(View.GONE);
        }else {
            this.noGenresFound.setVisibility(View.VISIBLE);
        }
    }
}
