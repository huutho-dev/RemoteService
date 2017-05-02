package com.remote.communication;

import android.os.Parcel;
import android.os.Parcelable;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class PlaylistEntity extends BaseEntity {
    public int mId;

    public String name;

    public int dateAdded;

    public PlaylistEntity() {

    }

    public PlaylistEntity(int mId, String name, int dateAdded) {
        this.mId = mId;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    public PlaylistEntity(Builder builder) {
        this.mId = builder.mId;
        this.name = builder.name;
        this.dateAdded = builder.dateAdded;
    }

    @Override
    public String toString() {
        return "PlaylistEntity{" +
                "mId=" + mId +
                ", name='" + name + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }

    public static class Builder {
        private int mId;

        private String name;

        private int dateAdded;

        public Builder setMId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDateAdded(int date) {
            this.dateAdded = date;
            return this;
        }

        public PlaylistEntity build() {
            return new PlaylistEntity(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.name);
        dest.writeInt(this.dateAdded);
    }

    protected PlaylistEntity(Parcel in) {
        this.mId = in.readInt();
        this.name = in.readString();
        this.dateAdded = in.readInt();
    }

    public static final Parcelable.Creator<PlaylistEntity> CREATOR = new Parcelable.Creator<PlaylistEntity>() {
        @Override
        public PlaylistEntity createFromParcel(Parcel source) {
            return new PlaylistEntity(source);
        }

        @Override
        public PlaylistEntity[] newArray(int size) {
            return new PlaylistEntity[size];
        }
    };
}
