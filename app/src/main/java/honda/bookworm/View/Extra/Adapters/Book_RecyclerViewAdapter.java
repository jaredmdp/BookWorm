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
import honda.bookworm.View.Extra.ImageConverter;

public class Book_RecyclerViewAdapter extends RecyclerView.Adapter<Book_RecyclerViewAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;

    public Book_RecyclerViewAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Book_RecyclerViewAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creates and inflates rows for the view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sub_view_recycler_row, parent, false);
        return new Book_RecyclerViewAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Book_RecyclerViewAdapter.BookViewHolder holder, int position) {
        //update data for the recycler view
        holder.bookTitle.setText(bookList.get(position).getName());
        holder.bookAuthor.setText("By " + bookList.get(position).getAuthor());
        holder.bookGenre.setText("Genre: " + bookList.get(position).getGenre().toString());
        holder.bookISBN.setText("ISBN: " + bookList.get(position).getISBN());

       if(!bookList.get(position).getCover().equals("")) {
           holder.bookImage.setForeground(null);
           holder.bookImage.setImageBitmap(ImageConverter.DecodeToBitmap(bookList.get(position).getCover()));
        }else{
           holder.reset();
       }
    }

    @Override
    public int getItemCount() {
        //keeps track for onBindViewHolder
        return bookList.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView bookImage;
        TextView bookTitle, bookAuthor, bookGenre, bookISBN;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.recycler_booktitle);
            bookAuthor = itemView.findViewById(R.id.recycler_book_author);
            bookGenre = itemView.findViewById(R.id.recycler_book_genre);
            bookISBN = itemView.findViewById(R.id.recycler_book_isbn);
            bookImage = itemView.findViewById(R.id.recycler_book_image);

            itemView.setOnClickListener(this);
        }

        public void reset(){
            bookImage.setForeground(context.getDrawable(R.mipmap.ic_launcher_foreground));
            bookImage.setImageResource(R.drawable.shape_empty_book);
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
