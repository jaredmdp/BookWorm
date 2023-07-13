package honda.bookworm.View.Extra;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.View.Extra.Adapters.Book_Horizontalscroll_RecyclerViewAdapter;

public class Book_horizontalscroll_constructor {

    public static View create(Context context, String Title, List<Book>bookList){
        View container = LayoutInflater.from(context).inflate(R.layout.books_horizontal_scroll_container,null);
        TextView tv  = container.findViewById(R.id.books_horizontal_info_text);
        RecyclerView rv = container.findViewById(R.id.books_horizontal_recyclerview);
        tv.setText(Title);
        buildRecyclerView(context,rv,bookList);
        return container;
    }

    private static void buildRecyclerView(Context c, RecyclerView rv, List<Book> bookList){
        Book_Horizontalscroll_RecyclerViewAdapter adapter = new Book_Horizontalscroll_RecyclerViewAdapter(c,bookList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(c,RecyclerView.HORIZONTAL,false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);
    }

}
