package training.com.tplayer.ui.entity;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 13/04/2017.
 */

public class DummyEntity extends BaseEntity {
    public String mTitle;
    public int mImage;
    public String mDesc;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mTitle);
        dest.writeInt(this.mImage);
        dest.writeString(this.mDesc);
    }

    public DummyEntity(String mTitle, int mImage, String mDesc) {
        this.mDesc = mDesc;
        this.mImage = mImage;
        this.mTitle = mTitle;

    }

    protected DummyEntity(Parcel in) {
        super(in);
        this.mTitle = in.readString();
        this.mImage = in.readInt();
        this.mDesc = in.readString();
    }

    public static final Creator<DummyEntity> CREATOR = new Creator<DummyEntity>() {
        @Override
        public DummyEntity createFromParcel(Parcel source) {
            return new DummyEntity(source);
        }

        @Override
        public DummyEntity[] newArray(int size) {
            return new DummyEntity[size];
        }
    };
}
