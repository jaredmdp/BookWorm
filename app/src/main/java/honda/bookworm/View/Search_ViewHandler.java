package honda.bookworm.View;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.honda.bookworm.R;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.InvalidSearchException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.View.Extra.Adapters.Book_RecyclerViewAdapter;
import honda.bookworm.View.Extra.Adapters.User_RecyclerViewAdapter;

public class Search_ViewHandler extends AppCompatActivity {

    SearchView search;
    RadioGroup searchCategory;

    TextView searchResultHeading;
    TextView searchResultParagraph;

    ISearchManager searchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.search_search_input);
        searchCategory = findViewById(R.id.search_category_radio_group);
        searchResultHeading = findViewById(R.id.search_result_heading);
        searchResultParagraph = findViewById(R.id.search_result_p);
        radioSelectChange();
        searchQueryListener();

        searchManager = new SearchManager();
    }

    private void radioSelectChange() {
        searchCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                search.setInputType(InputType.TYPE_CLASS_TEXT);
                if (checkedId == R.id.search_for_genre) {
                    search.setQueryHint("Search Books by Genre");
                } else if (checkedId == R.id.search_for_author) {
                    search.setQueryHint("Search Books by Author");
                } else if (checkedId == R.id.search_for_book_title) {
                    search.setQueryHint("Search Books by Title");
                } else if (checkedId == R.id.search_for_isbn) {
                    search.setQueryHint("Search Books by ISBN");
                    search.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else if(checkedId == R.id.search_for_users) {
                    search.setQueryHint("Search for Users");
                }
                search.setQuery("", false);
            }
        });
    }

    private void searchQueryListener() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int checkedId = searchCategory.getCheckedRadioButtonId();
                List<Book> bookList = new ArrayList<>();

                if (!query.isEmpty()) {
                    // Search queries called from here
                    try {
                        if (checkedId == R.id.search_for_genre) {
                            bookList = searchManager.performSearchGenre(query);
                        } else if (checkedId == R.id.search_for_author) {
                            bookList = searchManager.performSearchAuthor(query);
                        } else if (checkedId == R.id.search_for_book_title) {
                            bookList = searchManager.performSearchTitle(query);
                        } else if (checkedId == R.id.search_for_isbn) {
                            bookList = searchManager.performSearchISBN(query);
                        }
                        updateRecyclerView(bookList, query);

                        if(checkedId == R.id.search_for_users){
                            List<User> userList = searchManager.performSearchUser(query);
                            updateRecyclerViewUser(userList, query);
                        }

                    } catch (InvalidSearchException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    searchResultHeading.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void updateRecyclerView(List<Book> bookList, String resultHeading) {
        RecyclerView bookRecycler = findViewById(R.id.search_book_recycler);

        if (!bookList.isEmpty()) {
            Book_RecyclerViewAdapter bookAdapter = new Book_RecyclerViewAdapter(Search_ViewHandler.this, bookList);
            bookRecycler.setAdapter(bookAdapter);
            bookRecycler.setLayoutManager(new LinearLayoutManager(this));
            bookRecycler.setVisibility(View.VISIBLE);

            int resCount = bookList.size();
            String resMsg = resCount + " result" + (resCount < 2 ? "" : "s") + " found.";
            searchResultHeading.setText("Searched For: "+resultHeading);
            searchResultParagraph.setText(resMsg);
        } else {
            bookRecycler.setVisibility(View.GONE);
            searchResultHeading.setText("No results found");
            searchResultParagraph.setText(R.string.searchError);
        }

        searchResultHeading.setVisibility(View.VISIBLE);
        searchResultParagraph.setVisibility(View.VISIBLE);
    }

    public void updateRecyclerViewUser(List<User> userList, String resultHeading) {
        RecyclerView bookRecycler = findViewById(R.id.search_book_recycler);

        if (!userList.isEmpty()) {
            User_RecyclerViewAdapter userAdapter = new User_RecyclerViewAdapter(Search_ViewHandler.this, userList);
            bookRecycler.setAdapter(userAdapter);
            bookRecycler.setLayoutManager(new LinearLayoutManager(this));
            bookRecycler.setVisibility(View.VISIBLE);

            int resCount = userList.size();
            String resMsg = resCount + " result" + (resCount < 2 ? "" : "s") + " found.";
            searchResultHeading.setText("Searched For: "+resultHeading);
            searchResultParagraph.setText(resMsg);
        } else {
            bookRecycler.setVisibility(View.GONE);
            searchResultHeading.setText("No results found");
            searchResultParagraph.setText(R.string.searchError);
        }

        searchResultHeading.setVisibility(View.VISIBLE);
        searchResultParagraph.setVisibility(View.VISIBLE);
    }


}