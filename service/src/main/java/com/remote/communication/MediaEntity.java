package com.remote.communication;

import android.os.Parcel;
import android.os.Parcelable;

import training.com.BaseEntity;


/**
 * Created by ThoNH on 4/27/2017.
 */

public class MediaEntity extends BaseEntity {
    public int mId;

    public int id;

    public String data;

    public String displayName;

    public int size;

    public String mimeType;

    public int dateAdded;

    public String title;

    public int duration;

    public int artistId;

    public String artist;

    public String album;

    public int albumId;

    public boolean isFavorite;

    public String lyric;

    public String art;

    public boolean isPlaying ;


    public MediaEntity(){

    }

    public MediaEntity(Builder builder) {
        this.mId = builder.mId;
        this.id = builder.id;
        this.data = builder.data;
        this.displayName = builder.displayName;
        this.size = builder.size;
        this.mimeType = builder.mimeType;
        this.dateAdded = builder.dateAdded;
        this.title = builder.title;
        this.duration = builder.duration;
        this.artistId = builder.artistId;
        this.artist = builder.artist;
        this.album = builder.album;
        this.albumId = builder.albumId;
        this.isFavorite = builder.isFavorite;
        this.lyric = builder.lyric;
        this.art = builder.art;
    }


    public static class Builder {
        private int mId;

        private int id;

        private String data;

        private String displayName;

        private int size;

        private String mimeType;

        private int dateAdded;

        private String title;

        private int duration;

        private int artistId;

        private String artist;

        private String album;

        private int albumId;

        private boolean isFavorite;

        private String lyric;

        private String art;

        public Builder setMId(int mId) {
            this.mId = mId;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Builder setDisplayName(String name) {
            this.displayName = name;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setMIMEType(String mime) {
            this.mimeType = mime;
            return this;
        }

        public Builder setAdded(int dateAdded) {
            this.dateAdded = dateAdded;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setArtistId(int id) {
            this.artistId = id;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }

        public Builder setAlbumId(int id) {
            this.albumId = id;
            return this;
        }

        public Builder setFavorite(boolean isFav) {
            this.isFavorite = isFav;
            return this;
        }

        public Builder setLyric(String lyricPath) {
            this.lyric = lyricPath;
            return this;
        }

        public Builder setArt(String art) {
            this.art = art;
            return this;
        }

        public MediaEntity builder() {
            return new MediaEntity(this);
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
        dest.writeString(this.data);
        dest.writeString(this.displayName);
        dest.writeInt(this.size);
        dest.writeString(this.mimeType);
        dest.writeInt(this.dateAdded);
        dest.writeString(this.title);
        dest.writeInt(this.duration);
        dest.writeInt(this.artistId);
        dest.writeString(this.artist);
        dest.writeString(this.album);
        dest.writeInt(this.albumId);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.lyric);
        dest.writeString(this.art);
    }

    protected MediaEntity(Parcel in) {
        super(in);
        this.mId = in.readInt();
        this.id = in.readInt();
        this.data = in.readString();
        this.displayName = in.readString();
        this.size = in.readInt();
        this.mimeType = in.readString();
        this.dateAdded = in.readInt();
        this.title = in.readString();
        this.duration = in.readInt();
        this.artistId = in.readInt();
        this.artist = in.readString();
        this.album = in.readString();
        this.albumId = in.readInt();
        this.isFavorite = in.readByte() != 0;
        this.lyric = in.readString();
        this.art = in.readString();
    }

    public static final Parcelable.Creator<MediaEntity> CREATOR = new Parcelable.Creator<MediaEntity>() {
        @Override
        public MediaEntity createFromParcel(Parcel source) {
            return new MediaEntity(source);
        }

        @Override
        public MediaEntity[] newArray(int size) {
            return new MediaEntity[size];
        }
    };

    @Override
    public String toString() {
        return "MediaEntity{" +
                "mId=" + mId +
                ", id=" + id +
                ", data='" + data + '\'' +
                ", displayName='" + displayName + '\'' +
                ", size=" + size +
                ", mimeType='" + mimeType + '\'' +
                ", dateAdded=" + dateAdded +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", artistId=" + artistId +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", albumId=" + albumId +
                ", isFavorite=" + isFavorite +
                ", lyric='" + lyric + '\'' +
                ", art='" + art + '\'' +
                '}';
    }
}
