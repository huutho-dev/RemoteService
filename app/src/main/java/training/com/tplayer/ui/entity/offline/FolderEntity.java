package training.com.tplayer.ui.entity.offline;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class FolderEntity  extends BaseEntity {
    public String mFolderName;

    public String mPath;

    public int mNumberSong ;

    public FolderEntity(){

    }

    public FolderEntity(String mFolderName, String mPath, int mNumberSong) {
        this.mFolderName = mFolderName;
        this.mPath = mPath;
        this.mNumberSong = mNumberSong;
    }


    @Override
    public String toString() {
        return "FolderEntity{" +
                "mFolderName='" + mFolderName + '\'' +
                ", mPath='" + mPath + '\'' +
                ", mNumberSong='" + mNumberSong + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mFolderName);
        dest.writeString(this.mPath);
        dest.writeInt(this.mNumberSong);
    }

    protected FolderEntity(Parcel in) {
        super(in);
        this.mFolderName = in.readString();
        this.mPath = in.readString();
        this.mNumberSong = in.readInt();
    }

    public static final Creator<FolderEntity> CREATOR = new Creator<FolderEntity>() {
        @Override
        public FolderEntity createFromParcel(Parcel source) {
            return new FolderEntity(source);
        }

        @Override
        public FolderEntity[] newArray(int size) {
            return new FolderEntity[size];
        }
    };
}
