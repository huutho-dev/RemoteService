package com.remote.communication;

import com.remote.communication.MediaEntity;

interface IMyAidlInterface {

   int getAudioSS();

   void setBassBoost(int id);

   void setSong(in MediaEntity song);

   void startSongPosition(int position);

   void startSong(in MediaEntity entity);

   void setPlayList(in List<MediaEntity> playLists);

   int getDuration();

   boolean isPlayerStop();

   boolean isPlayerPlaying ();

   int getCurrentPosition();

   MediaEntity getCurrentSong();

   List<MediaEntity> getPlaylist();

   void setPosition(int value);

   void forward();

   void backward();

   void setVolume(float value);

   void setShuffle(boolean isShuffle);

   boolean playPause();

   void repeat(int repeatType);

   void addNextPlaying(in MediaEntity entity) ;

   void addEndPlaying(in MediaEntity entity);

   void addListNextPlaying(in List<MediaEntity> entity) ;

   void addToEndListNowPlaying(in List<MediaEntity> entity) ;
}
