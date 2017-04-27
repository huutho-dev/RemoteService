package com.remote.communication;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class ArtistEntity extends BaseEntity {
    public int mId ;

    public int id ;

    public String author;

    public int numberOfAlbum ;

    public int numberOfTrack ;



    public ArtistEntity(){

    }

    public ArtistEntity(Builder builder){
        this.mId = builder.mId;
        this.id = builder.id;
        this.author = builder.artist;
        this.numberOfAlbum = builder.numberOfAlbum;
        this.numberOfTrack = builder.numberOfTrack;
    }



    @Override
    public String toString() {
        return "ArtistEntity{" +
                "mId=" + mId +
                ", id=" + id +
                ", author='" + author + '\'' +
                ", numberOfAlbum=" + numberOfAlbum +
                ", numberOfTrack=" + numberOfTrack +
                '}';
    }

    public static class Builder {
        private int mId ;

        private int id ;

        private String artist ;

        private int numberOfAlbum ;

        private int numberOfTrack ;

        public Builder setMId(int id){
            this.mId = id;
            return this;
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setArtist(String artist){
            this.artist = artist;
            return this;
        }

        public Builder setNumberOfAlbum(int number){
            this.numberOfAlbum = number ;
            return this;
        }

        public Builder setNumberOfTrack(int number){
            this.numberOfTrack = number;
            return this;
        }

        public ArtistEntity build(){
            return new ArtistEntity(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mId);
        dest.writeInt(this.id);
        dest.writeString(this.author);
        dest.writeInt(this.numberOfAlbum);
        dest.writeInt(this.numberOfTrack);
    }

    protected ArtistEntity(Parcel in) {
        super(in);
        this.mId = in.readInt();
        this.id = in.readInt();
        this.author = in.readString();
        this.numberOfAlbum = in.readInt();
        this.numberOfTrack = in.readInt();
    }

    public static final Creator<ArtistEntity> CREATOR = new Creator<ArtistEntity>() {
        @Override
        public ArtistEntity createFromParcel(Parcel source) {
            return new ArtistEntity(source);
        }

        @Override
        public ArtistEntity[] newArray(int size) {
            return new ArtistEntity[size];
        }
    };
}
