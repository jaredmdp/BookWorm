package honda.bookworm.View.Extra.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.View.BookView_ViewHandler;

public class Book_Horizontalscroll_RecyclerViewAdapter extends RecyclerView.Adapter<Book_Horizontalscroll_RecyclerViewAdapter.BookCardHolder> {

    private Context context;
    private List<Book> bookList;

    public Book_Horizontalscroll_RecyclerViewAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }


    @NonNull
    @Override
    public Book_Horizontalscroll_RecyclerViewAdapter.BookCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sub_view_book_card, parent, false);
        return new Book_Horizontalscroll_RecyclerViewAdapter.BookCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Book_Horizontalscroll_RecyclerViewAdapter.BookCardHolder holder, int position) {
        holder.bookTitle.setText(bookList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView bookImage;
        TextView bookTitle;

        public BookCardHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_card_image);
            bookTitle = itemView.findViewById(R.id.book_card_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String isbn = bookList.get(position).getISBN();
            Intent i = new Intent(context, BookView_ViewHandler.class);
            i.putExtra("isbn", isbn);
            context.startActivity(i);
        }
    }
}
