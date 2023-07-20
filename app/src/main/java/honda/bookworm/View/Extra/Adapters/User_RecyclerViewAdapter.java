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

import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;
import honda.bookworm.View.UserProfile_ViewHandler;

public class User_RecyclerViewAdapter extends RecyclerView.Adapter<User_RecyclerViewAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public User_RecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public User_RecyclerViewAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sub_view_user_recycler_row,parent, false);
        return new User_RecyclerViewAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = userList.get(position);
        holder.username.setText("@"+u.getUsername());
        holder.fullName.setText(u.getFirstName()+" "+u.getLastName());

        if(u instanceof Author)
            holder.image.setImageResource(R.drawable.icon_author_pen);
        else
            holder.image.setImageResource(R.drawable.icon_person);
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fullName;
        TextView username;
        ImageView image;

        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            fullName = itemView.findViewById(R.id.recycler_user_fullname);
            username = itemView.findViewById(R.id.recycler_user_username);
            image = itemView.findViewById(R.id.recycler_user_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String userName = userList.get(position).getUsername();
            Intent i = new Intent(context, UserProfile_ViewHandler.class);
            i.putExtra(UserProfile_ViewHandler.REQUEST_CODE, userName);
            context.startActivity(i);

        }
    }
}
