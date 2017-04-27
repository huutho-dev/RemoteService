package training.com.tplayer.ui.entity;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by hnc on 14/04/2017.
 */

public class BasicSongOnlEntity extends BaseEntity {
    public int order ;
    public String link ;
    public String title;
    public String artist ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.order);
        dest.writeString(this.link);
        dest.writeString(this.title);
        dest.writeString(this.artist);
    }

    public BasicSongOnlEntity() {
    }

    protected BasicSongOnlEntity(Parcel in) {
        super(in);
        this.order = in.readInt();
        this.link = in.readString();
        this.title = in.readString();
        this.artist = in.readString();
    }

    public static final Creator<BasicSongOnlEntity> CREATOR = new Creator<BasicSongOnlEntity>() {
        @Override
        public BasicSongOnlEntity createFromParcel(Parcel source) {
            return new BasicSongOnlEntity(source);
        }

        @Override
        public BasicSongOnlEntity[] newArray(int size) {
            return new BasicSongOnlEntity[size];
        }
    };

    @Override
    public String toString() {
        return "BasicSongOnlEntity{" +
                "order=" + order +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", author='" + artist + '\'' +
                '}';
    }
}
