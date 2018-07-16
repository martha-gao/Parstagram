package me.martha_gao.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import me.martha_gao.parstagram.model.Post;


public class TimelineFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.

    private RecyclerView timelineRecycler;
    ArrayList<Post> posts;
    private PostAdapter postAdapter;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_timeline, parent, false);

        // Defines the xml file for the fragment
        return view;

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // find the RecyclerView
        timelineRecycler = view.findViewById(R.id.rvPosts);
        // construct the adapter from this data source
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(posts);

        // RecyclerView setup (layout manager, use adapter)
        timelineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        timelineRecycler.setAdapter(postAdapter);
        loadTopPosts();


        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                loadTopPosts();
                swipeContainer.setRefreshing(false);
                // once the network request has completed successfully.
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Setup any handles to view objects here
        profileImage = view.findViewById(R.id.ivProfileImage);

    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    postAdapter.clear();
                    posts.clear();
                    posts.addAll(0, objects);
                    postAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }


}
