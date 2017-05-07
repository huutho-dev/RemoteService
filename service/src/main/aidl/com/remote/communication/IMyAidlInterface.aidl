package com.remote.communication;
import com.remote.communication.MediaEntity;

interface IMyAidlInterface {

   void setSong(in MediaEntity song);

   void startSongPosition(int position);

   void startSong(in MediaEntity entity);

   void setPlayList(in List<MediaEntity> playLists);

   int getDuration();

   int getCurrentPosition();

   void setPosition(int value);

   void forward();

   void backward();

   void setVolume(float value);

   boolean playPause();

   void repeat();

   void addNextPlaying(in MediaEntity entity) ;

   void addEndPlaying(in MediaEntity entity);

   void addListNextPlaying(in List<MediaEntity> entity) ;

   void addToEndListNowPlaying(in List<MediaEntity> entity) ;
}
