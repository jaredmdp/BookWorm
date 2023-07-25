package honda.bookworm.View.Extra.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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


        if(!bookList.get(position).getCover().equals(""))
        {
            holder.bookImage.setForeground(null);
            holder.bookImage.setImageBitmap(ImageConverter.DecodeToBitmap(bookList.get(position).getCover()));
            holder.bookImage.setColorFilter(0);
            holder.bookImage.setAlpha(1f);
            holder.bookImage.setImageTintMode(null);
            holder.bookImage.setMinimumWidth(-1);
            holder.bookImage.setMaxWidth(1000);
            holder.bookTitle.setVisibility(View.GONE);
        }else{
            holder.reset();
        }

        holder.bookTitle.setText(bookList.get(position).getName());
        holder.parent.setTag(bookList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View parent;
        ImageView bookImage;
        TextView bookTitle;
        private int minWidth,maxWidth;


        public BookCardHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_card_image);
            bookTitle = itemView.findViewById(R.id.book_card_title);
            parent = itemView;

            minWidth = bookImage.getMinimumWidth();
            maxWidth = bookImage.getMaxWidth();

            itemView.setOnClickListener(this);
        }

        public void reset(){
            bookImage.setImageResource(R.mipmap.ic_launcher_foreground);
            bookImage.setAlpha(0.7f);
            bookImage.setColorFilter(R.color.black,PorterDuff.Mode.SRC_IN);
            bookImage.setMaxWidth(maxWidth);
            bookImage.setMinimumWidth(minWidth);
            bookTitle.setVisibility(View.VISIBLE);
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
