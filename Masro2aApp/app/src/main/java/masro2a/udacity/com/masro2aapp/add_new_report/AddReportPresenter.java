package masro2a.udacity.com.masro2aapp.add_new_report;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import masro2a.udacity.com.masro2aapp.common.base.BasePresenter;
import masro2a.udacity.com.masro2aapp.common.helpers.Constants;
import masro2a.udacity.com.masro2aapp.models.Post;

/**
 * Created by peter on 23/04/18.
 */

public class AddReportPresenter extends BasePresenter {

    private Context context;
    private AddReportView addReportView;
    private DatabaseReference databasePosts;
    private StorageReference storagePosts;
    private Uri carImageUrl = null;
    private Post post;


    public AddReportPresenter(Context context, AddReportView addReportView) {
        this.context = context;
        this.addReportView = addReportView;
        databasePosts = FirebaseDatabase.getInstance().getReference();
        storagePosts = FirebaseStorage.getInstance().getReference();
    }

    public boolean validatePost(int spSelectedCarMakePosition, String carModel, LatLng carLatLng) {
        boolean isCarMakeSelected = true;
        boolean isValidCarModel = true;
        boolean isValidCarLocation = true;

        if (spSelectedCarMakePosition == Constants.GeneralKeys.NOT_SELECTED_CAR_MAKE_POSITION)
            isCarMakeSelected = false;

        if (TextUtils.isEmpty(carModel))
            isValidCarModel = false;

        if (carLatLng == null)
            isValidCarLocation = false;

        addReportView.showValidationErrors(isCarMakeSelected, isValidCarModel, isValidCarLocation);

        return isCarMakeSelected && isValidCarLocation && isValidCarModel;
    }

    public void addPost(Uri carImage64, String carMake, String carModel, LatLng carLatLng, String notes, String contactInfo, String carAddress) {
        addReportView.showProgress(true);
        final String postId = databasePosts.push().getKey();
        post = new Post();

        if (!TextUtils.isEmpty(carMake))
            post.setPostMake(carMake);

        if (!TextUtils.isEmpty(carModel))
            post.setPostModel(carModel);

        if (carLatLng != null)

        {
            post.setPostLat(carLatLng.latitude);
            post.setPostLong(carLatLng.longitude);
        }

        if (!TextUtils.isEmpty(notes))
            post.setPostNotes(notes);

        if (!TextUtils.isEmpty(contactInfo))
            post.setPostContactInfo(contactInfo);

        if (!TextUtils.isEmpty(carAddress))
            post.setPostAddress(carAddress);


        StorageReference storageReference = storagePosts.child(postId);
        storageReference.putFile(carImage64)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        carImageUrl = taskSnapshot.getDownloadUrl();
                        post.setPostImage(String.valueOf(carImageUrl));
                        addPostToFirebase(postId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        addPostToFirebase(postId);
                    }
                });
    }

    private void addPostToFirebase(String postId) {

        databasePosts.child(postId).setValue(post);
        addReportView.showProgress(false);
        addReportView.onAddSuccess();
    }
}

