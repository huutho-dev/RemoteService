package training.com.tplayer.ui.entity;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 17/04/2017.
 */

public class DataCodeEntity extends BaseEntity {
    public String dataCode ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.dataCode);
    }

    public DataCodeEntity(String dataCode) {
        this.dataCode = dataCode;
    }

    public DataCodeEntity(Parcel in) {
        super(in);
        this.dataCode = in.readString();
    }

    public static final Creator<DataCodeEntity> CREATOR = new Creator<DataCodeEntity>() {
        @Override
        public DataCodeEntity createFromParcel(Parcel source) {
            return new DataCodeEntity(source);
        }

        @Override
        public DataCodeEntity[] newArray(int size) {
            return new DataCodeEntity[size];
        }
    };
}
