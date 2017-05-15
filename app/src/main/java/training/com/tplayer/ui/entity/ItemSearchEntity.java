package training.com.tplayer.ui.entity;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 15/05/2017.
 */

public class ItemSearchEntity extends BaseEntity {

    @SerializedName("id")
    public String id ;

    @SerializedName("thumb")
    public String mImage ;

    @SerializedName("name")
    public String mTitle ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mImage);
        dest.writeString(this.mTitle);
    }

    public ItemSearchEntity(String title, String image) {
        this.mTitle = title;
        this.mImage = image;
    }

    protected ItemSearchEntity(Parcel in) {
        super(in);
        this.mImage = in.readString();
        this.mTitle = in.readString();
    }

    public static final Creator<ItemSearchEntity> CREATOR = new Creator<ItemSearchEntity>() {
        @Override
        public ItemSearchEntity createFromParcel(Parcel source) {
            return new ItemSearchEntity(source);
        }

        @Override
        public ItemSearchEntity[] newArray(int size) {
            return new ItemSearchEntity[size];
        }
    };

    @Override
    public String toString() {
        return "ItemSearchEntity{" +
                "id='" + id + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
