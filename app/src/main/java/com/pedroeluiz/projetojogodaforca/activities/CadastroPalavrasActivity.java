package com.pedroeluiz.projetojogodaforca.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pedroeluiz.projetojogodaforca.R;
import com.pedroeluiz.projetojogodaforca.model.Palavra;
import com.pedroeluiz.projetojogodaforca.repository.PalavraRepository;

import java.util.List;

public class CadastroPalavrasActivity extends AppCompatActivity {

    private EditText etPalavra;
    PalavraRepository palavraRepository;
    List<Palavra> palavras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_palavras);
        setup();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void adicionar(View v) {
        String palavra = etPalavra.getText().toString();
        boolean adicionou = palavraRepository.adicionarPalavra(this, new Palavra(palavra));
        String mensagem = adicionou ? "Palavra adicionada!" : "Palavra já existe!";
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void setup() {
        palavraRepository = new PalavraRepository();
        etPalavra = findViewById(R.id.et_palavra);
        palavras = palavraRepository.carregarPalavras(this);
        Button btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());
    }
}