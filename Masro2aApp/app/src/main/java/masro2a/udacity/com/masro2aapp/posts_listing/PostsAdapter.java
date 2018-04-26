package masro2a.udacity.com.masro2aapp.posts_listing;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.models.Post;

/**
 * Created by peter on 26/04/18.
 */

public class PostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Post> postList;
    private PostsAdapterInteraction postsAdapterInteraction;


    public PostsAdapter(Context context, List<Post> postList, PostsAdapterInteraction postsAdapterInteraction) {
        this.context = context;
        this.postList = postList;
        this.postsAdapterInteraction = postsAdapterInteraction;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_rv_post, parent, false);
        return new PostsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (!TextUtils.isEmpty(postList.get(position).getPostImage()))
                Picasso.with(context).load(postList.get(position).getPostImage())
                        .error(R.drawable.car_placeholder)
                        .placeholder(R.drawable.car_placeholder)
                        .into(((ViewHolder) holder).imgCarPost);
            else
                ((ViewHolder) holder).imgCarPost.setBackground(context.getResources().getDrawable(R.drawable.car_placeholder));

            if (!TextUtils.isEmpty(postList.get(position).getPostAddress()))
                ((ViewHolder) holder).txtCarLocation.setText(postList.get(position).getPostAddress());

            if (!TextUtils.isEmpty(postList.get(position).getPostMake()))
                ((ViewHolder) holder).txtCarMake.setText(postList.get(position).getPostMake());

            if (!TextUtils.isEmpty(postList.get(position).getPostModel()))
                ((ViewHolder) holder).txtCarModel.setText(postList.get(position).getPostModel());

            ((ViewHolder) holder).cvPostContanier.setTag(position);
            ((ViewHolder) holder).cvPostContanier.setOnClickListener(cvPostContainerClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface PostsAdapterInteraction {

        void onPostClick(Post post);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCarPost;
        private TextView txtCarMake, txtCarModel, txtCarLocation;
        private CardView cvPostContanier;

        public ViewHolder(View view) {
            super(view);
            imgCarPost = view.findViewById(R.id.imgCarPost);
            txtCarMake = view.findViewById(R.id.txtCarMake);
            txtCarModel = view.findViewById(R.id.txtCarModel);
            txtCarLocation = view.findViewById(R.id.txtCarLocation);
            cvPostContanier = view.findViewById(R.id.cvPostContanier);

        }
    }

    private View.OnClickListener cvPostContainerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            if (postsAdapterInteraction != null)
                postsAdapterInteraction.onPostClick(postList.get(position));
        }
    };

}