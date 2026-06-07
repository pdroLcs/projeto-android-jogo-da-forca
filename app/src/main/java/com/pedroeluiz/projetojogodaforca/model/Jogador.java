package com.pedroeluiz.projetojogodaforca.model;

import java.io.Serializable;

public class Jogador implements Serializable {

    private String nome;
    private String avatarUri;
    private int pontos;

    public Jogador(String nome, String avatarUri) {
        this.nome = nome;
        this.avatarUri = avatarUri;
        this.pontos = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
