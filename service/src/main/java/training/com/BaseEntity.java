package training.com;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hnc on 05/04/2017.
 */

public class BaseEntity implements Parcelable {
    protected BaseEntity(Parcel in) {
    }

    public BaseEntity() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
