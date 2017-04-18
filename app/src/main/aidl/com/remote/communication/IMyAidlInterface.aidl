package com.remote.communication;

import com.remote.communication.Song;

interface IMyAidlInterface {

   void setSong(in Song book);

   void setPlayList(in List<Song> playLists);

   int getDuration();

   int getCurrentPosition();
}
