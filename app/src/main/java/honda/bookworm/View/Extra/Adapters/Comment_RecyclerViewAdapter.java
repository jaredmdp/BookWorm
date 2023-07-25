package honda.bookworm.View.Extra.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.honda.bookworm.R;

import java.util.List;

import honda.bookworm.Object.Comment;
import honda.bookworm.View.Extra.DateParser;
import honda.bookworm.View.UserProfile_ViewHandler;

public class Comment_RecyclerViewAdapter extends RecyclerView.Adapter<Comment_RecyclerViewAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList;
    private final String author_username;

    public Comment_RecyclerViewAdapter(Context context, List<Comment> userList, String author_username) {
        this.context = context;
        this.commentList = userList;
        this.author_username = author_username;
    }

    @NonNull
    @Override
    public Comment_RecyclerViewAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comments_recycler_row,parent, false);
        return new Comment_RecyclerViewAdapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment c = commentList.get(position);
        holder.username.setText("@"+c.getUsername());
        holder.content.setText(c.getComment());
        holder.timestamp.setText(DateParser.parseDateString(c.getTime()));

        if (author_username.equals(c.getUsername())) {
            holder.card.setStrokeColor(context.getColor(R.color.worm_skin));
            holder.card.setStrokeWidth(5);
        } else {
            holder.card.setStrokeColor(null);
            holder.card.setStrokeWidth(0);
        }


    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username;
        TextView content;
        TextView timestamp;
        MaterialCardView card;

        public CommentViewHolder(@NonNull View itemView){
            super(itemView);

            card = itemView.findViewById(R.id.recycler_comment_card);
            username = itemView.findViewById(R.id.recycler_comment_username);
            timestamp = itemView.findViewById(R.id.recycler_comment_timestamp);
            content = itemView.findViewById(R.id.recycler_comment_content);

            username.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String userName = commentList.get(position).getUsername();
            Intent i = new Intent(context, UserProfile_ViewHandler.class);
            i.putExtra(UserProfile_ViewHandler.REQUEST_CODE, userName);
            context.startActivity(i);

        }
    }
}
