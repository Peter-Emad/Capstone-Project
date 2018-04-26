package masro2a.udacity.com.masro2aapp.posts_listing;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.common.base.BaseFragment;
import masro2a.udacity.com.masro2aapp.models.Post;
import masro2a.udacity.com.masro2aapp.post_details.PostDetailsFragment;


public class ListingFragment extends BaseFragment implements ListingView, PostsAdapter.PostsAdapterInteraction {

    private Context context;
    private ListingPresenter listingPresenter;
    private NestedScrollView sclPostsContainer;
    private RecyclerView rvPostsListing;
    private ProgressBar postsListingProgressBar;
    private PostsAdapter postsAdapter;
    private TextView txtError;

    public static ListingFragment newInstance() {
        return new ListingFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        listingPresenter = new ListingPresenter(context, this);
        initRv();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listingPresenter.executePostsTask();
    }

    @Override
    protected void initializeViews(View v) {
        sclPostsContainer = v.findViewById(R.id.sclPostsContainer);
        rvPostsListing = v.findViewById(R.id.rvPostsListing);
        postsListingProgressBar = v.findViewById(R.id.postsListingProgressBar);
        txtError = v.findViewById(R.id.txtError);

    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void showProgress(boolean show) {
        sclPostsContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        postsListingProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void initRv() {
        postsAdapter = new PostsAdapter(context, listingPresenter.getPostList(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPostsListing.setLayoutManager(linearLayoutManager);
        rvPostsListing.setHasFixedSize(true);
        rvPostsListing.setNestedScrollingEnabled(false);
        rvPostsListing.setAdapter(postsAdapter);
    }

    @Override
    public void onPostClick(Post post) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, PostDetailsFragment.newInstance(post)).addToBackStack(null).commit();
    }

    @Override
    public void updateAdapter() {
        if (postsAdapter != null)
            postsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        sclPostsContainer.setVisibility(View.GONE);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(message);
    }


}
