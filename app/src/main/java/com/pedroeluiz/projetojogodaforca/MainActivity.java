package com.pedroeluiz.projetojogodaforca;

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

public class MainActivity extends AppCompatActivity {

    private Button btnSelecionarAvatar, btnJogar;
    private ImageView ivAvatar;
    private Uri avatarUri;
    private EditText etNomeJogador;

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
            intent = new Intent(this, TelaPrincipalActivity.class);
            intent.putExtra("nomeJogador", String.valueOf(etNomeJogador.getText()));
            intent.putExtra("avatarUri", avatarUri);
            startActivity(intent);
        });
    }
}