package com.e.szerepjatek

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager

import android.media.SoundPool
import com.e.datalayer.Music


class AudioPlayer(mainActivity: MainActivity) {
    private var mainActivity: MainActivity
    var soundPool: SoundPool
    private var audioManager: AudioManager
    private val MAX_STREAMS = 5
    private var streamType = AudioManager.STREAM_MUSIC

    private var loaded = false
    private var soundIdBeep2 = 0
    private var soundIdBeep3 = 0
    private var volume = 0f

    init{
        this.mainActivity = mainActivity

        // AudioManager audio settings for adjusting the volume
        audioManager = mainActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Current volumn Index of particular stream type.
        val currentVolumeIndex =
            audioManager.getStreamVolume(streamType).toFloat()

        // Get the maximum volume index for a particular stream type.
        val maxVolumeIndex =
            audioManager.getStreamMaxVolume(streamType).toFloat()

        // Volumn (0 --> 1)
        volume = currentVolumeIndex / maxVolumeIndex

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        mainActivity.volumeControlStream = streamType

        var audioAttrib = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        var builder = SoundPool.Builder()
        builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS)

        soundPool = builder.build()

        // When Sound Pool load complete.
        soundPool.setOnLoadCompleteListener { _soundPool, _sampleId, _status -> loaded = true }

        // Load sound file (beep2.wav) into SoundPool.
        soundIdBeep2 = soundPool.load(this.mainActivity as Context, R.raw.beep2,1);
        soundIdBeep3 = soundPool.load(this.mainActivity as Context, R.raw.beep3,2);
    }

    fun play(music: Music){
        if(loaded)  {
            var leftVolumn = volume
            var rightVolumn = volume
            var streamId: Int = 0

            when(music){
                Music.Beep2 -> streamId =
                soundPool.play(soundIdBeep2, leftVolumn, rightVolumn, 1, 0, 1f)
                Music.Beep3 -> streamId =
                    soundPool.play(soundIdBeep3, leftVolumn, rightVolumn, 1, 0, 1f)
            }
        }



    }




}