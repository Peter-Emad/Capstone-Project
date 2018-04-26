package masro2a.udacity.com.masro2aapp.post_details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import masro2a.udacity.com.masro2aapp.R;
import masro2a.udacity.com.masro2aapp.common.base.BaseFragment;
import masro2a.udacity.com.masro2aapp.common.helpers.AppPreferences;
import masro2a.udacity.com.masro2aapp.common.helpers.Constants;
import masro2a.udacity.com.masro2aapp.models.Post;

/**
 * Created by peter on 26/04/18.
 */

public class PostDetailsFragment extends BaseFragment implements PostDetailsView {

    private Context context;
    private ImageView imgCar;
    private TextView txtCarMake, txtCarModel, txtCarLocation, txtNotes, txtContactInfo;
    private PostDetailsPresenter postDetailsPresenter;


    public static PostDetailsFragment newInstance(Post post) {
        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.GeneralKeys.POST_KEY, post);
        postDetailsFragment.setArguments(bundle);
        return postDetailsFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        postDetailsPresenter = new PostDetailsPresenter(context, this);
        postDetailsPresenter.setPostDetails(getArguments());
        setPostDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        imgCar = v.findViewById(R.id.imgCar);
        txtCarMake = v.findViewById(R.id.txtCarMake);
        txtCarModel = v.findViewById(R.id.txtCarModel);
        txtCarLocation = v.findViewById(R.id.txtCarLocation);
        txtNotes = v.findViewById(R.id.txtNotes);
        txtContactInfo = v.findViewById(R.id.txtContactInfo);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void showProgress(boolean show) {

    }

    private void setPostDetails() {
        if (postDetailsPresenter.getPost() != null) {
            saveViewedPost();
            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostImage()))
                Picasso.with(context).load(postDetailsPresenter.getPost().getPostImage())
                        .placeholder(R.drawable.car_placeholder)
                        .error(R.drawable.car_placeholder)
                        .into(imgCar);
            else
                imgCar.setBackground(context.getResources().getDrawable(R.drawable.car_placeholder));

            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostModel()))
                txtCarModel.setText(context.getString(R.string.details_car_model, postDetailsPresenter.getPost().getPostModel()));
            else
                txtCarModel.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostMake()))
                txtCarMake.setText(context.getString(R.string.details_car_make, postDetailsPresenter.getPost().getPostMake()));
            else
                txtCarMake.setVisibility(View.GONE);


            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostAddress()))
                txtCarLocation.setText(context.getString(R.string.details_car_location, postDetailsPresenter.getPost().getPostAddress()));
            else
                txtCarLocation.setVisibility(View.GONE);


            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostNotes()))
                txtNotes.setText(context.getString(R.string.details_notes, postDetailsPresenter.getPost().getPostNotes()));
            else
                txtNotes.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostContactInfo()))
                txtContactInfo.setText(context.getString(R.string.details_contact_info, postDetailsPresenter.getPost().getPostContactInfo()));
            else
                txtContactInfo.setVisibility(View.GONE);
        }
    }

    private void saveViewedPost() {

        if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostMake()))
            AppPreferences.setString(Constants.GeneralKeys.LAST_CAR_MAKE_VIEWED, postDetailsPresenter.getPost().getPostMake(), context);
        else
            AppPreferences.clearKey(Constants.GeneralKeys.LAST_CAR_MAKE_VIEWED, context);

        if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostModel()))
            AppPreferences.setString(Constants.GeneralKeys.LAST_CAR_MODEL_VIEWED, postDetailsPresenter.getPost().getPostModel(), context);
        else
            AppPreferences.clearKey(Constants.GeneralKeys.LAST_CAR_MODEL_VIEWED, context);

        if (!TextUtils.isEmpty(postDetailsPresenter.getPost().getPostAddress()))
            AppPreferences.setString(Constants.GeneralKeys.LAST_CAR_ADDRESS_VIEWED, postDetailsPresenter.getPost().getPostAddress(), context);
        else
            AppPreferences.clearKey(Constants.GeneralKeys.LAST_CAR_ADDRESS_VIEWED, context);
    }

}
