package com.remote.communication;

import com.remote.communication.Song;

interface IMyAidlInterface {

   void setSong(in Song book);

   void setPlayList(in List<Song> playLists);

   int getDuration();

   int getCurrentPosition();

   void setPosition(int value);

   void forward();

   void backward();

   void setVolume(float value);

   boolean playPause();

   void repeat();

}
