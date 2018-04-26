package masro2a.udacity.com.masro2aapp.posts_listing;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import masro2a.udacity.com.masro2aapp.common.base.BasePresenter;
import masro2a.udacity.com.masro2aapp.models.Post;

/**
 * Created by peter on 23/04/18.
 */

public class ListingPresenter extends BasePresenter {
    private Context context;
    private ListingView listingView;
    private List<Post> postList = new ArrayList<>();
    private DatabaseReference databasePosts;

    public ListingPresenter(Context context, ListingView listingView) {
        this.context = context;
        this.listingView = listingView;
        databasePosts = FirebaseDatabase.getInstance().getReference();
    }

    public List<Post> getPostList() {
        return postList;
    }


    public void executePostsTask() {
        new RetrievePostsTask().execute();
    }


    class RetrievePostsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            databasePosts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    postList.clear();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Post post = postSnapshot.getValue(Post.class);
                        postList.add(post);
                    }
                    listingView.showProgress(false);
                    listingView.updateAdapter();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listingView.onError(databaseError.getMessage());
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            listingView.showProgress(true);
        }

    }
}
