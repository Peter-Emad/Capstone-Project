package masro2a.udacity.com.masro2aapp.post_details;

import android.content.Context;
import android.os.Bundle;

import masro2a.udacity.com.masro2aapp.common.base.BasePresenter;
import masro2a.udacity.com.masro2aapp.common.helpers.Constants;
import masro2a.udacity.com.masro2aapp.models.Post;

/**
 * Created by peter on 26/04/18.
 */

public class PostDetailsPresenter extends BasePresenter {

    private Context context;
    private PostDetailsView postDetailsView;
    private Post post;

    public PostDetailsPresenter(Context context, PostDetailsView postDetailsView) {
        this.context = context;
        this.postDetailsView = postDetailsView;
    }

    public Post getPost() {
        return post;
    }

    void setPostDetails(Bundle bundle) {
        if (bundle.getParcelable(Constants.GeneralKeys.POST_KEY) != null)
            post = bundle.getParcelable(Constants.GeneralKeys.POST_KEY);

    }

}
