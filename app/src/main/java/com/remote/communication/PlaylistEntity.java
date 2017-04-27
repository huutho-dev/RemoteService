package com.remote.communication;

import android.os.Parcel;
import android.os.Parcelable;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class PlaylistEntity extends BaseEntity {
    public int mId;

    public int id ;

    public String data;

    public String name ;

    public int dateAdded ;

    public PlaylistEntity(){

    }

    public PlaylistEntity(Builder builder) {
        this.mId = builder.mId;
        this.id = builder.id;
        this.data = builder.data;
        this.name = builder.name;
        this.dateAdded = builder.dateAdded;
    }

    @Override
    public String toString() {
        return "PlaylistEntity{" +
                "mId=" + mId +
                ", id=" + id +
                ", data='" + data + '\'' +
                ", name='" + name + '\'' +
                ", dateAdded=" + dateAdded +
                '}';
    }

    public static class Builder {
        private int mId;

        private int id ;

        private String data;

        private String name ;

        private int dateAdded ;

        public Builder setMId(int id){
            this.mId = id;
            return this;
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setData(String data){
            this.data = data;
            return this ;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setDateAdded(int date){
            this.dateAdded = date;
            return this;
        }
        public PlaylistEntity build(){
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
        dest.writeInt(this.id);
        dest.writeString(this.data);
        dest.writeString(this.name);
        dest.writeInt(this.dateAdded);
    }

    protected PlaylistEntity(Parcel in) {
        this.mId = in.readInt();
        this.id = in.readInt();
        this.data = in.readString();
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
