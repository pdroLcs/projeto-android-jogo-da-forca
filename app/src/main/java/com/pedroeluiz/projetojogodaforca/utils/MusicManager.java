package com.pedroeluiz.projetojogodaforca.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.pedroeluiz.projetojogodaforca.R;

public class MusicManager {

    private static MediaPlayer mediaPlayer;
    private static int activitiesAbertas = 0;

    public static void start(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.musica_principal);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void activityStarted() {
        activitiesAbertas++;
        if (activitiesAbertas == 1) {
            resume();
        }
    }

    public static void activityStopped() {
        activitiesAbertas--;
        if (activitiesAbertas == 0) {
            pause();
        }
    }

}
