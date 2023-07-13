package honda.bookworm.View.Extra.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.honda.bookworm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import honda.bookworm.Object.Genre;

public class GenreAdapter extends ArrayAdapter {

    List<String> genreList;
    List<Genre> allGenre;
    List<Genre> favGenres;

    public GenreAdapter(@NonNull Context context, List<Genre> favGenres) {
        super(context, R.layout.simple_list_item, R.id.list_item_text);
        this.favGenres = favGenres;
        genreList = new ArrayList<>();
        initiateGenreList();
    }

    public GenreAdapter(@NonNull Context context) {
        this(context, null);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currItemView = convertView;

        if (currItemView == null) {
            currItemView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item, parent, false);
        }

        ((TextView) currItemView.findViewById(R.id.list_item_text)).setText(genreList.get(position));

        return currItemView;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        for (Genre g : allGenre)
            if (g.toString().equals(genreList.get(position)))
                return g;
       return genreList.get(position);
    }

    @Override
    public int getCount() {
        return genreList.size();
    }

    public void update(){
        genreList.clear();
        genreList.add("---Select a Genre---");
        for (Genre g : allGenre)
            if (favGenres == null || (favGenres!=null && !favGenres.contains(g)) ) {
                genreList.add(g.toString());
            }
    }


    private void initiateGenreList() {
        allGenre = new ArrayList<>(Arrays.asList(Genre.values()));
        update();
    }

}
