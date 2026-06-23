package com.pedroeluiz.projetojogodaforca.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedroeluiz.projetojogodaforca.R;

public class InfoJogoFragment extends Fragment {

    TextView tvTempo, tvCategoria, tvPontos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_jogo, container, false);

        tvTempo = view.findViewById(R.id.tv_tempo);
        tvCategoria = view.findViewById(R.id.tv_categoria);
        tvPontos = view.findViewById(R.id.tv_pontos);

        return view;
    }

    public void atualizarInfo(String categoria, String tempo, int pontos) {
        if (tvCategoria == null) {
            return;
        }
        tvCategoria.setText("Categoria: " + categoria);
        tvPontos.setText("Pontos: " + pontos);
        tvTempo.setText(tempo);
    }
}