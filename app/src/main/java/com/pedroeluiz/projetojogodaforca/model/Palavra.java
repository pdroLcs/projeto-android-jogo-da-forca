package com.pedroeluiz.projetojogodaforca.model;

public class Palavra {

    private String palavra;

    public Palavra(String palavra) {
        this.palavra = palavra;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public Dificuldade getDificuldade() {
        int tamanho = palavra.length();
        if (tamanho <= 4) {
            return Dificuldade.FACIL;
        } else if (tamanho <= 7) {
            return Dificuldade.MEDIO;
        } else {
            return Dificuldade.DIFICIL;
        }
    }
}
