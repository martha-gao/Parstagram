package me.martha_gao.parstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.martha_gao.parstagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;

    private List<Post> mPosts;
    // pass in the Tweets array in the constructor
    public PostAdapter(List<Post> posts) { mPosts = posts;
    }

    // for each row, inflate the layout (with data) and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data according to the position
        Post post = mPosts.get(position);

        // populate the views according to this data
        holder.usernameTop.setText(post.getUser().getUsername());
        holder.usernameBottom.setText(post.getUser().getUsername());
        holder.caption.setText(post.getDescription());
        if(post.getUser().getParseFile("profileImage") != null)
            Glide.with(context).load(post.getUser().getParseFile("profileImage")).apply(RequestOptions.circleCropTransform()).into(holder.userPhoto);
        Glide.with(context).load(post.getImage().getUrl()).into(holder.postPhoto);
        holder.relativeTime.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    // create ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameTop;
        public TextView usernameBottom;
        public ImageView userPhoto;
        public ImageView postPhoto;
        public TextView caption;

        public TextView relativeTime;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups

            usernameTop = itemView.findViewById(R.id.tvUsernameTop);
            usernameBottom = itemView.findViewById(R.id.tvUsernameBottom);
            userPhoto = itemView.findViewById(R.id.ivProfileImage);
            postPhoto = itemView.findViewById(R.id.ivPostPhoto);
            caption = itemView.findViewById(R.id.tvCaption);

            relativeTime = itemView.findViewById(R.id.tvRelativeTime);
        }
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}