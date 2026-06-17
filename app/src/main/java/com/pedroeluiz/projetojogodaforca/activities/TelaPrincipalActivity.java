package com.pedroeluiz.projetojogodaforca.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pedroeluiz.projetojogodaforca.R;
import com.pedroeluiz.projetojogodaforca.model.Jogador;

public class TelaPrincipalActivity extends AppCompatActivity {

    Intent intent;
    TextView tvNomeJogador, tvTempo;
    ImageView ivAvatar;
    Uri avatarUri;
    Jogador jogador;
    Thread tempoThread;
    Handler handler;

    private boolean rodando = true;
    private int tempoRestante = 180;
    private long tempoInicial = 0;
    private long tempoCongelado = 0;
    private long tempoEntradoBackground = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);
        setup();
        iniciarCronometro();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void setup() {
        ivAvatar = findViewById(R.id.iv_avatar);
        tvNomeJogador = findViewById(R.id.tv_nomeJogador);
        tvTempo = findViewById(R.id.tv_tempo);
        jogador = (Jogador) getIntent().getSerializableExtra("jogador");
        tvNomeJogador.setText(jogador.getNome());
        handler = new Handler(Looper.getMainLooper());
        if (jogador.getAvatarUri() != null) {
            avatarUri = Uri.parse(jogador.getAvatarUri());
            ivAvatar.setImageURI(avatarUri);
        } else {
            ivAvatar.setImageResource(android.R.drawable.sym_def_app_icon);
        }
    }

    private void iniciarCronometro() {
        tempoInicial = System.currentTimeMillis();
        tempoCongelado = 0;
        tempoRestante = 180;
        rodando = true;
        criarThreadCronometro();
    }

    private void criarThreadCronometro() {
        tempoThread = new Thread(() -> {
            while (rodando && tempoRestante >= 0) {
                long tempoDecorrido = (System.currentTimeMillis() - tempoInicial - tempoCongelado) / 1000;
                int novoTempoRestante = 180 - (int) tempoDecorrido;

                if (novoTempoRestante < 0) {
                    novoTempoRestante = 0;
                }

                tempoRestante = novoTempoRestante;

                int minutos = tempoRestante / 60;
                int segundos = tempoRestante % 60;
                handler.post(() -> tvTempo.setText(String.format("%02d:%02d", minutos, segundos)));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                if (tempoRestante <= 0 && rodando) {
                    rodando = false;
                    handler.post(() ->
                            Toast.makeText(TelaPrincipalActivity.this, "Tempo esgotado!", Toast.LENGTH_SHORT).show());
                    break;
                }
            }
        });
        tempoThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rodando = false;
        tempoEntradoBackground = System.currentTimeMillis();

        if (tempoThread != null) {
            tempoThread.interrupt();
            try {
                tempoThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tempoRestante > 0 && !rodando) {
            if (tempoEntradoBackground > 0) {
                tempoCongelado += System.currentTimeMillis() - tempoEntradoBackground;
                tempoEntradoBackground = 0;
            }
            rodando = true;
            criarThreadCronometro();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rodando = false;
        if (tempoThread != null) {
            tempoThread.interrupt();
        }
    }
}