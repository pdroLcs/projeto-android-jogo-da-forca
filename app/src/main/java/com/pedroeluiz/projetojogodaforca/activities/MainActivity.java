package com.pedroeluiz.projetojogodaforca.activities;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    private ActivityResultLauncher<String> selecionarImagem = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
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
            selecionarImagem.launch("image/*");
        });
        btnJogar.setOnClickListener(v -> {
            if (etNomeJogador.getText().toString().isBlank()) {
                Toast.makeText(this, "Digite um nome válido", LENGTH_LONG).show();
            } else if (avatarUri == null) {
                mostrarAlertDialog();
            } else {
                iniciarJogo();
            }
        });
    }

    private void mostrarAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Avatar não selecionado")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Deseja iniciar sem um avatar?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        iniciarJogo();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void iniciarJogo() {
        jogador = new Jogador(etNomeJogador.getText().toString().trim(), avatarUri == null ? null : avatarUri.toString());
        intent = new Intent(this, TelaPrincipalActivity.class);
        intent.putExtra("jogador", jogador);
        startActivity(intent);
    }
}