package training.com.tplayerservice;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by HuuTho on 4/4/2017.
 * <p>
 * Reference : https://developer.android.com/reference/android/media/MediaPlayer.html
 */

public class TPlayer extends MediaPlayer {

    private WifiManager.WifiLock wifiLock ;
    /*
    When designing applications that play media in the background,
    the device may go to sleep while your service is running.
     Because the Android system tries to conserve battery while the device is sleeping,
     the system tries to shut off any of the phone's features that are not necessary,
     including the CPU and the WiFi hardware. However,
     if your service is playing or streaming music,
     you want to prevent the system from interfering with your playback.

    In order to ensure that your service continues to run under those conditions,
    you have to use "wake locks." A wake lock is a way to signal to the system
    that your application is using some feature that should stay available even if the phone is idle.
     */

    public TPlayer(Context context) {

    }


    public void setPlayerStreamOverNetwork(Context context) {
         wifiLock = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "mylock");

        wifiLock.acquire();
    }

    // pause or stop you should call
    public void releaseStreamOverNetwork() {
        wifiLock.release();
    }

    /**
     * --->(Idle)
     * <p>
     * After reset(), the object is like being just created.
     * Can be call to restore MediaPlayer Obj to its IDLE state
     */
    public void resetPlayer() {
        super.reset();
    }


    /**
     * ---->(initialized)
     *
     * @param context the Context to use when resolving the Uri
     * @param uri     the Content URI of the data you want to play
     * @throws IllegalStateException if it is called in an invalid state
     *                               Called for transfers a MediaPlayer object in the Idle state to the Initialized state.
     */
    public void setDataSourcePlayer(@NonNull Context context, @NonNull Uri uri)
            {
                try {
                    super.setDataSource(context, uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    /**
     * ---->(initialized)
     * <p>
     * Sets the data source (file-path or http/rtsp URL) to use.
     *
     * @param path the path of the file, or the http/rtsp URL of the stream you want to play
     */
    public void setDataSourcePlayer(String path)
             {
                 try {
                     super.setDataSource(path);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }

    /**
     * ---> Preparing (OnPreparedListener.onPrepared)  ---> Prepared
     * <p>
     * Prepares the player for playback, asynchronously.
     * For streams, you should call prepareAsync(),
     * which returns immediately rather than blocking until enough data has been buffered.
     * <p>
     * This method starts preparing the media in the background and returns immediately
     */
    public void prepareAsyncPlayer() throws IllegalStateException {
        super.prepareAsync();
    }

    /**
     * ---> Prepared
     * Prepares the player for playback, synchronously
     * For files, it is OK to call prepare(),
     * which blocks until MediaPlayer is ready for playback
     * <p>
     * never call it from your application's UI thread because its  take a long time to execute (might involve fetching and decoding media data)
     */
    public void preparePlayer() {
        try {
            prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    /**
     * Seeks to specified time position.(Asynchronuous)
     *
     * @param msec the offset in milliseconds from the start to seek to
     * @throws IllegalStateException if the internal player engine has not been  initialized
     *                               ---> seekTo()
     */
    public void seekToPlayer(int msec) throws IllegalStateException {
        super.seekTo(msec);
    }


    /**
     * ---> Started
     * Starts or resumes playback
     * While in the Started state, the internal player engine calls a user supplied
     * OnBufferingUpdateListener.onBufferingUpdate() callback method allows applications
     * to keep track of the buffering status while streaming audio/video.
     * <p>
     * seekTo()
     * start()
     * <p>
     * ---> stop()---> Stopped
     * ---> pause() ---> Paused
     * <p>
     * ---->(looping == false && onComplete() invoked on OnCompletionListener) ---> PlaybackComplete (if call stop --> stop, if call start --> start from begin)
     */
    public void startPlayer() {
        super.start();
    }


    /**
     * ---> Stopped
     * Stops playback after playback has been stopped or paused.
     * <p>
     * --->prepare() ---> Prepare
     * --->preparAsync() --->Preparing
     */
    public void stopPlayer() {
        super.stop();
    }


    /**
     * ---> Paused
     * Note that the transition from the Started state to the Paused state and vice versa happens asynchronously in the player engine
     * It may take some time before the state is updated in calls to isPlaying(), and it can be a number of seconds in the case of streamed content.
     * --->seekTo()
     * --->stop() ---> Stopped
     * --->start() ---> Started
     */
    public void pausePlayer() {
        super.pause();
    }


    /**
     * After release(), the object is no longer available.
     * -->END
     */
    public void releasePlayer() {
        super.release();
    }


    /**
     * Register a callback to be invoked when an error has happened
     * during an asynchronous operation.
     *
     * @param listener
     */
    public void setOnErrorListenerPlayer(OnErrorListener listener) {
        super.setOnErrorListener(listener);
    }


    /**
     * Register a callback to be invoked when a seek operation has been completed.
     *
     * @param seekCompleteListener
     */
    public void setOnSeekCompletePlayer(OnSeekCompleteListener seekCompleteListener) {
        super.setOnSeekCompleteListener(seekCompleteListener);
    }


    /**
     * Register a callback to be invoked when the status of a network
     * stream's buffer has changed.
     */
    public void setOnBufferingUpdateListenerPlayer(OnBufferingUpdateListener listener) {
        super.setOnBufferingUpdateListener(listener);
    }


    /**
     * Register a callback to be invoked when the end of a media source
     * has been reached during playback.
     */
    public void setOnCompletionListenerPlayer(OnCompletionListener listener) {
        super.setOnCompletionListener(listener);
    }


    /**
     * Register a callback to be invoked when an info/warning is available
     */
    public void setOnInfoListenerPlayer(OnInfoListener listener) {
        super.setOnInfoListener(listener);
    }

    /**
     * Register a callback to be invoked when a track has data available.
     */
    public void setOnTimedTextListenerPlayer(OnTimedTextListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setOnTimedTextListener(listener);
        }
    }


    /**
     * Returns an array of track information.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TrackInfo[] getTrackInfoPlayer() {
        return super.getTrackInfo();
    }


    /**
     * * Adds an external timed text source file.
     * <p>
     * Currently supported format is SubRip with the file extension .srt, case insensitive.
     * Note that a single external timed text source may contain multiple tracks in it.
     * One can find the total number of available tracks using {@link #getTrackInfo()} to see what
     * additional tracks become available after this method call.
     *
     * @param path     The file path of external timed text source file.
     * @param mimeType The mime type of the file. Must be one of the mime types listed above
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addTimedTextSourcePlayer(String path, String mimeType)
            throws IOException, IllegalArgumentException, IllegalStateException {
        super.addTimedTextSource(path, mimeType);
    }


    /**
     * * Adds an external timed text source file (FileDescriptor).
     * <p>
     * It is the caller's responsibility to close the file descriptor.
     * It is safe to do so as soon as this call returns.
     * <p>
     * Currently supported format is SubRip. Note that a single external timed text source may
     * contain multiple tracks in it. One can find the total number of available tracks
     * using {@link #getTrackInfo()} to see what additional tracks become available
     * after this method call.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addTimedTextSourcePlayer(FileDescriptor fd, String mimeType) throws IOException, IllegalArgumentException, IllegalStateException {
        super.addTimedTextSource(fd, mimeType);
    }


    /**
     * Attaches an auxiliary effect to the player.
     * A typical auxiliary effect is a reverberation
     * effect which can be applied on any sound source that directs a certain amount of its
     * energy to this effect. This amount is defined by setAuxEffectSendLevel().
     * See {@link #setAuxEffectSendLevel(float)}.
     * <p>
     * <p>After creating an auxiliary effect (e.g.
     * {@link android.media.audiofx.EnvironmentalReverb}), retrieve its ID with
     * {@link android.media.audiofx.AudioEffect#getId()} and use it when calling this method
     * to attach the player to the effect.
     * <p>
     * <p>To detach the effect from the player, call this method with a null effect id.
     * <p>This method must be called after one of the overloaded <code> setDataSource </code>
     * methods.
     *
     * @param effectId effectId system wide unique id of the effect to attach
     */
    public void attachAuxEffectPlayer(int effectId) {
        super.attachAuxEffect(effectId);
    }


    /**
     * Returns the audio session ID.
     *
     * @return the audio session ID. {@see #setAudioSessionId(int)}
     * Note that the audio session ID is 0 only if a problem occured when the MediaPlayer was contructed.
     */
    public int getAudioSessionIdPlayer() {
        return super.getAudioSessionId();
    }


    /**
     * * Gets the current playback position. (milis)
     */
    public int getCurrentPositionPlayer() {
        return super.getCurrentPosition();
    }


    /**
     * Gets the duration of the file.
     */
    public int getDurationPlayer() {
        return super.getDuration();
    }


    /**
     * Checks whether the MediaPlayer is looping or non-looping.
     *
     * @return true if the MediaPlayer is currently looping, false otherwise
     */
    public boolean isPlayerLooping() {
        return super.isLooping();
    }


    /**
     * Checks whether the MediaPlayer is playing.
     *
     * @return true if currently playing, false otherwise
     * @throws IllegalStateException if the internal player engine has not been
     *                               initialized or has been released.
     */
    public boolean isPlayerPlaying() {
        return super.isPlaying();
    }


    /**
     * Sets the audio session ID.
     *
     * @param sessionId the audio session ID.
     *                  The audio session ID is a system wide unique identifier for the audio stream played by
     *                  this MediaPlayer instance.
     *                  The primary use of the audio session ID  is to associate audio effects to a particular
     *                  instance of MediaPlayer: if an audio session ID is provided when creating an audio effect,
     *                  this effect will be applied only to the audio content of media players within the same
     *                  audio session and not to the output mix.
     *                  When created, a MediaPlayer instance automatically generates its own audio session ID.
     *                  However, it is possible to force this player to be part of an already existing audio session
     *                  by calling this method.
     *                  This method must be called before one of the overloaded <code> setDataSource </code> methods.
     * @throws IllegalStateException if it is called in an invalid state
     */
    public void setAudioSessionIdPlayer(int sessionId) {
        super.setAudioSessionId(sessionId);
    }


    /**
     * Sets the audio stream type for this MediaPlayer. See {@link AudioManager}
     * for a list of stream types. Must call this method before prepare() or
     * prepareAsync() in order for the target stream type to become effective
     * thereafter.
     *
     * @param streamtype the audio stream type
     * @see android.media.AudioManager
     */
    public void setAudioStreamTypePlayer(int streamtype) {
        super.setAudioStreamType(streamtype);
    }


    /**
     * Sets the audio attributes for this MediaPlayer.
     * See {@link AudioAttributes} for how to build and configure an instance of this class.
     * You must call this method before {@link #prepare()} or {@link #prepareAsync()} in order
     * for the audio attributes to become effective thereafter.
     *
     * @param attributes a non-null set of audio attributes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAudioAttributesPlayer(AudioAttributes attributes) {
        super.setAudioAttributes(attributes);
    }

    /**
     * Sets the send level of the player to the attached auxiliary effect.
     * See {@link #attachAuxEffect(int)}. The level value range is 0 to 1.0.
     * <p>By default the send level is 0, so even if an effect is attached to the player
     * this method must be called for the effect to be applied.
     * <p>Note that the passed level value is a raw scalar. UI controls should be scaled
     * logarithmically: the gain applied by audio framework ranges from -72dB to 0dB,
     * so an appropriate conversion from linear UI input x to level is:
     * x == 0 -> level = 0
     * 0 < x <= R -> level = 10^(72*(x-R)/20/R)
     *
     * @param level send level scalar
     */
    public void setAuxEffectSendLevelPlayer(float level) {
        super.setAuxEffectSendLevel(level);
    }


    /**
     * Sets the player to be looping or non-looping.
     *
     * @param looping whether to loop or not
     */
    public void setLoopingPlayer(boolean looping) {
        super.setLooping(looping);
    }


    /**
     * Sets the volume on this player.
     * This API is recommended for balancing the output of audio streams
     * within an application. Unless you are writing an application to
     * control user settings, this API should be used in preference to
     * {@link AudioManager #setStreamVolume(int, int, int)} which sets the volume of ALL streams of
     * a particular type. Note that the passed volume values are raw scalars in range 0.0 to 1.0.
     * UI controls should be scaled logarithmically.
     *
     * @param left  left volume scalar
     * @param right right volume scalar
     */
    public void setVolumePlayer(float left, float right) {
        super.setVolume(left, right);
    }

}
