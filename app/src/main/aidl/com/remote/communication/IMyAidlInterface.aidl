package com.remote.communication;

import com.remote.communication.MediaEntity;

interface IMyAidlInterface {

   void setSong(in MediaEntity song);

   void startSongPosition(int position);

   void setPlayList(in List<MediaEntity> playLists);

   int getDuration();

   int getCurrentPosition();

   void setPosition(int value);

   void forward();

   void backward();

   void setVolume(float value);

   boolean playPause();

   void repeat();

   void addNextNowPlaying(in MediaEntity entity);

   void addNowPlaying(in MediaEntity entity) ;

   void addListNowPlaying(in List<MediaEntity> entity) ;
}
