package com.pedroeluiz.projetojogodaforca.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pedroeluiz.projetojogodaforca.R;
import com.pedroeluiz.projetojogodaforca.model.Jogador;

public class TelaPrincipalActivity extends AppCompatActivity {

    Intent intent;
    TextView tvNomeJogador;
    ImageView ivAvatar;
    String nomeJogador;
    Uri avatarUri;
    Jogador jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_principal);
        setup();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    protected void setup() {
        jogador = (Jogador) getIntent().getSerializableExtra("jogador");
        nomeJogador = jogador.getNome();
        avatarUri = Uri.parse(jogador.getAvatarUri());
        tvNomeJogador = findViewById(R.id.tv_nomeJogador);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvNomeJogador.setText(nomeJogador);
        ivAvatar.setImageURI(avatarUri);
    }
}