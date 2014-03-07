
package sample.titoi2.vinclu.sample;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class VincleLed {
    private static final String TAG = VincleLed.class.getSimpleName();

    private static final int SAMPLERATE = 44100;
    private static final int DURATION = 2;
    static VincleLed mVincleLed;
    private AudioTrack mAudioTrack;
    private short[] mBufferLightning;
    private short[] mBufferBlinking;
    private short[] mBufferViolentlyBlinking;

    private VincleLed() {
        mBufferLightning = generateBuffer(100, 100);
        mBufferBlinking = generateBuffer(100, 10);
        mBufferViolentlyBlinking = generateBuffer(100, 1);
    }

    public short[] generateBuffer(double frequencyL, double frequencyR) {
        int SAMPLES = SAMPLERATE * DURATION;
        short[] buffer = new short[SAMPLES * 2]; // 2チャンネル
        for (int i = 0; i < buffer.length; i += 2) {
            double d = 2.0 * Math.PI * i / SAMPLERATE;
            buffer[i] = (short) (Math.sin(d * frequencyL) * Short.MAX_VALUE);
            buffer[i + 1] = (short) (Math.sin(d * frequencyR) * Short.MAX_VALUE * -1);
        }

        return buffer;
    }

    public static VincleLed getInstannce() {
        if (mVincleLed == null) {
            mVincleLed = new VincleLed();
        }
        return mVincleLed;
    }

    private void soundPlay(short[] buf) {
        if (mAudioTrack != null) {
            if (mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED) {
                mAudioTrack.stop();
            }
            mAudioTrack.release();
            mAudioTrack = null;
        }

        // short = 2バイト ｘ 2チャンネル
        int byteLength = buf.length * 2 * 2;

        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                SAMPLERATE,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                byteLength,
                AudioTrack.MODE_STATIC);

        mAudioTrack.reloadStaticData();
        mAudioTrack.write(buf, 0, buf.length);
        mAudioTrack.setLoopPoints(0, buf.length / 2, -1);
        mAudioTrack.play();
    }

    public void lighting() {
        soundPlay(mBufferLightning);
    }

    public void blinking() {
        soundPlay(mBufferBlinking);
    }

    public void violentlyBlinking() {
        soundPlay(mBufferViolentlyBlinking);
    }

    public void stop() {
        if (mAudioTrack == null) {
            return;
        }
        int state = mAudioTrack.getPlayState();
        Log.v(TAG, "off state:" + state);
        if (AudioTrack.PLAYSTATE_STOPPED != state) {

        }
        mAudioTrack.stop();
    }

}
