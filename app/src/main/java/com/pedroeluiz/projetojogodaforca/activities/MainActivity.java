package com.pedroeluiz.projetojogodaforca.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pedroeluiz.projetojogodaforca.R;
import com.pedroeluiz.projetojogodaforca.model.Jogador;

public class MainActivity extends AppCompatActivity {

    private Button btnSelecionarAvatar, btnJogar;
    private ImageView ivAvatar;
    private Uri avatarUri;
    private EditText etNomeJogador;
    private Jogador jogador;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setup();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private ActivityResultLauncher<String> selecionarImgagem = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null) {
            avatarUri = uri;
            ivAvatar.setImageURI(uri);
        }
    });

    protected void setup() {
        etNomeJogador = findViewById(R.id.et_nome_jogador);
        btnSelecionarAvatar = findViewById(R.id.btn_selecionar_avatar);
        btnJogar = findViewById(R.id.btn_jogar);
        ivAvatar = findViewById(R.id.iv_selecionar_avatar);
        btnSelecionarAvatar.setOnClickListener(v -> {
            selecionarImgagem.launch("image/*");
        });
        btnJogar.setOnClickListener(v -> {
            jogador = new Jogador(etNomeJogador.getText().toString(), avatarUri.toString());
            intent = new Intent(this, TelaPrincipalActivity.class);
            intent.putExtra("jogador", jogador);
            startActivity(intent);
        });
    }
}