package training.com.tplayer.ui.entity;

import android.os.Parcel;

/**
 * Created by ThoNH on 4/13/2017.
 */

public class HotSongOnlEntity extends BasicSongOnlEntity {
    public String data_id;
    public String data_code ;
    public String image ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.data_id);
        dest.writeString(this.data_code);
        dest.writeString(this.image);
    }

    public HotSongOnlEntity() {
    }

    protected HotSongOnlEntity(Parcel in) {
        super(in);
        this.data_id = in.readString();
        this.data_code = in.readString();
        this.image = in.readString();
    }

    public static final Creator<HotSongOnlEntity> CREATOR = new Creator<HotSongOnlEntity>() {
        @Override
        public HotSongOnlEntity createFromParcel(Parcel source) {
            return new HotSongOnlEntity(source);
        }

        @Override
        public HotSongOnlEntity[] newArray(int size) {
            return new HotSongOnlEntity[size];
        }
    };
}
