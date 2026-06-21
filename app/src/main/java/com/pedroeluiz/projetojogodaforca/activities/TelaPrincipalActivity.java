package com.pedroeluiz.projetojogodaforca.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.pedroeluiz.projetojogodaforca.model.Palavra;
import com.pedroeluiz.projetojogodaforca.repository.PalavraRepository;

import java.util.List;
import java.util.Random;

public class TelaPrincipalActivity extends AppCompatActivity {

    Intent intent;
    TextView tvNomeJogador, tvTempo, tvPalavraSelecionada;
    ImageView ivAvatar, ivForca;
    Uri avatarUri;
    Jogador jogador;
    Thread tempoThread;
    Handler handler;
    EditText etLetra;
    Button btnAtualizarForca;

    private boolean rodando = true;
    private int tempoRestante = 180;
    private long tempoInicial = 0;
    private long tempoCongelado = 0;
    private long tempoEntradoBackground = 0;
    private int erros = 0;
    private String palavraSelecionada = "";
    private StringBuilder palavraForca = new StringBuilder();


    private int[] imagens = {
            R.drawable.forca0,
            R.drawable.forca1,
            R.drawable.forca2,
            R.drawable.forca3,
            R.drawable.forca4,
            R.drawable.forca5,
            R.drawable.forca6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);
        setup();
        iniciarCronometro();
        mostrarPalavra();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void setup() {
        etLetra = findViewById(R.id.et_letra);
        btnAtualizarForca = findViewById(R.id.btn_atualizar_forca);
        ivAvatar = findViewById(R.id.iv_avatar);
        ivForca = findViewById(R.id.iv_forca);
        ivForca.setImageResource(imagens[erros]);
        tvNomeJogador = findViewById(R.id.tv_nomeJogador);
        tvPalavraSelecionada = findViewById(R.id.tv_palavra_selecionada);
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

    private String selecionarPalavra() {
        PalavraRepository palavraRepository = new PalavraRepository();
        List<Palavra> palavras = palavraRepository.carregarPalavras(this);
        return palavras.get(new Random().nextInt(palavras.size())).getPalavra();
    }

    private void mostrarPalavra() {
        palavraSelecionada = selecionarPalavra();
        for (int i = 0; i < palavraSelecionada.length(); i++) {
            palavraForca.append('*');
        }
        tvPalavraSelecionada.setText(palavraForca.toString());
    }

    public void atualizarForca(View v) {
        char letra = etLetra.getText().toString().trim().toUpperCase().charAt(0);
        tvPalavraSelecionada.setText(tvPalavraSelecionada.getText().toString());
        if (palavraSelecionada.contains(Character.toString(letra))) {
            for (int i = 0; i < palavraSelecionada.length(); i++) {
                if (palavraSelecionada.charAt(i) == letra) {
                    palavraForca.setCharAt(i, palavraSelecionada.charAt(i));
                } else if (palavraSelecionada.charAt(i) == '*') {
                    palavraForca.setCharAt(i, '*');
                }
            }
            tvPalavraSelecionada.setText(palavraForca);
        } else {
            erros++;
            ivForca.setImageResource(imagens[erros]);
        }
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