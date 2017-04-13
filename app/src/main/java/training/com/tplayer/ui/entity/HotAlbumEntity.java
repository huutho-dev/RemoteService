package training.com.tplayer.ui.entity;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class HotAlbumEntity extends BaseEntity {
    public String link;
    public String image;
    public String artist;
    public String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.link);
        dest.writeString(this.image);
        dest.writeString(this.artist);
        dest.writeString(this.name);
    }

    public HotAlbumEntity() {
    }

    protected HotAlbumEntity(Parcel in) {
        super(in);
        this.link = in.readString();
        this.image = in.readString();
        this.artist = in.readString();
        this.name = in.readString();
    }

    public static final Creator<HotAlbumEntity> CREATOR = new Creator<HotAlbumEntity>() {
        @Override
        public HotAlbumEntity createFromParcel(Parcel source) {
            return new HotAlbumEntity(source);
        }

        @Override
        public HotAlbumEntity[] newArray(int size) {
            return new HotAlbumEntity[size];
        }
    };

    @Override
    public String toString() {
        return "HotAlbumEntity{" +
                "link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", artist='" + artist + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
