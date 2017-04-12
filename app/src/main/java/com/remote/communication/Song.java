package com.remote.communication;

import android.os.Parcel;

import training.com.tplayer.base.BaseEntity;

/**
 * Created by ThoNH on 10/04/2017.
 */

public class Song extends BaseEntity {

    /**
     * Id of song in database of system  (like:  _id=18574)
     */
    public int _id;


    /**
     * Path of song in external storage (like:  /storage/emulated/0/Voice Recorder/Thoại 001.m4a)
     * Using its to convert to Uri for play
     */
    public String _data;


    /**
     * Display name of song (like:  Thoại 001.m4a)
     */
    public String _display_name;


    /**
     * Name of song (like: Thoại 001)
     */
    public String _title;


    /**
     * Duration of song (like : duration=269 s)
     */
    public String _duration;


    /**
     * Lyric of song (.lrc)
     */
    public String _lyric;


    /**
     * Image of song (save as url)
     */
    public String _art;


    /**
     * Genre of song (like : genre_name=<rock>)
     */
    public String _genre_name;


    /**
     * Is favorite
     */
    public boolean _is_favorite;


    /**
     * Name artist of song (like: Sơn Tùng - MTP)
     */
    public String artist_name;


    /**
     * Image of artist (save as url)
     */
    public String artist_art;


    /**
     * Name album of song
     */
    public String album_name;


    /**
     * Image of album
     */
    public String album_art;






    protected Song(Parcel in) {
        super(in);
        this._id = in.readInt();
        this._data = in.readString();
        this._display_name = in.readString();
        this._title = in.readString();
        this._duration = in.readString();
        this._lyric = in.readString();
        this._art = in.readString();
        this._genre_name = in.readString();
        this._is_favorite = in.readByte() != 0;
        this.artist_name = in.readString();
        this.artist_art = in.readString();
        this.album_name = in.readString();
        this.album_art = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this._id);
        dest.writeString(this._data);
        dest.writeString(this._display_name);
        dest.writeString(this._title);
        dest.writeString(this._duration);
        dest.writeString(this._lyric);
        dest.writeString(this._art);
        dest.writeString(this._genre_name);
        dest.writeByte(this._is_favorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.artist_name);
        dest.writeString(this.artist_art);
        dest.writeString(this.album_name);
        dest.writeString(this.album_art);
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

}
