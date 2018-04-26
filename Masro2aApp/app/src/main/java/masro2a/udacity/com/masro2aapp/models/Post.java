package masro2a.udacity.com.masro2aapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by peter on 25/04/18.
 */

public class Post implements Parcelable {
    private String postImage;
    private String postMake;
    private String postModel;
    private double postLat;
    private double postLong;
    private String postNotes;
    private String postContactInfo;
    private String postAddress;


    public Post() {

    }

    protected Post(Parcel in) {
        postImage = in.readString();
        postMake = in.readString();
        postModel = in.readString();
        postLat = in.readDouble();
        postLong = in.readDouble();
        postNotes = in.readString();
        postContactInfo = in.readString();
        postAddress = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostMake() {
        return postMake;
    }

    public void setPostMake(String postMake) {
        this.postMake = postMake;
    }

    public String getPostModel() {
        return postModel;
    }

    public void setPostModel(String postModel) {
        this.postModel = postModel;
    }


    public String getPostNotes() {
        return postNotes;
    }

    public void setPostNotes(String postNotes) {
        this.postNotes = postNotes;
    }

    public String getPostContactInfo() {
        return postContactInfo;
    }

    public void setPostContactInfo(String postContactInfo) {
        this.postContactInfo = postContactInfo;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public double getPostLat() {
        return postLat;
    }

    public void setPostLat(double postLat) {
        this.postLat = postLat;
    }

    public double getPostLong() {
        return postLong;
    }

    public void setPostLong(double postLong) {
        this.postLong = postLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(postImage);
        parcel.writeString(postMake);
        parcel.writeString(postModel);
        parcel.writeDouble(postLat);
        parcel.writeDouble(postLong);
        parcel.writeString(postNotes);
        parcel.writeString(postContactInfo);
        parcel.writeString(postAddress);
    }
}
