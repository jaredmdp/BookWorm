package honda.bookworm.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;


import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Object.Book;

public class Search_ViewHandler extends AppCompatActivity {

    SearchView search;
    RadioGroup searchCategory;

    TextView searchResultHeading;
    TextView searchResultParagraph;

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


        //updateRecyclerView(Services.getBookPersistence(true).getAllBooks(), "All the books"); // TEMPORARY TO REMOVE

    }

    private void radioSelectChange(){
        searchCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.search_for_genre){
                    search.setQueryHint("Search by Genre");
                }else if(checkedId == R.id.search_for_author){
                    search.setQueryHint("Search by Author");
                }else if(checkedId == R.id.search_for_book_title){
                    search.setQueryHint("Search by Book Title");
                }else if(checkedId == R.id.search_for_isbn){
                    search.setQueryHint("Search by ISBN");
                }
                search.setQuery("",false);
            }
        });
    }


    private void searchQueryListener(){
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int checkedId = searchCategory.getCheckedRadioButtonId();

                //Search queries called from here
                if(!query.isEmpty()) {
                    if (checkedId == R.id.search_for_genre) {
                        searchResultHeading.setText("Searched For Genre");
                    } else if (checkedId == R.id.search_for_author) {
                        searchResultHeading.setText("Searched For Author");
                    } else if (checkedId == R.id.search_for_book_title) {
                        searchResultHeading.setText("Search For Book Title");
                    } else if (checkedId == R.id.search_for_isbn) {
                        searchResultHeading.setText("Searched For ISBN");
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

    public void updateRecyclerView(List<Book> bookList, String resultHeading){
        RecyclerView bookRecycler = findViewById(R.id.search_book_recycler);
        TextView result = findViewById(R.id.search_result_heading);
        Book_RecyclerViewAdapter bookAdapter = new Book_RecyclerViewAdapter(Search_ViewHandler.this,bookList);


        bookRecycler.setAdapter(bookAdapter);
        bookRecycler.setLayoutManager(new LinearLayoutManager(this));

        result.setVisibility(View.VISIBLE);
        result.setText(resultHeading);
    }



}