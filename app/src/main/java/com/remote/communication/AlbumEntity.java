package com.remote.communication;

import android.os.Parcel;
import android.os.Parcelable;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class AlbumEntity extends BaseEntity {
    public int mId;

    public int id;

    public String album;

    public String artist;

    public int artistId;

    public int numberOfSong;

    public String albumArt;

    public AlbumEntity() {

    }

    public AlbumEntity(Builder builder) {
        this.mId = builder.mId;
        this.id = builder.id;
        this.album = builder.album;
        this.artist = builder.artist;
        this.artistId = builder.artistId;
        this.numberOfSong = builder.numberOfSong;
        this.albumArt = builder.albumArt;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "mId=" + mId +
                ", id=" + id +
                ", album='" + album + '\'' +
                ", author='" + artist + '\'' +
                ", artistId=" + artistId +
                ", numberOfSong=" + numberOfSong +
                ", albumArt='" + albumArt + '\'' +
                '}';
    }

    public static class Builder {
        private int mId;

        private int id;

        private String album;

        private String artist;

        private int artistId;

        private int numberOfSong;

        private String albumArt;


        public Builder setMId(int id) {
            this.mId = id;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setArtistId(int artistId) {
            this.artistId = artistId;
            return this;
        }

        public Builder setNumberOfSong(int number) {
            this.numberOfSong = number;
            return this;
        }

        public Builder setAlbumArt(String albumArt) {
            this.albumArt = albumArt;
            return this;
        }

        public AlbumEntity build() {
            return new AlbumEntity(this);
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
        dest.writeString(this.album);
        dest.writeString(this.artist);
        dest.writeInt(this.artistId);
        dest.writeInt(this.numberOfSong);
        dest.writeString(this.albumArt);
    }

    protected AlbumEntity(Parcel in) {
        this.mId = in.readInt();
        this.id = in.readInt();
        this.album = in.readString();
        this.artist = in.readString();
        this.artistId = in.readInt();
        this.numberOfSong = in.readInt();
        this.albumArt = in.readString();
    }

    public static final Parcelable.Creator<AlbumEntity> CREATOR = new Parcelable.Creator<AlbumEntity>() {
        @Override
        public AlbumEntity createFromParcel(Parcel source) {
            return new AlbumEntity(source);
        }

        @Override
        public AlbumEntity[] newArray(int size) {
            return new AlbumEntity[size];
        }
    };
}
