package me.martha_gao.parstagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import me.martha_gao.parstagram.model.Post;


public class CameraFragment extends Fragment {

    ImageView ivPhoto;
    private Button postButton;
    private EditText caption;
    private File selectedPhotoFile;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.fragment_camera, parent, false);
        ivPhoto = v.findViewById(R.id.ivPhoto);
        return v;
    }

//     This event is triggered soon after onCreateView().
//     Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        postButton = view.findViewById(R.id.btPost);
        caption = view.findViewById(R.id.etCaption);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = caption.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final ParseFile file = new ParseFile(selectedPhotoFile);

                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // create post after photo has been uploaded.
                            final Post post = new Post();
                            post.setUser(user);
                            post.setImage(file);
                            post.setDescription(description);

                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getContext(), "Post successful! :)", Toast.LENGTH_SHORT).show();
                                    } else {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void setSelectedPhoto(File file) {
        selectedPhotoFile = file;

        Bitmap rawTakenImage = BitmapFactory.decodeFile(file.getAbsolutePath());
        Bitmap resizedBitmap = com.codepath.tejen.myapplication.BitmapScaler.scaleToFitWidth(rawTakenImage, 400);
        ivPhoto.setImageBitmap(resizedBitmap);
    }

}
