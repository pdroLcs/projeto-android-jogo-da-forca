package com.pedroeluiz.projetojogodaforca.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.pedroeluiz.projetojogodaforca.R;

public class SoundManager {

    private static MediaPlayer somVitoria, somDerrota, somProximidade;

    public static void tocarSomVitoria(Context context) {
        somVitoria = MediaPlayer.create(context.getApplicationContext(), R.raw.som_de_vitoria);
        somVitoria.setOnCompletionListener(MediaPlayer::release);
        somVitoria.start();
    }

    public static void tocarSomDerrota(Context context) {
        somDerrota = MediaPlayer.create(context.getApplicationContext(), R.raw.som_de_derrota);
        somDerrota.setOnCompletionListener(MediaPlayer::release);
        somDerrota.start();
    }

    public static void tocarSomProximidade(Context context) {
        somProximidade = MediaPlayer.create(context.getApplicationContext(), R.raw.faaah);
        somProximidade.setOnCompletionListener(MediaPlayer::release);
        somProximidade.start();
    }

}
